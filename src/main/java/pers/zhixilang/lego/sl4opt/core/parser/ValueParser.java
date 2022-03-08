package pers.zhixilang.lego.sl4opt.core.parser;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import pers.zhixilang.lego.sl4opt.core.Sl4optContext;
import pers.zhixilang.lego.sl4opt.exception.Sl4optException;

/**
 * 表达式解析.value解析
 * @author zhixilang
 * @version 1.0.0
 * date 2022-03-05 21:23
 */
public class ValueParser {

    private static ExpressionParser expressionParser = new SpelExpressionParser();

    public static String parse(String expressionStr) {
        try {
            Expression expression =  expressionParser.parseExpression(expressionStr);
            return String.valueOf(expression.getValue(Sl4optContext.getContext()));
        } catch (Exception e) {
            throw new Sl4optException(expressionStr, "value parse error.", e);
        }
    }
}
