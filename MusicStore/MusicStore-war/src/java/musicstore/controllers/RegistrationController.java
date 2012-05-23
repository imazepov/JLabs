/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package musicstore.controllers;

import java.util.Set;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import musicstore.ejb.ListingBeanLocal;
import musicstore.misc.JsfUtil;

/**
 *
 * @author ivan
 */
@ManagedBean(name = "registration")
@RequestScoped
public class RegistrationController {

    private String login;
    private String password;
    private String name;
    private String email;
    private String phone;
    
    @EJB
    private ListingBeanLocal listingBean;
    
    public RegistrationController() {
    }
    
    public String register() {
        try {
            listingBean.registerUser(login, password, name, email, phone);
            return "success";
        } catch (Exception ex) {
            String message = ex.getMessage();
            if (message == null) {
                Throwable cause = ex.getCause();
                
                message = "Системная ошибка: ";
                if (cause != null) {
                    message += cause.getMessage();
                }
                
                if (cause instanceof ConstraintViolationException) {
                    Set<ConstraintViolation<?>> viols = ((ConstraintViolationException)cause).getConstraintViolations();
                    for (ConstraintViolation<?> v : viols) {
                        message += " --- " + v.getMessage();
                    }
                }
            }
            
            JsfUtil.addMessage(FacesMessage.SEVERITY_ERROR, message, null);
            ex.printStackTrace();
            return "fail";
        }        
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
