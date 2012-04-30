/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import misc.ReferenceField;

/**
 *
 * @author ivan
 */
public class CarRental extends Model {
    private Timestamp startDate;
    private int days;
    
    @ReferenceField
    Car car;
    @ReferenceField
    Customer customer;

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public Calendar getStartDate() {
        Calendar cal = new GregorianCalendar();
        cal.setTimeInMillis(startDate.getTime());
        return cal;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = new Timestamp(startDate.getTimeInMillis());
    }   
}
