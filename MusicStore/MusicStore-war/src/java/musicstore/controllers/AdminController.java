/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package musicstore.controllers;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Map;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import musicstore.data.Article;
import musicstore.data.Category;
import musicstore.ejb.AdminBeanLocal;
import musicstore.misc.JsfUtil;

/**
 *
 * @author ivan
 */
@ManagedBean(name = "admin")
@SessionScoped
public class AdminController implements Serializable {

    @EJB
    private AdminBeanLocal adminBean;
    
    private String articleName;
    private String articlePhoto;
    private String articleDescription;
    private float articlePrice;
    private Category articleCategory;
    private String categoryName;
    
    private long categoryToRemove;
    private long articleToRemove;
    
    public AdminController() {
    }
    
    public boolean getIsAllowed() {
        return JsfUtil.isUserInRole("Admin");        
    }
    
    public String addCategory() {
        try {
            adminBean.addCategory(categoryName);
            return "admin_ok";
        } catch (Exception ex) {
            JsfUtil.addMessage(FacesMessage.SEVERITY_ERROR, ex.getCause().getMessage(), null);
            return "admin_fail";
        }
    }
    
    public String removeCategory() {
        try {
            adminBean.removeCategory(categoryToRemove);
            return "admin_ok";
        } catch (Exception ex) {
            JsfUtil.addMessage(FacesMessage.SEVERITY_ERROR, ex.getCause().getMessage(), null);
            return "admin_fail";
        }
    }
    
    public String addArticle() {
        try {            
            adminBean.addArticle(articleName, articlePhoto, articleDescription, articlePrice, articleCategory);
            return "admin_ok";
        } catch (Exception ex) {
            JsfUtil.addMessage(FacesMessage.SEVERITY_ERROR, ex.getCause().getMessage(), null);
            return "admin_fail";
        }
    }
    
    public String removeArticle() {
        try {
            adminBean.removeArticle(articleToRemove);
            return "admin_ok";
        } catch (Exception ex) {
            JsfUtil.addMessage(FacesMessage.SEVERITY_ERROR, ex.getCause().getMessage(), null);
            return "admin_fail";
        }
    }
    
    public String getArticleDescription() {
        return articleDescription;
    }

    public void setArticleDescription(String articleDescription) {
        this.articleDescription = articleDescription;
    }

    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public String getArticlePhoto() {
        return articlePhoto;
    }

    public void setArticlePhoto(String articlePhoto) {
        this.articlePhoto = articlePhoto;
    }

    public float getArticlePrice() {
        return articlePrice;
    }

    public void setArticlePrice(float articlePrice) {
        this.articlePrice = articlePrice;
    }

    public long getArticleToRemove() {
        return articleToRemove;
    }

    public void setArticleToRemove(long articleToRemove) {
        this.articleToRemove = articleToRemove;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public long getCategoryToRemove() {
        return categoryToRemove;
    }

    public void setCategoryToRemove(long categoryToRemove) {
        this.categoryToRemove = categoryToRemove;
    }
    
    public Category getArticleCategory() {
        return articleCategory;
    }

    public void setArticleCategory(Category articleCategory) {
        this.articleCategory = articleCategory;
    }
    
}
