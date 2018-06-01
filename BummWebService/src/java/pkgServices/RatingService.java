/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgServices;

import com.google.gson.Gson;
import java.sql.SQLException;
import java.util.Collection;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
import pkgData.Database;
import pkgData.Rating;

/**
 *
 * @author schueler
 */
@Path("ratings")
public class RatingService {

    @Context
    private UriInfo context;
    Database db = null;
    Gson gson;
    /**
     * Creates a new instance of UserService
     */
    public RatingService() {
        try {
            db = Database.newInstance();
            gson = new Gson();
        } catch (Exception ex) {
            System.out.println("error while trying to create db.");
        }
    }

    //get all ratings of each article for eg. admin want too see every article to check if no bad words are..
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAllRatings() throws Exception {
        return Response.ok(gson.toJson(db.getAllRatings())).build();
    }

    //returns ratings of specific article for eg customer
    @GET
    @Path("/{artNr}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getRatingsOfArticle(@PathParam("artNr") int artNr) throws Exception {
        return Response.ok(gson.toJson(db.getRatings(artNr))).build();
    }



    //adds rating to db 
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response addNewRating(String newR) throws Exception {
        Response r = Response.ok().build();
      
        try {
            db.addRating(gson.fromJson(newR,Rating.class));
        }catch (SQLException e) {
            r= Response.status(Response.Status.BAD_REQUEST).entity("u've already rated this article.").build();;
        } catch(Exception ex){
            r= Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();;
        }

        return r;
    }

    
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    public Response updateRating(String rToUpdate) throws Exception{
        Response r = Response.ok().build();
        try{         
            db.updateRating(gson.fromJson(rToUpdate,Rating.class));
        }catch(Exception ex){
            r= Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();;
        }
        return r;
    }
    
    
    @DELETE
    @Path("/{artNr}/{username}") 
    @Produces({MediaType.APPLICATION_JSON})
    public Response deleteRatingV2(@PathParam("artNr") int artNr, @PathParam("username") String username) throws Exception{
        Response isDeleted= Response.ok().build();
        try{    
            db.deleteRating(username, artNr);
        }catch(Exception ex){
            isDeleted = Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
        
        return isDeleted;
    }
    
}
