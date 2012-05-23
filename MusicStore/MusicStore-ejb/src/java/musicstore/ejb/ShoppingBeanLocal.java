/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package musicstore.ejb;

import javax.ejb.Local;
import musicstore.data.Article;
import musicstore.data.Customer;
import musicstore.data.Invoice;

/**
 *
 * @author Ivan
 */
@Local
public interface ShoppingBeanLocal {
    
    void addItem(Customer customer, Article item);
    void addItem(String sessionId, Article item);
    void removeItem(Customer customer, Article item);
    void removeItem(String sessionId, Article item);

    void assignCustomerToOrder(Customer customer, String sessionId);
    
    void submitOrder(Customer customer, String phone, String address);
    void discardOrder(String sessionId);
    
    Invoice getOrder(Customer customer);
    Invoice getOrder(String sessionId);

    void createOrder(String sessionId);

    boolean hasCustomerOrder(Customer customer);
}
