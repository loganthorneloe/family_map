
package dataAccessClasses;

import java.sql.*;
import java.util.*;

import modelClasses.ModelPersons;
import modelClasses.ModelUsers;

/** Data Access Class used to access the users table in the database */
public class AccessUsers{

    /** instance of the current Data Access Object (DAO) */
    static AccessUsers instance;

    /** returns instance of the data access object
     * @return   instance of data access object
     */
    public static AccessUsers getInstance(){
        if(instance == null){
            instance = new AccessUsers();
        }
        return instance;
    }

    /** returns modelClass object for the requested row in users table based on the name entered, uses the username as key
     * @param username  submits name of row in database table for method to retrieve
     * @return   requested row of users table
     */
    public ModelUsers getRow(String username, Connection conn) throws SQLException{
//        try {
            String query = "select * from users where username = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            ResultSet results = stmt.executeQuery();

        String usernamein = "";
        String passwordin = "";
        String emailin = "";
        String first_namein = "";
        String last_namein = "";
        String genderin = "";
        String personIDin = "";

        if(results.next()){
             usernamein = results.getString(1);
             passwordin = results.getString(2);
             emailin = results.getString(3);
             first_namein= results.getString(4);
             last_namein= results.getString(5);
             genderin = results.getString(6);
             personIDin = results.getString(7);
        }
            results.close();
            stmt.close();
            return new ModelUsers(usernamein,
                    passwordin,
                    emailin,
                    first_namein,
                    last_namein,
                    genderin,
                    personIDin);
        }
//        catch(SQLException ex){
//            ex.printStackTrace();
//        }
//        return null;
//    }

    /** returns an ArrayList of multiple modelClass objects that represent rows in the users table
     * @param usernames  an ArrayList of modelClass objects corresponding to the rows in the users table that need to be retrieved
     * @return   an ArrayList of the modelClass objects representing the rows requested
     */

    /** creates a row in the users table that is represented by the ModelClass object passed in
     * @param thisObject a modelClass object that represents a table row
     */
    public void createRow(ModelUsers thisObject, Connection conn) throws SQLException{

            String insert = "insert into users values( ? , ? , ? , ? , ? , ? , ? )";
            PreparedStatement stmt = conn.prepareStatement(insert);
//            System.out.println("Made it past preparing statement");
            stmt.setString(1, thisObject.getuserName());
            stmt.setString(2, thisObject.getpassword());
            stmt.setString(3, thisObject.getemail());
            stmt.setString(4, thisObject.getfirstName());
            stmt.setString(5, thisObject.getlastName());
            stmt.setString(6, thisObject.getgender());
            stmt.setString(7, thisObject.getpersonID());
//            System.out.println("Made it past setters.");
            stmt.executeUpdate();
//            System.out.println("UpdateExecuted");
            stmt.close();

    }

    /** creates the users table in the database
     */
    public void createTable(Connection conn){
        try{
            String create = "create table if not exists users(\n" + "username text not null primary key,\n" +
                    "password text not null,\n" +
                    "email text not null,\n" +
                    "first_name text not null,\n" +
                    "last_name text not null,\n" +
                    "gender text not null,\n" +
                    "personID text not null\n)";
            PreparedStatement stmt = conn.prepareStatement(create);
            stmt.executeUpdate();
            stmt.close();
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    /** removes a row from the users table
     * @param username  username used as the key to find the row to be removed
     * @return  boolean value of whether or not the removal was successful
     */
    public boolean removeRow(String username, Connection conn){
        try {
            String delete = "delete from users where username = ? ";
            PreparedStatement stmt = conn.prepareStatement(delete);
            stmt.setString(1, username);
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
            String drop = "drop table if exists users";
            PreparedStatement stmt = conn.prepareStatement(drop);
            stmt.executeUpdate();
            stmt.close();
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }


}