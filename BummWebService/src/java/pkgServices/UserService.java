/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgServices;

import com.google.gson.Gson;
import java.sql.SQLException;
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
import javax.ws.rs.core.Response;
import pkgData.Database;
import pkgData.User;

/**
 *
 * @author schueler
 */
////shift+alt+f
@Path("users")
public class UserService {

    @Context
    private UriInfo context;
    Database db = null;
    Gson gson;
    /**
     * Creates a new instance of UserService
     */
    public UserService() {
        try {
            db = Database.newInstance();
            gson=new Gson();
        } catch (Exception ex) {
            System.out.println("error while trying to create db.");
        }
    }

    //get all users
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getUsers() throws Exception {
        return Response.ok().entity(gson.toJson(db.getAllUsers())).build();
    }
    

    //returns specific user by id: eg if admin checks a specific user he only needs username
    @GET
    @Path("/{username}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getUser(@PathParam("username") String username) throws Exception {
        User u = db.getUser(username);
        Response r=null;
        if (u == null) {
            r= Response.status(Response.Status.NOT_FOUND).entity("user not found").build();
        }else
            r = Response.ok().entity(gson.toJson(u)).build();

        return r;
    }

    @GET
    @Path("/filter")
    @Produces({MediaType.APPLICATION_JSON})
    public Response filterUsers(@Context HttpHeaders httpHeaders) throws Exception {   
       String usernameToFilter = httpHeaders.getRequestHeader("username").get(0);        
       return Response.ok().entity(gson.toJson(db.filterUsers(usernameToFilter))).build();
    }

    @POST
    @Path("/login")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response login(User loginData) throws Exception {
        Response r;
        User u = db.getUser(loginData.getUsername(), loginData.getPassword());
        if (u != null) 
            r = Response.ok().entity(gson.toJson(u,User.class)).build();
        else
            r= Response.status(Response.Status.NOT_FOUND).entity("user not found").build();
        
        return r;
    }

    
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response addNewUser(String newUser) throws Exception {
        Response r = Response.ok().build();        
        try {
            db.addUser(gson.fromJson(newUser, User.class));
        } catch (SQLException e) { //catches SQLIntegratonError so I already now what happened
            r= Response.status(Response.Status.BAD_REQUEST).entity("user already exists").build();
        } catch(Exception ex){
            r= Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
        return r;
    }

    //updates user
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    public Response updateUser(String userToUpdate) throws Exception {
        Response r = Response.ok().build();        
        try {
            db.updateUser(gson.fromJson(userToUpdate, User.class));
        } catch (Exception ex) {
            r= Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
        return r;
    }

}
