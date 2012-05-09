/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package carrental.management;

import carrental.model.Car;
import carrental.model.CarRental;
import carrental.model.Customer;
import java.util.List;

/**
 *
 * @author Ivan
 */
public interface RentalManager {

    void addCar(Car car) throws RentalManagerException;

    void addCustomer(Customer customer) throws RentalManagerException;

    void closeRental(CarRental rental) throws RentalManagerException;

    void closeRentalById(int id) throws RentalManagerException;

    Car getCarById(int id) throws RentalManagerException;

    Customer getCustomerById(int id) throws RentalManagerException;

    List<Car> listCars() throws RentalManagerException;

    List<Customer> listCustomers() throws RentalManagerException;

    List<CarRental> listRentals() throws RentalManagerException;

    void removeCar(Car car) throws RentalManagerException;

    void removeCar(int id) throws RentalManagerException;

    void removeCustomer(Customer customer) throws RentalManagerException;

    void removeCustomer(int id) throws RentalManagerException;

    CarRental rent(Customer customer, Car car, int days) throws RentalManagerException;

    public void updateCustomer(Customer customer) throws RentalManagerException;
    
}
