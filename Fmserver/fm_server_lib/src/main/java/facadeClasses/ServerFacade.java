
package facadeClasses;

import dataAccessClasses.*;
import modelClasses.*;
import java.util.*;
import java.sql.*;


/**Takes care of the server requests from the client*/
public class ServerFacade{

    private EncodeDecode coder = new EncodeDecode();
    private Database database = new Database();
    private Creator create = new Creator();
    Connection conn = database.openConnection();


    /**Takes a request Object containing request information and returns an object containing response information
     *@param request a RequestRegister object containing information to complete a registration request
     */
    public ResponseRegister register(RequestRegister request){ //fill 4 here for the user?
        ResponseRegister response = new ResponseRegister();

        System.out.println("IN SERVER FACADE");

        String convert = coder.encodeRegister(request);
        ModelUsers userObject = coder.decodetoModelUser(convert);

        String personID = UUID.randomUUID().toString().replace("-", "");
        String auth_token = UUID.randomUUID().toString().replace("-", "");

        String personID2 = personID.substring(0,8); //personID generated
        String auth_token2 = auth_token.substring(0,8); //auth_token generated

        userObject.setpersonID(personID2);
        ModelAuthTokens authTokenObject = new ModelAuthTokens(userObject.getuserName(), auth_token2);

        AccessUsers userAccess = new AccessUsers();
        AccessAuthTokens tokenAccess = new AccessAuthTokens();
        try{
            userAccess.createRow(userObject, conn); //one of these is throwing an exception
//            System.out.println("after UserAccess");
            tokenAccess.createRow(authTokenObject, conn);
//            System.out.println("after TokenAccess");

        }catch(SQLException ex){
//            System.out.println("In here!");
            String message = "Could not add to database.";
            response.setmessage(message);
            return response;
        }

//        System.out.println("HERE:" + userObject.getuserName());
        fill(userObject.getuserName());

        response.setuserName(userObject.getuserName());
        response.setpersonID(personID2);
        response.setauthToken(auth_token2);
        return response;
        //converts those to a response object and sends it back
    }
    /**Takes a request Object containing request information and returns an object containing response information
     *@param request a RequestLogin object containing information to complete a login request
     */
    public ResponseLogin login(RequestLogin request){
        ResponseLogin response = new ResponseLogin();
        ModelUsers userObject;
//        Connection conn = database.openConnection();
        try{
            AccessUsers userAccess = new AccessUsers();
            userObject = userAccess.getRow(request.getuserName(), conn); //may throw exception
            if(!(userObject.getpassword().equals(request.getpassword()))){ //checks to make sure password matches
                throw new Exception();
            }
        }catch(SQLException ex){
            String message = "User is not in database";
            response.setmessage(message);
//            database.closeConnection();
            return response;
        }catch(Exception ex){
            String message = "Credentials not applicable";
            response.setmessage(message);
//            database.closeConnection();
            return response;
        }

        AccessAuthTokens accessTokens = new AccessAuthTokens();
        String returnusername = userObject.getuserName();
        String returnpersonID = userObject.getpersonID();
        ModelAuthTokens tokensObject = accessTokens.getRowByUsername(returnusername, conn);

//        database.closeConnection();
        //creates new auth_token
        String auth = UUID.randomUUID().toString().replace("-", "");
        String auth2 = auth.substring(0,8); //personID generated

        //needs to store in database here
        try {
            accessTokens.createRow(new ModelAuthTokens(returnusername, auth2), conn);
        }catch(Exception ex){
            ex.printStackTrace();
        }

        response.setpersonID(returnpersonID);
        response.setuserName(returnusername);
        response.setauthToken(auth2);

        return response;
        //grabs information from table to send back to user if successful

    }

    /**Completes a clear on the server and returns an object containing response information
     */
    public ResponseClear clear(){ //how can this have an errors?
        ResponseClear response = new ResponseClear();
//        Connection conn = database.openConnection();

        AccessAuthTokens tokenAccess = new AccessAuthTokens();
        AccessUsers userAccess = new AccessUsers();
        AccessEvents eventAccess = new AccessEvents();
        AccessPersons personAccess = new AccessPersons();

        tokenAccess.dropTable(conn);
        userAccess.dropTable(conn);
        eventAccess.dropTable(conn);
        personAccess.dropTable(conn);

        tokenAccess.createTable(conn);
        userAccess.createTable(conn);
        eventAccess.createTable(conn);
        personAccess.createTable(conn);

//        database.closeConnection();

        String message = "Clear succeeded.";
        response.setmessage(message);

        return response;
    }

