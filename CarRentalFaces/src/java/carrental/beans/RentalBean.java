/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package carrental.beans;

import carrental.management.Management;
import carrental.management.ManagementException;
import carrental.management.RentalManager;
import carrental.management.RentalManagerException;
import carrental.misc.Util;
import carrental.model.CarRental;
import carrental.model.Customer;
import java.util.Calendar;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

/**
 *
 * @author Ivan
 */
@ManagedBean(name="rental")
@SessionScoped
public class RentalBean {

    private DataModel<Customer> customerModel;
    private Customer customer;
    
    private DataModel<CarRental> rentalModel;
    private CarRental carRental;
            
    public RentalBean() throws ManagementException, RentalManagerException {
        RentalManager manager = Management.getManager();
        
        customerModel = new ListDataModel<Customer>(manager.listCustomers());        
        rentalModel = new ListDataModel<CarRental>(manager.listRentals());
    }
    
    public DataModel getCustomerModel() {
        return customerModel;
    }
    
    public DataModel<CarRental> getRentalModel() {
        return rentalModel;
    }
    
    public Customer getCustomer() {
        return customer;
    }
    
    public SelectItem[] getCustomerSelection() {
        return Util.getSelectItems((List<Customer>)customerModel.getWrappedData());
    }
    
    public CarRental getCarRental() {
        return carRental;
    }
    
    public String prepareEditCustomer() {
        customer = rentalModel.getRowData().getCustomer();
        return "edit_customer";
    }
    
    public String editCustomer() throws ManagementException, RentalManagerException {
        RentalManager manager = Management.getManager();        
        manager.updateCustomer(customer);        
        return "list_data";
    }
    
    public String prepareAddRental() {
        customer = new Customer();
        carRental = new CarRental();
        
        return "rent_car";
    }
    
    public String addRental() throws ManagementException, RentalManagerException {
        if(customer.getRegisterDate() == null)
            customer.setRegisterDate(Calendar.getInstance());        
        
        RentalManager manager = Management.getManager();
        manager.addCustomer(customer);
        
        manager.rent(customer, carRental.getCar(), carRental.getDays());
        
        reloadRentals(manager);
                
        return "list_data";
    }
    
    public String addRentalExistingCustomer() throws ManagementException, RentalManagerException {
        RentalManager manager = Management.getManager();
        
        manager.rent(customer, carRental.getCar(), carRental.getDays());
                
        return "list_data";
    }
    
    public String closeRental() throws ManagementException, RentalManagerException {
        RentalManager manager = Management.getManager();
        
        carRental = rentalModel.getRowData();
        manager.closeRental(carRental);
        manager.removeCustomer(carRental.getCustomer());        
        
        reloadRentals(manager);
        
        return "list_data";
    }
    
    private void reloadRentals(RentalManager manager) throws RentalManagerException {
        rentalModel.setWrappedData(manager.listRentals());
    } 
}
