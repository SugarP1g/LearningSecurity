package com.SugarP1g.deserialization.XStream;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Objects;

public class S2_052 {
    public static void main(String[] args) throws FileNotFoundException {
        String filename = Objects.requireNonNull(S2_052.class.getClassLoader().getResource("s2-052.xml")).getPath();
        FileInputStream xml = new FileInputStream(filename);
        XStream xstream = new XStream(new DomDriver());
        xstream.fromXML(xml);
    }
}
