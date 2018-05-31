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


    public CategoryService() {
         try{
            db = Database.newInstance();
        }catch(Exception ex){
            System.out.println("error while trying to create db.");
        }
    }
    
     //gets all categories as a collection
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllCategories() throws Exception {   
        return Response.ok(new Gson().toJson(db.getAllCategories())).build();       
    }
    
    

}
