/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package musicstore.controllers;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import musicstore.data.SiteUser;
import musicstore.ejb.ListingBeanLocal;
import musicstore.ejb.ShoppingBeanLocal;
import musicstore.misc.JsfUtil;

/**
 *
 * @author ivan
 */
@ManagedBean(name = "login")
@SessionScoped
public class LoginController implements Serializable, HttpSessionListener {
    
    @EJB
    private ShoppingBeanLocal shoppingBean;
    
    @EJB
    private ListingBeanLocal listingBean;
        
    private Boolean isLoggedIn;
    
    private SiteUser user;
    
    private String userName;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public boolean getIsLoggedIn() {
        if (isLoggedIn == null) {
            isLoggedIn = getLoggedInUser() != null;
        }
        return isLoggedIn;
    }
    
    public SiteUser getLoggedInUser() {
        HttpServletRequest request = JsfUtil.getRequest();
        String requestUser = request.getRemoteUser();
        if (user == null && requestUser != null) {            
            user = listingBean.getUser(requestUser);
        }
        
        return user;
    }
    
    public LoginController() {
        
    }
    
    public String login() {
        HttpServletRequest request = JsfUtil.getRequest();
        try {
            request.login(userName, password);
            
            user = listingBean.getUser(userName);
            HttpSession session = JsfUtil.getSession(true);
            
            session.setAttribute("user", user);
            
            if (user.getCustomer() != null) {
                if (!shoppingBean.hasCustomerOrder(user.getCustomer())) {
                    shoppingBean.assignCustomerToOrder(user.getCustomer(), session.getId());
                }
            }
            
            isLoggedIn = true;
            
            return "success";
        } catch (ServletException ex) {
            JsfUtil.addMessage(FacesMessage.SEVERITY_ERROR, "An Error Occured: Login failed", null);
            ex.printStackTrace();
            isLoggedIn = false;
        }
        
        return "fail";
    }
    
    public String logout() {
        HttpSession session = JsfUtil.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        
        return "logout";
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) { 
        shoppingBean.createOrder(se.getSession().getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        shoppingBean.discardOrder(se.getSession().getId());
    }
}
