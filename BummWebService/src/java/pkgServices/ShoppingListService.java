/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgServices;

import com.google.gson.Gson;
import java.sql.SQLException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import pkgData.Article;
import pkgData.Database;

/**
 *
 * @author schueler
 */
@Path("shoppingList")
public class ShoppingListService {
    
    @Context
    private UriInfo context;
    Gson gson;
    Database db = null;


    public ShoppingListService() {
         try{
            db = Database.newInstance();
            gson = new Gson();
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
    @Produces({MediaType.APPLICATION_JSON})
    public Response getShoppingCart(@PathParam("username") String username) throws Exception {
        return Response.ok().entity(gson.toJson(db.getShoppingList(username))).build();       
    }
    
    // not secessary right now...
    @GET
    @Path("/{username}/{artNr}")
    @Produces({MediaType.APPLICATION_JSON})
    public Article getShoppingCart(@PathParam("username") String username, @PathParam("artNr") int artNr) throws Exception {   
        Article a = db.getArticleFromShoppingList(username, artNr);
        if(a==null)
            throw new Exception("user does not have this article in his shopping list");
        
        return a;
    }
    
    @POST
    @Path("/{username}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response addArticleToList(@PathParam("username") String username, Article a) throws Exception{
        
        Response r = Response.ok().build();
      
        try{
            db.addArticleToShoppingList(username,a);
        } catch (SQLException e ) {
            r = Response.status(Response.Status.BAD_REQUEST).entity("article already added in list").build();
        }catch(Exception ex){
            r = Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
       
        return r;
    }

 
    @DELETE //semiprof but delete request does not work
    @Path("/{artNr}/{username}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response deleteArticleFromList(@PathParam("artNr") int artNr,@PathParam("username") String username) throws Exception{
        Response isDeleted= Response.ok().build();
        try{    
            db.deleteArticleFromShoppingList(username, artNr);
        }catch(Exception ex){
            isDeleted = Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
        
        return isDeleted;
    }
    
}
