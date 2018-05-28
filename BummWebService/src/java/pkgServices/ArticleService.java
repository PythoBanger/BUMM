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
import javax.ws.rs.core.UriInfo;
import pkgData.Article;
import pkgData.Database;

/**
 *
 * @author schueler
 */
@Path("articles")
public class ArticleService {
    
    @Context
    private UriInfo context;
    
    Database db = null;


    public ArticleService() {
         try{
            db = Database.newInstance();
        }catch(Exception ex){
            System.out.println("error while trying to create db.");
        }
    }
    
    //gets all articles ordered by articleId (default)
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public String getArticles() throws Exception {  
        return new Gson().toJson(db.getAllArticles());       
    }
      
    @GET
    @Path("/{artNr}")
    @Produces({MediaType.APPLICATION_JSON})
    public Article getArticle(@PathParam("artNr") int artNr) throws Exception {   
        Article a= db.getArticle(artNr);
        return a;
    }

    @GET
    @Path("/filter")
    @Produces({MediaType.APPLICATION_JSON})
    public Collection<Article> filterArticles(@Context HttpHeaders httpHeaders) throws Exception {   
        String nameToFilter = httpHeaders.getRequestHeader("name").get(0);
        String categoryName = httpHeaders.getRequestHeader("category").get(0);
        if(categoryName.length()==0)
                categoryName="Alle Artikel"; //default category
        
       return db.filterArticles(nameToFilter,categoryName);
    }

   
    //creates a new article
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public String addNewArticle(Article newArticle) throws Exception{
        String isAdded="new article added";
        try{
            db.addArticle(newArticle);
        }catch(Exception ex){
            isAdded=ex.getMessage();
        }
        return isAdded;
    }

    
    //updates article. not finished eg admin and onstock
    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public String updateArticle(Article articleToUpdate) throws Exception{
        String isUpdated="article updated";
        try{
            db.updateArticle(articleToUpdate);
        }catch(Exception ex){
            isUpdated=ex.getMessage();
        }
        return isUpdated;
    }

    //TODO: delete article but erst beim artikel gedanken machen......

    
    //
    @PUT
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
    }

}
