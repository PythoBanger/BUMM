/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgServices;

import com.google.gson.Gson;
import java.util.Collection;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import pkgData.Category;
import pkgData.Database;

/**
 *
 * @author schueler
 */
@Path("categories")
public class CategoryService {
    
    @Context
    private UriInfo context;
    
    Database db = null;
    Gson gson;

    public CategoryService() {
         try{
            db = Database.newInstance();
            gson = new Gson();
        }catch(Exception ex){
            System.out.println("error while trying to create db.");
        }
    }
    
     //gets all categories as a collection
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllCategories() throws Exception {   
        return Response.ok(gson.toJson(db.getAllCategories())).build();       
    }
    
    //gets categories which are not parent = lowest children (to add article) 
    @GET
    @Path("/lowest")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getCategoriesWhichAreNotParents() throws Exception {   
        return Response.ok(gson.toJson(db.getLowestCategories())).build();       
    }

}
