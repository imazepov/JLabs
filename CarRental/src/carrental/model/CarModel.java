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
public class CarModel {
    
    public List<Car> getCars() {
        try {        
            RentalManager manager = Management.getManager();        
            return manager.listCars();
        } catch(Exception ex) {
            return new ArrayList<Car>();
        }
    }
    
}
