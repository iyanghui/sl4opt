package pers.zhixilang.lego.sl4opt;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pers.zhixilang.lego.sl4opt.core.function.ISl4optFunction;
import pers.zhixilang.lego.sl4opt.core.function.Sl4optFunctionFactory;
import pers.zhixilang.lego.sl4opt.service.ISl4optLogService;
import pers.zhixilang.lego.sl4opt.service.ISl4optOperatorService;
import pers.zhixilang.lego.sl4opt.service.impl.DefaultLogServiceImpl;
import pers.zhixilang.lego.sl4opt.service.impl.DefaultOperatorServiceImpl;
import pers.zhixilang.lego.sl4opt.aop.Sl4optAspect;
import pers.zhixilang.lego.sl4opt.core.Sl4optParser;

import java.util.List;

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
    @ConditionalOnMissingBean(ISl4optOperatorService.class)
    public ISl4optOperatorService operatorService() {
        return new DefaultOperatorServiceImpl();
    }

    @Bean("sl4optLogService")
    @ConditionalOnMissingBean(ISl4optLogService.class)
    public ISl4optLogService logService() {
        return new DefaultLogServiceImpl();
    }

    @Bean
    public Sl4optFunctionFactory sl4optFunctionFactory(List<ISl4optFunction> functions) {
        return new Sl4optFunctionFactory(functions);
    }

    @Bean
    public Sl4optParser sl4optExpressionParser(ISl4optOperatorService operatorService) {
        return new Sl4optParser();
    }
}
