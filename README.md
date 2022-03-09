> 本项目的实现思路来源于美团技术团队文章[如何优雅地记录操作日志？](https://tech.meituan.com/2021/09/16/operational-logbook.html)
>

# 项目介绍
`Simple-Logging-For-Operate`(下称`sl4opt`)，一个支持灵活配置操作日志记录小工具。

**为什么要开发`sl4opt`?**

常规项目在研发阶段通过都会设计**操作日志**这样一个小功能来处理业务操作的归档，而实现上一般是采用**日志切面**来做统一拦截和记录，差不多类似下面这样:

首先提供`@Log`注解，业务按需配置到方法上，支持(固定)配置多个参数；

然后定义`Aspect`类，对使用注解的方法进行解析，获取配置的参数及入口信息

```java
@Aspect
public class LogAspect {
    @Around("@annotation(xx.xx.Log)")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        // ...省略
        Log log = xxx.getAnnotation(Log.class);
        // ...省略
        HttpServletRequest request = xxx.getRequest();

        OperLog operLog = new OperLog();
        // 记录请求信息
        operLog.setOperIp(request.xxxip);
        operLog.setOperUrl(request.getRequestURI());
        operLog.setRequestMethod(request.getMethod());
        // 记录注解携带信息
        operLog.setType(log.type());
        // 记录调用类、方法、入参...
        try {
        	return joinPoint.proceed();
        } catch(Throwable throwable) {
	        operLog.setStatus(FAIL);
            // 记录出错信息...
        }  finally {
            // 保存日志...
        }
    }
}
```

可以看到这种方式下所记录的信息有很强烈的“程序员味道”，因为里面的内容大多是编写代码这个层面所赋予的，不具备易读性。

在这种情况下，如果有非研发人员（比如运营或客户）有日志查看的需求，这时期望的日志内容自然得是自然语言；或者需要在日志中加一些衍生的业务信息时。就必须在业务逻辑中额外的进行硬编码，针对每条日志入口做调整，实现上不够灵活。久而久之，真正的业务逻辑会慢慢淹没在繁复的日志记录逻辑中，阅读体验大大降低。

**`sl4opt`是怎么做的?**

先通过一个示例来看看借助`sl4opt`是如何实现日志配置及搜集的，对其先有个简单认识。
```java
    @Sl4opt(success = "新增用户「@#person.name@」成功, ID=「@#_res.id@」 自动生成昵称「@nick_func#person.name,#person.age@」,附加信息「@#extra@」",
            bizType = "1",
            fail = "新增用户「@#person.name@」失败：@#_err@")
    public Person createPerson(Person person) {
        if (person.getAge() <= 0) {
            throw new RuntimeException("age不合法");
        }
        String id = UUID.randomUUID().toString();
        person.setId(id);
        
        Sl4optContext.putVariable("extra", "ok");
        return person;
    }

/**
 * pojo定义
 */
public class Person {

    private String id;

    private String name;

    private Integer age;
}
```

> **配置解释**
>
> `#person.name`和`#person.age`是方法入参`Person`里的字段；
>
> `nick_func`是一个自定义函数，其中入参为`name`和`age`；
>
> `#_res`是方法执行完成后的返回值(这里是`Person`对象)，`_res.id`取字段`id`的值；
>
> `#_err`是方法执行出错时抛出的信息；
>
> `#extra`是业务方自己设置的变量。
>
> **日志内容**
>
> - 传入`Person("hello", 18)`，**执行成功**输出：`method execute success=> OptLog{result=SUCCESS, content='新增用户「hello」成功, ID=「0ff467b8-c38e-4654-b7b7-2f421b4d41f7」 自动生成昵称「nick_[hello, 18]」,附加信息「ok」', time=1646812671149, bizType='1', operator='ADMINISTRATOR'}`
> - 传入`Person("hello", 0)`，**执行失败**输出：`method execute fail=> OptLog{result=FAIL, content='新增用户「hello」失败：age不合法', time=1646812903215, bizType='1', operator='ADMINISTRATOR'}`
>
> 关于`bizType`和`operator`在之后做介绍。

可以看到`sl4opt`在使用上和上述方案并没什么不同，同样是提供一个注解，业务方在目标方法进行引用，之后再由`Sl4optAspect`完成日志搜集，在这里业务方可通过实现暴露的日志接口完成日志归档（默认使用`slf4j`进行输出）。

主要需要关注的点在注解参数的配置上：为了实现灵活记录自定义日志内容，`@Sl4opt`可以使用模板表达式进行配置，提供**常规表达式**、**自定义变量**、**全局变量**和**自定义函数**支持。

下面将详细介绍如何接入和使用`sl4opt`。
# 如何使用
## 前期准备
### 添加`Maven`依赖
由于`sl4opt`还没有发布到公共的`Maven`仓库，所以业务方需要手动下载后 -> `maven clear install`到本地仓库后进行集成。

### 使配置生效
`sl4opt`在被引入后默认是不开启状态，需要在项目启动类手动引入`@EnableSl4opt`使其生效。
```java
@SpringBootApplication
@EnableSl4opt
public class Application {
    public static void main(String[] args) {
        new SpringApplication(Application.class).run(args);
    }
}
```

## 核心内容介绍
### `@Sl4opt`
`@Sl4opt`是该工具的核心注解，同时也是业务方在使用过程中接触最为频繁的。它提供以下几个参数：
- `success`，成功模板(必填)，方法执行成功的话会使用该模板作为操作详情进行记录
-  `fail`: 失败模板(非必填)，方法执行出错的话会使用该模板作为操作详情进行记录
- `operator`: 操作对象(非必填)，有两种配置方式：通过模板表达式进行配置；通过实现`sl4opt`提供的接口来完成获取
- `bizType`:  业务类型(非必填)，常规的扩展字段，支持模板表达式配置，默认为空。

### 接口扩展
`sl4opt`在日志的搜集和归档阶段分别提供了一个扩展接口，业务可以通过实现暴露的接口来完成扩展。

- **操作对象相关**
```java
public interface ISl4optOperatorService {
    /**
     * 获取当前操作对象信息
     * @return 操作对象信息
     */
    String currentOperator();
}
```
这个接口在上面介绍`@Sl4opt`时已经做了个简单说明，功能就是获取当前操作对象，当`@Sl4opt`未配置`operator`时，将会调用该接口获取操作对象。

`sl4opt`对该接口进行了默认实现 => 返回`ADMINISTRATOR`。

- **日志相关**
```java
public interface ISl4optLogService {
    /**
     * log归档
     * @param optLog log
     */
    void archive(OptLog optLog);
}
```
这个接口的功能是做日志的归档，日志解析完成后会调用该方法传入`OptLog`对象。

`sl4opt`同样对该接口进行了默认实现 => 使用`slf4j`对其进行输出。

## 业务使用
**!!!模板必须被@包围**

### 普通表达式
```java
@Sl4opt(success = "hello @#name@", bizType = "1")
public void createUser(String name, Integer age) {

}
```
当有使用方法入参值的时候，可直接在模板表达式中配置该参数名，然后在参数名称前面加上"#"即可完成获取。

```java
@Sl4opt(success = "新增用户「@#person.name@」成功", bizType = "1")
public void createPerson(Person person) {
    
}
```
和获取普通入参类似，当入参是`pojo`时，可以通过定位到对象字段名来进行获取。

### 自定义变量
由于``sl4opt`的实现原理是基于[SpEL](https://docs.spring.io/spring-framework/docs/3.0.x/reference/expressions.html)，所以也支持自定义变量（上下文传递）的设置和获取：
```java
@Sl4opt(success = "hello @#name@", bizType = "1",
        fail = "失败: @#_err@")
public void print() {
    Sl4optContext.putVariable("name", "hello");
}
```
另外自定义的变量也可以是`pojo`，使用上和普通表达式一样。

### 自定义`function`
`sl4opt`支持**自定义`function`**的调用，其中对参数没有数量限制(多个参数中间使用英文”,“进行分隔），同时入参也**支持表达式和变量引用**。

首先通过实现`ISl4optFunction`完成自定义功能扩展:
```java
@Component
public class NickGenFunction implements ISl4optFunction {

    @Override
    public String name() {
        return "nick_func";
    }

    @Override
    public String apply(Object[] args) {
        if (null == args) {
            return "non-args";
        }
        return "nick_" + Arrays.toString(args);
    }
}
```
> 必须实现`name`方法，`sl4opt`通过返回的名称来进行函数定位。
> 
> 必须将实现的`function`交给`Spring IoC Context`进行管理

```java
@Sl4opt(success = "新增用户「@#person.name@」成功, 自动生成昵称「@nick_func#person.name,#person.age@",
        bizType = "1")
public void createPerson(Person person) {
}
```
自定义函数的使用与普通表达式的区别就是**在@和#之间加上了函数了名称**。

### 全局变量
考虑到方法执行中都会产生一些过程及结果信息，为了避免在使用时对这部分变量的重复定义，`sl4opt`将这些信息变量进行了预置，业务方可直接在模板中进行引用，使用上和自定义变量完全一致。

| 变量name | 说明 | 生效范围 |
| --- | --- | --- |
| _res | 方法执行返回结果 | 只有成功时才有值 |
| _err | 方法执行出错信息 | 只有失败时才有值|
| _time | 方法执行耗时(单位: ms) | 全局有效 |
| _stime | 方法执行前时间戳(timestamp) | 全局有效 |
| _etime | 方法执行结束时时间戳(timestamp) | 全局有效 |

> 使用时要注意变量的生效范围
>
```java
@Sl4opt(success = "执行返回=@#_res@; 开始时间=@#_stime@, 结束时间=@#_etime@; 耗时=@#_time@")
public String globalVariables() {
    return "global variables";
}
```
如果`#_res`是`pojo`，则同样可以通过`#_res.xx`来完成具体字段值的获取。

