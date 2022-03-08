package pers.zhixilang.lego.sl4opt.function;

import pers.zhixilang.lego.sl4opt.core.function.ISl4optFunction;

import java.util.UUID;

/**
 * @author zhixilang
 * @version 1.0.0
 * date 2022-03-07 21:45
 */
public class IdGenFunction implements ISl4optFunction {
    @Override
    public String name() {
        return "id_func";
    }

    @Override
    public String apply(Object[] args) {
        return UUID.randomUUID().toString();
    }
}
