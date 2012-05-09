/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package carrental.ejb;

/**
 *
 * @author ivan
 */
public class CarRentalBeanException extends Exception {
    public CarRentalBeanException(String msg, Exception ex) {
        super(msg, ex);
    }
    
    public CarRentalBeanException(String msg) {
        this(msg, null);
    }
    
    public CarRentalBeanException(Exception ex) {
        this(null, ex);
    }
}
