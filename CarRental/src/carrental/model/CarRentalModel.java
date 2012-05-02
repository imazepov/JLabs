/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package carrental.model;

import carrental.management.RentalManager;
import carrental.management.Management;
import java.util.List;

/**
 *
 * @author Ivan
 */
public class CarRentalModel {
    public List<CarRental> getRentals() throws Exception {
        try {
            RentalManager manager = Management.getManager();
            return manager.listRentals();
        } catch(Exception ex) {
//            return new ArrayList<CarRental>();
            throw ex;
        }
    }
}
