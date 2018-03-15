package dataAccessClasses;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;

import modelClasses.ModelAuthTokens;
import modelClasses.ModelEvents;
import modelClasses.ModelPersons;

import java.sql.SQLException;
import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by logan on 11/1/2017.
 */
public class AccessPersonsTest {

    AccessPersons tester = new AccessPersons();
    Database database = new Database();
    ModelPersons obj = new ModelPersons("username", "username", "username", "username", "username", "username", "username", "username");
    Connection conn;

    //setup and tear down test the createTable and dropTable functions
    @Before
    public void setUp() throws Exception {
        conn = database.openConnection();
        tester.createTable(conn);
        tester.createRow(obj,conn);
        ModelPersons token = new ModelPersons("This", "That", "This", "This", "This", "This", "This", "This");
        tester.createRow(token, conn);
    }

    @After
    public void tearDown() throws Exception {
        tester.dropTable(conn);
        database.closeConnection();
    }

    @Test
    public void getInstance() throws Exception {
        //checks to make sure the classes of the instances are correct
        assertTrue(tester.getClass() == tester.getInstance().getClass());
        assertFalse(tester.getClass() != tester.getInstance().getClass());
    }

    @Test
    public void getRow() throws Exception {
        //makes sure getRow uses the key given to it to properly retrieve a row from database
        String token = "username";
        assertTrue(tester.getRow(token, conn).getpersonID().equals(obj.getpersonID()));
        assertFalse(tester.getRow(token, conn).getpersonID().equals("This"));
    }

    @Test
    public void getRows() throws Exception {
        ModelPersons obj2 = new ModelPersons("user", "username", "username", "username", "username", "username", "username", "username");
        ModelPersons obj3 = new ModelPersons("passer", "username", "username", "username", "username", "username", "username", "username");
        tester.createRow(obj2, conn);
        tester.createRow(obj3, conn);
        ArrayList<ModelPersons> list = tester.getRows("username", conn);
        for(ModelPersons p: list){
            assertTrue(p.getdescendant().equals("username"));
        }

    }

    @Test
    public void createRow() throws Exception {
        //checks for row creation by asserting true when searching for the row and false when finding another row
        ModelPersons tokentester = new ModelPersons("auth_token", "auth_token","auth_token", "auth_token","auth_token", "auth_token","auth_token", "auth_token");
        tester.createRow(tokentester, conn);
        assertTrue(tokentester.getpersonID().equals(tester.getRow("auth_token", conn).getpersonID()));
        assertFalse(tokentester.getpersonID().equals(tester.getRow("This",conn).getpersonID()));
    }

    @Test
    public void removeRow() throws Exception {
        //searches for row that was there after calling the remove function on it to prove it no longer exists
        tester.removeRow("This", conn); //this works
        try{
            tester.getRow("This", conn).getpersonID().equals(obj.getpersonID());
        }catch(SQLException ex){ //throws exception if it no longer exists
            assertTrue(true);
        }
        assertTrue(tester.getRow("username", conn).getpersonID().equals("username")); //this should be true because it exists
    }

    @Test
    public void removeRowByDescendant() throws Exception {
        //searches for row that was there after calling the remove function on it to prove it no longer exists
        tester.removeRowByDescendant("That", conn); //this works
        try{
            tester.getRow("This", conn).getpersonID().equals(obj.getpersonID());
        }catch(SQLException ex){ //exception should be thrown here
            assertTrue(true);
        }
        try{
            tester.getRow("username", conn).getpersonID().equals("username");
        }catch(SQLException ex){ //should not be thrown here
            assertTrue(false);
        }

    }


}