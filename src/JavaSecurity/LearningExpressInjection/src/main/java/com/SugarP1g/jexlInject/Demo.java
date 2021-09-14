package com.SugarP1g.jexlInject;

import org.apache.commons.jexl3.*;
import org.apache.commons.jexl3.introspection.JexlSandbox;

public class Demo {

    public void poc(String exp) {
        JexlSandbox js = new JexlSandbox(false);
        js.white("java.lang.Integer");
        js.white("java.util.HashMap");
        js.white("java.lang.Class");
        js.white("java.lang.Runtime");
        JexlEngine je = new JexlBuilder().sandbox(js).strict(true).create();
        JexlContext jc = new MapContext();

        JexlExpression jex = je.createExpression(exp);
        System.out.println(jex.evaluate(jc));
    }

    public static void main(String[] args) {
        String exp = args[1];
        new Demo().poc(exp);
    }
}
