
package dataAccessClasses;

import com.sun.org.apache.bcel.internal.generic.SWAP;

import java.util.*;

import modelClasses.ModelAuthTokens;
import modelClasses.ModelEvents;
import modelClasses.ModelPersons;

import java.sql.*;

/** Data Access Class used to access the events table in the database */
public class AccessEvents{

    /** instance of the current Data Access Object (DAO) */
    static AccessEvents instance;


    /** returns instance of the data access object
     * @return   instance of data access object
     */
    public static AccessEvents getInstance(){
        if(instance == null){
            instance = new AccessEvents();
        }
        return instance;
    }
    /** returns modelClass object for the requested row in events table based on the name entered, uses the eventID as key
     * @param event  submits name of row in database table for method to retrieve
     * @return   requested row of events table
     */
    public ModelEvents getRow(String event, Connection conn) throws SQLException{
//        try {
            String query = "select * from events where auth_token = ?"; //this is actually eventID by my database names it incorrectly
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, event);
            ResultSet results = stmt.executeQuery();

        String eventIDin = "";
        String personIDin = "";
        String descendantin = "";
        double latitudein = 0;
        double longitudein = 0;
        String countryin = "";
        String cityin = "";
        String eventTypein = "";
        int yearin = 0;

        if(results.next()){
             eventIDin = results.getString(1);
             personIDin = results.getString(2);
             descendantin = results.getString(3);
             latitudein= results.getDouble(4);
             longitudein= results.getDouble(5);
             countryin = results.getString(6);
             cityin = results.getString(7);
             eventTypein = results.getString(8);
             yearin = results.getInt(9);
        }else{
            throw new SQLException();
        }
            results.close();
            stmt.close();
            return new ModelEvents(eventIDin,
                    descendantin, //changed these
                    personIDin, //changed these
                    latitudein,
                    longitudein,
                    countryin,
                    cityin,
                    eventTypein,
                    yearin);
//        }
//        catch(SQLException ex){
//            ex.printStackTrace();
//        }
//        return null;
    }


    /** returns an ArrayList of multiple modelClass objects that represent rows in the events table
     * @param
     * @return   an ArrayList of the modelClass objects representing the rows requested
     */
    public ArrayList<ModelEvents> getRows(String descendant, Connection conn) throws SQLException {
        ArrayList<ModelEvents> toReturn = new ArrayList<ModelEvents>();
            String query = "select * from events where descendant = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, descendant);
            ResultSet results = stmt.executeQuery();
            while (results.next()) {
                String eventIDin = results.getString(1);
                String personIDin = results.getString(2);
                String descendantin = results.getString(3);
                double latitudein = results.getDouble(4);
                double longitudein = results.getDouble(5);
                String countryin = results.getString(6);
                String cityin = results.getString(7);
                String eventTypein = results.getString(8);
                int yearin = results.getInt(9);
                ModelEvents event = new ModelEvents(eventIDin, descendantin, personIDin, latitudein, longitudein, countryin, cityin, eventTypein, yearin);
                toReturn.add(event);
            }
            results.close();
            stmt.close();

        return toReturn;
    }

    /** creates a row in the events table that is represented by the ModelClass object passed in
     * @param thisObject a modelClass object that represents a table row
     */
    public void createRow(ModelEvents thisObject, Connection conn) throws SQLException{

            String insert = "insert into events values(?,?,?,?,?,?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(insert);
            stmt.setString(1, thisObject.geteventID());
            stmt.setString(2, thisObject.getdescendant());
            stmt.setString(3, thisObject.getpersonID());
            stmt.setDouble(4, thisObject.getlatitude());
            stmt.setDouble(5, thisObject.getlongitude());
            stmt.setString(6, thisObject.getcountry());
            stmt.setString(7, thisObject.getcity());
            stmt.setString(8, thisObject.geteventType());
            stmt.setInt(9, thisObject.getyear());
            stmt.executeUpdate();
            stmt.close();

    }

    /** creates the events table in the database
     */
    public void createTable(Connection conn){
        try{
            String create = "create table if not exists events(\n" + "auth_token text primary key,\n" + " descendant text not null,\n" +
                    "personID text not null,\n" +
                    "latitude real not null,\n" +
                    "longitude real not null,\n" +
                    "country text not null,\n" +
                    "city text not null,\n" +
                    "eventType text not null,\n" +
                    "year int not null\n)";
            PreparedStatement stmt = conn.prepareStatement(create);
            stmt.executeUpdate();
            stmt.close();
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    /** removes a row from the events table
     * @param event  name of eventID used as the key to find the row to be removed
     * @return  boolean value of whether or not the removal was successful
     */
    public boolean removeRow(String event, Connection conn){
        try {
            String delete = "delete from events where eventID = ? "; //changed this
            PreparedStatement stmt = conn.prepareStatement(delete);
            stmt.setString(1, event);
            stmt.executeUpdate();
            stmt.close();
        }
        catch(SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }
    //will this delete all?
    public boolean removeRowDescendant(String descendant, Connection conn){
        try {
            String delete = "delete from events where descendant = ? ";
            PreparedStatement stmt = conn.prepareStatement(delete);
            stmt.setString(1, descendant);
            stmt.executeUpdate();
            stmt.close();
        }
        catch(SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean removeRowPersonID(String personID, Connection conn){
        try {
            String delete = "delete from events where personID = ? ";
            PreparedStatement stmt = conn.prepareStatement(delete);
            stmt.setString(1, personID);
            stmt.executeUpdate();
            stmt.close();
        }
        catch(SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    /** removes multiple rows from events table
     * @param events an ArrayList of eventIDs used as the key to find the multiple rows to be removed
     * @return  boolean value of whether or not removal was successful
     */
    public boolean removeRows(ArrayList<String> events, Connection conn){
        for(int i = 0; i < events.size(); i++){
            if(!(removeRow(events.get(i), conn))){
                return false;
            }
        }
        return true;
    }

    public void dropTable(Connection conn){
        try{
            String drop = "drop table if exists events";
            PreparedStatement stmt = conn.prepareStatement(drop);
            stmt.executeUpdate();
            stmt.close();
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }


}