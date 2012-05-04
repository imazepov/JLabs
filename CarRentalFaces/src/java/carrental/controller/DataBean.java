/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package carrental.controller;

import carrental.management.RentalManagerException;
import carrental.model.Car;
import carrental.management.Management;
import carrental.management.ManagementException;
import carrental.management.RentalManager;
import java.util.List;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author ivan
 */
@ManagedBean(name="data")
public class DataBean {
    public List<Car> getCars() throws ManagementException, RentalManagerException {
        RentalManager manager = Management.getManager();
        return manager.listCars();        
    }
}
