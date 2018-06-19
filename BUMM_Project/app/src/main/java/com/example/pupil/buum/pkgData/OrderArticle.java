package com.example.pupil.buum.pkgData;

import java.util.Objects;

/**
 * Created by Melanie on 6/19/18.
 */

public class OrderArticle {
    private Article orderedArticle;
    private int amount;

    public OrderArticle() {
    }

    public OrderArticle(Article orderedArticle, int amount) {
        this.orderedArticle = orderedArticle;
        this.amount = amount;
    }


    public Article getOrderedArticle() {
        return orderedArticle;
    }

    public void setOrderedArticle(Article orderedArticle) {
        this.orderedArticle = orderedArticle;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "OrderArticle{" + "orderedArticle=" + orderedArticle + ", amount=" + amount + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.orderedArticle);
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
        final OrderArticle other = (OrderArticle) obj;
        if (!Objects.equals(this.orderedArticle, other.orderedArticle)) {
            return false;
        }
        return true;
    }
}
