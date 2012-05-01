/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package carrental.management;

import carrental.data.DbConnection;
import carrental.model.Car;
import carrental.model.CarRental;
import carrental.model.Customer;
import carrental.model.adapters.AdapterException;
import carrental.model.adapters.CarAdapter;
import carrental.model.adapters.CarRentalAdapter;
import carrental.model.adapters.CustomerAdapter;
import java.sql.SQLException;
import java.util.*;

/**
 *
 * @author ivan
 */
public final class RentalManager {
    private CarAdapter carAdapter;
    private CustomerAdapter customerAdapter;
    private CarRentalAdapter rentalAdapter;
    
    public RentalManager(DbConnection conn) throws AdapterException {
        carAdapter = new CarAdapter(conn);
        customerAdapter = new CustomerAdapter(conn);
        rentalAdapter = new CarRentalAdapter(conn, carAdapter, customerAdapter);
    }  
    
    public CarRental rent(Customer customer, Car car, int days) throws AdapterException {
        CarRental rental = new CarRental();
        rental.setCar(car);
        rental.setCustomer(customer);
        rental.setStartDate(new GregorianCalendar());
        rental.setDays(days);
        
        rentalAdapter.addObject(rental);
        
        return rental;
    }
    
    public void closeRentalById(int id) throws AdapterException {
        rentalAdapter.removeObject(id);
    }
    
    public void closeRental(CarRental rental) throws AdapterException {
        rentalAdapter.removeObject(rental);
    }
    
    public List<CarRental> listRentals() throws SQLException, AdapterException {
        return rentalAdapter.getObjects();
    }
    
    public List<Car> listCars() throws SQLException, AdapterException {
        return carAdapter.getObjects();
    }

    public List<Customer> listCustomers() throws SQLException, AdapterException {
        return customerAdapter.getObjects();
    }
    
    public Car getCarById(int id) throws SQLException, AdapterException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("ID", id);
        List<Car> cars = carAdapter.getObjects(params);
        if(cars.isEmpty())
            return null;
        return cars.get(0);        
    }
    
    public Customer getCustomerById(int id) throws SQLException, AdapterException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("ID", id);
        List<Customer> customers = customerAdapter.getObjects(params);
        if(customers.isEmpty())
            return null;
        return customers.get(0);        
    }
}
