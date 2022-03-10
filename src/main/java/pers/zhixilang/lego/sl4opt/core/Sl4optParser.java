package pers.zhixilang.lego.sl4opt.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import pers.zhixilang.lego.sl4opt.core.parser.FunctionParser;
import pers.zhixilang.lego.sl4opt.core.parser.ValueParser;
import pers.zhixilang.lego.sl4opt.exception.Sl4optException;

import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 自定义Expression解析器
 * @author zhixilang
 * @version 1.0.0
 * date 2022-03-05 20:29
 */
public class Sl4optParser {
    /**
     * 模板表达式pattern
     */
    private final Pattern pattern = Pattern.compile("@\\s*(\\w*)\\s*(.*?)@");

    private static final Logger logger = LoggerFactory.getLogger("sl4opt");

    /**
     * 模板表达式解析
     * @param expressionMap expression map
     */
    public void parse(Map<String, String> expressionMap) {
        if (null == expressionMap)  {
            return;
        }

        Set<String> keySet = expressionMap.keySet();
        Matcher matcher;
        for (String expressionStr: keySet) {

            matcher = pattern.matcher(expressionStr);
            StringBuffer sb = new StringBuffer();

            while (matcher.find()) {
                String valueExpressionStr = matcher.group(2);
                String functionExpressionStr = matcher.group(1);
                String parsedStr;

                try {
                    if (StringUtils.isEmpty(functionExpressionStr)) {
                        parsedStr = String.valueOf(ValueParser.parse(valueExpressionStr));
                    } else {
                        parsedStr = FunctionParser.parse(functionExpressionStr, valueExpressionStr);
                    }
                } catch (Sl4optException e) {
                    logger.error("parse error=> {}", e.getMessage(), e);
                    parsedStr = matcher.group();
                }

                matcher.appendReplacement(sb, parsedStr);
            }
            matcher.appendTail(sb);
            expressionMap.put(expressionStr, sb.toString());
        }
    }
}
