/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaseproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author 6sand
 */
public class Database {
    Connection con;
    Statement statement;

    public void dbConnect() throws ClassNotFoundException, SQLException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionUrl = "jdbc:sqlserver://localhost:1433;databaseName=3.1 Database Project;user=sa;password=586475";
        con = DriverManager.getConnection(connectionUrl);
        System.out.println("connected");
        statement = con.createStatement();
    }
    public void runQueryUpdate(String query) throws SQLException {
        String queryString = query;
        int updatestat = statement.executeUpdate(queryString);
        System.out.println(updatestat);
    }
    public Connection getCon() {
        return con;
    }

}
