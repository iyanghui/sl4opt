package pers.zhixilang.lego.sl4opt.core;

import org.springframework.expression.spel.support.StandardEvaluationContext;
import pers.zhixilang.lego.sl4opt.constants.Sl4optVariables;

/**
 * sl4opt执行上下文,存储Expression过程数据
 * @author zhixilang
 * @version 1.0.0
 * date 2022-03-05 20:27
 */
public class Sl4optContext {

    private static final ThreadLocal<StandardEvaluationContext> THREAD_LOCAL = new ThreadLocal<StandardEvaluationContext>(){
        @Override
        protected StandardEvaluationContext initialValue() {
            StandardEvaluationContext context = new StandardEvaluationContext(null);
            context.setVariable(Sl4optVariables.ERR, null);
            context.setVariable(Sl4optVariables.RES, null);
            return context;
        }
    };

    public static StandardEvaluationContext getContext() {
        return THREAD_LOCAL.get();
    }

    /**
     * 增加variable
     * @param key key
     * @param value value
     */
    public static void putVariable(String key, Object value) {
        THREAD_LOCAL.get().setVariable(key, value);
    }

    /**
     * set global variable {@code _stime}
     * @see Sl4optVariables#S_TIME
     * @param stime 时间(timestamp)
     */
    public static void putStime(long stime) {
        putVariable(Sl4optVariables.S_TIME, stime);
    }

    /**
     * set global variable {@code _etime}
     * @see Sl4optVariables#E_TIME
     * @param etime 时间(timestamp)
     */
    public static void putEtime(long etime) {
        putVariable(Sl4optVariables.E_TIME, etime);
    }

    /**
     * set global variable {@code time}
     * @see Sl4optVariables#TIME
     * @param time 时间
     */
    public static void putTime(long  time) {
        putVariable(Sl4optVariables.TIME, time);
    }

    /**
     * set global variable {@code _res}
     * @see Sl4optVariables#RES
     * @param res 方法执行返回
     */
    public static void putRes(Object res) {
        putVariable(Sl4optVariables.RES, res);
    }

    /**
     * set global variable {@code _err}
     * @see Sl4optVariables#ERR
     * @param err 报错信息
     */
    public static void putErr(String err) {
        putVariable(Sl4optVariables.ERR, err);
    }

    public static void registerFunction() {

    }

    /**
     * 清除上下文
     */
    public static void clearContext() {
        THREAD_LOCAL.remove();
    }
}
