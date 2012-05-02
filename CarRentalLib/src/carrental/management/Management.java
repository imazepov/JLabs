/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package carrental.management;

import carrental.data.ConnectionPool;
import carrental.data.DbConnection;

/**
 *
 * @author Ivan
 */
public final class Management {
    
    private Management() {        
    }
    
    private static RentalManager manager;
    private static DbConnection connection;    
    
    public static final ManagerType DEFAULT_MANAGER_TYPE = ManagerType.JPA;

    public static RentalManager getManager() throws ManagementException {
        return getManager(DEFAULT_MANAGER_TYPE);
    }
    
    public static RentalManager getManager(ManagerType type) throws ManagementException {        
        switch(type) {
            case JDBC:
                return getJDBCManager();

            case JPA:
                return getJPAManager();

            default:
                throw new ManagementException("Unknown manager type");
        }
    }

    private static RentalManager getJDBCManager() throws ManagementException {
        try {
            DbConnection currentConnection = ConnectionPool.getConnection();
            if(currentConnection != connection) {
                connection = currentConnection;
                manager = new RentalManagerJDBC(connection);
            }
            
            if(!(manager instanceof RentalManagerJDBC))
                manager = new RentalManagerJDBC(connection);
            
            return manager;
        } catch(Exception ex) {
            throw new ManagementException(ex);
        }
    }

    private static RentalManager getJPAManager() {
        if(!(manager instanceof RentalManagerJPA))
            manager = new RentalManagerJPA();
        
        return manager;
    }    
}
