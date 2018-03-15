
package facadeClasses;

import modelClasses.*;

/**Class to store information sent into server facade in order to Load information into the server*/
public class RequestLoad{

    public RequestLoad() {
    }

    /** contains a list of users to load*/
    public ModelUsers[] users;
    /** contains a list of persons to load*/
    public ModelPersons[] persons;
    /** contains a list of events to load*/
    public ModelEvents[] events;

    public void setUsers(ModelUsers[] users) {
        this.users = users;
    }

    public void setPersons(ModelPersons[] persons) {
        this.persons = persons;
    }

    public void setEvents(ModelEvents[] events) {
        this.events = events;
    }

    /** method to store the users for the load request
     */


    public ModelUsers[] getUsers(){
        return users;
    }

    public ModelPersons[] getPersons(){
        return persons;
    }

    public ModelEvents[] getEvents(){
        return events;
    }

}