package com.SugarP1g.deserialization.XMLDecoder;

import java.beans.*;
import java.io.*;

public class POC {

    public void decode() {
        XMLDecoder d = null;
        String fileName = this.getClass().getClassLoader().getResource("XMLDecoderPayload.xml").getPath();
        try {

            System.out.println(fileName);
            d = new XMLDecoder(new BufferedInputStream(new FileInputStream(fileName)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Object result = d.readObject();
        d.close();
    }


    public static void main(String args[]) {
        new POC().decode();
//        try {
//            XMLEncoder e = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("Test.xml")));
//            e.writeObject(new JButton("Hello, world"));
//            e.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
    }
}