    public void deleteAllPertainingtoUser(String username){

        //deletes all events and people pertaining to the user
        AccessEvents eventsAccess = new AccessEvents();
        AccessPersons personAccess = new AccessPersons();
//        ModelPersons person = null;
//        try {
//            person = personAccess.getRowByUsername(username, conn);
//        }catch(SQLException ex){
//            ex.printStackTrace();
//        }
        //search for people by person ID or by descendant
//        String personID = person.getpersonID();
//        String descendant = person.getdescendant();

        eventsAccess.removeRowDescendant(username, conn);
//        eventsAccess.removeRowPersonID(personID, conn);
        personAccess.removeRowByDescendant(username, conn); //person is own descendant

        //all rows relating to the person should be removed from database

    }

    /**Completes a fill on the server and returns an object containing response information
     */
    public ResponseFill fill(String username, int generations){

//        System.out.println("In Fill non-generic");
        ResponseFill response = new ResponseFill();
        //check if user is registered in the database
        AccessUsers userAccess = new AccessUsers();
        ModelUsers user;
        try {
            user = userAccess.getRow(username, conn);
        }catch(SQLException ex){
//            System.out.println("SQLException in Fill Generic");
            response.setmessage("Unable to locate user in database.");
            return response;
        }

        //delete all things pertaining to the user here
        deleteAllPertainingtoUser(username);

        create.seteventstozero(); //reset events added to zero

        //creates a modelperson for the modeluser
        String convert = coder.encodeModelUsers(user);
        ModelPersons userPerson = coder.decodetoModelPersons(convert);
        userPerson.setdescendant(username);
        create.createBirth(userPerson, username, conn); //adds own users birth

        ArrayList<ModelPersons> useList = new ArrayList<ModelPersons>();
        ArrayList<ModelPersons> nextList = new ArrayList<ModelPersons>();
        AccessPersons personAccess = new AccessPersons();
        nextList.add(userPerson);

        int num_people = 0;
        for(int i = 1; i <= generations; i++){
//            System.out.println(nextList.size());
            //copy from nextList to useList
            useList.clear();
            for(ModelPersons p: nextList){
                useList.add(p);
            }
            nextList.clear();
//            System.out.println(useList.size());
            for(ModelPersons obj: useList){
//                System.out.println("HERE:" + username);
                ArrayList<ModelPersons> parents = create.createCouple(username, conn, i, obj);
//                System.out.println("Past creating a couple");
                obj.setfather(parents.get(0).getpersonID()); //assign IDs to the proper child
                obj.setmother(parents.get(1).getpersonID());
//                System.out.println("Has set mother and father as one another's spouse");
                try {
//                    System.out.println("Adding person");
                    personAccess.createRow(obj, conn); //add the person to the database
                    num_people++;
                }catch(SQLException ex){
                    String message = "Could not add person in fill to database.";
                    response.setmessage(message);
                    return response;
                }

                for(ModelPersons obj2: parents){
                    nextList.add(obj2);
                }

            }

        }
        for(ModelPersons obj: nextList){
            try{
                personAccess.createRow(obj, conn); //add the last generation
            }catch(SQLException ex) {
                String message = "Could not add person in fill to database.";
                response.setmessage(message);
                return response;
            }
            num_people++;
        }

        response.setmessage("Successfully added " + num_people + " persons and " + create.getevents()+  " events to the database.");
        return response;
    }

