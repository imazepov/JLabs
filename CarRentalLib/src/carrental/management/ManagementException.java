/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package carrental.management;

/**
 *
 * @author Ivan
 */
public class ManagementException extends Exception {    
    public ManagementException(String msg, Exception ex) {
        super(msg, ex);
    }
    
    public ManagementException(Exception ex) {
        this(null, ex);
    }
    
    public ManagementException(String msg) {
        this(msg, null);
    }
}
