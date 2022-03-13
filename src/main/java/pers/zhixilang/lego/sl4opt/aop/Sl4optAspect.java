package pers.zhixilang.lego.sl4opt.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.util.StringUtils;
import pers.zhixilang.lego.sl4opt.annotation.Sl4opt;
import pers.zhixilang.lego.sl4opt.constants.Result;
import pers.zhixilang.lego.sl4opt.core.Sl4optContext;
import pers.zhixilang.lego.sl4opt.core.Sl4optParser;
import pers.zhixilang.lego.sl4opt.pojo.OptLog;
import pers.zhixilang.lego.sl4opt.pojo.OptLogTemplate;
import pers.zhixilang.lego.sl4opt.service.ISl4optLogService;
import pers.zhixilang.lego.sl4opt.service.ISl4optOperatorService;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private ISl4optLogService logService;

    @Resource
    private ISl4optOperatorService operatorService;

    /**
     * sl4opt切面
     * @param joinPoint joinPoint
     * @return res
     * @throws Throwable
     */
    @Around("@annotation(pers.zhixilang.lego.sl4opt.annotation.Sl4opt) || @annotation(pers.zhixilang.lego.sl4opt" +
            ".annotation.Sl4opts)")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();

        registryMethodParameters(method, joinPoint.getArgs());

        Sl4opt[] sl4opts = method.getAnnotationsByType(Sl4opt.class);
        List<OptLogTemplate> templates = new ArrayList<>();
        for (Sl4opt sl4opt: sl4opts) {
            templates.add(buildLogTemplate(sl4opt));
        }

        long sTime = System.currentTimeMillis();
        Sl4optContext.putStime(sTime);

        Result result = Result.SUCCESS;
        try {
            Object res = joinPoint.proceed();

            Sl4optContext.putRes(res);
            return res;
        } catch (Throwable throwable) {
            result = Result.FAIL;
            Sl4optContext.putErr(throwable.getMessage());

            throw throwable;
        }  finally {
            long eTime = System.currentTimeMillis();
            Sl4optContext.putEtime(eTime);
            Sl4optContext.putTime(eTime - sTime);

            try {
                parseAndArchive(templates, result);
            } finally {
                Sl4optContext.clearContext();
            }
        }
    }

    /**
     * 填充参数
     * @param sl4opt 核心注解{@code sl4opt}
     * @return LogEvent
     */
    private OptLogTemplate buildLogTemplate(Sl4opt sl4opt) {
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
    private void registryMethodParameters(Method method, Object[] args) {
        Object[] parameters = DISCOVERER.getParameterNames(method);
        if (null == parameters) {
            return;
        }

        for (int i = 0; i < parameters.length; i++) {
            Sl4optContext.putVariable((String) parameters[i], args[i]);
        }
    }

    /**
     * 解析template并归档
     * @param templates 原始template集合
     * @param result 方法执行状态
     */
    private void parseAndArchive(List<OptLogTemplate> templates, Result result) {
        for (OptLogTemplate optLogTemplate: templates) {
            OptLog optLog = new OptLog().setResult(result)
                    .setTime(System.currentTimeMillis());

            // build expression map for parsing
            Map<String, String> expressionMap = new HashMap<>(4);
            expressionMap.put(getContentFromTemplate(optLogTemplate, result), null);
            expressionMap.put(optLogTemplate.getBizType(), null);
            String operator = getOperatorOrPut(optLogTemplate.getOperator(), expressionMap);

            sl4OptParser.parse(expressionMap);

            operator = (operator == null) ? expressionMap.get(optLogTemplate.getOperator()) : operator;
            optLog.setOperator(operator);
            optLog.setContent(expressionMap.get(getContentFromTemplate(optLogTemplate, result)));
            optLog.setBizType(expressionMap.get(optLogTemplate.getBizType()));

            logService.archive(optLog);
        }
    }


    /**
     * 获取operator
     * 1. 从service获取
     * 2. 自定义
     * @param operatorStr @sl4opt.operator
     * @param expressionMap expression map
     * @return operator
     */
    private String getOperatorOrPut(String operatorStr, Map<String, String> expressionMap) {
        if (StringUtils.isEmpty(operatorStr)) {
            return operatorService.currentOperator();
        }
        expressionMap.put(operatorStr, null);
        return null;
    }

    private String getContentFromTemplate(OptLogTemplate template, Result result) {
        return Result.SUCCESS.equals(result) ? template.getSuccess() : template.getFail();
    }
}
