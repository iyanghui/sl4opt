package pers.zhixilang.middleware.sl4opt.service;

import pers.zhixilang.middleware.sl4opt.annotation.Sl4opt;

/**
 * @author zhixilang
 * @version 1.0.0
 * date 2022-03-06 15:11
 */
public class ParameterService {

    @Sl4opt(success = "hello @#name@", bizType = "1", fail = "失败: @#_err@")
    public String print(String name, Integer age) {
        return "from non-static method => name: " + name + "; age:" + age;
    }
}
