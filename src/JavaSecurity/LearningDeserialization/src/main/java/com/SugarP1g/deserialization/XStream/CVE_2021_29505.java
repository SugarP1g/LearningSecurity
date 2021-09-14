package com.SugarP1g.deserialization.XStream;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Objects;

public class CVE_2021_29505 {
    public static void main(String[] args) throws FileNotFoundException {
        String filename = Objects.requireNonNull(CVE_2021_29505.class.getClassLoader().getResource("CVE-2021-29505.xml")).getPath();
        FileInputStream xml = new FileInputStream(filename);
        XStream xstream = new XStream(new DomDriver());
        xstream.fromXML(xml);
    }
}
