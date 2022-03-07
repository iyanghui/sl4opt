package pers.zhixilang.middleware.sl4opt.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.DefaultParameterNameDiscoverer;
import pers.zhixilang.middleware.sl4opt.annotation.Sl4opt;
import pers.zhixilang.middleware.sl4opt.constants.Result;
import pers.zhixilang.middleware.sl4opt.core.Sl4optContext;
import pers.zhixilang.middleware.sl4opt.core.Sl4optParser;
import pers.zhixilang.middleware.sl4opt.pojo.OptLogTemplate;
import pers.zhixilang.middleware.sl4opt.pojo.OptLog;
import pers.zhixilang.middleware.sl4opt.service.ILogService;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * @author zhixilang
 * @version 1.0.0
 * date 2021-10-24 23:06
 */
@Aspect
public class Sl4optAspect {

    private static final DefaultParameterNameDiscoverer DISCOVERER = new DefaultParameterNameDiscoverer();

    @Resource
    private Sl4optParser sl4OptParser;

    @Resource
    private ILogService logService;

    /**
     * sl4opt切面
     * @param joinPoint joinPoint
     * @return res
     * @throws Throwable
     */
    @Around("@annotation(pers.zhixilang.middleware.sl4opt.annotation.Sl4opt)")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Sl4opt sl4opt = method.getAnnotation(Sl4opt.class);

        registryParameters(method, joinPoint.getArgs());

        OptLogTemplate optLogTemplate = buildLogEvent(sl4opt);

        long sTime = System.currentTimeMillis();
        Sl4optContext.putStime(sTime);

        Object res;
        try {
            res = joinPoint.proceed();

            Sl4optContext.putRes(res);
            optLogTemplate.setResult(Result.SUCCESS);

        } catch (Throwable throwable) {
            Sl4optContext.putErr(throwable.getMessage());
            optLogTemplate.setResult(Result.SUCCESS);

            throw throwable;
        }  finally {
            long eTime = System.currentTimeMillis();
            Sl4optContext.putEtime(eTime);
            Sl4optContext.putTime(eTime - sTime);

            parseAndArchive(optLogTemplate);

            Sl4optContext.clearContext();
        }

        return res;
    }

    /**
     * 填充参数
     * @param sl4opt 核心注解{@code sl4opt}
     * @return LogEvent
     */
    private OptLogTemplate buildLogEvent(Sl4opt sl4opt) {
        OptLogTemplate optLogTemplate = new OptLogTemplate();
        optLogTemplate.setSuccess(sl4opt.success());
        optLogTemplate.setFail(sl4opt.fail());
        optLogTemplate.setBizType(sl4opt.bizType());
        optLogTemplate.setOperator(sl4opt.operator());
        return optLogTemplate;
    }

    /**
     * 参数registry
     * @param method 目标方法
     * @param args 入参
     */
    private void registryParameters(Method method, Object[] args) {
        Object[] parameters = DISCOVERER.getParameterNames(method);
        if (null == parameters) {
            return;
        }

        for (int i = 0; i < parameters.length; i++) {
            Sl4optContext.putVariable((String) parameters[i], args[i]);
        }
    }

    private void parseAndArchive(OptLogTemplate optLogTemplate) {
        OptLog optLog = sl4OptParser.parse(optLogTemplate);
        logService.archive(optLog);
    }
}
