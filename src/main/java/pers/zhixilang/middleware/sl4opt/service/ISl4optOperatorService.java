package pers.zhixilang.middleware.sl4opt.service;

/**
 * 操作员相关接口
 * @author zhixilang
 * @version 1.0.0
 * date 2021-10-24 23:07
 */
public interface ISl4optOperatorService {
    /**
     * 获取当前操作对象信息
     * @return 操作对象信息
     */
    String currentOperator();
}
