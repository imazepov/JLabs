/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.adapters;

import java.util.HashMap;
import java.util.List;
import data.DbConnection;
import java.sql.SQLException;
import model.Car;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ivan
 */
public class CarAdapterTest {
    
    public CarAdapterTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void testAddObject() throws SQLException, AdapterException {
        DbConnection conn = DbConnection.create("localhost", 3306, "carrental", "rent", "CarRental");
        
        Car newCar = new Car();
        newCar.setMake("Ferrari");
        newCar.setModel("F550");
        newCar.setEngineVolume(8.2f);
        newCar.setYear(1990);
        
        CarAdapter adapter = new CarAdapter(conn);
        List<Car> cars1 = adapter.getObjects();
        
        adapter.saveObject(newCar);
        
        List<Car> cars2 = adapter.getObjects();
        final Car dbCar = cars2.get(cars2.size()-1);
        
        assertEquals(cars1.size()+1, cars2.size());
        assertEquals(newCar.getId(), dbCar.getId());
        assertEquals("Ferrari", dbCar.getMake());
        assertEquals("F550", dbCar.getModel());
        assertEquals(8.2f, dbCar.getEngineVolume(), 0.0f);
        assertEquals(1990, dbCar.getYear());
        
        adapter.removeObject(newCar);
        
        List<Car> cars3 = adapter.getObjects();
        
        assertArrayEquals(cars1.toArray(), cars3.toArray());
    }
    
    @Test
    public void testUpdateObject() throws SQLException, AdapterException {
        DbConnection conn = DbConnection.create("localhost", 3306, "carrental", "rent", "CarRental");
        
        Car newCar = new Car();
        newCar.setMake("Ferrari");
        newCar.setModel("F550");
        newCar.setEngineVolume(8.2f);
        newCar.setYear(1990);
        
        CarAdapter adapter = new CarAdapter(conn);        
        adapter.saveObject(newCar);
        
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("ID", newCar.getId());
        
        List<Car> cars = adapter.getObjects();
        Car dbCar = cars.get(cars.size()-1);
        
        dbCar.setYear(1992);
        
        adapter.saveObject(dbCar);
        
        List<Car> dbCars = adapter.getObjects(params);
        assertEquals(1, dbCars.size());
        
        Car dbModifiedCar = dbCars.get(0);
        
        assertEquals(1992, dbModifiedCar.getYear());
    }
}
