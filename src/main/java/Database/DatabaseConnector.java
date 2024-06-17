/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Tran Giap
 */
public class DatabaseConnector {
    private static final String URL = "jdbc:sqlserver://192.168.1.7:1433;databaseName=test;user=sa;password={Q;`J;'[K]ya't`e)EDct};encrypt=true;trustServerCertificate=true;";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }
    
}