    //without generations
    public ResponseFill fill(String username){

//        System.out.println("In Fill generic");
        final int default_generations = 4;
        ResponseFill response = new ResponseFill();
        //check if user is registered in the database
        AccessUsers userAccess = new AccessUsers();
        ModelUsers user;
        try {
            user = userAccess.getRow(username, conn);
        }catch(SQLException ex){
//            System.out.println("SQLException in Fill Generic");
            response.setmessage("Unable to locate user in database.");
            return response;
        }

        //delete all things pertaining to the user here
        deleteAllPertainingtoUser(username);

        create.seteventstozero(); //reset events added to zero

        //creates a modelperson for the modeluser
        String convert = coder.encodeModelUsers(user);
        ModelPersons userPerson = coder.decodetoModelPersons(convert);
        userPerson.setdescendant(username);
        create.createBirth(userPerson, username, conn); //adds own users birth

        ArrayList<ModelPersons> useList = new ArrayList<ModelPersons>();
        ArrayList<ModelPersons> nextList = new ArrayList<ModelPersons>();
        AccessPersons personAccess = new AccessPersons();
        nextList.add(userPerson);


        //need to create a birthdate event for the current user
//        create.createBirth(userPerson, userPerson.getdescendant(), conn);

        int num_people = 0;
        for(int i = 1; i <= default_generations; i++){
//            System.out.println(nextList.size());
            //copy from nextList to useList
            useList.clear();
            for(ModelPersons p: nextList){
                useList.add(p);
            }
            nextList.clear();
//            System.out.println(useList.size());
            for(ModelPersons obj: useList){
//                System.out.println("HERE:" + username);
                ArrayList<ModelPersons> parents = create.createCouple(username, conn, i, obj);
//                System.out.println("Past creating a couple");
                obj.setfather(parents.get(0).getpersonID()); //assign IDs to the proper child
                obj.setmother(parents.get(1).getpersonID());
//                System.out.println("Has set mother and father as one another's spouse");
                try {
//                    System.out.println("Adding person");
                    personAccess.createRow(obj, conn); //add the person to the database
                    num_people++;
                }catch(SQLException ex){
                    String message = "Could not add person in fill to database.";
                    response.setmessage(message);
                    return response;
                }

                for(ModelPersons obj2: parents){
                    nextList.add(obj2);
                }

            }

        }
        for(ModelPersons obj: nextList){
            try{
                personAccess.createRow(obj, conn); //add the last generation
            }catch(SQLException ex) {
                String message = "Could not add person in fill to database.";
                response.setmessage(message);
                return response;
            }
            num_people++;
        }

        response.setmessage("Successfully added " + num_people + " persons and " + create.getevents()+  " events to the database.");
        return response;
    }

    /**Takes a request Object containing request information and returns an object containing response information
     *@param request a RequestLoad object containing information to complete a load request
     */
    public ResponseLoad load(RequestLoad request){
        ResponseLoad response = new ResponseLoad();

        //clear all information here
        clear();

        AccessEvents eventAccess = new AccessEvents();
        AccessUsers userAccess = new AccessUsers();
        AccessPersons personAccess = new AccessPersons();

        int num_persons = 0;
        int num_users = 0;
        int num_events = 0;

        for(ModelPersons person: request.getPersons()){
            try {
                personAccess.createRow(person, conn);
                num_persons++;
            }catch(SQLException ex){
                String message = "In Load: could not add person to database.";
                response.setmessage(message);
                return response;
            }
        }
        for(ModelUsers user: request.getUsers()){ //should we create an auth token for these people?
            try{
                userAccess.createRow(user, conn);
                num_users++;
            }catch(SQLException ex){
                String message = "In Load: could not add person to database.";
                response.setmessage(message);
                return response;
            }
        }
        for(ModelEvents event: request.getEvents()){
            try{
                eventAccess.createRow(event, conn);
                num_events++;
             }catch(SQLException ex){
                String message = "In Load: could not add person to database.";
                response.setmessage(message);
                return response;
            }
        }

        String message = "Successfully added " + num_users + " users, " + num_persons + " persons, and " + num_events + " events to the database.";
        response.setmessage(message);
        return response;
    }

    /**Retrieves a person from the server and returns an object containing response information
     *@param personID passes in the eventID for the event information to return
     */
    public ResponsePerson person(String auth_token, String personID){

        ResponsePerson response = new ResponsePerson();
//        Connection conn = database.openConnection();
        AccessPersons personAccess = new AccessPersons();
        AccessAuthTokens tokenAccess = new AccessAuthTokens();
//        Connection conn = database.openConnection();
        String username;
        try {
            ModelAuthTokens tokenObject = tokenAccess.getRow(auth_token, conn);
            String descendant = tokenObject.getuserName();
            ModelPersons person = personAccess.getRow(personID, conn);
            if(!(person.getdescendant().equals(descendant))){
                String message = "You are not authorized to access this.";
                response.setmessage(message);
                return response;
            }
//            username = tokenObject.getuserName()()();
        }catch(SQLException ex){
            String message = "Invalid auth_token.";
            response.setmessage(message);
//            database.closeConnection();
            return response;
        }

        try {
            ModelPersons personObject = personAccess.getRow(personID, conn);
            response = coder.decodetoResponsePerson(coder.encodeModelPersons(personObject));
//            System.out.println(coder.encodeModelPersons(personObject));
        }catch(SQLException ex){
            String message = "Person is not in database.";
            response.setmessage(message);
//            database.closeConnection();
            return response;
        }

        return response;
    }

