/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package musicstore.converters;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.naming.NamingException;
import musicstore.data.Category;
import musicstore.ejb.ListingBeanLocal;

/**
 *
 * @author ivan
 */
@FacesConverter(forClass=Category.class)
@ManagedBean(name = "categoryConverter")
@RequestScoped
public class CategoryConverter implements Converter {

    @EJB
    private ListingBeanLocal listingBean;    
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try {            
            return listingBean.getCategoryById(Integer.parseInt(value));            
        } catch (NumberFormatException ex) {            
            ex.printStackTrace();
        }
        
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof Category) {
            return ((Category)value).getId().toString();
        }
        
        return null;
    }
    
}
