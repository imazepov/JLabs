/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package musicstore.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import musicstore.data.Article;
import musicstore.data.Category;
import musicstore.data.Customer;
import musicstore.data.Invoice;
import musicstore.data.SiteUser;
import musicstore.ejb.ListingBeanLocal;
import musicstore.ejb.ShoppingBeanLocal;
import musicstore.misc.JsfUtil;
import musicstore.modelview.ArticleInvoiceRecord;

/**
 *
 * @author Ivan
 */
@ManagedBean(name="shopping")
@SessionScoped
public class ShoppingController implements Serializable {
    
    @EJB
    private ListingBeanLocal listingBean;
    
    @EJB
    private ShoppingBeanLocal shoppingBean;
       
    public ShoppingController() {
    }
    
    public List<Category> getCategories() {
        return listingBean.getCategories();
    }
    
    private List<Article> goods;
    public List<Article> getGoods() {
        int categoryId = getParameter("category");
        goods = listingBean.getGoods(categoryId);
        
        return goods;
    }
    
    public String addToCart() {
        int articleId = getParameter("article");
        Article article = listingBean.getArticle(articleId);
        
        Customer customer = JsfUtil.getCurrentCustomer();
        if (customer == null) {        
            shoppingBean.addItem(getSessionId(), article);
        } else {
            shoppingBean.addItem(customer, article);
        }
        
        return "back_to_list";
    }
    
    public String removeFromCart() {
        int articleId = getParameter("article");
        Article article = listingBean.getArticle(articleId);
        
        Customer customer = JsfUtil.getCurrentCustomer();        
        if (customer == null) {
            shoppingBean.removeItem(getSessionId(), article);
        } else {
            shoppingBean.removeItem(customer, article);
        }
        
        return "back_to_list";
    }
    
    public void clearCart() {
        String sessionId = getSessionId();
        shoppingBean.createOrder(sessionId);
        Customer customer = JsfUtil.getCurrentCustomer();
        if (customer != null) {
            shoppingBean.assignCustomerToOrder(customer, sessionId);
        }
    }   
    
    public List<ArticleInvoiceRecord> getInvoiceArticles() {
        List<ArticleInvoiceRecord> result = new ArrayList<ArticleInvoiceRecord>();
        
        Invoice invoice;
        Customer customer = JsfUtil.getCurrentCustomer();
        if (customer == null) {
            invoice = shoppingBean.getOrder(getSessionId());
        } else {
            invoice = shoppingBean.getOrder(customer);
        }
        
        HashMap<Article, Integer> articles = new HashMap<Article, Integer>();
        for (Article article : invoice.getArticleList()) {
            Integer c = articles.get(article);
            if (c == null) {
                c = 1;
            } else {
                c++;
            }
            
            articles.put(article, c);
        }
        
        for (Entry<Article, Integer> entry : articles.entrySet()) {
            ArticleInvoiceRecord record = new ArticleInvoiceRecord();
            record.setArticle(entry.getKey());
            record.setCount(entry.getValue());
            result.add(record);
        }
        
        return result;
    }
    
    public float getInvoiceTotal() {
        Invoice invoice;
        Customer customer = JsfUtil.getCurrentCustomer();
        if (customer == null) {
            invoice = shoppingBean.getOrder(getSessionId());
        } else {
            invoice = shoppingBean.getOrder(customer);
        }
        
        float sum = 0.0f;
        for (Article article : invoice.getArticleList()) {
            sum += article.getPrice();
        }
        
        return sum;
    }

    private String getSessionId() {
        HttpSession session = JsfUtil.getSession(true);
        return session.getId();
    }

    private int getParameter(String name) { 
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Map<String, String> requestParams = facesContext.getExternalContext().getRequestParameterMap();
        try {            
            String paramStr = requestParams.get(name);
            if (paramStr == null) {
                return 0;
            }
            
            return Integer.parseInt(paramStr);
        } catch (NumberFormatException ex) {
            throw ex;
        }
    }
}
