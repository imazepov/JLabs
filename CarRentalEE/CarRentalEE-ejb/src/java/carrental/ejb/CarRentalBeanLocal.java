/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package carrental.ejb;

import carrental.model.Car;
import carrental.model.CarRental;
import carrental.model.Customer;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ivan
 */
@Local
public interface CarRentalBeanLocal  {
    public List<Customer> getCustomers() throws CarRentalBeanException;
    public List<Car> getCars() throws CarRentalBeanException;
    public List<CarRental> getRentals() throws CarRentalBeanException;
    public void updateCustomer(Customer customer) throws CarRentalBeanException;
    public void addCar(Car car) throws CarRentalBeanException;
    public void removeCar(Car car) throws CarRentalBeanException;
    public void addCustomer(Customer customer) throws CarRentalBeanException;
    public void removeCustomer(Customer customer) throws CarRentalBeanException;
    public void rent(Customer customer, Car car, int days) throws CarRentalBeanException;
    public void closeRental(CarRental carRental) throws CarRentalBeanException;    
}
