package com.SugarP1g.ldap;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.InitialDirContext;
import java.util.Hashtable;

public class Client {

    public static void main(String[] args) throws NamingException {
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.rmi.registry.RegistryContextFactory");
        env.put(Context.PROVIDER_URL, "rmi://localhost:9999");
        String name = args[1];
        Context ctx = new InitialDirContext();
        Object local_obj = ctx.lookup(name);
    }
}
