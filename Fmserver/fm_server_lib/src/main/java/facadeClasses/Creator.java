package facadeClasses;


import java.util.*;
import java.sql.*;
import java.lang.*;
import java.io.*;
import java.util.concurrent.ThreadLocalRandom;
import com.google.gson.Gson;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

//import jSonInput.*;

import dataAccessClasses.AccessEvents;
import modelClasses.ModelEvents;
import modelClasses.ModelPersons;


/**
 * Created by logan on 10/30/2017.
 */

//takes care of everything creation in the fill function
public class Creator{

    EncodeDecode coder = new EncodeDecode();

    int events_added;

    final String FEMALE_NAME_FILE_PATH = "C:/Users/logan/Documents/Android/Fmserver/fm_server_lib/src/json/fnames.json";
    final String MALE_NAME_FILE_PATH = "C:/Users/logan/Documents/Android/Fmserver/fm_server_lib/src/json/mnames.json";
    final String LAST_NAME_FILE_PATH = "C:/Users/logan/Documents/Android/Fmserver/fm_server_lib/src/json/snames.json";
    final String LOCATIONS = "C:/Users/logan/Documents/Android/Fmserver/fm_server_lib/src/json/locations.json";


    int base_year = 2017;
    int gen = 0;
    int birth_year_of_one_individual = 0;

    public void seteventstozero(){
        events_added = 0;
    }

    public int getevents(){
        return events_added;
    }

    public ArrayList<ModelPersons> createCouple(String descendant, Connection conn, int generation, ModelPersons child){ //passes in the name of the user
        gen = generation;
        ArrayList<ModelPersons> parents = new ArrayList<ModelPersons>(); //father first, mother second
        ModelPersons father = createPerson("m", descendant, conn, child);
        ModelPersons mother = createPerson("f", descendant, conn);
        father.setspouse(mother.getpersonID());
        mother.setspouse(father.getpersonID());
        createMarriage(father, mother, conn);

        parents.add(father);
        parents.add(mother);

        return parents;
        //add to parents back in other function
    }

    public ModelPersons createPerson(String gender, String descendant, Connection conn, ModelPersons child){ //passes in the gender of the person to create

        String personID = UUID.randomUUID().toString().replace("-", "");
        String personID2 = personID.substring(0,8); //personID generated
        String first_name;
        first_name = returnNameFromFile(MALE_NAME_FILE_PATH);
        ModelPersons response = new ModelPersons(personID2,
                descendant,
                first_name,
                child.getlastName(),
                gender,
                null, //add these in other class
                null,
                null); //add in other method
        //create birth event
        createBirth(response, descendant, conn);
        return response;
    }

    public ModelPersons createPerson(String gender, String descendant, Connection conn){ //passes in the gender of the person to create

        String personID = UUID.randomUUID().toString().replace("-", "");
        String personID2 = personID.substring(0,8); //personID generated
        String first_name = returnNameFromFile(FEMALE_NAME_FILE_PATH);
        String last_name = returnNameFromFile(LAST_NAME_FILE_PATH);
        ModelPersons response = new ModelPersons(personID2,
                descendant,
                first_name,
                last_name,
                gender,
                null, //add these in other class
                null,
                null); //add in other method
        //create birth event
        createBirth(response, descendant, conn);
        return response;
    }

    public void createBirth(ModelPersons person, String descendant, Connection conn){
        //pull in random city, country, long, and latitude
        String eventID = UUID.randomUUID().toString().replace("-", "");
        String eventID2 = eventID.substring(0,8);
        String personID = person.getpersonID();
        String eventType = "birth";
        int around_year_of_birth = base_year - 25 - (gen*20);
        int rand_year_of_birth = (around_year_of_birth - 5) + (int)(Math.random() * (((around_year_of_birth + 5) - (around_year_of_birth - 5)) + 1));
        birth_year_of_one_individual = rand_year_of_birth;
        ModelEvents birth = coder.changetoModelEvents(returnObjectFromFile(LOCATIONS));
        birth.seteventID(eventID2);
        birth.setpersonID(personID);
        birth.setdescendant(descendant);
        birth.seteventType(eventType);
        birth.setyear(rand_year_of_birth);
        AccessEvents eventAccess = new AccessEvents();
        try {
//            System.out.println("adding event");
            eventAccess.createRow(birth, conn);
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        events_added++;
        if(rand_year_of_birth < base_year - 92){
            createDeath(person, descendant, conn, rand_year_of_birth);
        }
    }

    public void createDeath(ModelPersons person, String descendant, Connection conn, int birthyear){ //if they are dead
        String eventID = UUID.randomUUID().toString().replace("-", "");
        String eventID2 = eventID.substring(0,8);
        String personID = person.getpersonID();
        int around_year_of_death = birthyear + 75;
        int rand_year_of_death = (around_year_of_death - 10) + (int)(Math.random() * (((around_year_of_death + 10) - (around_year_of_death - 10)) + 1));
        ModelEvents death = coder.changetoModelEvents(returnObjectFromFile(LOCATIONS));
        death.seteventID(eventID2);
        death.setpersonID(personID);
        death.setdescendant(descendant);
        death.seteventType("death");
        death.setyear(rand_year_of_death);
        AccessEvents eventAccess = new AccessEvents();
        try {
//            System.out.println("adding event");
            eventAccess.createRow(death, conn);
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        events_added++;
    }

    public void createMarriage(ModelPersons father, ModelPersons mother, Connection conn){
        int year_of_marriage = birth_year_of_one_individual + 18;
        String eventID = UUID.randomUUID().toString().replace("-", "");
        String eventID2 = eventID.substring(0,8);
        String eventID3 = UUID.randomUUID().toString().replace("-", "");
        String eventID4 = eventID3.substring(0,8);

        JSONObject obj = returnObjectFromFile(LOCATIONS);
        ModelEvents marriage1 = coder.changetoModelEvents(obj);
        marriage1.seteventID(eventID2);
        marriage1.setpersonID(father.getpersonID());
        marriage1.setdescendant(father.getdescendant());
        marriage1.seteventType("marriage");
        marriage1.setyear(year_of_marriage);

        ModelEvents marriage2 = coder.changetoModelEvents(obj);
        marriage2.seteventID(eventID4);
        marriage2.setpersonID(mother.getpersonID());
        marriage2.setdescendant(mother.getdescendant());
        marriage2.seteventType("marriage");
        marriage2.setyear(year_of_marriage);

        AccessEvents eventAccess = new AccessEvents();
        try {
//            System.out.println("adding event");
            eventAccess.createRow(marriage1, conn);
            eventAccess.createRow(marriage2, conn);
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        events_added++;
        events_added++; //two for marriage
    }


    public JSONObject returnObjectFromFile(String filename){
        JSONObject object = new JSONObject();
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(filename));
            object = (JSONObject) obj;
            JSONArray data = (JSONArray) object.get("data");
            int index = ThreadLocalRandom.current().nextInt(0, data.size());
            return (JSONObject) data.get(index);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return object;
    }

    public String returnNameFromFile(String filename){
        try {
            Gson gson = new Gson();
            Names data = gson.fromJson(new FileReader(filename), Names.class);
            int index = ThreadLocalRandom.current().nextInt(0, data.data.length);
            return data.data[index];
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return("");
    }

    private class Names{
        public String[] data;
    }
}
