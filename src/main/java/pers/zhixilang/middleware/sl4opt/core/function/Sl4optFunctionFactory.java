package pers.zhixilang.middleware.sl4opt.core.function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.zhixilang.middleware.sl4opt.core.parser.FunctionParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * function工厂
 * @author zhixilang
 * @version 1.0.0
 * date 2022-03-05 21:09
 */
public class Sl4optFunctionFactory {

    private static final Logger logger = LoggerFactory.getLogger("sl4opt");

    private static Map<String, ISl4optFunction> functionMap = new HashMap<>(0);

    public Sl4optFunctionFactory(List<ISl4optFunction> functions) {
        FunctionParser.injectFactory(this);

        if (functions.size() == 0) {
            return;
        }

        // avoid HashMap resize, 1/0.75F ≈ 1.5
        functionMap = new HashMap<>((int) Math.ceil(functions.size() * 1.5));
        for (ISl4optFunction function: functions) {
            if (functionMap.containsKey(function.name())) {
                logger.error("duplicate function「" + function.name() + "」 found, will be covered.");
            }

            functionMap.put(function.name(), function);
        }
    }

    /**
     * 获取自定义方法
     * @param name 名字
     * @return 方法实现
     */
    public ISl4optFunction getFunction(String name) {
        return functionMap.get(name);
    }
}
