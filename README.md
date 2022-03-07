> 本项目的实现思路来源于美团技术团队文章[如何优雅地记录操作日志？](https://tech.meituan.com/2021/09/16/operational-logbook.html)
>

# 项目介绍
`Simple-Logging-For-Operate`(下称`sl4opt`)，一个用于快捷记录操作日志的小工具.

## 适用场景


# 接入步骤
## `Maven`引入
- [ ] 注册maven repo

## 使配置生效

## 自定义函数

## 目标方法使用

### 普通表达式

### 自定义变量

### 函数使用

### 引用全局变量
> `sl4opt`支持的完整变量见#附录##全局变量

# 附录
## 全局变量
为了能给业务提供尽可能多的过程及结果信息，同时还保持灵活性，`sl4opt`预置了下列变量，为业务提供尽可能多的过程信息，模板中直接引用后即可。

| 变量name | 说明 |
| --- | --- |
| _ret | 方法执行返回结果，只有成功时才有值 |
| _err | 方法执行出错信息，只有失败时才有值 |
| _time | 方法执行耗时(单位: ms) |
| _stime | 方法执行前时间戳(timestamp) |
| _etime | 方法执行结束时时间戳(timestamp) |

# 待实现内容
- [ ] log归档对接HTTP调用
- [ ] log归档对接消息队列(暂定rabbitMQ)
