package pers.zhixilang.middleware.sl4opt.function;

import pers.zhixilang.middleware.sl4opt.core.function.ISl4optFunction;

import java.util.Arrays;

/**
 * @author zhixilang
 * @version 1.0.0
 * date 2022-03-07 21:45
 */
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
