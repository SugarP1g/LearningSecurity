package com.SugarP1g.securityManager;

public class ByCreateClassLoader {

    public static void main(String[] args) throws Exception {
        MyClassLoader mcl = new MyClassLoader();
        Class<?> c1 = Class.forName("ClassLoaderExpolit", true, mcl);
        Object obj = c1.newInstance();
        System.out.println(obj.getClass().getClassLoader());
    }
}
