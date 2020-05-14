package edu.slcc.asdv.beans;

import edu.slcc.asdv.bl.SignIn;
import java.io.Serializable;
import java.sql.SQLException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

@Named(value = "signInBean")
@SessionScoped
public class SignInBean implements Serializable {
    private boolean isLoggedIn;
    private String username, password;
    
        public boolean isIsLoggedIn() {
        return isLoggedIn;
    }

    public void setIsLoggedIn(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * Will encrypt user input, then check DB for exact match
     * @return index(success), no_match(register page) or error(DB exception)
     * @throws SQLException 
     */
    public String validateUser() throws SQLException {

        int res = SignIn.validateUser(this.username, this.password);
        String returning = "Taylor: noCaseOnValidateUser";
        switch (res) {
            case 0:
                returning = "error";break;
            case -1:
                returning = "no_match";break; //>loads register page on no match
            case 2:
                returning = "admin_functions";break; //>loads admin page
            case 1: {
                isLoggedIn = true;
                returning = "index";break; //>reloads home page on success
            }
        }
        System.out.println("RETURNING: " + returning);
        return returning;
    }
    
    /**
     * Inserts the username and encrypted password into the DB via register page 
     * @return "error" on any DB fail OR "index" on successful insertion
     * @throws SQLException 
     */
    public String ins() throws SQLException {
        if (SignIn.insertData(this.username, this.password) == 0) {
            return "error";
        } else {
            isLoggedIn = true;
            return "index"; //>reloads home page on success
        }
    }
    
    public String signOut()
    {
        this.isLoggedIn = false;
        return "index";
    }
    
}
