/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package carrental.model;

import carrental.management.RentalManager;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ivan
 */
public class CustomerModel {
    public List<Customer> getCustomers() {
        try {
            RentalManager manager = Management.getManager();
            return manager.listCustomers();
        } catch(Exception ex) {
            return new ArrayList<Customer>();
        }
    }
}
