package com.SugarP1g.runtime;


import java.lang.reflect.Method;

public class Reflection {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException {
        Class clazz = Class.forName("java.lang.Runtime");
        System.out.println(clazz);
        Method mthd = clazz.getMethod("exec", String.class);
        System.out.println(mthd);
    }
}
