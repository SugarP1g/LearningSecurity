package com.SugarP1g.reflection;


import java.util.Arrays;
import java.util.List;

public class TestRuntime {

    public void processBuilderReflect() throws Exception {
        Class clazz = Class.forName("java.lang.ProcessBuilder");
        clazz.getMethod("start").invoke(clazz.getConstructor(List.class).newInstance(
                Arrays.asList("calc.exe")));
    }

    public void runtimeReflect() throws Exception {
        Class clazz = Class.forName("java.lang.Runtime");
        clazz.getMethod("exec",
                String.class).invoke(clazz.getMethod("getRuntime").invoke(clazz),
                "calc.exe");
    }

    public static void main(String[] args) throws Exception {
        TestRuntime obj = new TestRuntime();
        obj.processBuilderReflect();
        // obj.runtimeReflect();
    }
}
