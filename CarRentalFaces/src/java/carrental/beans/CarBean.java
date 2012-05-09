/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package carrental.beans;

import carrental.management.Management;
import carrental.management.ManagementException;
import carrental.management.RentalManager;
import carrental.management.RentalManagerException;
import carrental.misc.Util;
import carrental.model.Car;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

/**
 *
 * @author Ivan
 */
@ManagedBean(name="cars")
@SessionScoped
public class CarBean implements Serializable {
    
    private DataModel<Car> model;
    private Car car;
    
    public CarBean() throws ManagementException, RentalManagerException {
        RentalManager manager = Management.getManager();
        model = new ListDataModel();
        model.setWrappedData(manager.listCars());
        
        car = new Car();
    }
    
    public SelectItem[] getSelection() {
        return Util.getSelectItems((List<Car>)model.getWrappedData());
    }
    
    public DataModel getModel() {
        return model;
    }
    
    public Car getNewCar() {
        return car;
    }
    
    public String addCar() throws ManagementException, RentalManagerException {
        RentalManager manager = Management.getManager();
        manager.addCar(car);
        return "list_data";
    }
    
    public String removeCar() throws ManagementException, RentalManagerException {
        Car car = model.getRowData();
        RentalManager manager = Management.getManager();
        manager.removeCar(car);
        return "list_data";
    }
}
