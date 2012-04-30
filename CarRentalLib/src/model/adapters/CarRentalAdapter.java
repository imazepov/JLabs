/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.adapters;

import data.DbConnection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Car;
import model.CarRental;
import model.Customer;

/**
 *
 * @author ivan
 */
public class CarRentalAdapter extends Adapter<CarRental> {
    
    private CarAdapter carAdapter;
    private CustomerAdapter customerAdapter;
    
    public CarRentalAdapter(DbConnection conn, CarAdapter carAdapter, CustomerAdapter customerAdapter) 
            throws AdapterException {
        super(conn, CarRental.class);
        this.carAdapter = carAdapter;
        this.customerAdapter = customerAdapter;
    }
    
    @Override
    protected CarRental getObjectFromResultSet(ResultSet rs, ResultSetMetaData metaData) 
            throws SQLException, AdapterException {
        CarRental result = super.getObjectFromResultSet(rs, metaData);
                
        int fieldCount = metaData.getColumnCount();
        for(int i=1; i<=fieldCount; i++) {
            final String tableName = metaData.getTableName(i);
            if(tableName.equalsIgnoreCase("Car")) {
                result.setCar(carAdapter.getObjectFromResultSet(rs, metaData));
            } else if(tableName.equalsIgnoreCase("Customer")) {
                result.setCustomer(customerAdapter.getObjectFromResultSet(rs, metaData));
            }
        }
        
        return result;
    }
    
    @Override
    public int addObject(CarRental rental) throws AdapterException {
        if(rental.getCar() == null || rental.getCustomer() == null) {
            throw new AdapterException("Some reference fields are missing");
        }
        
        Car car = rental.getCar();
        Customer customer = rental.getCustomer();
        
        int carId = car.getId();        
        if(carId == 0) {            
            carId = carAdapter.addObject(car);
        }
        
        int customerId = customer.getId();
        if(customerId == 0) {
            customerId = customerAdapter.addObject(customer);
        }
        
        int carRentalId = super.addObject(rental);
        
        ArrayList params = new ArrayList();
        params.add(carId);
        params.add(customerId);
        try {
            connection.executeQuery("UPDATE CarRental SET CarID = ?, CustomerID = ?", params);
        } catch (SQLException ex) {
            throw new AdapterException("Failed to update reference id fields", ex);
        }
        
        return carRentalId;
    }
}
