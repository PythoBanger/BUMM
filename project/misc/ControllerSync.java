package com.example.schueler.project.misc;

import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import android.os.AsyncTask;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

//semiprof daweil wegen ganzen if case etc....
public class ControllerSync extends AsyncTask<String, Void, String> {

    private static final String URI_2ND = "BummWebService/webresources/";
    private String url_1st;

    public ControllerSync(String url) throws Exception {
        this.url_1st = url;
    }

    @Override
    protected String doInBackground(String... command) {
        boolean isPut = false, isPost=false,isGetWithHeaders=false, isDelete=false;

        BufferedReader reader = null;
        BufferedWriter writer = null;
        String content = null;
        URL url = null;
        HttpURLConnection conn=null;


        try {
            switch(command[0]){
                case "GETALLUSERS":
                    url = new URL(url_1st + URI_2ND + "users"); //default getAllUsers
                    break;
                case "GETUSER":
                    url = new URL(url_1st + URI_2ND + "users/"+command[1]); //getUser
                    break;
                case "LOGINUSER":
                    url = new URL(url_1st + URI_2ND + "users/login"); //post make login
                    isPost=true;
                    break;
                case "ADDUSER":
                    url = new URL(url_1st + URI_2ND + "users"); //post add new user (register)
                    isPost=true;
                    break;
                case "UPDATEUSER":
                    url = new URL(url_1st + URI_2ND + "users"); //put update user
                    isPut=true;
                    break;
                case "GETALLARTICLES":
                    url = new URL(url_1st + URI_2ND + "articles"); //default getAllArticles
                    break;
                case "GETARTICLE":
                    url = new URL(url_1st + URI_2ND + "articles/"+command[1]); //returns Article
                    break;
                case "FILTERARTICLES":
                    url = new URL(url_1st + URI_2ND + "articles/filter"); //returns filtered articles
                    isGetWithHeaders=true;
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestProperty("name", command[1]);
                    conn.setRequestProperty("category", command[2]);
                    break;
                case "GETRATINGSOFARTICLE":
                    url = new URL(url_1st + URI_2ND + "ratings/"+command[1]); //returns allRatings of specific Article
                    break;
                case "ADDRATING":
                    url = new URL(url_1st + URI_2ND + "ratings"); //post add rating
                    isPost=true;
                    break;
                case "UPDATERATING":
                    url = new URL(url_1st + URI_2ND + "ratings"); //put update rating
                    isPut=true;
                    break;
                case "DELETERATING":
                    url = new URL(url_1st + URI_2ND + "ratings/delete"); //post delete rating
                    isPost=true; //semiprof but delete request wont work as it gets interpretted as get request....
                    //isDelete=false....
                    break;
                case "GETALLCATEGORIES":
                    url = new URL(url_1st + URI_2ND + "categories"); //default getAllCategories
                    break;
                case "ADDARTICLETOLIST":
                    url = new URL(url_1st + URI_2ND + "shoppingList/" + command[2]); //post adds article to specific username
                    isPost=true;
                    break;
                case "DELETEARTICLEFROMLIST":
                    url = new URL(url_1st + URI_2ND + "shoppingList/delete/" + command[2]); //post semiprof but delete req wont work
                    isPost=true;
                    break;
                default: break;
            }

            if(!isGetWithHeaders){ //without if it will overrride data
                conn = (HttpURLConnection) url.openConnection();
            }

            if (isPost) {
                conn.setRequestMethod("POST"); //semiprof for deleting but delete request dose not works(interpreted ass get)
                conn.setRequestProperty("Content-Type", "application/json");
                writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
                writer.write(command[1]); //object in json format
                writer.flush();
                writer.close();
                conn.getResponseCode();
            }
            else if(isPut){
                conn.setRequestMethod("PUT");
                conn.setRequestProperty("Content-Type", "application/json");
                writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
                writer.write(command[1]); //object in json format
                writer.flush();
                writer.close();
                conn.getResponseCode();
            }/* else if(isDelete){
                conn.setRequestProperty("X-HTTP-Method-Override", "DELETE");
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
                writer.write(command[1]); //object in json format
                writer.flush();
                writer.close();
                conn.getResponseCode();
            }*/

            // get data from server
            reader = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            content = sb.toString();
            reader.close();
            conn.disconnect();
        } catch (Exception ex) {
            content = "error in doInBackground: " + ex.getMessage();
        }
        return content;

    }
}
