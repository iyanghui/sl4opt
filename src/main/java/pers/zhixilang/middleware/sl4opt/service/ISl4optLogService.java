package pers.zhixilang.middleware.sl4opt.service;

import pers.zhixilang.middleware.sl4opt.pojo.OptLog;

/**
 * log归档相关接口
 * @author zhixilang
 * @version 1.0.0
 * date 2021-10-24 23:07
 */
public interface ISl4optLogService {
    /**
     * log归档
     * @param optLog log
     */
    void archive(OptLog optLog);
}
