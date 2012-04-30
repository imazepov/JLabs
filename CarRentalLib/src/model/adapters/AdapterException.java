/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.adapters;

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
