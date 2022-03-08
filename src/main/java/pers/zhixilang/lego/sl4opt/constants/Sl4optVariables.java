package pers.zhixilang.lego.sl4opt.constants;

/**
 * sl4opt全局变量定义,可在模板表达式中进行引用
 * exp=> success="新增成功，返回结果=@#_res@"
 * @author zhixilang
 * @version 1.0.0
 * date 2022-03-06 17:29
 */
public final class Sl4optVariables {
    /**
     * 方法执行完成后返回结果
     */
    public static final String RES = "_res";

    /**
     * 方法执行错误信息
     */
    public static final String ERR = "_err";

    /**
     * 执行前时间（timestamp）
     */
    public static final String S_TIME = "_stime";

    /**
     * 执行后时间（timestamp）
     */
    public static final String E_TIME = "_etime";

    /**
     * 执行耗时（ms）
     */
    public static final String TIME = "_time";
}
