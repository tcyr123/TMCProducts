package edu.slcc.asdv.bl;
import java.sql.Connection;
import java.util.List;

public interface DB {
    
     /**
     * Returns a Connection to the database or null if no connection is established.
     * @param databaseName
     * @param username
     * @param password
     * @param driver
     * @return 
     */
    public Connection getConnection(String databaseName, String username, String password, String driver);
    
    /**
     * Returns the list of all rows of the table "tableName".
     * @param tableName
     * @return 
     */
    public List<String> selectAllFromTable(String tableName);
    
    /**
     * Returns true if the table exists in the database, false otherwise.
     * @param tableName
     * @return 
     */
    boolean isTable(String tableName);
    
    /**
     * Closes connection to the current database.
     * @param c 
     */
    public void closeConnection(Connection c);
    
}
