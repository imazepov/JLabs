/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package carrental.ejb;

import carrental.management.Management;
import carrental.management.RentalManager;
import carrental.model.Car;
import carrental.model.CarRental;
import carrental.model.Customer;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Stateless;

/**
 *
 * @author ivan
 */
@Stateless
public class CarRentalBean implements CarRentalBeanLocal {

    public CarRentalBean() {                
    }
    
    @Override
    public List<Customer> getCustomers() throws CarRentalBeanException {
        try {
            RentalManager manager = Management.getManager();
            return manager.listCustomers();
        } catch (Exception ex) {
            throw new CarRentalBeanException(ex);
        }
    }

    @Override
    public List<Car> getCars() throws CarRentalBeanException {
        try {
            RentalManager manager = Management.getManager();
            return manager.listCars();
        } catch (Exception ex) {
            throw new CarRentalBeanException(ex);
        }
    }

    @Override
    public List<CarRental> getRentals() throws CarRentalBeanException {
        try {
            RentalManager manager = Management.getManager();
            return manager.listRentals();            
        } catch (Exception ex) {
            throw new CarRentalBeanException(ex);
        }
    }

    @Override
    public void updateCustomer(Customer customer) throws CarRentalBeanException {
        try {
            RentalManager manager = Management.getManager();
            manager.updateCustomer(customer);
        } catch (Exception ex) {
            throw new CarRentalBeanException(ex);
        }
    }

    @Override
    public void addCar(Car car) throws CarRentalBeanException {
        try {
            RentalManager manager = Management.getManager();
            manager.addCar(car);
        } catch (Exception ex) {
            throw new CarRentalBeanException(ex);
        }
    }

    @Override
    public void addCustomer(Customer customer) throws CarRentalBeanException {
        try {
            RentalManager manager = Management.getManager();
            manager.addCustomer(customer);
        } catch (Exception ex) {
            throw new CarRentalBeanException(ex);
        }
    }

    @Override
    public void rent(Customer customer, Car car, int days) throws CarRentalBeanException {
        try {
            RentalManager manager = Management.getManager();
            manager.rent(customer, car, days);
        } catch (Exception ex) {
            throw new CarRentalBeanException(ex);
        }
    }

    @Override
    public void removeCustomer(Customer customer) throws CarRentalBeanException {
        try {
            RentalManager manager = Management.getManager();
            manager.removeCustomer(customer);
        } catch (Exception ex) {
            throw new CarRentalBeanException(ex);
        }
    }

    @Override
    public void closeRental(CarRental carRental) throws CarRentalBeanException {
        try {
            RentalManager manager = Management.getManager();
            manager.closeRental(carRental);
        } catch (Exception ex) {
            throw new CarRentalBeanException(ex);
        }
    }

    @Override
    public void removeCar(Car car) throws CarRentalBeanException {
        try {
            RentalManager manager = Management.getManager();
            manager.removeCar(car);
        } catch (Exception ex) {
            throw new CarRentalBeanException(ex);
        }
    }
    
}
