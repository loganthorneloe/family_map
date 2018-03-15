package com.example.logan.fmclient_two.clientSide;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import facadeClasses.RequestLogin;
import facadeClasses.RequestRegister;
import facadeClasses.ResponseEvents;
import facadeClasses.ResponseLogin;
import facadeClasses.ResponsePeople;
import facadeClasses.ResponseRegister;

import static org.junit.Assert.*;

/**
 * Created by logan on 12/14/2017.
 */
public class ServerProxyTest {

    //my server is running, here are the port and ip address
    ServerProxy proxy = new ServerProxy("192.168.122.14", "7777");

    @Test
    public void register() throws Exception {
        //database is already set up with TA data

        //a correct test
        RequestRegister request = new RequestRegister();
        request.setuserName("d");
        request.setpassword("d");
        request.setfirstName("d");
        request.setlastName("d");
        request.setgender("d");
        request.setemail("d");
        ResponseRegister response = proxy.register(request);
        assertTrue(response.getAuthToken() != null);
        assertFalse(response.getMessage() != null);

        //this test should not work
        RequestRegister request2 = new RequestRegister();
        request.setuserName("sheila");
        request.setpassword("parker");
        request.setfirstName("Sheila");
        request.setlastName("Parker");
        request.setgender("f");
        request.setemail("sheila@parker.com");
        ResponseRegister response2 = proxy.register(request2);
        assertTrue(response2.getAuthToken() == null);
        assertFalse(response2.getMessage() == null);
    }

    @Test
    public void login() throws Exception {
        //successful test
        RequestLogin request = new RequestLogin();
        request.setUserName("sheila");
        request.setpassword("parker");
        ResponseLogin response = proxy.login(request);
        assertTrue(response.getAuthToken() != null);
        assertFalse(response.getMessage() != null);

        //unsuccessful test
        RequestLogin request2 = new RequestLogin();
        request.setUserName("k");
        request.setpassword("k");
        ResponseLogin response2 = proxy.login(request2);
        assertTrue(response2.getAuthToken() == null);
        assertFalse(response2.getMessage() == null);
    }

    @Test
    public void people() throws Exception {
        //get an auth token from a successful login attempt
        RequestLogin request = new RequestLogin();
        request.setUserName("sheila");
        request.setpassword("parker");
        ResponseLogin response = proxy.login(request);
        String auth_token = response.getAuthToken();
        ResponsePeople response2 = proxy.people(auth_token);
        assertTrue(response2.getMessage() == null); //means it was successful

        //this attempt should fail
        ResponsePeople fail = proxy.people("251837d7"); //this auth_token is invalid
        assertFalse(fail.getMessage() == null);
    }

    @Test
    public void events() throws Exception {
        //get an auth token from a successful login attempt
        RequestLogin request = new RequestLogin();
        request.setUserName("sheila");
        request.setpassword("parker");
        ResponseLogin response = proxy.login(request);
        String auth_token = response.getAuthToken();
        ResponseEvents response2 = proxy.events(auth_token);
        assertTrue(response2.getMessage() == null); //means it was successful

        //this attempt should fail
        ResponseEvents fail = proxy.events("251837d7"); //this auth_token is invalid
        assertFalse(fail.getMessage() == null);
    }

}