/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package musicstore.ejb;

import java.security.InvalidParameterException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import musicstore.data.Article;
import musicstore.data.Customer;
import musicstore.data.Invoice;

/**
 *
 * @author Ivan
 */
@Stateful
public class ShoppingBean implements ShoppingBeanLocal {
    
    @Resource
    private SessionContext session;
    
    @PersistenceContext(unitName="MusicStore")
    private EntityManager entityManager;
    
    private HashMap<Customer, Invoice> activeInvoices = new HashMap<Customer, Invoice>();
    
    @Override
    public synchronized void addItem(Customer customer, Article item) {
        Invoice curInvoice = activeInvoices.get(customer);
        if (curInvoice == null) {
            curInvoice = new Invoice();
            curInvoice.setCustomer(customer);
            curInvoice.setSubmitted(false);
            activeInvoices.put(customer, curInvoice);
        }
        
        List<Article> items = curInvoice.getArticleList();
        items.add(item);
    }
    
    @Override
    public synchronized void removeItem(Customer customer, Article item) {
        Invoice curInvoice = activeInvoices.get(customer);
        if (curInvoice == null) {
            throw new InvalidParameterException("The customer doesn't have an active order");
        }
        
        List<Article> items = curInvoice.getArticleList();
        items.remove(item);
    }

    @Override
    public void submitOrder(Customer customer) {
        Invoice invoice = activeInvoices.get(customer);
        if (invoice == null) {
            throw new InvalidParameterException("The customer doesn't have an active order");
        }
        
        invoice.setDate(new Date());
        invoice.setSubmitted(true);
        
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        
        entityManager.persist(invoice);
        
        transaction.commit();
    }
    
    @Override
    public Invoice getOrder(Customer customer) {
        return activeInvoices.get(customer);
    }
        
}
