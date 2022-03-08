package pers.zhixilang.lego.sl4opt.annotation;

import org.springframework.context.annotation.Import;
import pers.zhixilang.lego.sl4opt.Sl4optAutoConfiguration;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * sl4opt核心注解,使用则启用
 * @author zhixilang
 * @version 1.0.0
 * date 2022-03-06 17:56
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(Sl4optAutoConfiguration.class)
public @interface EnableSl4opt {
}
