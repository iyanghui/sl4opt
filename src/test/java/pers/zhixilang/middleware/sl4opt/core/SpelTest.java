package pers.zhixilang.middleware.sl4opt.core;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhixilang
 * @version 1.0.0
 * date 2021-10-24 23:06
 */
@SpringBootTest
public class SpelTest {

    @Test
    public void patternTest() {

        String expressionStr = "hello @#name@, gen nickName=@ADD#request.name,#age@, ID=@#_res.id@";
        final Pattern pattern1 = Pattern.compile("@\\s*(\\w*)\\s*(.*?)@");

        Matcher matcher = pattern1.matcher(expressionStr);

        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            System.out.println(matcher.toString());
            String c = matcher.group(2);
            String functionName = matcher.group(1);
            System.out.println("expression: " + c);
            System.out.println("functionName: " + functionName);

            matcher.appendReplacement(sb, "replace");
        }
        matcher.appendTail(sb);

        System.out.println(sb.toString());
    }

    @Test
    public void expressionTest() {


        ExpressionParser expressionParser = new SpelExpressionParser();

        StandardEvaluationContext context = new StandardEvaluationContext();


        Expression expression = expressionParser.parseExpression("'hello' + #name + '...'");

        context.setVariable("name", "zs from variable");
        System.out.println(expression.getValue(context));


        expression = expressionParser.parseExpression("templateParser variable: #{#name}", new TemplateParserContext());

        System.out.println(expression.getValue(context));



        Person person = new Person();
        person.setName("zs from pojo");

        context.setVariable("person", person);
        expression = expressionParser.parseExpression("#person.name");
        System.out.println(expression.getValue(context));


        expression = expressionParser.parseExpression("templateParser pojo: #{#person.name}",
                new TemplateParserContext());

        System.out.println(expression.getValue(context));



        try {
            Method method = SpelTest.class.getDeclaredMethod("say", String.class);
            method.setAccessible(true);

            context.registerFunction("say", method);
            expression = expressionParser.parseExpression("#say('zs')");
            System.out.println(expression.getValue(context));

            expression = expressionParser.parseExpression("#say(#person.name)");
            System.out.println(expression.getValue(context));

            method = SpelTest.class.getDeclaredMethod("say1", Person.class);
            method.setAccessible(true);

            context.registerFunction("say1", method);
            expression = expressionParser.parseExpression("#say1(#person)");
            System.out.println(expression.getValue(context));


            expression = expressionParser.parseExpression("templateParser function: #{#say(#person.name) + '...'}",
                    new TemplateParserContext());

            System.out.println(expression.getValue(context));


        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }

    private static String say(String str) {
        return "say=> hello world " +  str;
    }

    private static String say1(Person i) {
        return "say1=> hello world " +  i.getName();
    }

    public static class Person {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
