/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package musicstore.ejb;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NamedQuery;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.UserTransaction;
import musicstore.data.Article;
import musicstore.data.Category;
import musicstore.data.Customer;
import musicstore.data.SiteUser;

/**
 *
 * @author ivan
 */
@Stateless
public class ListingBean implements ListingBeanLocal {

    private static EntityManagerFactory enitityManagerFactory;
    
    private EntityManager getManager() {
        if(enitityManagerFactory == null) {
            enitityManagerFactory = Persistence.createEntityManagerFactory("MusicStore");
        }
        
        return enitityManagerFactory.createEntityManager();
    }
    
    @Override
    public List<Category> getCategories() {
        EntityManager em = getManager();
        
        try {
            Query query = em.createNamedQuery("Category.findByVisible");
            query.setParameter("visible", true);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Article> getGoods(int categoryId) {
        EntityManager em = getManager();
        
        try { 
            if (categoryId == 0) {
                TypedQuery<Article> query = em.createNamedQuery("Article.findByVisible", Article.class);
                query.setParameter("visible", true);
                return query.getResultList();  
            } else {
                TypedQuery<Article> query = em.createNamedQuery("Article.findByCategory", Article.class);
                query.setParameter("categoryId", categoryId);

                return query.getResultList();
            }
        } finally {
            em.close();
        }
    }

    @Override
    public Article getArticle(long id) {
        EntityManager em = getManager();
        
        try {
            TypedQuery<Article> query = em.createNamedQuery("Article.findById", Article.class);
            query.setParameter("id", id);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }

    @Override
    public SiteUser getUser(String login) {
        EntityManager em = getManager();
        
        try {
            TypedQuery<SiteUser> query = em.createNamedQuery("SiteUser.findByLogin", SiteUser.class);
            query.setParameter("login", login);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }
    
    @Resource
    private SessionContext context;
    
    @Override
    public void registerUser(String login, String password, String name, String email, String phone) throws Exception {
        if (login == null || login.isEmpty())
            throw new Exception("Логин не может быть пустым");
        if (password == null || password.isEmpty())
            throw new Exception("Логин не может быть пустым");
        if (name == null || name.isEmpty())
            throw new Exception("Имя не может быть пустым");
        if (email == null || email.isEmpty())
            throw new Exception("E-Mail не может быть пустым");
        if (phone == null || phone.isEmpty())
            throw new Exception("Номер телефона не может быть пустым");
        
        EntityManager em = getManager();
        try {
            TypedQuery<Long> userQuery = em.createNamedQuery("SiteUser.countByLogin", Long.class);
            userQuery.setParameter("login", login);
            if((Long)userQuery.getSingleResult() > 0L) {
                throw new Exception("Такой пользователь уже существует");
            }
            
            String passwordDigestString = getStringDigest(password);
            
                       
            SiteUser user = new SiteUser(login, passwordDigestString);
            em.persist(user);
            
            try {
                Customer customer = new Customer();
                customer.setName(name);
                customer.setEmail(email);
                customer.setPhone(phone);
                customer.setSiteUser(user);
                em.persist(customer);
            } catch (Exception ex) {
                context.setRollbackOnly();
                throw ex;
            }
        } finally {
            em.close();
        }
    }

    private String getStringDigest(String password) {
        MessageDigest digestObj = null;
        try {
            digestObj = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex) {
            // Can't happen
        }
        
        byte[] bytes = null;
        try {
            bytes = password.getBytes(Charset.isSupported("UTF-8") 
                    ? "UTF-8" 
                    : Charset.defaultCharset().name());
        } catch (UnsupportedEncodingException ex) {
            // Can't happen
        }
        
        byte[] digest = digestObj.digest(bytes);
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(String.format("%1$02x", b));
        }
        
        return sb.toString();
    }

    @Override
    public Category getCategoryById(int id) {
        EntityManager em = getManager();
        
        try {
            return em.find(Category.class, (int)id);
        } finally {
            em.close();
        }            
    }
}
