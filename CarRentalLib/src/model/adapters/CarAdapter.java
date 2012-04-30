/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.adapters;

import data.DbConnection;
import model.Car;

/**
 *
 * @author ivan
 */
public class CarAdapter extends Adapter<Car> {
    public CarAdapter(DbConnection conn) throws AdapterException {
        super(conn, Car.class);
    }
}
