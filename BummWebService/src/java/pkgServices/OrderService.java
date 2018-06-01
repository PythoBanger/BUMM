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
import pkgData.Order;
import pkgData.User;

/**
 *
 * @author schueler
 */
@Path("orders")
public class OrderService {

    @Context
    private UriInfo context;
    Gson gson;
    Database db = null;

    public OrderService() {
        try {
            db = Database.newInstance();
            gson = new Gson();
        } catch (Exception ex) {
            System.out.println("error while trying to create db.");
        }
    }
    
    @GET //returns every order from a specific user
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllOrders() throws Exception {
        return Response.ok().entity(gson.toJson(db.getAllOrders())).build();
    } 
    
    @GET //returns specific order
    @Path("/order/{orderId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getSpecificOrder(@PathParam("orderId") int orderId) throws Exception {
        return Response.ok().entity(gson.toJson(db.getSpecificOrder(orderId))).build();
    }     
    
    @GET //returns every order from a specific user
    @Path("/{username}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllOrdersFromUser(@PathParam("username") String username) throws Exception {
        return Response.ok().entity(gson.toJson(db.getAllOrdersFromUser(username))).build();
    } 
    
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response addOrderTODB(String order) throws Exception {
        Response r = Response.ok().build();
        try {
            db.addOrder(gson.fromJson(order,Order.class));
        }catch (Exception ex) {
            r = Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }

        return r;
    }
    
    
    @DELETE
    @Path("/{orderId}")  
    @Produces({MediaType.APPLICATION_JSON})
    public Response deleteOrder(@PathParam("orderId") int orderId) throws Exception{ //=bestellung stornieren
        Response isDeleted= Response.ok().build();
        try{    
            db.deleteOrder(orderId);
        }catch(Exception ex){
            isDeleted = Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
        
        return isDeleted;
    }
    
    
    @DELETE
    @Path("/{orderId}/{artNr}")  
    @Produces({MediaType.APPLICATION_JSON})
    public Response deleteSpecificOrderItem(@PathParam("orderId") int orderId,@PathParam("artNr") int artNr) throws Exception{ //bestimmte produkte von order stornieren
        Response isDeleted= Response.ok().build();
        try{    
            db.deleteSpecificArtFromOrder(orderId,artNr);
        }catch(Exception ex){
            isDeleted = Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
        
        return isDeleted;
    }
}
