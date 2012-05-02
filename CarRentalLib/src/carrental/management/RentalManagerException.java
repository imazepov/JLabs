/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package carrental.management;

/**
 *
 * @author Ivan
 */
public class RentalManagerException extends Exception {
    public RentalManagerException(String msg, Exception ex) {
        super(msg, ex);
    }
    
    public RentalManagerException(String msg) {
        this(msg, null);
    }
    
    public RentalManagerException(Exception ex) {
        this(null, ex);
    }
}
