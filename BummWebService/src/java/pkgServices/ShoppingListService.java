/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgServices;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import pkgData.Article;
import pkgData.Database;
import pkgData.ShoppingList;

/**
 *
 * @author schueler
 */
@Path("shoppingList")
public class ShoppingListService {
    
    @Context
    private UriInfo context;
    
    Database db = null;


    public ShoppingListService() {
         try{
            db = Database.newInstance();
        }catch(Exception ex){
            System.out.println("error while trying to create db.");
        }
    }
    
    //default return
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public String getShoppingCart() throws Exception {   
       return "u are visiting the shopping cart wesbervice";
    }
   
    //get  shoppingList Of specific User
    @GET
    @Path("/{username}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ShoppingList getShoppingCart(@PathParam("username") String username) throws Exception {   
        return db.getShoppingList(username);       
    }
    
    //returns the article with the article id from the users shoppinglist
    @GET
    @Path("/{username}/{artNr}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Article getShoppingCart(@PathParam("username") String username, @PathParam("artNr") int artNr) throws Exception {   
        //return db.getShoppingList(username);  
        Article a = db.getArticleFromShoppingList(username, artNr);
        if(a==null)
            throw new Exception("user does not have this article in his shopping list");
        
        return a;
    }
    
    @POST
    @Path("/{username}")
    @Consumes({MediaType.APPLICATION_JSON})
    public String addArticleToList(@PathParam("username") String username, Article a) throws Exception{
        String addStatus="add is ok";
        try{
            db.addArticleToShoppingList(username,a);
        }catch(Exception ex){
            addStatus=ex.getMessage();
        }
       
        return addStatus;
    }

    
    //not ok... always calls get....
    @DELETE
    @Path("/{username}/{artNr}")
    public String deleteArticleFromList(@PathParam("username") String username, @PathParam("artNr") int artNr ) throws Exception{
        String addStatus="delete is ok";
        try{
            db.deleteArticleFromShoppingList(username,artNr);
        }catch(Exception ex){
            addStatus=ex.getMessage();
        }
       
        return addStatus;
    }
    
}
