package com.SugarP1g.securityManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.security.*;
import java.security.cert.Certificate;

public class MyClassLoader extends ClassLoader {
    public MyClassLoader() {
    }

    public MyClassLoader(ClassLoader parent) {
        super(parent);
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        if (name.contains("ClassLoaderExpolit")) {
            return findClass(name);
        }
        return super.loadClass(name);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        File file = getClassFile(name);
        try {
            byte[] bytes = getClassBytes(file);
            //在这里调用defineClazz，而不是super.defineClass
            Class<?> c = defineClazz(name, bytes, 0, bytes.length);
            return c;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return super.findClass(name);
    }

    protected final Class<?> defineClazz(String name, byte[] b, int off, int len) throws ClassFormatError {
        try {
            PermissionCollection pc = new Permissions();
            pc.add(new AllPermission());

            //设置ProtectionDomain
            ProtectionDomain pd = new ProtectionDomain(new CodeSource(null, (Certificate[]) null),
                    pc, this, null);
            return this.defineClass(name, b, off, len, pd);
        } catch (Exception e) {
            return null;
        }
    }

    private File getClassFile(String name) {
        File file = new File("D:\\Project\\zch\\LearningSecurity\\src\\JavaSecurity\\LearningSandboxEscape\\target\\classes\\com\\SugarP1g\\securityManager\\ClassLoaderExpolit.class");
        return file;
    }

    private byte[] getClassBytes(File file) throws Exception {
        FileInputStream fis = new FileInputStream(file);
        FileChannel fc = fis.getChannel();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        WritableByteChannel wbc = Channels.newChannel(baos);
        ByteBuffer by = ByteBuffer.allocate(1024);

        while (true) {
            int i = fc.read(by);
            if (i == 0 || i == -1) {
                break;
            }

            by.flip();
            wbc.write(by);
            by.clear();
        }
        fis.close();
        return baos.toByteArray();
    }
}
