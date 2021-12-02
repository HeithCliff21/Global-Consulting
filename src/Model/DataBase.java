/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author Heith
 */
public class DataBase {
    private static final String DBNAME = "U04Y0w";
    private static final String URL = "jdbc:mysql://wgudb.ucertify.com/" + DBNAME;
    private static final String USER = "U04Y0w"; 
    private static final String PASS = "53688377399";
    private static final String DRIVER = "com.mysql.jdbc.Driver"; 
    public static Connection conn;
    
 
    public static void makeConnection() throws ClassNotFoundException, SQLException, Exception
    {
        Class.forName(DRIVER);
        conn = DriverManager.getConnection(URL, USER, PASS);
        System.out.println("Connection sucessful!");
    }
    
    public static void closeConnection() throws ClassNotFoundException, SQLException, Exception
    {
        conn.close();
        System.out.println("Connection closed!");
    }
    public static Connection getConnection(){
        return conn;
    }
}
