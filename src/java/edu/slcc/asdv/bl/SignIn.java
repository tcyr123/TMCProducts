package edu.slcc.asdv.bl;

import edu.slcc.asdv.utilities.connect;
import edu.slcc.asdv.utilities.DESUtil;
import edu.slcc.asdv.utilities.UserKey;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SignIn {
    
    /**
     * Encrypts user input then searches the DB for exact matches
     * @param username
     * @param password
     * @return -1(no results), 0(DB connection fail), 1(success)
     * @throws SQLException 
     */
    public static int validateUser(String username, String password)
            throws SQLException {
        Connection con = connect.connection();
        if (con == null) {
            System.out.println("Cannot connect to DB");
            return 0;
        }
        
        UserKey usr = new UserKey(username, password);//>encrypt user input
        byte[] ar = usr.StringToKey(usr.keyToString());
        password = DESUtil.encrypt(usr.getPassword(), ar);

        try {
            Statement stmt = con.createStatement();
            ResultSet result = stmt.executeQuery(
                    "SELECT * FROM login WHERE username = '" + username +
                            "' AND password = '" + password + "'");
            if (result.next() == false) {
                return -1;
            } else {
                if(result.getString("type").equals("A"))
                    return 2;
                else
                    return 1;
            }
        } finally {
            con.close();
        }
    }

    
    public static int insertData(String username, String password)
            throws SQLException {
        Connection con = connect.connection();
        if (con == null) {
            System.out.println("Cannot connect to DB");
            return 0;
        }

        //encrypt user input
        UserKey usr = new UserKey(username, password);
        byte[] ar = usr.StringToKey(usr.keyToString());
        password = DESUtil.encrypt(usr.getPassword(), ar);

        try {
            Statement stmt = con.createStatement();
            int result = stmt.executeUpdate("INSERT INTO login VALUES('" +
                    username + "', '" + password + "', '" + "U" + "')"); //user default
            System.out.println("INSERT INT RESULT " + result);
            return result;
        } finally {
            con.close();
        }
    }
}
