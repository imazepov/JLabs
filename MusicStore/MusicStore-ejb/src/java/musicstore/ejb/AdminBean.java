/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package musicstore.ejb;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import musicstore.data.Article;
import musicstore.data.Category;

/**
 *
 * @author ivan
 */
@Stateless
@RolesAllowed("Admin")
public class AdminBean implements AdminBeanLocal {

    private static EntityManagerFactory enitityManagerFactory;
    
    private EntityManager getManager() {
        if(enitityManagerFactory == null) {
            enitityManagerFactory = Persistence.createEntityManagerFactory("MusicStore");
        }
        
        return enitityManagerFactory.createEntityManager();
    }
    
    @Override
    public void addArticle(String name, String photoFileName, String description, float price, Category category) {
        EntityManager em = getManager();
        
        try {
            Article article = new Article();
            article.setName(name);
            article.setPhotoFileName(photoFileName);
            article.setDescription(description);
            article.setPrice(price);
            article.setCategory(category);
            article.setVisible(true);

            em.persist(article);        
        } finally {
            em.close();
        }        
    }

    @Override
    public void removeArticle(long id) {
        EntityManager em = getManager();
        try {
            TypedQuery<Article> query = em.createNamedQuery("Article.findById", Article.class);
            query.setParameter("id", id);
            try {
                Article article = query.getSingleResult();
                article.setVisible(false);
                em.merge(article);
            } catch (Exception ex) {
                return;
            }
        } finally {
            em.close();
        }
    }

    @Override
    public void addCategory(String name) {
        EntityManager em = getManager();
        try {
            Category category = new Category();
            category.setName(name);
            category.setVisible(true);
            
            em.persist(category);
        } finally {
            em.close();
        }
    }

    @Override
    public void removeCategory(long id) {
        EntityManager em = getManager();
        try {
            TypedQuery<Category> query = em.createNamedQuery("Category.findById", Category.class);
            query.setParameter("id", id);
            try {
                Category category = query.getSingleResult();
                category.setVisible(false);
                em.merge(category);
            } catch (Exception ex) {
                return;
            }
        } finally {
            em.close();
        }
    }

    private Category getCategory(long categoryId) {
        EntityManager em = getManager();
        
        try {
            TypedQuery<Category> query = em.createNamedQuery("Category.findById", Category.class);
            query.setParameter("id", categoryId);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }
}
