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
        return "Category{" + "curCategory=" + curCategory + ", parentCategory=" + parentCategory + ", children=" + children + '}';
    }
    
    
    
    
}
