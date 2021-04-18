package com.SugarP1g.deserialization.XStream;

import com.thoughtworks.xstream.*;
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

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
                "  <name>aaa</name>\n" +
                "  <age>6</age>\n" +
                "</com.huawei.icsl.Person>";

        XStream xstream = new XStream(new DomDriver());
        Person p = (Person) xstream.fromXML(xml);
        p.output();
    }

    public void evil_decode() throws FileNotFoundException {
        String filename = this.getClass().getClassLoader().getResource("XStreamPayload.xml").getPath();
        FileInputStream xml = new FileInputStream(filename);
        XStream xstream = new XStream(new DomDriver());
        Person p = (Person) xstream.fromXML(xml);
        p.output();
    }

    public static void main(String[] args) {
        // encode();
        try {
            new POC().evil_decode();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
