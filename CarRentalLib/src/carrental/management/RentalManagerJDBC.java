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
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ivan
 */
public final class RentalManagerJDBC implements RentalManager {
    private CarAdapter carAdapter;
    private CustomerAdapter customerAdapter;
    private CarRentalAdapter rentalAdapter;
    
    public RentalManagerJDBC(DbConnection conn) throws RentalManagerException {
        try {
            carAdapter = new CarAdapter(conn);
            customerAdapter = new CustomerAdapter(conn);
            rentalAdapter = new CarRentalAdapter(conn, carAdapter, customerAdapter);
        } catch (AdapterException ex) {
            throw new RentalManagerException(ex);
        }
    }  
    
    @Override
    public CarRental rent(Customer customer, Car car, int days) throws RentalManagerException {
        CarRental rental = new CarRental();
        rental.setCar(car);
        rental.setCustomer(customer);
        rental.setStartDate(new GregorianCalendar());
        rental.setDays(days);
        
        try {
            rentalAdapter.addObject(rental);
        } catch (AdapterException ex) {
            throw new RentalManagerException(ex);
        }
        
        return rental;
    }
    
    @Override
    public void closeRentalById(int id) throws RentalManagerException {
        try {
            rentalAdapter.removeObject(id);
        } catch (AdapterException ex) {
            throw new RentalManagerException(ex);
        }
    }
    
    @Override
    public void closeRental(CarRental rental) throws RentalManagerException {
        try {
            rentalAdapter.removeObject(rental);
        } catch (AdapterException ex) {
            throw new RentalManagerException(ex);
        }
    }
    
    @Override
    public List<CarRental> listRentals() throws RentalManagerException {
        try {
            return rentalAdapter.getObjects();
        } catch (Exception ex) {
            throw new RentalManagerException(ex);
        }
    }
    
    @Override
    public List<Car> listCars() throws RentalManagerException {
        try {
            return carAdapter.getObjects();
        } catch (Exception ex) {
            throw new RentalManagerException(ex);
        }
    }

    @Override
    public List<Customer> listCustomers() throws RentalManagerException {
        try {
            return customerAdapter.getObjects();
        } catch (Exception ex) {
            throw new RentalManagerException(ex);
        }
    }
    
    @Override
    public Car getCarById(int id) throws RentalManagerException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("ID", id);
        
        List<Car> cars;
        try {
            cars = carAdapter.getObjects(params);
        } catch (Exception ex) {
            throw new RentalManagerException(ex);
        }
        
        if(cars.isEmpty())
            return null;
        return cars.get(0);        
    }
    
    @Override
    public Customer getCustomerById(int id) throws RentalManagerException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("ID", id);
        
        List<Customer> customers;
        try {
            customers = customerAdapter.getObjects(params);
        } catch (Exception ex) {
            throw new RentalManagerException(ex);
        }
        
        if(customers.isEmpty())
            return null;
        return customers.get(0);        
    }
    
    @Override
    public void addCar(Car car) throws RentalManagerException {
        try {
            carAdapter.addObject(car);
        } catch (AdapterException ex) {
            throw new RentalManagerException(ex);
        }
    }
    
    @Override
    public void removeCar(Car car) throws RentalManagerException {
        try {
            carAdapter.removeObject(car);
        } catch (AdapterException ex) {
            throw new RentalManagerException(ex);
        }
    }
    
    @Override
    public void removeCar(int id) throws RentalManagerException {
        try {
            carAdapter.removeObject(id);
        } catch (AdapterException ex) {
            throw new RentalManagerException(ex);
        }
    }
    
    @Override
    public void addCustomer(Customer customer) throws RentalManagerException {
        try {
            customerAdapter.addObject(customer);
        } catch (AdapterException ex) {
            throw new RentalManagerException(ex);
        }
    }
    
    @Override
    public void removeCustomer(Customer customer) throws RentalManagerException {
        try {
            customerAdapter.removeObject(customer);
        } catch (AdapterException ex) {
            throw new RentalManagerException(ex);
        }
    }
    
    @Override
    public void removeCustomer(int id) throws RentalManagerException {
        try {
            customerAdapter.removeObject(id);
        } catch (AdapterException ex) {
            throw new RentalManagerException(ex);
        }
    }

    @Override
    public void updateCustomer(Customer customer) throws RentalManagerException {
        try {
            customerAdapter.saveObject(customer);
        } catch (AdapterException ex) {
            throw new RentalManagerException(ex);
        }
    }
}
