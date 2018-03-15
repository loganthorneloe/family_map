package com.example.logan.fmclient_two.clientSide;


import java.util.ArrayList;
import java.util.Collections;

import modelClasses.*;

/**
 * Created by logan on 11/14/2017.
 */

public class Model {

    private static Model instance = null;

    public static Model getInstance() {
        if (instance == null) {
            instance = new Model();
        }
        return instance;
    }

    Settings userSettings = new Settings();

    Filter userFilter = new Filter();

    //all people for current user
    ArrayList<ModelPersons> people = new ArrayList<ModelPersons>();
    //all events for current user
    ArrayList<ModelEvents> events = new ArrayList<ModelEvents>();
    //all event types for current user
    ArrayList<String> eventTypes = new ArrayList<String>();
    //all people on person fatherSide
    ArrayList<ModelPersons> personFatherSide = new ArrayList<ModelPersons>();
    //adds user and spouse
    ArrayList<ModelPersons> userAndSpouse = new ArrayList<ModelPersons>();
    //user and spouse events
    ArrayList<ModelEvents> userAndSpouseEvent = new ArrayList<ModelEvents>();
    //all people on person motherSide
    ArrayList<ModelPersons> personMotherSide = new ArrayList<ModelPersons>();
    //all events on person fatherSide
    ArrayList<ModelEvents> eventsFatherSide = new ArrayList<ModelEvents>();
    //all events on person motherSide
    ArrayList<ModelEvents> eventsMotherSide = new ArrayList<ModelEvents>();
    //final list of events to display based on filter settings
    ArrayList<ModelEvents> eventsToDisplay = new ArrayList<ModelEvents>();
    //list of ALL spouse lines to connect by spouse's respective first events
    ArrayList<PairofEvents> spouseListLines = new ArrayList<PairofEvents>();
    //stores the searchedList to display
    ArrayList<displayObject> searchList = new ArrayList<displayObject>();
    //stores persons auth_token, store this in SyncTaskPeople class
    private String auth_token;
    private String host;
    private String port;

    public ArrayList<ModelPersons> getPeople() {
        return people;
    }

    public ArrayList<ModelEvents> getEvents() {
        return eventsToDisplay;
    }

