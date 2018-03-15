package facadeClasses;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.util.Scanner;

import dataAccessClasses.AccessEvents;

import static org.junit.Assert.*;



/**
 * Created by logan on 11/1/2017.
 */
public class ServerFacadeTest {

    ServerFacade facade = new ServerFacade();
    EncodeDecode coder = new EncodeDecode();

    @Test
    public void register() throws Exception {

        facade.clear();

        //first registration test
        RequestRegister send = new RequestRegister();
        send.setuserName("Logan");
        send.setemail("email");
        send.setpassword("pass");
        send.setfirstName("Logan");
        send.setlastName("Thorneloe");
        send.setgender("m");


        ResponseRegister get_back = facade.register(send);

        assertTrue(get_back.getUserName().equals("Logan"));
        try {
            get_back.getMessage().equals("Could not add to database.");
        }catch(NullPointerException ex){//message should be null
            assertTrue(true);
        }

        //second will test a failure
        ResponseRegister get_back_second = facade.register(send);
        assertTrue(get_back_second.getMessage().equals("Could not add to database."));
        try {
            get_back_second.getUserName().equals("Logan");
        }catch(NullPointerException ex){//message should be null
            assertFalse(false);
        }

        clear(); //test clear function
    }

    @Test
    public void login() throws Exception {
        //ensure the database is clear
        facade.clear();

        //sets up database
        RequestRegister send = new RequestRegister();
        send.setuserName("Logan");
        send.setemail("email");
        send.setpassword("pass");
        send.setfirstName("Logan");
        send.setlastName("Thorneloe");
        send.setgender("m");

        ResponseRegister get_back = facade.register(send);
        //valid login
        RequestLogin login = new RequestLogin();
        login.setpassword("pass");
        login.setUserName("Logan");

        ResponseLogin response = facade.login(login);
//        System.out.println(response.getUserName());
        assertTrue(response.getUserName().equals("Logan"));

        //invalid login
        RequestLogin login2 = new RequestLogin();
        login.setpassword("password");
        login.setUserName("Logan");

        ResponseLogin response2 = facade.login(login);
        assertTrue(response2.getMessage().equals("Credentials not applicable"));

        clear(); //test clear function
    }

    @Test
    public void clear() throws Exception {
        facade.clear();
    }

    @Test
    public void fill() throws Exception {
        //fills database
        RequestRegister send = new RequestRegister();
        send.setuserName("Logan");
        send.setemail("email");
        send.setpassword("pass");
        send.setfirstName("Logan");
        send.setlastName("Thorneloe");
        send.setgender("m");


        ResponseRegister get_back = facade.register(send);
        //standard test
        ResponseFill fill_back = facade.fill("Logan");
        assertTrue(fill_back.getMessage() != null);

        //extra generations
        ResponseFill fill_back2 =facade.fill("Logan", 5);
        assertTrue(fill_back2.getMessage() != null);

        //failure
        ResponseFill fill_back3 =facade.fill("Not_Logan", 5);
        assertTrue(fill_back2.getMessage() != "Unable to locate user in database.");


        clear();
    }

    @Test
    public void load() throws Exception {
        File file = new File("C:/Users/logan/Documents/Android/Fmserver/fm_server_lib/src/json/example.json");
        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        fis.read(data);
        fis.close();
        String str = new String(data, "UTF-8"); //takes in example.json as String
        RequestLoad loader = coder.decodetoRequestLoad(str);

        //lets us know if it works
        ResponseLoad loadResponse = facade.load(loader);
        assertTrue(loadResponse.getMessage() != null);

        clear();
    }

    @Test
    public void person() throws Exception {
        facade.clear();
        //load a database
        RequestRegister send = new RequestRegister();
        send.setuserName("Logan");
        send.setemail("email");
        send.setpassword("pass");
        send.setfirstName("Logan");
        send.setlastName("Thorneloe");
        send.setgender("m");

        ResponseRegister get_back = facade.register(send);

        ResponsePerson person = facade.person(get_back.getAuthToken(),get_back.getPersonID());

        //tests successes
        assertTrue(person.getMessage() == null);
        assertTrue(person.getFirstName().equals("Logan"));

        //test failures
        ResponsePerson person2 = facade.person("ffffffff", get_back.getPersonID());
        assertFalse(person2.getMessage().equals("Logan"));
        assertTrue(person2.getMessage().equals("You are not authorized to access this."));

        clear();
    }

    @Test
    public void people() throws Exception {
        facade.clear();
        //load a database
        File file = new File("C:/Users/logan/Documents/Android/Fmserver/fm_server_lib/src/json/example.json");
        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        fis.read(data);
        fis.close();
        String str = new String(data, "UTF-8"); //takes in example.json as String
        RequestLoad loader = coder.decodetoRequestLoad(str);
        ResponseLoad loadResponse = facade.load(loader);

        //login to receive auth_token
        RequestLogin login = new RequestLogin();
        login.setpassword("parker");
        login.setUserName("sheila");

        ResponseLogin response = facade.login(login);
        String auth_token = response.getAuthToken();
        ResponsePeople persons = facade.people(auth_token);

        //success
        assertTrue(persons.getMessage() == null);

        ResponsePeople poeple2 = facade.people("ffffffff");
        //failure
        assertFalse(poeple2.getMessage() == null);

        clear();
    }

    @Test
    public void event() throws Exception {

        facade.clear();
        //load a database
        RequestRegister send = new RequestRegister();
        send.setuserName("Logan");
        send.setemail("email");
        send.setpassword("pass");
        send.setfirstName("Logan");
        send.setlastName("Thorneloe");
        send.setgender("m");

        ResponseRegister get_back = facade.register(send);

        ResponseEvent event = facade.event(get_back.getAuthToken(), "This_Will_Never_Be_Valid");

        //tests first failure, make sure message body is received
        assertTrue(event.getMessage() != null);
//        assertTrue(event.getMessage().equals("Event is not in database."));

        //test second failure case
        ResponsePerson person2 = facade.person("ffffffff", get_back.getPersonID());
        assertFalse(person2.getMessage().equals("Logan"));
        assertTrue(person2.getMessage().equals("You are not authorized to access this."));

        clear();
    }

    @Test
    public void events() throws Exception {
        facade.clear();
        //load a database
        File file = new File("C:/Users/logan/Documents/Android/Fmserver/fm_server_lib/src/json/example.json");
        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        fis.read(data);
        fis.close();
        String str = new String(data, "UTF-8"); //takes in example.json as String
        RequestLoad loader = coder.decodetoRequestLoad(str);
        ResponseLoad loadResponse = facade.load(loader);

        //login to receive auth_token
        RequestLogin login = new RequestLogin();
        login.setpassword("parker");
        login.setUserName("sheila");

        ResponseLogin response = facade.login(login);
        String auth_token = response.getAuthToken();
        ResponseEvents events = facade.events(auth_token);

        //success
        assertTrue(events.getMessage() == null);

        ResponseEvents events2 = facade.events("ffffffff");
        //failure
        assertFalse(events2.getMessage() == null);

        clear();

    }

}