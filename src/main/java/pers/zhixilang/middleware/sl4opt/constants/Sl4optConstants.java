package pers.zhixilang.middleware.sl4opt.constants;

/**
 * @author zhixilang
 * @version 1.0.0
 * date 2022-03-07 12:01
 */
public class Sl4optConstants {
    /**
     * 模板前缀
     */
    public static final String TEMPLATE_PREFIX;

    /**
     * 模板后缀
     */
    public static final String TEMPLATE_SUFFIX;

    static {
        // TODO 支持自定义配置,从property获取
        TEMPLATE_PREFIX = "@";
        TEMPLATE_SUFFIX = TEMPLATE_PREFIX;
    }
}
