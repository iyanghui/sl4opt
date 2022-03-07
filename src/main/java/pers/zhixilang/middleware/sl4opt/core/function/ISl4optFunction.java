package pers.zhixilang.middleware.sl4opt.core.function;

/**
 * 自定义function接口
 * @author zhixilang
 * @version 1.0.0
 * date 2022-03-05 21:07
 */
public interface ISl4optFunction {
    /**
     * function名称
     * @return name
     */
    String name();

    /**
     * function实现
     * @param args function入参
     * @return parsed string
     */
    String apply(Object[] args);
}
