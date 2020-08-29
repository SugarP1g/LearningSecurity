package com.SugarP1g.ssti.FreeMarker;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;


public class POC {

    public static void main(String[] args) {
        String resourcePath = "src/main/resources";
        System.out.println("[+] Resource path: " + resourcePath);
        Configuration config = new Configuration(Configuration.VERSION_2_3_30);
        Writer out = null;

        try {
            config.setDirectoryForTemplateLoading(new File(resourcePath));
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("classPath", "com.freemarker.hello");
            dataMap.put("className", "${2*2}");
            dataMap.put("helloWorld", "Hello, this is a freemarker demo.");

            Template template = config.getTemplate("hello.ftl");
            File docFile = new File("./FreeMarkerDemo.java");
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(docFile)));

            template.process(dataMap, out);
            System.out.println("[+] Template Rendering is success.");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
