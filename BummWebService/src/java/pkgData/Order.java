/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgData;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author schueler
 */
@XmlRootElement
public class Order {
    private int orderId;
    private User userWhoOrdered;
    private LocalDate orderDate;
    private ArrayList<OrderArticle> allOrderesArticles = new ArrayList<>();
    public Order() {
    }
    
    
    

    public Order(User userWhoOrdered) { 
        this(-99,userWhoOrdered);
    }

    public Order(int orderId, User userWhoOrdered) {
        this.orderId = orderId;
        this.userWhoOrdered = userWhoOrdered;
    }
    
    public Order(int orderId, User userWhoOrdered, LocalDate orderDate) {
        this.orderId = orderId;
        this.userWhoOrdered = userWhoOrdered;
        this.orderDate = orderDate;
    }
    

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
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

    public ArrayList<OrderArticle> getAllOrderesArticles() {
        return allOrderesArticles;
    }

    public void setAllOrderesArticles(ArrayList<OrderArticle> allOrderesArticles) {
        this.allOrderesArticles = allOrderesArticles;
    }

    public void addArticle(OrderArticle oa) throws Exception{
        this.allOrderesArticles.add(oa);
    }
    @Override
    public String toString() {
        return "Order{" + "orderId=" + orderId + ", userWhoOrdered=" + userWhoOrdered + ", allOrderesArticles=" + allOrderesArticles + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + this.orderId;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Order other = (Order) obj;
        if (this.orderId != other.orderId) {
            return false;
        }
        return true;
    }


    
    
    
    
}
