package com.example.logan.fmclient_two.clientSide;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import facadeClasses.RequestLogin;
import facadeClasses.ResponseEvent;
import facadeClasses.ResponseEvents;
import facadeClasses.ResponseLogin;
import facadeClasses.ResponsePeople;
import modelClasses.ModelEvents;
import modelClasses.ModelPersons;

import static org.junit.Assert.*;

/**
 * Created by logan on 12/14/2017.
 */
public class ModelTest {

    Model model = new Model();
    ServerProxy proxy = new ServerProxy("192.168.122.14", "7777");

    @Before
    public void setUp() throws Exception {
        //get an auth_token from correct login
        RequestLogin request = new RequestLogin();
        request.setUserName("sheila");
        request.setpassword("parker");
        ResponseLogin response = proxy.login(request);
        String auth_token = response.getAuthToken();

        //get people into model
        ResponsePeople peopleResponse = proxy.people(auth_token);
        model.getInstance().setPeople(peopleResponse.getData());

        //get events into model
        ResponseEvents eventsResponse = proxy.events(auth_token);
        model.getInstance().setEvents(eventsResponse.getData());

        //organizes model events for future use
        model.getInstance().organizeModel();

        //sets events that will be displayed
        model.getInstance().updateEventsToDisplay();

    }

    @After
    public void tearDown() throws Exception {
        model.getInstance().clear();
    }

    //filter events according to the current filter settings
    @Test
    public void updateEventsToDisplay() throws Exception {
        int sizeBeforeUpdate = model.getInstance().getEvents().size();
        model.getInstance().getUserFilter().setFemaleEvents(false); //this should take away half the events
        model.getInstance().updateEventsToDisplay();
        assertTrue(model.getInstance().getEvents().size() != sizeBeforeUpdate);

        model.getInstance().getUserFilter().setFemaleEvents(true); //this should set them back
        model.getInstance().updateEventsToDisplay();
        assertFalse(model.getInstance().getEvents().size() != sizeBeforeUpdate);
    }

    @Test
    public void determineChild() throws Exception {
        ModelPersons person = model.getInstance().getPersonByID("Blaine_McGary"); //gets sheilas father
        ModelPersons child = model.getInstance().determineChild(person);
        assertTrue(child.getfather().equals(person.getpersonID()));

        assertFalse(child.getfather().equals(person.getspouse()));
    }

    @Test
    public void findSpouseByID() throws Exception {
        ModelPersons person = model.getInstance().getPersonByID("Blaine_McGary"); //gets sheilas father
        ModelPersons spouse = model.getInstance().findSpouseByID(person);
        assertTrue(spouse.getspouse().equals(person.getpersonID()));

        assertFalse(spouse.getspouse().equals(person.getspouse()));
    }

    //chronologically ordering a persons events
    @Test
    public void getOrderLifeEvents() throws Exception {
        //shows that all the events are not ordered properly
        assertFalse(model.getInstance().getEvents().get(1).getyear() < model.getInstance().getEvents().get(5).getyear());
        ArrayList<ModelEvents> event = model.getInstance().getOrderLifeEvents(model.getInstance().getPeople().get(0));
        //now they are
        assertTrue(event.get(0).getyear() < event.get(1).getyear());
    }

    @Test
    public void findMother() throws Exception {
        ModelPersons person = model.getInstance().getPersonByID("Sheila_Parker"); //gets sheilas father
        ModelPersons mother = model.getInstance().findMother(person);
        assertTrue(mother.getpersonID().equals(person.getmother()));

        assertFalse(mother.getpersonID().equals(person.getfather()));
    }

    @Test
    public void findFather() throws Exception {
        ModelPersons person = model.getInstance().getPersonByID("Sheila_Parker"); //gets sheilas father
        ModelPersons father = model.getInstance().findFather(person);
        assertTrue(father.getpersonID().equals(person.getfather()));

        assertFalse(father.getpersonID().equals(person.getmother()));
    }

    //searching for people and events
    @Test
    public void search() throws Exception {
        model.getInstance().search("b"); //proves that this narrows down the list
        assertFalse(model.getInstance().getSearchList().size() == (model.getInstance().getEvents().size()+model.getInstance().getPeople().size()));
    }

}