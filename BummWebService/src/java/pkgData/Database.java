/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgData;

import java.io.ByteArrayInputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author schueler
 */
public class Database {
    // private static final String CONNECTSTRING = "jdbc:oracle:thin:@localhost:1521:orcl";

    private static final String CONNECTSTRING = "jdbc:oracle:thin:@212.152.179.117:1521:ora11g";
 //   private static final String CONNECTSTRING = "jdbc:oracle:thin:@192.168.128.152:1521:ora11g";
    private static final String USER = "d4a10";
    private static final String PASSWD = "d4a";
    private Connection conn = null;

    /**
     * Singleton
     */
    private static Database database = null;

    public static Database newInstance() throws Exception {
        if (database == null) {
            database = new Database();
        }

        return database;
    }

    private Database() throws Exception {
    }

    private Connection createConnection() throws Exception {
        DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        return DriverManager.getConnection(CONNECTSTRING, USER, PASSWD);
    }

    public Collection<User> getAllUsers() throws Exception {
        ArrayList<User> collUsers = new ArrayList<>();

        conn = createConnection();
        String select = "SELECT * FROM BummUser WHERE role = 'customer'"; //admin can only edit customers
        PreparedStatement stmt = conn.prepareStatement(select);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            collUsers.add(getUserValues(rs));
        }
        conn.close();
        return collUsers;
    }

    public User getUser(String username) throws Exception {
        conn = createConnection();
        String select = "SELECT * FROM BummUser WHERE username=?";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();
        User foundUser = null;
        while (rs.next()) {
            foundUser = getUserValues(rs);
        }
        conn.close();
        return foundUser;
    }

    public User getUser(String newUsername, String password) throws Exception {
        conn = createConnection();
        String select = "SELECT * FROM BummUser WHERE username=? and password=?";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setString(1, newUsername);
        stmt.setString(2, password);
        ResultSet rs = stmt.executeQuery();
        User foundUser = null;
        while (rs.next()) {
            foundUser = getUserValues(rs);
        }
        conn.close();
        return foundUser;
    }

    private User getUserValues(ResultSet rs) throws Exception {
        User newUser = null;
        LocalDate ld = null;
        Date bd = rs.getDate(6);
        if (bd != null) {
            ld = bd.toLocalDate();
        }

        Blob blob = rs.getBlob(11);
        if (blob != null) {
            int blobLength = (int) blob.length();
            byte[] profilePicAsBytes = blob.getBytes(1, blobLength);
            blob.free();
            newUser = new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(7), rs.getString(9), rs.getString(10), ld, rs.getInt(8), profilePicAsBytes, rs.getString(12));
        } else {
            newUser = new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(7), rs.getString(9), rs.getString(10), ld, rs.getInt(8), rs.getString(12));
        }
        return newUser;
    }

    private User checkIfEmailIstUnique(String email) throws Exception {
        String select = "SELECT * FROM BummUser WHERE email=?";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setString(1, email);
        ResultSet rs = stmt.executeQuery();
        User foundUser = null;
        while (rs.next()) {
            foundUser = getUserValues(rs);
        }
        return foundUser;
    }

    //TODO:and see how to make imagePic
    public void addUser(User newUser) throws Exception {
        conn = createConnection();
        User u = checkIfEmailIstUnique(newUser.getEmail());
        if (u != null) {
            throw new Exception("email already exists!");
        }

        String select = "INSERT INTO BummUser VALUES(?,?,?,?,?,?,?,?,?,'customer',?,'active')"; //customer ist fix. nicht veränderbar
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setString(1, newUser.getUsername());
        stmt.setString(2, newUser.getPassword());
        stmt.setString(3, newUser.getFirstName());
        stmt.setString(4, newUser.getLastName());
        stmt.setString(5, newUser.getEmail());
        stmt.setDate(6, Date.valueOf(newUser.getBirthdate()));
        stmt.setString(7, newUser.getLocation());
        stmt.setInt(8, newUser.getZipCode());
        stmt.setString(9, newUser.getAddress());
        byte[] imgData = newUser.getImageData();
        if (imgData != null) //if profile pic exsits save
        {
            stmt.setBinaryStream(10, new ByteArrayInputStream(imgData), imgData.length);
        } else //else dont. 
        {
            stmt.setNull(10, Types.BLOB);
        }

        stmt.executeQuery();
        conn.close();
    }

    //TODO: Service delete LocalDate.now() and see how to update imagePic
    public void updateUser(User userToUpdate) throws Exception {
        conn = createConnection();
        User u = checkIfEmailIstUnique(userToUpdate.getEmail());
        if (u != null) {
            if (!userToUpdate.getUsername().equals(u.getUsername())) {
                throw new Exception("email already belonges to someone else");
            }
        }
        String select = "UPDATE BummUser SET password=?, firstname=?, lastname=?, email=?, birthdate=?"
                + ", location=?, zipcode=?, address=?, profilePic=?, status=? WHERE username=?"; //customer ist fix. nicht veränderbar
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setString(1, userToUpdate.getPassword());
        stmt.setString(2, userToUpdate.getFirstName());
        stmt.setString(3, userToUpdate.getLastName());
        stmt.setString(4, userToUpdate.getEmail());
        stmt.setDate(5, Date.valueOf(userToUpdate.getBirthdate()));
        stmt.setString(6, userToUpdate.getLocation());
        stmt.setInt(7, userToUpdate.getZipCode());
        stmt.setString(8, userToUpdate.getAddress());
        stmt.setString(10, userToUpdate.getStatus());
        stmt.setString(11, userToUpdate.getUsername());
        byte[] imgData = userToUpdate.getImageData();
        if (imgData != null) //if new pic was selected update
        {
            stmt.setBinaryStream(9, new ByteArrayInputStream(imgData), imgData.length);
        } else //else set to null bc user doesnt want pic anymore... 
        {
            stmt.setNull(9, Types.BLOB);
        }

        stmt.executeQuery();
        conn.close();
    }

    public Collection<Article> getAllArticles() throws Exception {
        ArrayList<Article> collArticles = new ArrayList<>();

        conn = createConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Article ORDER BY artNr");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            collArticles.add(getArticleValues(rs));
        }
        conn.close();
        return collArticles;
    }

    private Article getArticleValues(ResultSet rs) throws Exception {
        return (new Article(rs.getInt(1), rs.getInt(5), rs.getString(2), rs.getString(3), rs.getString(6), rs.getFloat(4)));
    }

    public Article getArticle(int artNr) throws Exception {
        conn = createConnection();
        String select = "SELECT * FROM Article WHERE artNr=?";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setInt(1, artNr);
        ResultSet rs = stmt.executeQuery();
        Article foundArticle = null;
        while (rs.next()) {
            foundArticle = getArticleValues(rs);
        }
        conn.close();
        return foundArticle;
    }

    public void addArticle(Article newArticle) throws Exception {
        conn = createConnection();

        String select = "INSERT INTO Article VALUES(seqArticle.NEXTVAL,?,?,?,?,?)";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setString(1, newArticle.getName());
        stmt.setString(2, newArticle.getDescription());
        stmt.setFloat(3, newArticle.getPrice());
        stmt.setInt(4, newArticle.getOnStock());
        stmt.setString(5, newArticle.getArtCategory());
        stmt.executeQuery();
        conn.close();

    }

    //change when doing admin.... as decstock must be +
    public void updateArticle(Article articleToUpdate) throws Exception {
        conn = createConnection();

        String select = "UPDATE Article SET name=?, description=?, price=?, onStock=onStock+?,"
                + " artCategory=? WHERE artNr = ?";
        PreparedStatement stmt = conn.prepareStatement(select);

        stmt.setString(1, articleToUpdate.getName());
        stmt.setString(2, articleToUpdate.getDescription());
        stmt.setFloat(3, articleToUpdate.getPrice());
        stmt.setInt(4, articleToUpdate.getDecStock());
        stmt.setString(5, articleToUpdate.getArtCategory());
        stmt.setInt(6, articleToUpdate.getArtNr());
        stmt.executeQuery();
        conn.close();

    }

    public Collection<Article> filterArticles(String nameToFilter, String categoryName) throws Exception {
        ArrayList<Article> collArticles = new ArrayList<>();

        conn = createConnection();
        PreparedStatement stmt = conn.prepareStatement("select * from allCatWithArt where upper(artName) like upper(?) "
                + "   start with child = ? connect by prior child = parent order siblings by child");

        stmt.setString(1, "%" + nameToFilter + "%");
        stmt.setString(2, categoryName);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Article a = new Article(rs.getInt("artNr"), rs.getInt("artOnStock"), rs.getString("artName"), rs.getString("artDesc"),
                    rs.getString("child"), rs.getFloat("artPrice"));

            collArticles.add(a);
        }
        conn.close();
        return collArticles;
    }

    /*public void decreaseOnStock(Article art) throws Exception {
       
        conn = createConnection();

        String select = "UPDATE Article SET  onStock=onStock-?  WHERE artNr = ?";
        PreparedStatement stmt = conn.prepareStatement(select);

        stmt.setInt(1, art.getDecStock());
        stmt.setInt(2, art.getArtNr());
        stmt.executeQuery();
        conn.close();
    }*/

    //toDo: think how the best return should be..... wheter like now or using the arraylist ask ortner
    public Collection<Category> getAllCategories() throws Exception {
        ArrayList<Category> collCategories = new ArrayList<>();

        conn = createConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Category START WITH CURCATEGORY = 'Alle Artikel' CONNECT BY PRIOR CURCATEGORY=PARENTCATEGORY ORDER siblings BY CURCATEGORY");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            collCategories.add(new Category(rs.getString(1), rs.getString(2))); //current,parent
        }
        conn.close();
        return collCategories;
    }

    public ShoppingList getShoppingList(String username) throws Exception {
        ShoppingList sp = new ShoppingList(this.getUser(username));
        conn = createConnection();
        String select = "SELECT * FROM ShoppingList WHERE username=?";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            sp.addArticle(this.getArticle(rs.getInt("artNr")));
        }
        conn.close();
        return sp;

    }

    public void addArticleToShoppingList(String username, Article a) throws Exception {
        conn = createConnection();

        String select = "INSERT INTO ShoppingList VALUES(?,?)";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setString(1, username);
        stmt.setInt(2, a.getArtNr());

        stmt.executeQuery();
        conn.close();
    }

    public Article getArticleFromShoppingList(String username, int artNr) throws Exception {
        Article a = null;
        conn = createConnection();
        String select = "SELECT * FROM ShoppingList WHERE username=? and artNr=?";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setString(1, username);
        stmt.setInt(2, artNr);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            a = getArticle(rs.getInt("artNr"));
        }
        conn.close();
        return a;
    }


    public void deleteArticleFromShoppingList(String username, int artNr) throws Exception {
        conn = createConnection();

        String select = "DELETE FROM ShoppingList WHERE username=? and artNr=?";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setString(1, username);
        stmt.setInt(2, artNr);
        stmt.executeQuery();
        conn.close();

    }

    public Collection<Rating> getAllRatings() throws Exception {
        ArrayList<Rating> collRatings = new ArrayList<>();

        conn = createConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Rating ORDER BY artNr,ratingDate,username");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            collRatings.add(getRatingValues(rs));
        }
        conn.close();
        return collRatings;
    }

    private Rating getRatingValues(ResultSet rs) throws Exception {
        return (new Rating(getArticle(rs.getInt("artNr")), getUser(rs.getString("username")), rs.getDate("ratingDate").toLocalDate(), rs.getString("ratingComment"), rs.getInt("ratingValue")));
    }

    public Collection<Rating> getRatings(int artNr) throws Exception {
        ArrayList<Rating> collRatings = new ArrayList<>();

        conn = createConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Rating WHERE artNr=? ORDER BY ratingDate, username");
        stmt.setInt(1, artNr);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            collRatings.add(getRatingValues(rs));
        }
        conn.close();
        return collRatings;
    }

    public Rating getRating(String username, int artNr) throws Exception {
        Rating r = null;

        conn = createConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Rating WHERE username=? and artNr=?");
        stmt.setString(1, username);
        stmt.setInt(2, artNr);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            r = getRatingValues(rs);
        }
        conn.close();
        return r;
    }


    public void addRating(Rating newRating) throws Exception {
        conn = createConnection();

        String select = "INSERT INTO Rating VALUES(?,?,?,?,?)";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setInt(1, newRating.getRatedArticle().getArtNr());
        stmt.setString(2, newRating.getUserWhoRated().getUsername());
        stmt.setDate(3, Date.valueOf(LocalDate.now()));
        stmt.setString(4, newRating.getRatingComment());
        stmt.setInt(5, newRating.getRatingValue());
        stmt.executeQuery();
        conn.close();
    }

    public void updateRating(Rating ratingToUpdate) throws Exception {
        conn = createConnection();

        String select = "UPDATE Rating SET ratingComment=?, ratingValue=?, ratingDate=? WHERE artNr = ? AND username=?"; //u can't change the date as it localdate.now (date of creation)
        PreparedStatement stmt = conn.prepareStatement(select);

        stmt.setString(1, ratingToUpdate.getRatingComment());
        stmt.setInt(2, ratingToUpdate.getRatingValue());
        stmt.setDate(3, Date.valueOf(LocalDate.now())); //date is the date of last time edited which is currdate
        stmt.setInt(4, ratingToUpdate.getRatedArticle().getArtNr());
        stmt.setString(5, ratingToUpdate.getUserWhoRated().getUsername());
        stmt.executeQuery();
        conn.close();
    }

    public void deleteRating(Rating ratingToUpdate) throws Exception{
        if(ratingToUpdate==null)
            throw new Exception("no rating found");
        
        conn = createConnection();

        String select = "DELETE FROM Rating WHERE artNr = ? AND username=?"; 
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setInt(1, ratingToUpdate.getRatedArticle().getArtNr());
        stmt.setString(2, ratingToUpdate.getUserWhoRated().getUsername());
        stmt.executeQuery();
        conn.close();
    }
}