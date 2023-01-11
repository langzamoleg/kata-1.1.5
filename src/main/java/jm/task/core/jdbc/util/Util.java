package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    public static final String DB_URL = "jdbc:mysql://localhost:3306/test";
    public static final String DB_Driver = "com.mysql.cj.jdbc.Driver";
    public static Connection connection;

    public static Connection connection() {
        try {
            Class.forName(DB_Driver);
            connection = DriverManager.getConnection(DB_URL, "root", "04oleg04");
        } catch (ClassNotFoundException classNotFoundException) {
            classNotFoundException.getStackTrace();
            System.out.println("driver not found");
        } catch (SQLException sqlException) {
            sqlException.getStackTrace();
            System.out.println("cant connect to db");
        }
        return connection;
    }
}