    /**Retrieves multiple persons from the server and returns an object containing response information
     */
    public ResponsePeople people(String auth_token){
        ResponsePeople response = new ResponsePeople();
        AccessAuthTokens tokenAccess = new AccessAuthTokens();
//        Connection conn = database.openConnection();
        String username;
        try {
            ModelAuthTokens tokenObject = tokenAccess.getRow(auth_token, conn);
            username = tokenObject.getuserName();
        }catch(SQLException ex){
            String message = "Invalid auth_token.";
            response.setmessage(message);
//            database.closeConnection();
            return response;
        }

        AccessPersons personAccess = new AccessPersons();
        ArrayList<ModelPersons> data;
        try {
            data = personAccess.getRows(username, conn);
        }catch(SQLException ex){
            String message = "No persons related to this user.";
            response.setmessage(message);
            return response;
        }

        if(data.size() == 0) {
            String message = "There are no persons related to this user in the database";
            response.setmessage(message);
            response.setnull();
            return response;
        }

        response.setdata(data);

        return response;
    }

    /**Retrieves an event from the server and returns an object containing response information
     *@param eventID passes in the eventID for the event information to return
     */
    public ResponseEvent event(String auth_token, String eventID){
        ResponseEvent response = new ResponseEvent();
//        Connection conn = database.openConnection();
        AccessAuthTokens tokenAccess = new AccessAuthTokens();
        AccessEvents eventAccess = new AccessEvents();
//        Connection conn = database.openConnection();
        ModelAuthTokens tokenObject;
        try {
            tokenObject = tokenAccess.getRow(auth_token, conn);
            String descendant = tokenObject.getuserName();
            ModelEvents event = eventAccess.getRow(eventID, conn); //gets the event to compare descendants
            if(!(event.getdescendant().equals(descendant))){
                String message = "You are not authorized to access this.";
                response.setmessage(message);
                return response;
            }
        }catch(SQLException ex){
            String message = "Invalid auth_token.";
            response.setmessage(message);
//            database.closeConnection();
            return response;
        }
        ModelEvents eventObject = new ModelEvents(null, null, null, null, null, null, null, null, null);
        try {
            eventObject = eventAccess.getRow(eventID, conn);
            response = coder.decodetoResponseEvent(coder.encodeModelEvents(eventObject));
        }catch(SQLException ex){
            //need to set the evenObject to null;
            eventObject.setNull();
            String message = "Event is not in database.";
            response.setmessage(message);
//            database.closeConnection();
            return response;
        }
//        database.closeConnection();
        return response;
    }

    /**Retrieves multiple events from the server and returns an object containing response information
     */
    public ResponseEvents events(String auth_token){
        ResponseEvents response = new ResponseEvents();
        AccessAuthTokens tokenAccess = new AccessAuthTokens();
//        Connection conn = database.openConnection();
        String username;
        try {
            ModelAuthTokens tokenObject = tokenAccess.getRow(auth_token, conn);
            username = tokenObject.getuserName();
        }catch(SQLException ex){
            String message = "Invalid auth_token.";
            response.setmessage(message);
            return response;
        }

        System.out.println("USERNAME: " + username);
//
        AccessEvents eventAccess = new AccessEvents();
        ArrayList<ModelEvents> data;
        try {
            data = eventAccess.getRows(username, conn);
        }catch(SQLException ex){
            String message = "No persons related to this user.";
            response.setmessage(message);
            return response;
        } //has a list of all people related to user at this point

        if(data.size() == 0) {
            String message = "There are no events related to this user in the database";
            response.setmessage(message);
            response.setnull();
            return response;
        }
        response.setevents(data);

        return response;

    }

}
