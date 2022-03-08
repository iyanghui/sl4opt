package pers.zhixilang.lego.sl4opt.core.parser;

import org.springframework.util.StringUtils;
import pers.zhixilang.lego.sl4opt.core.function.ISl4optFunction;
import pers.zhixilang.lego.sl4opt.core.function.Sl4optFunctionFactory;
import pers.zhixilang.lego.sl4opt.exception.Sl4optException;

import java.util.Arrays;

/**
 * 表达式解析.function解析
 * @author zhixilang
 * @version 1.0.0
 * date 2022-03-05 21:21
 */
public class FunctionParser {

    /**
     * 参数分隔符
     */
    private static final String PARAMETER_SEPARATOR = ",";

    /**
     * 函数factory
     */
    private static Sl4optFunctionFactory functionFactory;

    /**
     * factory注入
     * @param sl4optFunctionFactory Sl4optFunctionFactory
     */
    public static void injectFactory(Sl4optFunctionFactory sl4optFunctionFactory) {
        functionFactory = sl4optFunctionFactory;
    }

    /**
     * 模板解析
     * @param functionName 函数名称
     * @param parameters 函数入参
     * @return 解析后值
     */
    public static String parse(String functionName, String parameters) {
        Object[] args = null;
        if (!StringUtils.isEmpty(parameters)) {
            String[] parameterExpressions = parameters.split(PARAMETER_SEPARATOR);
            args = new Object[parameterExpressions.length];

            for (int i = 0; i < parameterExpressions.length; i++) {
                args[i] = ValueParser.parse(parameterExpressions[i]);
            }
        }


        ISl4optFunction function = functionFactory.getFunction(functionName);
        if (null == function) {
            throw new Sl4optException(functionName + "(" + parameters + ")",
                    "function apply error=> function「"+ functionName +"」 not exists.");
        }

        try {
            return function.apply(args);
        } catch (Exception e) {
            throw new Sl4optException(functionName + "(" + parameters + ")",
                    "function apply error=> args「" + Arrays.toString(args) + "」",
                    e);
        }
    }
}
