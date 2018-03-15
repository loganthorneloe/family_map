
package dataAccessClasses;

import java.util.*;
import modelClasses.ModelAuthTokens;
import java.sql.*;

/** Data Access Class used to access the auth_tokens table in the database */
public class AccessAuthTokens{


    /** instance of the current Data Access Object (DAO) */
    static AccessAuthTokens instance;

    /** returns instance of the data access object
     * @return   instance of data access object
     */
    public static AccessAuthTokens getInstance(){
        if(instance == null){
            instance = new AccessAuthTokens();
        }
        return instance;
    }

    /** returns modelClass object for the requested row in Auth Tokens table based on the name entered, uses the auth_token as key
     * @param token  submits auth_token of row in database table for method to retrieve
     * @return   requested row of Auth Tokens table
     */
    public ModelAuthTokens getRow(String token, Connection conn) throws SQLException{
//    try {
        String query = "select * from auth_tokens where auth_token = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, token);
        ResultSet results = stmt.executeQuery();
        ModelAuthTokens tokenReturn = new ModelAuthTokens("","");
        while(results.next()){
            String auth_token = results.getString(1);
            String username = results.getString(2);
            tokenReturn = new ModelAuthTokens(username, auth_token);
            }
        results.close();
        stmt.close();
        return tokenReturn;
//    }
//    catch(SQLException ex){
//        ex.printStackTrace();
//    }
//        return null;
    }

    public ModelAuthTokens getRowByUsername(String username, Connection conn){
        try {
            String query = "select * from auth_tokens where username = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            ResultSet results = stmt.executeQuery();
            ModelAuthTokens token_auth = new ModelAuthTokens("","");
        while(results.next()){
            String auth_token = results.getString(1);
            String usernameToReturn = results.getString(2);
            token_auth = new ModelAuthTokens(usernameToReturn, auth_token);
        }
            results.close();
            stmt.close();
            return token_auth;
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }

    /** returns an ArrayList of multiple modelClass objects that represent rows in the AuthTokens table
     * @param tokens  an ArrayList of modelClass objects corresponding to the rows in the auth_tokens table that need to be retrieved
     * @return   an ArrayList of the modelClass objects representing the rows requested
     */
    public ArrayList<ModelAuthTokens> getRows(ArrayList<String> tokens, Connection conn) throws SQLException{
        ArrayList<ModelAuthTokens> toReturn = new ArrayList<ModelAuthTokens>();
        for(int i = 0; i < tokens.size(); i++){
            ModelAuthTokens thisToken = getRow(tokens.get(i), conn);
            if(thisToken!=null){
                toReturn.add(thisToken);
            }
        }
        return toReturn;
    }

    /** creates a row in the auth_tokens table that is represented by the ModelClass object passed in
     * @param thisObject a modelClass object that represents a table row
     */
    public void createRow(ModelAuthTokens thisObject, Connection conn) throws SQLException{ //use jSON here?
        try {
            String insert = "insert into auth_tokens values(?,?)";
            PreparedStatement stmt = conn.prepareStatement(insert);
            stmt.setString(1, thisObject.getauthToken());
            stmt.setString(2, thisObject.getuserName());
            stmt.executeUpdate();
            stmt.close();
        }catch(SQLException ex){
            ex.printStackTrace();
        }

    }

    /** creates the auth_tokens table in the database
     */
    public void createTable(Connection conn){

        //database class opens and closes connection and loads the driver, run access classes through database class
        //import sqlite jar for the entire project

        //put in database class in open connection
//        String dbname = "jdbc:sqlite:database.db";
//        Connection connection = DriverManager.getConnection(dbname);
        try{
        String create = "create table if not exists auth_tokens(" +
                "auth_token text NOT NULL PRIMARY KEY, " +
                "username text NOT NULL" +
                ");";
        PreparedStatement stmt = conn.prepareStatement(create);
        stmt.executeUpdate();
        stmt.close();
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }

    }

    /** removes a row from the auth_tokens table
     * @param token  name of auth_token used as the key to find the row to be removed
     * @return  boolean value of whether or not the removal was successful
     */
    public boolean removeRow(String token, Connection conn){
    try {
        String delete = "delete from auth_tokens where auth_token = ? ";
        PreparedStatement stmt = conn.prepareStatement(delete);
        stmt.setString(1, token);
        stmt.executeUpdate();
        stmt.close();
    }
    catch(SQLException ex) {
        ex.printStackTrace();
            return false;
        }
        return true;
    }




    public void dropTable(Connection conn){
        try{
            String drop = "drop table if exists auth_tokens";
            PreparedStatement stmt = conn.prepareStatement(drop);
            stmt.executeUpdate();
            stmt.close();
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }


}