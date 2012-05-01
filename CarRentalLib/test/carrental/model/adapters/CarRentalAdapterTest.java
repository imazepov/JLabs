/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package carrental.model.adapters;

import carrental.data.DbConnection;
import carrental.model.Car;
import carrental.model.CarRental;
import carrental.model.Customer;
import java.sql.SQLException;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author ivan
 */
public class CarRentalAdapterTest {
    
    public CarRentalAdapterTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void testAddObject() throws Exception {
        DbConnection conn = DbConnection.create("localhost", 3306, "carrental", "rent", "CarRental");
        
        Car newCar = new Car();
        newCar.setMake("Ferrari");
        newCar.setModel("F550");
        newCar.setEngineVolume(8.2f);
        newCar.setYear(1990);
        
        Customer newCustomer = new Customer();
        newCustomer.setFirstName("Terry");
        newCustomer.setLastName("Johnson");
        newCustomer.setIdCode("09876543211234567890");
        newCustomer.setRegisterDate(new GregorianCalendar());
        
        CarRental carRental = new CarRental();
        carRental.setCar(newCar);
        carRental.setCustomer(newCustomer);
        carRental.setDays(10);
        final GregorianCalendar curCal = new GregorianCalendar();
        carRental.setStartDate(curCal);
        
        CarAdapter carAdapter = new CarAdapter(conn);
        CustomerAdapter customerAdapter = new CustomerAdapter(conn);
        CarRentalAdapter adapter = new CarRentalAdapter(conn, carAdapter, customerAdapter);
        
        List<CarRental> rentals1 = adapter.getObjects();
        
        adapter.saveObject(carRental);
        
        List<CarRental> rentals2 = adapter.getObjects();
        
        assertEquals(rentals1.size()+1, rentals2.size());
        final CarRental dbRental = rentals2.get(rentals2.size()-1);
        assertEquals(10, dbRental.getDays());
        assertEquals(newCar, dbRental.getCar());
        assertEquals(newCustomer, dbRental.getCustomer());
        
        adapter.removeObject(dbRental);
        carAdapter.removeObject(dbRental.getCar());
        customerAdapter.removeObject(dbRental.getCustomer());
    }
    
    public void testUpdateObject() throws AdapterException, SQLException {
        DbConnection conn = DbConnection.create("localhost", 3306, "carrental", "rent", "CarRental");
        
        Car newCar = new Car();
        newCar.setMake("Ferrari");
        newCar.setModel("F550");
        newCar.setEngineVolume(8.2f);
        newCar.setYear(1990);
        
        Customer newCustomer = new Customer();
        newCustomer.setFirstName("Terry");
        newCustomer.setLastName("Johnson");
        newCustomer.setIdCode("09876543211234567890");
        newCustomer.setRegisterDate(new GregorianCalendar());
        
        CarRental carRental = new CarRental();
        carRental.setCar(newCar);
        carRental.setCustomer(newCustomer);
        carRental.setDays(10);
        final GregorianCalendar curCal = new GregorianCalendar();
        carRental.setStartDate(curCal);
        
        CarAdapter carAdapter = new CarAdapter(conn);
        CustomerAdapter customerAdapter = new CustomerAdapter(conn);
        CarRentalAdapter adapter = new CarRentalAdapter(conn, carAdapter, customerAdapter);
                
        adapter.saveObject(carRental);
        
        carRental.setDays(5);
        
        adapter.saveObject(carRental);
        
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("ID", carRental.getId());
        List<CarRental> rentals = adapter.getObjects(params);
        
        assertEquals(1, rentals.size());
        
        CarRental dbRental = rentals.get(0);
        
        assertEquals(5, dbRental.getDays());
        
        adapter.removeObject(carRental);
        carAdapter.removeObject(carRental.getCar());
        customerAdapter.removeObject(carRental.getCustomer());
    }
}
