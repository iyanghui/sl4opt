package pers.zhixilang.middleware.sl4opt.service.impl;

import pers.zhixilang.middleware.sl4opt.service.ISl4optOperatorService;

/**
 * 操作员service默认实现
 *
 * @author zhixilang
 * @version 1.0.0
 * date 2022-03-06 17:42
 */
public class DefaultOperatorServiceImpl implements ISl4optOperatorService {
    @Override
    public String currentOperator() {
        return "ADMINISTRATOR";
    }
}
