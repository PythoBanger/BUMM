package com.example.pupil.buum.pkgData;

import java.util.*;

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
