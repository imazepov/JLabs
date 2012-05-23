/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package musicstore.modelview;

import musicstore.data.Article;

/**
 *
 * @author ivan
 */
public class ArticleInvoiceRecord {
    private Article article;
    private int count;

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }   
}
