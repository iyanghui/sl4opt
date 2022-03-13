package pers.zhixilang.lego.sl4opt.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * sl4opt核心注解
 * @author zhixilang
 * @version 1.0.0
 * date 2021-10-24 23:06
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Sl4opts {

    Sl4opt[] value();
}
