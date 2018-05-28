package com.example.pupil.buum.Data;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.Date;

public class Database{


    private static Database database = null;
    private ArrayList<Customer> allCustomers;
    private ArrayList<Article> allArticles;
    private Customer currCustomer=null;
    public static Database newInstance() {
        if (database == null) {
            database = new Database();
        }

        return database;
    }

    private Database() {
        allCustomers = new ArrayList<>();
        allArticles = new ArrayList<>();
        createTestData();
    }
    private void createTestData(){
        allArticles.add(new Article(1,"Größe und/oder Gewicht: 6,4 x 1,3 x 11,4 cm ; 113 g\n" +
                "Produktgewicht inkl. Verpackung: 159 g\n" +
                "Batterien 1 Lithium Metal Batterien erforderlich (enthalten).\n" +
                "Modellnummer: iPhone 4 unlocked\n" +
                "ASIN: B0097CZBH4",
                "iPhone4",450f,100));
        allArticles.add(new Article(2,"Größe und/oder Gewicht: 6,4 x 1,3 x 11,4 cm ; 113 g\n" +
                "Produktgewicht inkl. Verpackung: 159 g\n" +
                "Batterien 1 Lithium Metal Batterien erforderlich (enthalten).\n" +
                "Modellnummer: iPhone 5 unlocked\n" +
                "ASIN: B0097CZBH4",
                "iPhone5",210f,10));
        allArticles.add(new Article(3,"Größe und/oder Gewicht: 6,4 x 1,3 x 11,4 cm ; 113 g\n" +
                "Produktgewicht inkl. Verpackung: 159 g\n" +
                "Batterien 1 Lithium Metal Batterien erforderlich (enthalten).\n" +
                "Modellnummer: iPhone 7 unlocked\n" +
                "ASIN: B0097CZBH4",
                "iPhone7",220.20f,1));
        allArticles.add(new Article(4,"Größe und/oder Gewicht: 6,4 x 1,3 x 11,4 cm ; 113 g\n" +
                "Produktgewicht inkl. Verpackung: 159 g\n" +
                "Batterien 1 Lithium Metal Batterien erforderlich (enthalten).\n" +
                "Modellnummer: iPhone 2 unlocked\n" +
                "ASIN: B0097CZBH4",
                "iPhone2",500f,50));
        allArticles.add(new Article(5,"Größe und/oder Gewicht: 6,4 x 1,3 x 11,4 cm ; 113 g\n" +
                "Produktgewicht inkl. Verpackung: 159 g\n" +
                "Batterien 1 Lithium Metal Batterien erforderlich (enthalten).\n" +
                "Modellnummer: iPhone 8 unlocked\n" +
                "ASIN: B0097CZBH4",
                "iPhone8",700f,5));
        allArticles.add(new Article(6,"Größe und/oder Gewicht: 6,4 x 1,3 x 11,4 cm ; 113 g\n" +
                "Produktgewicht inkl. Verpackung: 159 g\n" +
                "Batterien 1 Lithium Metal Batterien erforderlich (enthalten).\n" +
                "Modellnummer: iPhone 8+ unlocked\n" +
                "ASIN: B0097CZBH4",
                "iPhone8+",800f,14));
        allArticles.add(new Article(7,"Größe und/oder Gewicht: 6,4 x 1,3 x 11,4 cm ; 113 g\n" +
                "Produktgewicht inkl. Verpackung: 159 g\n" +
                "Batterien 1 Lithium Metal Batterien erforderlich (enthalten).\n" +
                "Modellnummer: iPhone X unlocked\n" +
                "ASIN: B0097CZBH4",
                "iPhoneX",900f,169));
        allCustomers.add(new Customer("un9900","pw","firstName","lastName","adress","email","location","status","role",new Date(),9500));
    }

    public Article getArticle(int id) throws Exception {  // für produktansicht
        Article result=null;

        for(Article ar : this.getArticles()){
            if(ar.getId() == id){
                result=ar;
            }

        }
        return result;
    }

    public ArrayList<Article> getArticles() throws Exception { //nach login um alle produkte anzuzeigen
        return allArticles;
    }

    public Customer getCustomer(String username, String password) throws Exception { //für login.

        if(username.equals("")|| password.equals("")){
            throw new Exception("You have to enter your data in all fields");
        }

        for(Customer c: allCustomers){
            if(c.getUsername().equals(username) && c.getPassword().equals(password)){
                currCustomer=c;
                currCustomer.setStatus("logged in");
                return currCustomer;
            }

        }

        throw new Exception("enter data correctly");
    }

    public ArrayList<Customer> getCustomers() throws Exception { //irrelevant auser es ist der admin
        return allCustomers;
    }

    public void updateCustomer(Customer newC) throws Exception { //gilt für daten ändern. aber auch für loggof weil man status nur zum ändern braucht
        if(newC.getFirstName().equals("") || newC.getLastName().equals("") || newC.getPassword().equals("") || (""+ newC.getZipcode()).equals("") || newC.getAddress().equals("") || newC.getLocation().equals("")){
            throw new Exception("You have to enter your data in all fields");
        }
        for(Customer c1 : allCustomers){
            if(newC.getUsername().equals(c1.getUsername())){
                c1.setPassword(newC.getPassword());
                c1.setAddress(newC.getAddress());
                c1.setBirthdate(newC.getBirthdate());
                c1.setEmail(newC.getEmail());
                c1.setFirstName(newC.getFirstName());
                c1.setLastName(newC.getLastName());
                c1.setLocation(newC.getLocation());
                c1.setRole(newC.getRole());
                c1.setStatus(newC.getStatus());
                c1.setZipcode(newC.getZipcode());
            }
        }
    }




