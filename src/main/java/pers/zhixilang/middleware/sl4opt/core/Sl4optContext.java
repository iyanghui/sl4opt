package pers.zhixilang.middleware.sl4opt.core;

import java.util.HashMap;
import java.util.Map;

/**
 * sl4opt执行上下文,存储Expression过程数据
 * @author zhixilang
 * @version 1.0.0
 * date 2022-03-05 20:27
 */
public class Sl4optContext {

    private static final ThreadLocal<Map<String, Object>> THREAD_LOCAL = new ThreadLocal<Map<String, Object>>(){
        @Override
        protected Map<String, Object> initialValue() {
            return new HashMap<>(10);
        }
    };

    /**
     * 增加variable
     * @param key key
     * @param value value
     */
    public static void putVariable(String key, Object value) {
        Sl4optContext.THREAD_LOCAL.get().put(key, value);
    }

    public static Map<String, Object> getVariables() {
        return Sl4optContext.THREAD_LOCAL.get();
    }

    public static Object getVariable(String key) {
        return Sl4optContext.THREAD_LOCAL.get().get(key);
    }

    public static void clear() {
        Sl4optContext.THREAD_LOCAL.get().clear();
        Sl4optContext.THREAD_LOCAL.remove();
    }
}