    public ArrayList<displayObject> getSearchList() {
        return searchList;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getEventType(int index){
        return eventsToDisplay.get(index).geteventType();
    }

    public String getEventInfo(ModelEvents event){
        String toReturn = event.geteventType().toLowerCase() + ": " + event.getcity() + ", "
                + event.getcountry() + " (" + event.getyear() + ")";
        return toReturn;
    }

    public ModelPersons getPersonByID(String id){
        for(int i = 0; i < people.size(); i++){
            if(people.get(i).getpersonID().equals(id)){
                return people.get(i);
            }
        }
        return null;
    }

    public String getAuthToken(){
        return auth_token;
    }

    public void storeAuthToken(String token){
        auth_token = token;
    }

    public ModelEvents getEventByID(String eventID){
        for(int i = 0; i < eventsToDisplay.size(); i++){
            if(eventsToDisplay.get(i).geteventID().equals(eventID)){ //this is the proper event
               return eventsToDisplay.get(i);
            }
        }
        return null; //no event exists with this id
    }

    public ModelEvents getEventAt(int index){ return eventsToDisplay.get(index); }

    public Filter getUserFilter() {
        return userFilter;
    }

    //gets the full name of the person who the event belongs
    public ModelPersons getPersonNameAtEventIndex(ModelEvents event){
        for(int i = 0; i < people.size(); i++){
            if(event.getpersonID().equals(people.get(i).getpersonID())){ //this is the person the event belongs to
                return people.get(i); //returns the persons name
            }
        }
        return null;
    }

    public int getHue(String eventType){
        int hueSplit = 360/(eventTypes.size()+1);
        int hueManipulator = 1;
        for(int i = 0; i < eventTypes.size(); i++){
            if(eventTypes.get(i).equalsIgnoreCase(eventType)){
                hueManipulator = i;
            }
        }
        return hueManipulator*hueSplit;
    }

    public void updateEventsToDisplay(){
        eventsToDisplay.clear();

        for(int i = 0; i < userAndSpouseEvent.size(); i++){
            eventsToDisplay.add(userAndSpouseEvent.get(i));
        }

        if(userFilter.isFatherEvents() == true){
            for(int i = 0; i < eventsFatherSide.size(); i++){
                eventsToDisplay.add(eventsFatherSide.get(i));
            }
        }
        if(userFilter.isMotherEvents() == true){
            for(int i = 0; i < eventsMotherSide.size(); i++){
                eventsToDisplay.add(eventsMotherSide.get(i));
            }
        }

        if(userFilter.isFemaleEvents() == false){ //remove all female events
            for(int i = eventsToDisplay.size() - 1; i >=0 ; i--){
                if(findPerson(eventsToDisplay.get(i).getpersonID()).getgender().equalsIgnoreCase("f")){
                    eventsToDisplay.remove(i);
                }
            }
        }
        if(userFilter.isMaleEvents() == false){ //remove all male events
            for(int i = eventsToDisplay.size() - 1; i >=0 ; i--){
                if(findPerson(eventsToDisplay.get(i).getpersonID()).getgender().equalsIgnoreCase("m")){
                    eventsToDisplay.remove(i);
                }
            }
        }

        //something wrong with this for-loop
        for(int i = 0; i < userFilter.getFilterArray().size(); i++){ //goes through each event type filter
            if(userFilter.getFilterArray().get(i) == false){ //goes in here only if that event type is NOT to be shown
                String eventType = eventTypes.get(i); //gets event type corresponding to the filer
                for(int j = eventsToDisplay.size() - 1; j >= 0; j--){ //looks through display events BACKWARD to remove those that do not belong
                    if(eventsToDisplay.get(j).geteventType().equalsIgnoreCase(eventType)){
                        eventsToDisplay.remove(j); //removes the correct event from the events to display
                    }
                }
            }
        }
        boolean stophere = true;
        if(stophere){

        }

    }

    public void groupPeopleBySide(String familySide){ //base case for recursion
        ModelPersons person = people.get(0); //grabs users personID
        if(familySide.equals("mother")){ //looking for mother side
            if(person.getmother()!=null){ //mother actually exists
                personMotherSide.add(findPerson(person.getmother())); //add the mother to motherside
                findAllAncestorsAddToMotherSide(findPerson(person.getmother()));
            }
        }else{ //looking for father side
            if(person.getfather()!=null){ //father actually exists
                personFatherSide.add(findPerson(person.getfather())); //add the father to fatherside
                findAllAncestorsAddToFatherSide(findPerson(person.getfather()));
            }
        }
    }

    public double getEventLat(int index){
        return eventsToDisplay.get(index).getlatitude();
    }

    public double getEventLong(int index){
        return eventsToDisplay.get(index).getlongitude();
    }

    public void findAllAncestorsAddToMotherSide(ModelPersons person){
        if(person.getfather() != null && person.getmother() != null){ //if it makes it past here, either father is null or mother is null
            personMotherSide.add(findPerson(person.getmother())); //add the mother to motherside
            personMotherSide.add(findPerson(person.getfather())); //add the father to motherside
            findAllAncestorsAddToMotherSide(findPerson(person.getmother()));
            findAllAncestorsAddToMotherSide(findPerson(person.getfather()));
        }else if(person.getfather() != null){ //only father isn't null
            personMotherSide.add(findPerson(person.getfather())); //add the mother to motherside
            findAllAncestorsAddToMotherSide(findPerson(person.getfather()));
        }else if(person.getmother() != null){ //only mother isn't null
            personMotherSide.add(findPerson(person.getmother())); //add the father to motherside
            findAllAncestorsAddToMotherSide(findPerson(person.getmother()));
        }else{ //both are null
            return;
        }
    }

    public void findAllAncestorsAddToFatherSide(ModelPersons person){
        if(person.getfather() != null && person.getmother() != null){ //if it makes it past here, either father is null or mother is null
            personFatherSide.add(findPerson(person.getmother())); //add the mother to fatherside
            personFatherSide.add(findPerson(person.getfather())); //add the father to fatherside
            findAllAncestorsAddToFatherSide(findPerson(person.getmother()));
            findAllAncestorsAddToFatherSide(findPerson(person.getfather()));
        }else if(person.getfather() != null){ //only father isn't null
            personFatherSide.add(findPerson(person.getfather())); //add the mother to fatherside
            findAllAncestorsAddToFatherSide(findPerson(person.getfather()));
        }else if(person.getmother() != null){ //only mother isn't null
            personFatherSide.add(findPerson(person.getmother())); //add the father to fatherside
            findAllAncestorsAddToFatherSide(findPerson(person.getmother()));
        }else{ //both are null
            return;
        }
    }

    //return the person with the corresponding ID
    public ModelPersons findPerson(String ID){
        for(int i = 0; i < people.size(); i++){
            if(people.get(i).getpersonID().equals(ID)){
                return people.get(i);
            }
        } //should never get past this line
        return null;
    }

    public void organizeModel(){
        //add Sheila and spouse to their own array events to events to display
        userAndSpouse.add(people.get(0));
        ModelPersons spouse = findSpouseByID(people.get(0));
        if(spouse != null){
            userAndSpouse.add(spouse);
        }
        //group people by fathers and mothers side
        groupPeopleBySide("mother");
        groupPeopleBySide("father");
        //now group events by the same thing
        groupEvents();
        //at this point the events AND people should be sorted based on their side of the tree
    }

    public void groupEvents(){
        //add user and spouse events to new events array
        for(int i = 0; i < events.size(); i++){
            String compareID = events.get(i).getpersonID();
            for(int j = 0; j < userAndSpouse.size(); j++){
                if(compareID.equals(userAndSpouse.get(j).getpersonID())){ //means its the user or their spouses event
                    userAndSpouseEvent.add(events.get(i));
                }
            }
        }
        for(int i = 0; i < events.size(); i++){
            String compareID = events.get(i).getpersonID();
            for(int j = 0; j < personMotherSide.size(); j++){
                if(compareID.equals(personMotherSide.get(j).getpersonID())){ //means its a mother side event
                    eventsMotherSide.add(events.get(i));
                }
            }
        }
        for(int i = 0; i < events.size(); i++){
            String compareID = events.get(i).getpersonID();
            for(int j = 0; j < personFatherSide.size(); j++){
                if(compareID.equals(personFatherSide.get(j).getpersonID())){ //means its a father side event
                    eventsFatherSide.add(events.get(i));
                }
            }
        }
    }

    public void setPeople(ArrayList<ModelPersons> list){
        people = list;
    }

    public void setEvents(ArrayList<ModelEvents> list){
        events = list;
    }

    public void clear(){
        people.clear();
        events.clear();
        eventTypes.clear();
        userFilter.clear();
        personFatherSide.clear();
        personMotherSide.clear();
        eventsFatherSide.clear();
        eventsMotherSide.clear();
        spouseListLines.clear();
        searchList.clear();
        userFilter.setDefaults();
        userSettings.setDefaults();
        userAndSpouse.clear();
        userAndSpouseEvent.clear();
    }

    public String getFirstName(){
        ModelPersons person = people.get(0);
        String full_name = person.getfirstName() + " " + person.getlastName();
        return full_name;
    }

    //find the child of the person passed in by looking at motherID and fatherID
    public ModelPersons determineChild(ModelPersons toDetermine){
        for(int i = 0; i < people.size(); i++){
            if(people.get(i).getmother() != null){
                if(people.get(i).getmother().equals(toDetermine.getpersonID())){
                    return people.get(i);
                }
            }
            if(people.get(i).getfather() != null){
                if(people.get(i).getfather().equals(toDetermine.getpersonID())){
                    return people.get(i);
                }
            }
        }
        return null;
    }

    //creates a list of all event types in model
    public void determineEventTypes(){
        for(int i = 0 ; i < events.size(); i++){
            if(!doesTypeExist(events.get(i).geteventType())){ //means this is a new event type
               eventTypes.add(events.get(i).geteventType());
            }
        }
        userFilter.copyEventsTypesArray(eventTypes); //sends event types over to filter class
    }

    //passes in event, returns person associated with it
    public ModelPersons getPersonByEvent(ModelEvents event){
        for(int i = 0; i < people.size(); i++){
            if(event.getpersonID().equals(people.get(i).getpersonID())){ //this event matches the person
                return people.get(i);
            }
        }
        return null; //this should never happen
    }

    //compares input event type with already existing event types
    public boolean doesTypeExist(String checkType){
        for(int i = 0; i < eventTypes.size(); i++){
            if(eventTypes.get(i).equalsIgnoreCase(checkType)){ //this eventType already exists in model
                return true;
            }
        }
        return false;
    }

    public Settings getUserSettings(){
        return userSettings;
    }

    //sets up the spouse events
    public void connectSpouseEvents(){
        for(int i = 0; i < people.size(); i++){
            ModelPersons spouse = findSpouse(people.get(i));
            if(spouse != null){ //means this person does indeed have a spouse
                ModelEvents firstEvent = findFirstEvent(people.get(i));
                ModelEvents secondEvent = findFirstEvent(spouse);
                if(firstEvent != null && secondEvent != null){ //both people in the couple have at least one event
                    PairofEvents pair = new PairofEvents(firstEvent, secondEvent);
                    spouseListLines.add(pair); //will have duplicates of everything, but whatever
                }
            }
        }
    }

    public ModelEvents findFirstEvent(ModelPersons person) {
        int earliestYear = Integer.MAX_VALUE;
        ModelEvents earliestEvent = null;
        for(int j = 0; j < eventsToDisplay.size(); j++){
            if(eventsToDisplay.get(j).getpersonID().equals(person.getpersonID())){ //this event belongs to the person in question
                if(earliestEvent == null || eventsToDisplay.get(j).getyear() < earliestYear){ //if its the first event of earlier than a previous event
                    earliestYear = eventsToDisplay.get(j).getyear(); //set it as the earliestYear
                    earliestEvent = eventsToDisplay.get(j);
                }
            }
        }
        return earliestEvent;
    }

    public ModelPersons findSpouse(ModelPersons person){
        ModelPersons child = determineChild(person);
        if(child != null) {
            String personToReturnID = null;
            if (child.getmother().equals(person.getpersonID())) { //we need to get the husband because this is the mother
                personToReturnID = child.getfather();
            } else if (child.getfather().equals(person.getpersonID())) { //we need to return mother because this is the father
                personToReturnID = child.getmother();
            }
            for (int j = 0; j < people.size(); j++) {
                if (people.get(j).getpersonID().equals(personToReturnID)) {
                    return people.get(j);
                }
            }
        }
        return null; //means this person does not have a spouse
    }

    public ModelPersons findSpouseByID(ModelPersons person){
        for(int i = 0; i < people.size(); i++){
            if(person.getspouse().equals(people.get(i).getpersonID())){
                return people.get(i);
            }
        }
        //means this person does not have a spouse
        return null;
    }

    public ArrayList<ModelEvents> getOrderLifeEvents(ModelPersons person){
        ArrayList<ModelEvents> returnEvents = new ArrayList<ModelEvents>();
        for(int i = 0; i < eventsToDisplay.size();  i++){
            if(person.getpersonID().equals(eventsToDisplay.get(i).getpersonID())){
                returnEvents.add(eventsToDisplay.get(i));
            }
        } //all events are added at this point, now they need to be order
        for(int i = 0; i < 60; i++){
            for(int j = 0; j < returnEvents.size() - 1; j++){
                if(returnEvents.get(j).getyear() > returnEvents.get(j + 1).getyear()){
                    Collections.swap(returnEvents, j, j+1);
                }
            }
        }//should be swapped at this point
        return returnEvents;
    }

    public ArrayList<displayObject> getOrderDisplayObjects(ModelPersons person){
        ArrayList<displayObject> returnObjects = new ArrayList<displayObject>();
        displayObject birth = new displayObject();
        displayObject death = new displayObject();
        for(int i = 0; i < eventsToDisplay.size();  i++){ //grabs all events and converts them to displayObjects
            if(person.getpersonID().equals(eventsToDisplay.get(i).getpersonID())){ //belongs to the person
                if(eventsToDisplay.get(i).geteventType().equalsIgnoreCase("birth")){
                    birth.setEvent(eventsToDisplay.get(i));
                }else if(eventsToDisplay.get(i).geteventType().equalsIgnoreCase("death")){
                    death.setEvent(eventsToDisplay.get(i));
                }else{ //adds non-birth or death event to array to sort
                    displayObject obj = new displayObject();
                    obj.setEvent(eventsToDisplay.get(i));
                    returnObjects.add(obj);
                }
            }
        }
        for(int j = 0; j < 60; j++){
            for(int i = 0; i < returnObjects.size() - 1; i++){
                if(returnObjects.get(i).getYear() > returnObjects.get(i + 1).getYear()){
                    Collections.swap(returnObjects, i, i+1);
                } else if(returnObjects.get(i).getYear() == returnObjects.get(i + 1).getYear()){
                    if(returnObjects.get(i).getEventType().compareTo(returnObjects.get(i + 1).getEventType()) > 0){
                        Collections.swap(returnObjects, i, i+1);
                    }
                }
            }
        }


        //add birth to beginning, add death to end
        if(birth.getEvent()!=null){ //they could have no birth event
            returnObjects.add(0, birth);
        }
        if(death.getEvent()!= null){ //they could have no death event
            returnObjects.add(death);
        }

        return returnObjects;
    }

    public ModelPersons findMother(ModelPersons person){
        for(int i = 0; i < people.size(); i++){
            if(people.get(i).getpersonID().equals(person.getmother())){
                return people.get(i);
            }
        }
        return null;
    }

    public ModelPersons findFather(ModelPersons person){
        for(int i = 0; i < people.size(); i++){
            if(people.get(i).getpersonID().equals(person.getfather())){
                return people.get(i);
            }
        }
        return null;
    }

    public void search(String searchString){
        searchList.clear();
        for(int i = 0; i < people.size(); i++){ //searches people first
            //this persons first or last name (or both) contain the search criteria
            if(people.get(i).getlastName().toLowerCase().contains(searchString.toLowerCase())){
                displayObject obj = new displayObject();
                obj.setPerson(people.get(i)); //this will set the relevant people fields from the object
                searchList.add(obj); //adds it to list
            }else if(people.get(i).getfirstName().toLowerCase().contains(searchString.toLowerCase())){
                displayObject obj = new displayObject();
                obj.setPerson(people.get(i)); //this will set the relevant people fields from the object
                searchList.add(obj); //adds it to list
            }
        }
        for(int i = 0; i < eventsToDisplay.size(); i++){ //searches events next
            String compareYear = Integer.toString(eventsToDisplay.get(i).getyear());
            if(compareYear.contains(searchString)){ //compares event to search inquiry
                displayObject obj = new displayObject();
                obj.setEvent(eventsToDisplay.get(i));
                searchList.add(obj);
            } else if(eventsToDisplay.get(i).geteventType().toLowerCase().contains(searchString.toLowerCase())){ //compares event type to inquiry
                displayObject obj = new displayObject();
                obj.setEvent(eventsToDisplay.get(i));
                searchList.add(obj);
            } else if(eventsToDisplay.get(i).getcountry().toLowerCase().contains(searchString.toLowerCase())){ //adds based on country
                displayObject obj = new displayObject();
                obj.setEvent(eventsToDisplay.get(i));
                searchList.add(obj);
            } else if(eventsToDisplay.get(i).getcity().toLowerCase().contains(searchString.toLowerCase())){ //adds based on city
                displayObject obj = new displayObject();
                obj.setEvent(eventsToDisplay.get(i));
                searchList.add(obj);
            }
        }
    }

}
