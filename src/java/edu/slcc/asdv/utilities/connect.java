package edu.slcc.asdv.utilities;

import java.sql.Connection;
import java.sql.DriverManager;

public class connect {
    /**
     * Develop a connection to the DB hard coded beneath
     * throws InstantiationException, IllegalAccessException
     * @return 
     */
    public static Connection connection()
    {
        String databaseName = "bestBoi";
        String userName = "root";
        String password = "";
        String URL2 = "com.mysql.jdbc.Driver";
        //String URL2 = "com.mysql.cj.jdbc.Driver";
        Connection con = null;
        try {// Load Sun's jdbc driver
            Class.forName(URL2).newInstance();
           // System.out.println("JDBC Driver loaded!");
        } catch (Exception e) // driver not found
        {
            System.err.println("Unable to load database driver");
            System.err.println("Details : " + e);
            return null;
        }
        String ip = "localhost"; //internet connection
        String url = "jdbc:mysql://" + ip + ":3306/" + databaseName;
        try {
            con = DriverManager.getConnection(url, userName, password);
            con.setReadOnly(false);
        } catch (Exception e) {
            System.err.println(e.toString());
            return null;
        }
       // System.out.println("connection successfull");
        return con;
    }
}
