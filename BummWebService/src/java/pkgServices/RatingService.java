/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgServices;

import java.util.Collection;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
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

    /**
     * Creates a new instance of UserService
     */
    public RatingService() {
        try {
            db = Database.newInstance();
        } catch (Exception ex) {
            System.out.println("error while trying to create db.");
        }
    }

    //get all ratings of each article for eg. admin want too see every article to check if no bad words are..
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Collection<Rating> getUsers() throws Exception {
        return db.getAllRatings();
    }

    //returns ratings of specific article for eg customer
    @GET
    @Path("/{artNr}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Collection<Rating> getUser(@PathParam("artNr") int artNr) throws Exception {
        return db.getRatings(artNr);
    }

    //return specific rating of specific user and article
    @GET
    @Path("/{artNr}/{username}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Rating getUser(@PathParam("artNr") int artNr, @PathParam("username") String username) throws Exception {
        Rating r = db.getRating(username, artNr);
        if (r == null) {
            throw new Exception("no rating with from that user with this article found...");
        }

        return r;
    }

    //adds rating to db 
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public String addNewRating(Rating newRating) throws Exception {
        String addStatus = "add is ok";
      
        try {
            db.addRating(newRating);
        } catch (Exception ex) {
            addStatus = ex.getMessage();
        }

        return addStatus;
    }

    
    @PUT
    @Path("/{artNr}/{username}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public String updateRating(@PathParam("artNr") int artNr, @PathParam("username") String username, Rating ratingToUpdate) throws Exception{
        String isUpdated="rating updated";
        try{
            if(!ratingToUpdate.getUserWhoRated().getUsername().equals(username) || ratingToUpdate.getRatedArticle().getArtNr()!=artNr)
                throw new Exception("u can only update your own articles.....");
            
            
            db.updateRating(ratingToUpdate);
        }catch(Exception ex){
            isUpdated=ex.getMessage();
        }
        return isUpdated;
    }
    
    
}
