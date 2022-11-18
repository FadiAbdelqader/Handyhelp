package com.miage.handyhelp.repository;

import java.sql.*;

public class DBConnection {
    static  final String URL = "jdbc:hsqldb:hsql://localhost/";

    public static void main(String [] args) {
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            connexion();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection connexion() throws SQLException{
        Connection conn = DriverManager.getConnection(URL, "SA", "");
        return conn;
    }

    public static ResultSet executeStatement(String query) throws SQLException {
        Statement stmt = connexion().createStatement();
        return stmt.executeQuery(query);
    }

}
