package com.example.pupil.buum.pkgData;

import com.example.pupil.buum.pkgMisc.ControllerSync;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Database {
    private static Database db=null;
    private static ControllerSync controller = null;
    private static String url = null;
    private static User curUser =null;

    private Database() {
    }

    public static Database newInstance(){
        if(db==null)
            db= new Database();
        return db;
    }

    public static User getCurUser() {
        return curUser;
    }

    public static void setCurUser(User curUser) {
        Database.curUser = curUser;
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
            throw new Exception("your account is currenty disabled!");

        curUser =loggedUser;
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
        System.out.println(""+res);
        if(res.length()==0){
            res="register ok";
        }else {
            throw new Exception(res);
        }
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
        if(res.length()==0){
            res="update ok";
        }else {
            throw new Exception(res);
        }
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

    public ArrayList<Category> getCategories() throws Exception {
        Gson gson = new Gson();
        controller = new ControllerSync(url);

        controller.execute("GETALLCATEGORIES");
        String strFromWebService = controller.get();
        System.out.println("" + strFromWebService);
        Type collectionType = new TypeToken<ArrayList<Category>>() {
        }.getType();
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
        if(res.length()==0){
            res="add ok";
        }else {
            throw new Exception(res);
        }
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
        if(res.length()==0){
            res="update ok";
        }else {
            throw new Exception(res);
        }
        return res;
    }

    //semi prof: bc post (delete request does not work) ask org
    /*public String deleteRating(Rating r) throws Exception {
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
    }*/

    public String addArticleToList(String username, Article article) throws Exception{
        Gson gson = new Gson();
        controller = new ControllerSync(url);

        String strArticle = gson.toJson(article);

        String paras[] = new String[3];
        paras[0] = "ADDARTICLETOLIST";
        paras[1] = strArticle;
        paras[2]= username;
        controller.execute(paras);
        String res=  controller.get();
        if(res.length()==0){
            res="add ok";
        }else {
            throw new Exception(res);
        }
        return res;
    }

    public String deleteArticleFromList(String username, Article article) throws Exception{
        Gson gson = new Gson();
        controller = new ControllerSync(url);

        String paras[] = new String[2];
        paras[0] = "DELETEARTICLEFROMLIST";
        paras[1] = ""+article.getArtNr()+"/"+username;
        controller.execute(paras);
        String res=  controller.get();
        if(res.length()==0){
            res="delete ok";
        }else {
            throw new Exception(res);
        }
        return res;
    }
    public ArrayList<Article> getShoppingListOfUser(User curUsername) throws Exception {
        Gson gson = new Gson();
        controller = new ControllerSync(url);
        String paras[] = new String[2];
        paras[0] = "GETSHOPPINGLISTOFUSER";
        paras[1] = ""+curUsername.getUsername();

        controller.execute(paras);
        String strFromWebService = controller.get();
        Type collectionType = new TypeToken<ShoppingList>() {}.getType();
        ShoppingList sl = gson.fromJson(strFromWebService, collectionType);
        db.curUser.setShoppingList(sl);
        return sl.getArticlesInList();
    }

    public Category getParentCategory(String curCategoryName) throws Exception{
        Gson gson = new Gson();
        controller = new ControllerSync(url);
        String paras[] = new String[2];
        paras[0] = "GETPARENTCATEGORY";
        paras[1] = ""+curCategoryName;
        controller.execute(paras);
        String strFromWebService = controller.get();
        Type collectionType = new TypeToken<Category>() {}.getType();
        Category vec = gson.fromJson(strFromWebService, collectionType);
        if(vec==null)
            throw new Exception("no category was found....");
        return vec;
    }

    public void addOrder(Order o) throws Exception{
        Gson gson = new Gson();
        controller = new ControllerSync(url);

        String strOrder = gson.toJson(o);

        String paras[] = new String[2];
        paras[0] = "ADDORDER";
        paras[1] = strOrder;

        controller.execute(paras);
        String res=  controller.get();
        if(res.length()!=0)
            throw new Exception(res);

    }

    public ArrayList<Order> getOrdersByUser(User curUser) throws Exception{
        Gson gson = new Gson();
        controller = new ControllerSync(url);
        String paras[] = new String[2];
        paras[0] = "GETORDERSBYUSER";
        paras[1] = ""+curUser.getUsername();

        controller.execute(paras);
        String strFromWebService = controller.get();
        Type collectionType = new TypeToken<ArrayList<Order>>() {}.getType();
        ArrayList<Order> ordersByUser= gson.fromJson(strFromWebService, collectionType);
        return ordersByUser;
    }


    public void deleteOrder(int orderId) throws Exception {
        Gson gson = new Gson();
        controller = new ControllerSync(url);

        String paras[] = new String[2];
        paras[0] = "DELETEORDER";
        paras[1] = "" + orderId;
        controller.execute(paras);
        String res = controller.get();
        if (!(res.length() == 0)) {
            throw new Exception(res);
        }
    }

    public Order getOrderById(int orderId) throws Exception{
        Gson gson = new Gson();
        controller = new ControllerSync(url);
        String paras[] = new String[2];
        paras[0] = "GETORDERBYORDERID";
        paras[1] = ""+orderId;

        controller.execute(paras);
        String strFromWebService = controller.get();
        Type collectionType = new TypeToken<Order>() {}.getType();
        Order orderByOrderId= gson.fromJson(strFromWebService, collectionType);
        return orderByOrderId;
    }
}
