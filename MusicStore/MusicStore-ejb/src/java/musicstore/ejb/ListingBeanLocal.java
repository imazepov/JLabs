/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package musicstore.ejb;

import java.util.List;
import javax.ejb.Local;
import musicstore.data.Article;
import musicstore.data.Category;
import musicstore.data.SiteUser;

/**
 *
 * @author ivan
 */
@Local
public interface ListingBeanLocal {

    List<Category> getCategories();
    List<Article> getGoods(int categoryId);

    Article getArticle(long id);

    SiteUser getUser(String login);

    void registerUser(String login, String password, String name, String email, String phone) throws Exception;

    Category getCategoryById(int id);
}
