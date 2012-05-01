/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package carrental.model;

import carrental.data.DbConnection;
import carrental.management.RentalManager;
import carrental.model.adapters.AdapterException;
import java.sql.SQLException;

/**
 *
 * @author Ivan
 */
public class Management {
    private static RentalManager manager;
    private static DbConnection connection;
    
    public static RentalManager getManager() throws AdapterException, SQLException, ConnectionPoolException {
        DbConnection currentConnection = ConnectionPool.getConnection();
        if(currentConnection != connection) {
            connection = currentConnection;
            manager = new RentalManager(connection);
        }
        
        return manager;
    }
}
