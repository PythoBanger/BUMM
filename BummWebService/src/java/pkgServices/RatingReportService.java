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
import pkgData.Database;
import pkgData.RatingReport;

/**
 *
 * @author schueler
 */
@Path("ratingReports")
public class RatingReportService {

    Gson gson;
    Database db = null;

    /**
     * Creates a new instance of UserService
     */
    public RatingReportService() {
        try {
            db = Database.newInstance();
            gson = new Gson();
        } catch (Exception ex) {
            System.out.println("error while trying to create db.");
        }
    }
    //get all ratings of each article for eg. admin want too see every article to check if no bad words are..
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllRatingsReports() throws Exception {
        return Response.ok(gson.toJson(db.getAllRatingsReports())).build();
    }
    
    
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public Response addNewRatingReport(String newRP) throws Exception {
        Response r = Response.ok().build();
        try {
            db.addRatingReport(gson.fromJson(newRP,RatingReport.class));
        }catch(SQLException ex) {
            r= Response.status(Response.Status.BAD_REQUEST).entity("u've already reported the rating.").build();;
        } catch(Exception ex){
            r= Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();;
        }

        return r;
    }

    @DELETE
    @Path("/{artNr}/{reportedUser}")  //if i delete rating reports will autoomactically also be delete(see db) but for eg if a lot of people report comment but comment is valid i am going to delete the unneccesarry reports
    @Produces({MediaType.APPLICATION_JSON})
    public Response deleteRatingReport(@PathParam("artNr") int artNr, @PathParam("reportedUser") String username) throws Exception{
        Response isDeleted= Response.ok().build();
        try{    
            db.deleteRatingReport(username,artNr);
        }catch(Exception ex){
            isDeleted = Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
        
        return isDeleted;
    }
    
}
