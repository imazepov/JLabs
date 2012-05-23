/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package musicstore.ejb;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.ejb.Singleton;
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
@Singleton
public class ShoppingBean implements ShoppingBeanLocal {
    
    @PersistenceContext(unitName="MusicStore")
    private EntityManager entityManager;
    
    private final HashMap<Customer, Invoice> userInvoices = new HashMap<Customer, Invoice>();
    private final HashMap<String, Invoice> anonymousInvoices = new HashMap<String, Invoice>();
    
    @Override
    public void addItem(Customer customer, Article item) {
        synchronized (userInvoices) {
            Invoice curInvoice = userInvoices.get(customer);
            if (curInvoice == null) {
                curInvoice = new Invoice();
                curInvoice.setCustomer(customer);                
                userInvoices.put(customer, curInvoice);
            }

            List<Article> items = curInvoice.getArticleList();
            items.add(item);
        }
    }
    
    @Override
    public void addItem(String sessionId, Article item) {
        synchronized (anonymousInvoices) {
            Invoice curInvoice = anonymousInvoices.get(sessionId);
            if (curInvoice == null) {
                curInvoice = new Invoice();
                anonymousInvoices.put(sessionId, curInvoice);
            }
            
            List<Article> items = curInvoice.getArticleList();
            if (items == null) {
                items = new ArrayList<Article>();
                curInvoice.setArticleList(items);
            }
            
            items.add(item);
        }
    }
    
    
    @Override
    public synchronized void removeItem(Customer customer, Article item) {
        synchronized (userInvoices) {
            Invoice curInvoice = userInvoices.get(customer);
            if (curInvoice == null) {
                throw new InvalidParameterException("The customer doesn't have an active order");
            }

            List<Article> items = curInvoice.getArticleList();
            items.remove(item);
        }
    }

    @Override
    public void removeItem(String sessionId, Article item) {
        synchronized (anonymousInvoices) {
            Invoice curInvoice = anonymousInvoices.get(sessionId);
            if (curInvoice == null) {
                throw new InvalidParameterException("The customer doesn't have an active order");
            }

            List<Article> items = curInvoice.getArticleList();
            items.remove(item);
        }
    }
    
    @Override
    public void assignCustomerToOrder(Customer customer, String sessionId) {
        synchronized (userInvoices) {
            synchronized (anonymousInvoices) {
                Invoice invoice = anonymousInvoices.get(sessionId);
                if (invoice == null) {
                    throw new InvalidParameterException("No order is being made for given session");
                }
                
                invoice.setCustomer(customer);
                
                anonymousInvoices.remove(sessionId);
                userInvoices.put(customer, invoice);
            }
        }
    }
    
    @Override
    public Invoice getOrder(Customer customer) {
        return userInvoices.get(customer);
    }

    @Override
    public Invoice getOrder(String sessionId) {
        return anonymousInvoices.get(sessionId);
    }  

    @Override
    public boolean hasCustomerOrder(Customer customer) {
        return userInvoices.containsKey(customer);
    }

    @Override
    public void submitOrder(Customer customer, String phone, String address) {
        synchronized (userInvoices) {
            Invoice invoice = userInvoices.get(customer);
            if (invoice == null) {
                throw new InvalidParameterException("The customer doesn't have an active order");
            }

            invoice.setShipmentAddress(address);
            invoice.setPhone(phone);
            invoice.setDate(new Date());
            invoice.setSubmitted(true);
            
            entityManager.persist(invoice);

            userInvoices.put(customer, createInvoice());
        }
    }

        
    @Override
    public void createOrder(String sessionId) {
        anonymousInvoices.remove(sessionId);
        anonymousInvoices.put(sessionId, createInvoice());
    }

    private Invoice createInvoice() {
        Invoice invoice = new Invoice();
        invoice.setArticleList(new ArrayList<Article>());
        return invoice;
        
    }
    
    @Override
    public void discardOrder(String sessionId) {
        anonymousInvoices.remove(sessionId);
    }
}
