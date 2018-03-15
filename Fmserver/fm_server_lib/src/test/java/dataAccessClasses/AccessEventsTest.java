package dataAccessClasses;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;

import modelClasses.ModelAuthTokens;
import modelClasses.ModelEvents;

import static org.junit.Assert.*;

/**
 * Created by logan on 11/1/2017.
 */
public class AccessEventsTest {

    AccessEvents tester = new AccessEvents();
    Database database = new Database();
    Double two = 2.5;
    ModelEvents obj = new ModelEvents("username", "username", "username", two, two, "username", "username", "username", 0);
    Connection conn;

    @Test
    public void getInstance() throws Exception {
        //checks to make sure the classes of the instances are correct
        assertTrue(tester.getClass() == tester.getInstance().getClass());
        assertFalse(tester.getClass() != tester.getInstance().getClass());
    }

    @Test
    public void getRow() throws Exception {
        //makes sure getRow uses the key given to it to properly retrieve a row from database
        String eventID = "username";
        assertTrue(tester.getRow(eventID, conn).geteventID().equals(obj.geteventID()));
        assertFalse(tester.getRow(eventID, conn).getdescendant().equals("user"));
    }

    @Test
    public void createRow() throws Exception {
        //checks for row creation by asserting true when searching for the row and false when finding another row
        ModelEvents tokentester = new ModelEvents("user", "user", "user", two, two, "user", "user", "user", 0);
        ModelEvents tokentester2 = new ModelEvents("test", "test", "test", two, two, "test", "test", "test", 0);
        tester.createRow(tokentester, conn);
        tester.createRow(tokentester2, conn);
        assertTrue(tokentester.geteventID().equals(tester.getRow("user", conn).geteventID()));
        assertFalse(tokentester.geteventID().equals(tester.getRow("test",conn).geteventID())); //sqlexception
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
        assertFalse(tester.getRow("This", conn).geteventID().equals(obj.geteventID()));
        assertTrue(tester.getRow("username", conn).geteventID().equals("username"));
    }

    @Test
    public void removeRowDescendant() throws Exception {
        ModelEvents tokentester = new ModelEvents("user", "user", "user", two, two, "user", "user", "user", 0);
        tester.removeRowDescendant("user", conn);
    }

    @Test
    public void removeRowPersonID() throws Exception {
        ModelEvents tokentester = new ModelEvents("user", "user", "user", two, two, "user", "user", "user", 0);
        tester.removeRowPersonID("user", conn);
    }


    //setup and tear down test the createTable and dropTable functions
    @Before
    public void setUp() throws Exception {
        conn = database.openConnection();
        tester.createTable(conn);
        tester.createRow(obj,conn);
        ModelEvents token = new ModelEvents("This", "That", "This", two, two, "This", "This", "This", 0);
        tester.createRow(token, conn);
    }

    @After
    public void tearDown() throws Exception {
        tester.dropTable(conn);
        database.closeConnection();
    }
}