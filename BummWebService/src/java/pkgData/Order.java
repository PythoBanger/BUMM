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
public class Order {
    private int orderId;
    private User userWhoOrdered;
    private ArrayList<Article> orderedArticles = new ArrayList<>();

    public Order() {
    }

    public Order(int orderId, User userWhoOrdered) {
        this.orderId = orderId;
        this.userWhoOrdered = userWhoOrdered;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public User getUserWhoOrdered() {
        return userWhoOrdered;
    }

    public void setUserWhoOrdered(User userWhoOrdered) {
        this.userWhoOrdered = userWhoOrdered;
    }

    public ArrayList<Article> getOrderedArticles() {
        return orderedArticles;
    }

    public void setOrderedArticles(ArrayList<Article> orderedArticles) {
        this.orderedArticles = orderedArticles;
    }

    @Override
    public String toString() {
        return "orderId=" + orderId + ", userWhoOrdered=" + userWhoOrdered.getUsername() + ", orderedArticles=" + orderedArticles.toString() + '}';
    }
    
    
    
}
