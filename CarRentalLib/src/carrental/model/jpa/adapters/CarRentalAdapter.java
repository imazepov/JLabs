/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package carrental.model.jpa.adapters;

import carrental.model.CarRental;
import java.util.GregorianCalendar;
import javax.persistence.EntityManager;

/**
 *
 * @author Ivan
 */
public final class CarRentalAdapter {
    private CarRentalAdapter() {        
    }
    
    public static carrental.model.jpa.entities.CarRental convertCarRentalToEntity(CarRental carRental) {
        return convertCarRentalToEntity(carRental, null);
    }
    
    public static carrental.model.jpa.entities.CarRental convertCarRentalToEntity(CarRental carRental, EntityManager manager) {
        carrental.model.jpa.entities.CarRental entity = new carrental.model.jpa.entities.CarRental();
        
        entity.setId(carRental.getId());
        entity.setCarID(CarAdapter.convertCarToEntity(carRental.getCar(), manager));
        entity.setCustomerID(CustomerAdapter.convertCustomerToEntity(carRental.getCustomer(), manager));
        entity.setDays(carRental.getDays());
        entity.setStartDate(carRental.getStartDate().getTime());
        
        if(manager != null)
            manager.merge(entity);
        
        return entity;
    }
    
    public static CarRental convertEntityToCarRental(carrental.model.jpa.entities.CarRental entity) {
        CarRental carRental = new CarRental();
        
        carRental.setId(entity.getId());
        carRental.setCar(CarAdapter.convertEntityToCar(entity.getCarID()));
        carRental.setCustomer(CustomerAdapter.convertEntityToCustomer(entity.getCustomerID()));
        carRental.setDays(entity.getDays());
        
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(entity.getStartDate());
        
        carRental.setStartDate(calendar);
        
        return carRental;        
    }
}
