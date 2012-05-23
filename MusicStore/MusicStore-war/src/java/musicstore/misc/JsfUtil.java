/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package musicstore.misc;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import musicstore.data.Customer;
import musicstore.data.SiteUser;

/**
 *
 * @author ivan
 */
public final class JsfUtil {
    private JsfUtil() {}
    
    public static HttpSession getSession(boolean create) {
        HttpSession session = (HttpSession)FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getSession(create);
        return session;
    }
    
    public static HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest)FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getRequest();
        return request;
    }
    
    public static void addMessage(Severity severity, String message, String detail) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, message, detail));
    }
    
    public static SiteUser getLoggedInUser() {
        Object userObj = getSession(true).getAttribute("user");
        if (userObj instanceof SiteUser) {
            return (SiteUser)userObj;
        }
        
        return null;
    }
    
    public static Customer getCurrentCustomer() {
        SiteUser user = getLoggedInUser();
        if (user != null) {
            return user.getCustomer();
        }
        
        return null;
    }
    
    public static boolean isUserInRole(String role) {
        return FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .isUserInRole(role);
    }
}
