package com.SugarP1g.deserialization.XStream;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Objects;

import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;

public class CVE_2021_21344 {
    public static void main(String[] args) throws FileNotFoundException {
        String filename = Objects.requireNonNull(CVE_2021_21344.class.getClassLoader().getResource("CVE-2021-21344.xml")).getPath();
        FileInputStream xml = new FileInputStream(filename);
        XStream xstream = new XStream(new DomDriver());
        xstream.fromXML(xml);
    }
}
