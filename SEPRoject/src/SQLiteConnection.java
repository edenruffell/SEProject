
import java.sql.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author AliyahButt1
 */
public class SQLiteConnection {
    
    public static Connection Connector() {
        Connection conn = null;
        try {
            // db parameters
            String url = "jdbc:sqlite:test.db ";
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");
            return conn;
         
        }catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
