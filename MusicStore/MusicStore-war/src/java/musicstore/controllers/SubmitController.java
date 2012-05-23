/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package musicstore.controllers;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import musicstore.data.Customer;
import musicstore.ejb.ShoppingBeanLocal;
import musicstore.misc.JsfUtil;

/**
 *
 * @author ivan
 */
@ManagedBean(name = "submit")
@RequestScoped
public class SubmitController {

    @EJB
    private ShoppingBeanLocal shoppingBean;
    
    private String phone;
    private String address;
    
    public SubmitController() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String submit() {
        Customer customer = JsfUtil.getCurrentCustomer();
        if (customer != null) {
            shoppingBean.submitOrder(customer, phone, address);
            return "success";
        } else {
            return "login_required";
        }
    }
}
