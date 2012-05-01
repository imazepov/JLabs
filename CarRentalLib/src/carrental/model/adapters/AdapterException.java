/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package carrental.model.adapters;

/**
 *
 * @author ivan
 */
public class AdapterException extends Exception {
    public AdapterException(String message, Exception innerException) {
        super(message, innerException);
    }
    
    public AdapterException(String message) {
        super(message);
    }
}
