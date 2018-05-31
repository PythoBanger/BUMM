/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgData;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author schueler
 */
@XmlRootElement
public class Article {
    int artNr, onStock, changeStock;
    String name, description, artCategory;
    float price;

    public Article() {
    }

    
    public Article(int artNr, int onStock, String name, String description, String artCategory, float price) {
        this(artNr,onStock,0,name,description,artCategory,price);
    }

    public Article(int artNr, int onStock, int changeStock, String name, String description, String artCategory, float price) {
        this.artNr = artNr;
        this.onStock = onStock;
        this.changeStock = changeStock;
        this.name = name;
        this.description = description;
        this.artCategory = artCategory;
        this.price = price;
    }

    
    public int getArtNr() {
        return artNr;
    }

    public void setArtNr(int artNr) {
        this.artNr = artNr;
    }

    public int getOnStock() {
        return onStock;
    }

    public void setOnStock(int onStock) {
        this.onStock = onStock;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getArtCategory() {
        return artCategory;
    }

    public void setArtCategory(String artCategory) {
        this.artCategory = artCategory;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getChangeStock() {
        return changeStock;
    }

    public void setChangeStock(int changeStock) {
        this.changeStock = changeStock;
    }


    
    
    @Override
    public String toString() {
        return "Article{" + "artNr=" + artNr + ", onStock=" + onStock + ", decStock=" + changeStock + ", name=" + name + ", description=" + description + ", artCategory=" + artCategory + ", price=" + price + '}';
    }
    
    
    
    
}
