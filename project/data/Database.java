package com.example.schueler.project.data;

import com.example.schueler.project.misc.ControllerSync;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Database {
    private static Database db=null;
    private static ControllerSync controller = null;
    private static String url = null;
    private static String curUsername=null;

    private Database() {
    }

    public static Database newInstance(){
        if(db==null)
            db= new Database();
        return db;
    }

    public static void setURL(String _url){
        url=_url;
    }

    //return all customers (for admin only)
    public ArrayList<User> getUsers() throws Exception{
        Gson gson = new Gson();
        controller = new ControllerSync(url);

        controller.execute("GETALLUSERS");
        String strFromWebService = controller.get();
        Type collectionType = new TypeToken<ArrayList<User>>() {}.getType();
        ArrayList<User> vec = gson.fromJson(strFromWebService, collectionType);

        return vec;
    }

    //k for now. BUT SEMIPROOF: SHOULD WORK WITH RESPONSE IN ORDER TO CATCH THE SPECIFIC EXCEPTIONS FROM THE WEBSERVICE INSTEAD RETURNING STRING......



    public User loginUser(User u) throws Exception{
        Gson gson = new Gson();
        controller = new ControllerSync(url);

        String paras[] = new String[2];
        paras[0] = "LOGINUSER";
        paras[1] = gson.toJson(u,User.class);

        controller.execute(paras);
        String strFromWebService = controller.get();
        Type collectionType = new TypeToken<User>() {}.getType();
        User loggedUser = gson.fromJson(strFromWebService, collectionType);
        if(loggedUser==null)
            throw new Exception("wrong pw or username....");
        if(!loggedUser.getStatus().equals("active"))
            throw new Exception("your account is currenty not active!");

        curUsername=loggedUser.getUsername();
        return loggedUser;
    }

    public String addUser(User user) throws Exception{
        Gson gson = new Gson();
        controller = new ControllerSync(url);

        String strUser = gson.toJson(user,User.class);

        String paras[] = new String[2];
        paras[0] = "ADDUSER";
        paras[1] = strUser;
        controller.execute(paras);
        String res=  controller.get();
        if(!res.equals("register ok"))
            throw new Exception(res);

        return res;
    }

    public String updateUser(User user) throws Exception{
        Gson gson = new Gson();
        controller = new ControllerSync(url);

        String strUser = gson.toJson(user,User.class);

        String paras[] = new String[2];
        paras[0] = "UPDATEUSER";
        paras[1] = strUser;
        controller.execute(paras);
        String res=  controller.get();
        if(!res.equals("user updated"))
            throw new Exception(res);

        return res;
    }

    public ArrayList<Article> getArticles() throws Exception{
        Gson gson = new Gson();
        controller = new ControllerSync(url);

        controller.execute("GETALLARTICLES");
        String strFromWebService = controller.get();
        System.out.println(""+strFromWebService);
        Type collectionType = new TypeToken<ArrayList<Article>>() {}.getType();
        ArrayList<Article> vec = gson.fromJson(strFromWebService, collectionType);

        return vec;
    }

    public Article getArticle(int artNr) throws Exception{
        Gson gson = new Gson();
        controller = new ControllerSync(url);
        String paras[] = new String[2];
        paras[0] = "GETARTICLE";
        paras[1] = ""+artNr;
        controller.execute(paras);
        String strFromWebService = controller.get();
        Type collectionType = new TypeToken<Article>() {}.getType();
        Article vec = gson.fromJson(strFromWebService, collectionType);
        if(vec==null)
            throw new Exception("no article was found....");
        return vec;
    }

    public ArrayList<Category> getCategories() throws Exception{
        Gson gson = new Gson();
        controller = new ControllerSync(url);

        controller.execute("GETALLCATEGORIES");
        String strFromWebService = controller.get();
        System.out.println(""+strFromWebService);
        Type collectionType = new TypeToken<ArrayList<Category>>() {}.getType();
        ArrayList<Category> vec = gson.fromJson(strFromWebService, collectionType);

        return vec;
    }

    public ArrayList<Article> filterArticles(String artName, String curCategory) throws Exception { //searches automatically in the children too
        Gson gson = new Gson();
        controller = new ControllerSync(url);

        String paras[] = new String[3];
        paras[0] = "FILTERARTICLES";
        paras[1] = artName;
        paras[2] = curCategory;
        controller.execute(paras);

        String strFromWebService = controller.get();


        Type collectionType = new TypeToken<ArrayList<Article>>() {}.getType();
        ArrayList<Article> vec = gson.fromJson(strFromWebService, collectionType);
        return vec;

    }


    public ArrayList<Rating> getRatingOfArticle(int artNr) throws Exception {
        Gson gson = new Gson();
        controller = new ControllerSync(url);
        String paras[] = new String[2];
        paras[0] = "GETRATINGSOFARTICLE";
        paras[1] = ""+artNr;
        controller.execute(paras);
        String strFromWebService = controller.get();
        Type collectionType = new TypeToken<ArrayList<Rating>>() {}.getType();
        ArrayList<Rating> vec = gson.fromJson(strFromWebService, collectionType);
        return  vec;

    }

    public String addRating(Rating r) throws Exception{
        Gson gson = new Gson();
        controller = new ControllerSync(url);

        String strUser = gson.toJson(r);

        String paras[] = new String[2];
        paras[0] = "ADDRATING";
        paras[1] = strUser;
        controller.execute(paras);
        String res=  controller.get();
        if(!res.equals("rating added")) //semi-prof todo: work with response
            throw new Exception(res);

        return res;
    }

    public String  updateRating(Rating r) throws Exception{
        Gson gson = new Gson();
        controller = new ControllerSync(url);

        String strRating = gson.toJson(r);

        String paras[] = new String[2];
        paras[0] = "UPDATERATING";
        paras[1] = strRating;
        controller.execute(paras);
        String res=  controller.get();
        if(!res.equals("rating updated")) //toDo: see how to make it better (with response)
            throw new Exception(res);

        return res;
    }

    //semi prof: bc post (delete request does not work)s
    public String deleteRating(Rating r) throws Exception {
        Gson gson = new Gson();
        controller = new ControllerSync(url);

        String strRating = gson.toJson(r);

        String paras[] = new String[2];
        paras[0] = "DELETERATING";
        paras[1] = strRating;
        controller.execute(paras);
        String res=  controller.get();
        if(!res.equals("rating deleted")) //toDo: see how to make it better (with response for eg.)
            throw new Exception(res);

        return res;
    }

    public void addArticleToList(String username, Article article) throws Exception{
    }
}
