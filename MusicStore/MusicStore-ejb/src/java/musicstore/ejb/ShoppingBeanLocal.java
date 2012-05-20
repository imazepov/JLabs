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
    void removeItem(Customer customer, Article item);

    void submitOrder(Customer customer);
    Invoice getOrder(Customer customer);
}
