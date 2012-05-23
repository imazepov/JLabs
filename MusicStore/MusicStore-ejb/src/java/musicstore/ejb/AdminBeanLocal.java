/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package musicstore.ejb;

import javax.ejb.Local;
import musicstore.data.Category;

/**
 *
 * @author ivan
 */
@Local
public interface AdminBeanLocal {

    void addArticle(String name, String photoFileName, String description, float price, Category category);

    void removeArticle(long id);

    void addCategory(String name);

    void removeCategory(long id);
    
}
