package com.SugarP1g.groovyInjection;

import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import groovy.lang.GroovyShell;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;

public class POC {

    public static void main(String[] args) throws Exception {

        String exp = args[1];
        String path = args[2];
        insecureGroovyShellCall(exp);
        insecureGroovyClassLoaderCall(exp);
        insecureGroovyScriptEngineCall(path, exp);
    }

    private static void insecureGroovyShellCall(String exp) {
        GroovyShell groovyShell = new GroovyShell();
        groovyShell.evaluate(exp);
    }

    private static void insecureGroovyClassLoaderCall(String exp) throws IllegalAccessException, InstantiationException {
        GroovyClassLoader loader = new GroovyClassLoader();
        Class groovyClass = loader.parseClass(exp); // 也可以解析字符串
        GroovyObject groovyObject = (GroovyObject) groovyClass.newInstance();
        groovyObject.invokeMethod("run", "helloworld");
    }

    private static void insecureGroovyScriptEngineCall(String rootPath, String exp) throws Exception {
        GroovyScriptEngine groovyScriptEngine = new GroovyScriptEngine(rootPath);
        groovyScriptEngine.run("hello.groovy", new Binding());
    }

    private static void weakGroovyCall(String exp) {


    }
}
