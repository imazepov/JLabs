/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ivan
 */
public final class DbConnection {
    
    private Connection connection;
    
    public boolean isValid() {
        try {
            return connection != null && connection.isValid(0);
        } catch(Exception ex) {
            return false;
        }
    }
    
    public ResultSet executeQuery(String query, List params) throws SQLException {
        checkConnection();
            
        PreparedStatement statement = connection.prepareStatement(query);
        for(int i=0; i<params.size(); i++) {
            statement.setObject(i+1, params.get(i));
        }
        
        if(statement.execute()) {
            return statement.getResultSet();
        }      
      
        return null;
    }
    
    public void beginTransaction() throws SQLException {
        connection.setAutoCommit(false);        
    }
    
    public void commitTransaction() throws SQLException {
        try {
            connection.commit();
        } finally {
            connection.setAutoCommit(true);
        }
    }
    
    public void rollbackTransaction() throws SQLException {
        try {
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
    }

    private void checkConnection() throws SQLException {
        if(connection == null || !connection.isValid(0)) {
            throw new SQLException();
        }
    }
    
    public static void initialize() throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
    }
    
    private DbConnection(String url, String userName, String password) throws SQLException {        
        connection = DriverManager.getConnection(url, userName, password);        
    }
    
    public static DbConnection create(String server, int port, 
            String login, String password, String database) throws SQLException {
        String connectionUrl = String.format("jdbc:mysql://%s:%d/%s",
                server, port, database);
        
        DbConnection conn = new DbConnection(connectionUrl, login, password);
        
        return conn;
    }

    public ResultSet executeQuery(String query) throws SQLException {
        return executeQuery(query, new ArrayList());
    }
}
