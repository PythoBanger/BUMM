/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgServices;

import java.time.LocalDate;
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

    /**
     * Creates a new instance of UserService
     */
    public UserService() {
         try{
            db = Database.newInstance();
        }catch(Exception ex){
            System.out.println("error while trying to create db.");
        }
    }

    //get all users
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Collection<User> getUsers() throws Exception {  
        return db.getAllUsers();
    }
    
    //returns specific user by id: eg if admin checks a specific user he only needs username
    @GET
    @Path("/{username}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public User getUser(@PathParam("username") String username ) throws Exception{
        User u= db.getUser(username);
        if(u==null)
            throw new Exception("no user with that id found");
        
        return u;
    }
    
     
    @POST
    @Path("/login")
    @Consumes({MediaType.APPLICATION_JSON})
    public User login(User loginData) throws Exception{
        User u = db.getUser(loginData.getUsername(), loginData.getPassword()); 
        if(u==null)
            throw new Exception("user with un and pw not found..");
        return u;
    }
    
  
    //creates new User //{"username": "admin", "password":"nimda", "location": "Austria", "zipCode":9500, "email":"savanluffy@gmail.com","firstName":"savan","lastName":"maljuric","address":"villach","birthdate":null,"role":"customer","imageData":null}
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public String addNewUser(User newUser) throws Exception{
        String isAdded="new user added";
        newUser.setBirthdate(LocalDate.now());
        System.out.println(""+newUser);
        try{
            db.addUser(newUser);
        }catch(Exception ex){
            isAdded=ex.getMessage();
        }
        return isAdded;
    }
    
    
    //updates user
    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public String updateUser(User userToUpdate) throws Exception{
        String isUpdated="user updated";
        try{
            userToUpdate.setBirthdate(LocalDate.now());
            db.updateUser(userToUpdate);
        }catch(Exception ex){
            isUpdated=ex.getMessage();
        }
        return isUpdated;
    }
    
    
    

    //eventuell noch delete aber mal schauen erst bei admin gedanken machen
    /* not needed anymore
    @DELETE
    @Path("/{username}")
    public boolean deleteUserWithID(@PathParam("username") String username) throws Exception{      
        boolean isDeleted=true;
        try{
            isDeleted = db.deleteUserById(username);
        }catch(Exception ex){ //uknown error
            isDeleted=false;
        }
        return isDeleted;
    }*/

    /* not needed anymore: activate blockiert instead
    public boolean deleteUserById(String username) throws Exception { 
        conn = createConnection();
        PreparedStatement ps = conn.prepareStatement("DELETE * FROM BummUser WHERE username=? AND role = 'customer'"); //admins cant delete themselves..
        ps.setString(1, username);
        boolean isDeleted = ps.executeQuery().rowDeleted();          
        conn.close();
        return isDeleted;
    }*/
    
    

}

