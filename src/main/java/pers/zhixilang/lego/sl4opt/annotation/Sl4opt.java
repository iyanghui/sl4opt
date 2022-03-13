package pers.zhixilang.lego.sl4opt.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * sl4opt核心注解
 * @author zhixilang
 * @version 1.0.0
 * date 2021-10-24 23:06
 */
@Repeatable(Sl4opts.class)
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Sl4opt {
    /**
     * 成功模板,支持模板
     * @return 模板
     */
    String success();

    /**
     * 失败信息,支持模板
     * @return 模板
     */
    String fail() default "";

    /**
     * 业务类型,支持模板
     * @return 类型
     */
    String bizType() default "";

    /**
     * 操作员对象信息,支持模板
     * @return 对象信息
     */
    String operator() default "";
}