    public void addCustomer(String username, String password, String firstName, String lastName, String address, String email, String location, String status, String role, Date birthdate, int zipcode) throws Exception{ // für register
        if( username.length()==0 || password.length()==0 || firstName.length()==0 || lastName.length()==0   || address.length()==0 || email.length()==0 || location.length()==0  ||  status.length()==0  || role.length()==0 ||  birthdate==null ||  zipcode==0){
            throw new Exception("You have to enter your data in all field");
        }

        Customer newC = new Customer( username,  password,  firstName,  lastName,  address,  email,  location,  status,  role,  birthdate, zipcode);

        for(Customer c : allCustomers){
            if(c.getUsername().equals(newC.getUsername()))
                throw new Exception("customer already exists");
        }

        allCustomers.add(newC);
    }

    public Customer getCurrCustomer() {
        return currCustomer;
    }

    public void setCurrCustomer(Customer currCustomer) {
        this.currCustomer = currCustomer;
    }

    public void deleteCurrCustomer(){
        currCustomer.setStatus("logged off");
        allCustomers.remove(currCustomer);
        currCustomer=null;
    }

}


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

package com.example.pupil.buum.Data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Melanie
 *
public class Database {

//    private static final String CONNECTSTRING = "jdbc:oracle:thin:@localhost:1521:orcl";
    private static final String CONNECTSTRING = "jdbc:oracle:thin:@192.168.128.152:1521:ora11g";
    private static final String USER = "d4a04";
    private static final String PASSWD = "d4a";
    private Connection conn = null;

    private static Database database = null;

    public static Database newInstance() {
        if (database == null) {
            database = new Database();
        }

        return database;
    }

    private Database() {
    }

    private Connection createConnection() throws Exception {
        DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        return DriverManager.getConnection(CONNECTSTRING, USER, PASSWD);
    }


    public Article getArticle(int id) throws Exception {  // für produktansicht
        Article article = null;
        conn = createConnection();
        String select = "SELECT * FROM Article WHERE id = ? ";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            article = new Article(rs.getInt("id"), rs.getString("description"), rs.getString("name"), rs.getFloat("price"),rs.getInt("onStock"));
        } else {
            throw new Exception("article with id '" + id + "' not found");
        }
        conn.close();
        return article;
    }

    public Collection<Article> getArticles() throws Exception { //nach login um alle produkte anzuzeigen
        ArrayList<Article> collArticles = new ArrayList<>();

        conn = createConnection();
        String select = "SELECT id, description, name, price, onStock FROM Article";
        PreparedStatement stmt = conn.prepareStatement(select);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            collArticles.add(new Article(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getFloat(4),rs.getInt(5)));
        }
        conn.close();
        return collArticles;
    }

    public Customer getCustomer(String username, String password) throws Exception { //für login.
        Customer customer = null;
        conn = createConnection();
        String select = "SELECT * FROM customer WHERE username = ? and password = ?";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setString(1, username);
        stmt.setString(2, password);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            customer = new Customer(rs.getString("username"), rs.getString("password"), rs.getString("firstname"), rs.getString("lastname"),
                    rs.getString("address"), rs.getString("email"), rs.getString("location"),rs.getString("status"), rs.getString("role"), rs.getDate("birthdate"), rs.getInt("zipcode"));
        } else {
            throw new Exception("customer with username and pw not found");
        }
        conn.close();
        return customer;

    }

    public Collection<Customer> getCustomers() throws Exception { //irrelevant auser es ist der admin
        ArrayList<Customer> collCustomers = new ArrayList<>();

        conn = createConnection();
        String select = "SELECT * FROM Customer";
        PreparedStatement stmt = conn.prepareStatement(select);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            collCustomers.add(new Customer(rs.getString("username"), rs.getString("password"), rs.getString("firstname"), rs.getString("lastname"),
                    rs.getString("address"), rs.getString("email"), rs.getString("location"),rs.getString("status"), rs.getString("role"), rs.getDate("birthdate"), rs.getInt("zipcode")));
        }
        conn.close();
        return collCustomers;
    }

    public void updateCustomer(Customer c) throws Exception { //gilt für daten ändern. aber auch für loggof weil man status nur zum ändern braucht
        conn = createConnection();
        String select = "UPDATE customer SET username = ? , password = ?, firstName = ?, lastName = ?, address = ?, email = ?, location = ?, status = ?,birthdate = ?, zipcode = ?";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setString(1, c.getUsername());
        stmt.setString(2, c.getPassword());
        stmt.setString(3, c.getFirstName());
        stmt.setString(4, c.getLastName());
        stmt.setString(5, c.getAddress());
        stmt.setString(6, c.getEmail());
        stmt.setString(7, c.getLocation());
        stmt.setString(8, c.getStatus());
        stmt.setDate(9, c.getBirthdate());
        stmt.setInt(10, c.getZipcode());
        System.out.println("***update: " + stmt.executeUpdate());
        conn.commit();
        conn.close();
    }

    public void addCustomer(Customer c) throws Exception{ // für register
        conn = createConnection();
        String select = "INSERT INTO customer SET VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setString(1, c.getEmail());
        stmt.setDate(2, c.getBirthdate());
        stmt.setString(3, c.getFirstName());
        stmt.setString(4, c.getLastName());
        stmt.setString(5, c.getLocation());
        stmt.setString(6, c.getUsername());
        stmt.setString(7, c.getPassword());
        stmt.setInt(8, c.getZipcode());
        stmt.setString(9, c.getAddress());
        stmt.setString(10, c.getRole());
        stmt.setString(11, c.getStatus());

        conn.close();
    }
}**/


  //CONSTRAINT check_role CHECK (role = 'admin' or role = 'customer'),
   //CONSTRAINT check_status CHECK (status = 'logged in' or status = 'logged off') mann muss richtig schreiben
