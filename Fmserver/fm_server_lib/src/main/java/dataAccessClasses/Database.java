package dataAccessClasses;

import java.sql.*;

/**
 * Created by logan on 10/25/2017.
 */

//handles the access classes and how they manage the database
public class Database {

    static{
        try{
            String driver = "org.sqlite.JDBC";
            Class.forName(driver);
        }catch(ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }

    AccessAuthTokens tokenAccess = new AccessAuthTokens();
    AccessPersons personAccess = new AccessPersons();
    AccessUsers userAccess = new AccessUsers();
    AccessEvents eventsAccess = new AccessEvents();

    private Connection conn;

    public Database(){

        conn = this.openConnection();
        tokenAccess.createTable(conn);
        personAccess.createTable(conn);
        eventsAccess.createTable(conn);
        userAccess.createTable(conn);
//        this.closeConnection();
        //create database
        //load driver
    }

    public Connection openConnection() {

        try {
            //should this be sqlite or db?
            final String CONNECTION_URL = "jdbc:sqlite:database.db";

            // Open a database connection
            conn = DriverManager.getConnection(CONNECTION_URL);

            // Start a transaction
//            conn.setAutoCommit(false);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public void closeConnection() {
        try{
//            if(commit){
//                conn.commit();
//            }
//            else{
//                conn.rollback();
//            }

            conn.close();
            conn = null;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    //have to define database exception

    }
}


