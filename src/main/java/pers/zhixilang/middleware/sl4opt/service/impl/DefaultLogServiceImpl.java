package pers.zhixilang.middleware.sl4opt.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.zhixilang.middleware.sl4opt.constants.Result;
import pers.zhixilang.middleware.sl4opt.pojo.OptLog;
import pers.zhixilang.middleware.sl4opt.service.ISl4optLogService;

/**
 * log service默认实现 =>由项目log工具类输出
 *
 * @author zhixilang
 * @version 1.0.0
 * date 2022-03-06 17:27
 */
public class DefaultLogServiceImpl implements ISl4optLogService {

    private static final Logger logger = LoggerFactory.getLogger("sl4opt");

    @Override
    public void archive(OptLog optLog) {
        if (Result.SUCCESS.equals(optLog.getResult())) {
            logger.info("method execute success=> {}", optLog);
        } else  {
            logger.error("method execute fail=> {}", optLog);
        }
    }
}
