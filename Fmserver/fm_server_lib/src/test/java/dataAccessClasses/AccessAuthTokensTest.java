package dataAccessClasses;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import java.sql.*;

import modelClasses.ModelAuthTokens;

/**
 * Created by logan on 10/28/2017.
 */
public class AccessAuthTokensTest {

    AccessAuthTokens tester = new AccessAuthTokens();
    Database database = new Database();
    ModelAuthTokens obj = new ModelAuthTokens("username", "token");
    Connection conn;

    //need to add to the auth tokens table here

    @Test
    public void getInstance() throws Exception {
        //checks to make sure the classes of the instances are correct
        assertTrue(tester.getClass() == tester.getInstance().getClass());
        assertFalse(tester.getClass() != tester.getInstance().getClass());
    }

    @Test
    public void getRow() throws Exception {
        //makes sure getRow uses the key given to it to properly retrieve a row from database
        String token = "token";
        assertTrue(tester.getRow(token, conn).getauthToken().equals(obj.getauthToken()));
        assertFalse(tester.getRow(token, conn).getuserName().equals("user"));
    }

    @Test
    public void createRow() throws Exception {
        //checks for row creation by asserting true when searching for the row and false when finding another row
        ModelAuthTokens tokentester = new ModelAuthTokens("user", "auth_token");
        tester.createRow(tokentester, conn);
        assertTrue(tokentester.getauthToken().equals(tester.getRow("auth_token", conn).getauthToken()));
        assertFalse(tokentester.getauthToken().equals(tester.getRow("That",conn).getauthToken()));

    }

    @Test
    public void createTable() throws Exception {
        //works, not sure how to assert anything here, but I checked my database and this functions correctly
        tester.createTable(conn);

    }


    @Test
    public void removeRow() throws Exception {
        //searches for row that was there after calling the remove function on it to prove it no longer exists
        tester.removeRow("This", conn); //this works
        assertFalse(tester.getRow("This", conn).getauthToken().equals(obj.getauthToken()));
        assertTrue(tester.getRow("token", conn).getuserName().equals("username"));
    }

    //setup and tear down test the createTable and dropTable functions
    @Before
    public void setUp() throws Exception {  //set up a table with a row for each test
        conn = database.openConnection();
        tester.createTable(conn);
        tester.createRow(obj,conn);
        ModelAuthTokens token = new ModelAuthTokens("This", "That");
        tester.createRow(token, conn);

    }

    @After
    public void tearDown() throws Exception { //tear down the table after each test
        tester.dropTable(conn);
        database.closeConnection();
    }
}