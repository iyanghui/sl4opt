package pers.zhixilang.middleware.sl4opt;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pers.zhixilang.middleware.sl4opt.aop.Sl4optAspect;
import pers.zhixilang.middleware.sl4opt.core.Sl4optParser;
import pers.zhixilang.middleware.sl4opt.service.ILogService;
import pers.zhixilang.middleware.sl4opt.service.IOperatorService;
import pers.zhixilang.middleware.sl4opt.service.impl.Sl4optLogServiceImpl;
import pers.zhixilang.middleware.sl4opt.service.impl.Sl4optorServiceImpl;

/**
 * @author zhixilang
 * @version 1.0.0
 * date 2022-03-06 17:54
 */
@Configuration
public class Sl4optAutoConfiguration {

    @Bean
    public Sl4optAspect logPointAspect() {
        return new Sl4optAspect();
    }

    @Bean("sl4optOperatorService")
    @ConditionalOnMissingBean(IOperatorService.class)
    public IOperatorService operatorService() {
        return new Sl4optorServiceImpl();
    }

    @Bean("sl4optLogService")
    @ConditionalOnMissingBean(ILogService.class)
    public ILogService logService() {
        return new Sl4optLogServiceImpl();
    }

    @Bean
    public Sl4optParser sl4optExpressionParser(IOperatorService operatorService) {
        return new Sl4optParser(operatorService);
    }
}
