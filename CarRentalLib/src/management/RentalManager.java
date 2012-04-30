/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package management;

import data.DbConnection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import model.Car;
import model.CarRental;
import model.Customer;
import model.adapters.AdapterException;
import model.adapters.CarAdapter;
import model.adapters.CarRentalAdapter;
import model.adapters.CustomerAdapter;

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
    
    public CarRental rent(Customer customer, Car car) {
        CarRental rental = new CarRental();
        rental.setCar(car);
        rental.setCustomer(customer);
        
        return rental;
    }
    
    public List<CarRental> listRentals() throws SQLException, AdapterException {
        return rentalAdapter.getObjects(new HashMap<String, Object>());
    }
    
    public List<Car> listCars() {
        ArrayList<Car> list = new ArrayList<Car>();
        
        return list;
    }
}
