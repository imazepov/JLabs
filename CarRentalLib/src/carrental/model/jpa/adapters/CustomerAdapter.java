/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package carrental.model.jpa.adapters;

import carrental.model.Customer;
import java.util.GregorianCalendar;
import javax.persistence.EntityManager;

/**
 *
 * @author Ivan
 */
public final class CustomerAdapter {
    private CustomerAdapter() {        
    }
    
    public static carrental.model.jpa.entities.Customer convertCustomerToEntity(Customer customer) {
        return convertCustomerToEntity(customer, null);
    }
    
    public static carrental.model.jpa.entities.Customer convertCustomerToEntity(Customer customer, EntityManager manager) {
        carrental.model.jpa.entities.Customer entity = new carrental.model.jpa.entities.Customer();
        
        entity.setId(customer.getId());
        entity.setFirstName(customer.getFirstName());
        entity.setLastName(customer.getLastName());
        entity.setIdCode(customer.getIdCode());
        entity.setRegisterDate(customer.getRegisterDate().getTime());
        
        if(manager != null)
            manager.merge(entity);
        
        return entity;
    }
    
    public static Customer convertEntityToCustomer(carrental.model.jpa.entities.Customer entity) {
        Customer customer = new Customer();
        
        customer.setId(entity.getId());
        customer.setFirstName(entity.getFirstName());
        customer.setLastName(entity.getLastName());
        customer.setIdCode(entity.getIdCode());
        
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(entity.getRegisterDate());
        
        customer.setRegisterDate(calendar);
        
        return customer;
    }  
}
