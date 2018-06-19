/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgServices;

import com.google.gson.Gson;
import java.util.Collection;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import pkgData.Article;
import pkgData.Database;
import pkgData.User;

/**
 *
 * @author schueler
 */
@Path("articles")
public class ArticleService {
    
    @Context
    private UriInfo context;
    Gson gson;
    Database db = null;


    public ArticleService() {
         try{
            db = Database.newInstance();
            gson = new Gson();
        }catch(Exception ex){
            System.out.println("error while trying to create db.");
        }
    }
    
    //gets all articles ordered by articleId (default)
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getArticles() throws Exception {  
        return Response.ok().entity(gson.toJson(db.getAllArticles())).build();       
    }
      
    @GET
    @Path("/{artNr}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getArticle(@PathParam("artNr") int artNr) throws Exception {   
        Article a= db.getArticle(artNr);
        Response r =null;
        System.out.println(":"+a);
        if(a==null)
            r = Response.status(Response.Status.NOT_FOUND).entity("article not found").build();
        else
            r = Response.ok().entity(gson.toJson(a)).build();
        
        return r;
    }

    
    @GET
    @Path("/filter")
    @Produces({MediaType.APPLICATION_JSON})
    public Response filterArticles(@Context HttpHeaders httpHeaders) throws Exception {   
        String nameToFilter = httpHeaders.getRequestHeader("name").get(0);
        String categoryName = httpHeaders.getRequestHeader("category").get(0);
        if(categoryName.length()==0)
                categoryName="Alle Artikel"; //default category
        
       return Response.ok().entity(gson.toJson(db.filterArticles(nameToFilter,categoryName))).build();
    }

   
    @GET
    @Path("/restock")
    public Response getArticlesToRestock() throws Exception {   
       return Response.ok().entity(gson.toJson(db.getArticlesToRestock())).build();
    }
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response addNewArticle(String newArticle) throws Exception{
        Response r = Response.ok().build();
        try{
            db.addArticle(gson.fromJson(newArticle, Article.class));
        }catch(Exception ex){
            r = Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
        return r;
    }

    
    //updates article. not finished eg admin and onstock
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    public Response updateArticle(String articleToUpdate) throws Exception{
        Response r = Response.ok().build();
        try{
            db.updateArticle(gson.fromJson(articleToUpdate, Article.class));
        }catch(Exception ex){
            r = Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
        return r;
    }

    //TODO: delete article but erst beim artikel gedanken machen......
    //
    /*@PUT
    @Path("/decrease")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public String decreaseOnStock(Article articleToUpdate) throws Exception{
        String isUpdated="onstock decreased";
        try{
            db.decreaseOnStock(articleToUpdate);
        }catch(Exception ex){
            isUpdated=ex.getMessage();
        }
        return isUpdated;
    }*/

}
