/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package carrental.model;

import carrental.data.DbConnection;
import java.sql.SQLException;

/**
 *
 * @author Ivan
 */
public final class ConnectionPool {
    private static boolean initialized;    
    
    private static DbConnection connection;
    
    private static final String DB_NAME = "CarRental";
    private static final String DB_HOST = "localhost";
    private static final int DB_PORT = 3306;
    private static final String DB_LOGIN = "carrental";
    private static final String DB_PASSWORD = "rent";
    
    public static DbConnection getConnection() throws SQLException, ConnectionPoolException {
        if(!initialized)
            throw new ConnectionPoolException("Connection pool failed to initialize");
        
        if(connection == null) {
            connection = DbConnection.create(DB_HOST, DB_PORT, DB_LOGIN, DB_PASSWORD, DB_NAME);
        }
        
        return connection;
    }
    
    static {
        try {
            DbConnection.initialize();
            initialized = true;
        } catch (ClassNotFoundException ex) {            
        }
    }
    
    
}
