package pers.zhixilang.middleware.sl4opt.core;

import org.springframework.expression.common.TemplateParserContext;
import pers.zhixilang.middleware.sl4opt.constants.Sl4optConstants;

/**
 * 自定义模板解析上下文
 * @author zhixilang
 * @version 1.0.0
 * date 2022-03-07 16:35
 */
public class Sl4optTemplateParserContext extends TemplateParserContext {

    public Sl4optTemplateParserContext() {
        super(Sl4optConstants.TEMPLATE_PREFIX, Sl4optConstants.TEMPLATE_SUFFIX);
    }
}
