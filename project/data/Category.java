package com.example.schueler.project.data;

import java.util.ArrayList;

public class Category {
    private String curCategory, parentCategory;
    private ArrayList<Category> children;

    public Category() {
    }

    public Category(String curCategory, String parentCategory) {
        this.curCategory = curCategory;
        this.parentCategory = parentCategory;
        children = new ArrayList<>();
    }

    public String getCurCategory() {
        return curCategory;
    }

    public void setCurCategory(String curCategory) {
        this.curCategory = curCategory;
    }

    public String getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(String parentCategory) {
        this.parentCategory = parentCategory;
    }

    public ArrayList<Category> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<Category> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return curCategory;
    }
}
