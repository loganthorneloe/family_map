
package dataAccessClasses;

import java.util.*;
import modelClasses.ModelPersons;
import java.sql.*;

/** Data Access Class used to access the persons table in the database */
public class AccessPersons{

    /** instance of the current Data Access Object (DAO) */
    static AccessPersons instance;

    /** returns instance of the data access object
     * @return   instance of data access object
     */
    public static AccessPersons getInstance(){
        if(instance == null){
            instance = new AccessPersons();
        }
        return instance;
    }

    /** returns modelClass object for the requested row in persons table based on the name entered, uses the personID as key
     * @param person  submits name of row in database table for method to retrieve
     * @return   requested row of persons table
     */
    public ModelPersons getRow(String person, Connection conn) throws SQLException{
//        try {
            String query = "select * from persons where personID = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, person);
            ResultSet results = stmt.executeQuery();
            String personIDin = "";
            String descendantin = "";
            String first_namein = "";
            String last_namein = "";
            String genderin = "";
            String fatherIDin = "";
            String motherIDin = "";
            String spouseIDin = "";
        if(results.next()){ //it isnt going into here
             personIDin = results.getString(1);
             descendantin = results.getString(2);
             first_namein = results.getString(3);
             last_namein= results.getString(4);
             genderin= results.getString(5);
             fatherIDin = results.getString(6);
             motherIDin = results.getString(7);
             spouseIDin = results.getString(8);

        }else{
            throw new SQLException();
        }
            results.close();
            stmt.close();
            return new ModelPersons(personIDin,
                    descendantin,
                    first_namein,
                    last_namein,
                    genderin,
                    fatherIDin,
                    motherIDin,
                    spouseIDin);
//        }
//        catch(SQLException ex){
//            ex.printStackTrace();
//        }
//        return null;
    }

//    public ModelPersons getRowByUsername(String person, Connection conn) throws SQLException{
////        try {
//        String query = "select * from persons where username = ?";
//        PreparedStatement stmt = conn.prepareStatement(query);
//        stmt.setString(1, person);
//        ResultSet results = stmt.executeQuery();
//        String personIDin = "";
//        String descendantin = "";
//        String first_namein = "";
//        String last_namein = "";
//        String genderin = "";
//        String fatherIDin = "";
//        String motherIDin = "";
//        String spouseIDin = "";
//        while(results.next()){ //it isnt going into here
//            personIDin = results.getString(1);
//            descendantin = results.getString(2);
//            first_namein = results.getString(3);
//            last_namein= results.getString(4);
//            genderin= results.getString(5);
//            fatherIDin = results.getString(6);
//            motherIDin = results.getString(7);
//            spouseIDin = results.getString(8);
//
//        }
//        results.close();
//        stmt.close();
//        return new ModelPersons(personIDin,
//                descendantin,
//                first_namein,
//                last_namein,
//                genderin,
//                fatherIDin,
//                motherIDin,
//                spouseIDin);
////        }
////        catch(SQLException ex){
////            ex.printStackTrace();
////        }
////        return null;
//    }

    /** returns an ArrayList of multiple modelClass objects that represent rows in the persons table
     * @param descendant
     * @return   an ArrayList of the modelClass objects representing the rows requested
     */
    public ArrayList<ModelPersons> getRows(String descendant, Connection conn) throws SQLException{
        ArrayList<ModelPersons> toReturn = new ArrayList<ModelPersons>();
        String query = "select * from persons where descendant = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, descendant);
        ResultSet results = stmt.executeQuery();
        while(results.next()) {
            String personIDin = results.getString(1);
            String descendantin = results.getString(2);
            String first_namein = results.getString(3);
            String last_namein = results.getString(4);
            String genderin = results.getString(5);
            String fatherIDin = results.getString(6); //these may not always work
            String motherIDin = results.getString(7);
            String spouseIDin = results.getString(8);
            ModelPersons newPerson = new ModelPersons(personIDin, descendantin, first_namein, last_namein, genderin, fatherIDin, motherIDin, spouseIDin);
            toReturn.add(newPerson);
        }
        results.close();
        stmt.close();
        return toReturn;
    }

    /** creates a row in the persons table that is represented by the ModelClass object passed in
     * @param thisObject a modelClass object that represents a table row
     */
    public void createRow(ModelPersons thisObject, Connection conn) throws SQLException{

            String insert = "insert into persons values(?,?,?,?,?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(insert);
            stmt.setString(1, thisObject.getpersonID());
            stmt.setString(2, thisObject.getdescendant());
            stmt.setString(3, thisObject.getfirstName());
            stmt.setString(4, thisObject.getlastName());
            stmt.setString(5, thisObject.getgender());
            stmt.setString(6, thisObject.getfather());
            stmt.setString(7, thisObject.getmother());
            stmt.setString(8, thisObject.getspouse());
            stmt.executeUpdate();
            stmt.close();

    }

    /** creates the persons table in the database
     */
    public void createTable(Connection conn){
        try{
            String create = "create table if not exists persons(\n" + "personID text not null primary key,\n" +
                    "descendant text not null,\n" +
                    "first_name text not null,\n" +
                    "last_name text not null,\n" +
                    "gender text not null,\n" +
                    "fatherID text,\n" +
                    "motherID text,\n" +
                    "spouseID text\n)";
            PreparedStatement stmt = conn.prepareStatement(create);
            stmt.executeUpdate();
            stmt.close();
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    /** removes a row from the persons table
     * @param person  name of personID used as the key to find the row to be removed
     * @return  boolean value of whether or not the removal was successful
     */
    public boolean removeRow(String person, Connection conn){
        try {
            String delete = "delete from persons where personID = ? ";
            PreparedStatement stmt = conn.prepareStatement(delete);
            stmt.setString(1, person);
            stmt.executeUpdate();
            stmt.close();
        }
        catch(SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean removeRowByDescendant(String descendant, Connection conn){
        try {
            String delete = "delete from persons where descendant = ? ";
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


    public void dropTable(Connection conn){
        try{
            String drop = "drop table if exists persons";
            PreparedStatement stmt = conn.prepareStatement(drop);
            stmt.executeUpdate();
            stmt.close();
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }

}