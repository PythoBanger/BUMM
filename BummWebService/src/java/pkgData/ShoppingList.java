/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgData;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author schueler
 */
@XmlRootElement
public class ShoppingList {
    private User listOwner;
    private ArrayList<Article> articlesInList = new ArrayList<>();

    public ShoppingList() {
    }

    public ShoppingList(User listOwner) {
        this.listOwner = listOwner;
    }

    public User getListOwner() {
        return listOwner;
    }

    public void setListOwner(User listOwner) {
        this.listOwner = listOwner;
    }

    public ArrayList<Article> getArticlesInList() {
        return articlesInList;
    }

    public void setArticlesInList(ArrayList<Article> articlesInList) {
        this.articlesInList = articlesInList;
    }

    public void addArticle(Article a) throws Exception{
        articlesInList.add(a);
    }
    @Override
    public String toString() {
        return "ShoppingList{" + "listOwner="  + listOwner.getUsername()+ ", articlesInList=" + articlesInList + '}';
    }
    
    
    
    
    
}
