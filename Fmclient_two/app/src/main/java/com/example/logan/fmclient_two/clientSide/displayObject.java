package com.example.logan.fmclient_two.clientSide;

import modelClasses.ModelEvents;
import modelClasses.ModelPersons;

/**
 * Created by logan on 12/12/2017.
 */

public class displayObject {

    Model model = new Model();

    //**THIINGS TO DISPLAY FOR PERSON
    private String gender;
    public String relation;

    //**THINGS TO DISPLAY FOR EVENTS
    private String eventdetails;

    //**THINGS TO DISPLAY FOR BOTH
    private String name;

    //**STORAGE
    private ModelPersons person;
    private ModelEvents event;
    private Integer year;
    private String eventType;

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEventdetails() {
        return eventdetails;
    }

    public void setEventdetails(String eventdetails) {
        this.eventdetails = eventdetails;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ModelPersons getPerson() {
        return person;
    }

    public void setPerson(ModelPersons person) {
        this.person = person;
        setName(person.getfirstName() + " " + person.getlastName());
        setGender(person.getgender());
    }

    public ModelEvents getEvent() {
        return event;
    }

    public void setEvent(ModelEvents event) {
        this.event = event;
        setName(model.getInstance().getPersonByEvent(event).getfirstName() + " " + model.getInstance().getPersonByEvent(event).getlastName());
        setEventdetails(model.getInstance().getEventInfo(event));
        setYear(event.getyear());
        setEventType(event.geteventType());
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }
}
