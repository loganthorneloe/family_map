package dataAccessClasses;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import modelClasses.ModelPersons;
import modelClasses.ModelUsers;

import static org.junit.Assert.*;

/**
 * Created by logan on 11/1/2017.
 */
public class AccessUsersTest {

    AccessUsers tester = new AccessUsers();
    Database database = new Database();
    ModelUsers obj = new ModelUsers("username", "username", "username", "username", "username", "username", "username");
    Connection conn;

    //setup and tear down test the createTable and dropTable functions
    @Before
    public void setUp() throws Exception {
        conn = database.openConnection();
        tester.createTable(conn);
        tester.createRow(obj,conn);
        ModelUsers token = new ModelUsers("This", "That", "This", "This", "This", "This", "This");
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
        assertTrue(tester.getRow(token, conn).getuserName().equals(obj.getpersonID()));
        assertFalse(tester.getRow(token, conn).getuserName().equals("This"));
    }

    @Test
    public void createRow() throws Exception {
        //checks for row creation by asserting true when searching for the row and false when finding another row
        ModelUsers tokentester = new ModelUsers("auth_token", "auth_token","auth_token","auth_token", "auth_token","auth_token", "auth_token");
        tester.createRow(tokentester, conn);
        assertTrue(tokentester.getpersonID().equals(tester.getRow("auth_token", conn).getpersonID()));
        assertFalse(tokentester.getpersonID().equals(tester.getRow("This",conn).getpersonID()));
    }

    @Test
    public void removeRow() throws Exception {
        //searches for row that was there after calling the remove function on it to prove it no longer exists
        tester.removeRow("This", conn); //this works
        try{
            tester.getRow("This", conn).getuserName().equals(obj.getuserName());
        }catch(SQLException ex){ //throws exception if it no longer exists
            assertTrue(true);
        }
        assertTrue(tester.getRow("username", conn).getuserName().equals("username")); //this should be true because it exists

    }

}