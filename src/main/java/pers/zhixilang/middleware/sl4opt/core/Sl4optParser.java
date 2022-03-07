package pers.zhixilang.middleware.sl4opt.core;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import pers.zhixilang.middleware.sl4opt.pojo.OptLog;
import pers.zhixilang.middleware.sl4opt.pojo.OptLogTemplate;
import pers.zhixilang.middleware.sl4opt.service.IOperatorService;

/**
 * 自定义Expression解析器
 * @author zhixilang
 * @version 1.0.0
 * date 2022-03-05 20:29
 */
public class Sl4optParser {

    private SpelExpressionParser spelExpressionParser = new SpelExpressionParser();

    private ParserContext parserContext = new Sl4optTemplateParserContext();

    private IOperatorService operatorService;

    public Sl4optParser(IOperatorService operatorService) {
        this.operatorService = operatorService;
    }

    public OptLog parse(OptLogTemplate optLogTemplate) {
        OptLog optLog = new OptLog();

        Expression expression = spelExpressionParser.parseExpression(optLogTemplate.getContent(), parserContext);

        EvaluationContext evaluationContext = Sl4optContext.getContext();
        Object value = expression.getValue(evaluationContext);

        optLog.setContent((String) value);
        optLog.setResult(optLogTemplate.getResult());
        return optLog;
    }
}
