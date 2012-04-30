/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.adapters;

import data.DbConnection;
import model.Customer;

/**
 *
 * @author ivan
 */
public class CustomerAdapter extends Adapter<Customer> {
    public CustomerAdapter(DbConnection conn) throws AdapterException {
        super(conn, Customer.class);
    }
}
