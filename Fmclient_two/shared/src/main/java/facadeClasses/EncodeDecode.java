package facadeClasses;

import com.google.gson.Gson;

import modelClasses.*;

import org.json.simple.JSONObject;


//import org.json.simple.JSONObject;


/**
 * Created by logan on 10/27/2017.
 */
//be used to encode and decode json to and from objects
public class EncodeDecode {

    Gson gson = new Gson();

    //moves from JSON to object
    public ModelUsers decodetoModelUser(String jSON){
        return gson.fromJson(jSON, ModelUsers.class);
    }

    public ResponsePerson decodetoResponsePerson(String jSON){
        return gson.fromJson(jSON, ResponsePerson.class);
    }

    public ResponseEvent decodetoResponseEvent(String jSON){
        return gson.fromJson(jSON, ResponseEvent.class);
    }

    public ModelPersons decodetoModelPersons(String jSON){
        return gson.fromJson(jSON, ModelPersons.class);
    }

    public RequestLoad decodetoRequestLoad(String jSON){
        RequestLoad load = gson.fromJson(jSON, RequestLoad.class);
        return load;
    }

    public RequestLogin decodetoRequestLogin(String jSON){
        return gson.fromJson(jSON, RequestLogin.class);
    }

    public RequestRegister decodetoRequestRegister(String jSON){
//        System.out.println(jSON);
        return gson.fromJson(jSON, RequestRegister.class);
    }

    public ResponseRegister decodetoResponseRegister(String jSON){
        return gson.fromJson(jSON, ResponseRegister.class);
    }

    public ResponseLogin decodetoResponseLogin(String jSON){
        return gson.fromJson(jSON, ResponseLogin.class);
    }

    public ResponsePeople decodetoResponsePeople(String jSON){
        return gson.fromJson(jSON, ResponsePeople.class);
    }

    public ResponseEvents decodetoResponseEvents(String jSON){
        return gson.fromJson(jSON, ResponseEvents.class);
    }




    //moves from object to JSON
    public String encode(Object thisObject){
        return gson.toJson(thisObject);
    }

    public String encodeModelUsers(ModelUsers user){
        return gson.toJson(user);
    }


    public String encodeRegister(RequestRegister register){
        return gson.toJson(register);
    }

    public String encodeModelPersons(ModelPersons person){
        return gson.toJson(person);
    }

    public String encodeModelEvents(ModelEvents events){
        return gson.toJson(events);
    }

    public String encodetoResponseClear(ResponseClear clear){ return gson.toJson(clear);}

    public String encodeResponseEvent(ResponseEvent event){ return gson.toJson(event); }

    public String encodeResponseEvents(ResponseEvents events) {return gson.toJson(events); }

    public String encodeResponseFill(ResponseFill fillResponse){ return gson.toJson(fillResponse);}

    public String encodeResponseLoad(ResponseLoad loadResponse){return gson.toJson(loadResponse);}

    public String encodeResponseLogin(ResponseLogin loginResponse){return gson.toJson(loginResponse);}

    public String encodeResponsePerson(ResponsePerson personResponse){return gson.toJson(personResponse);}

    public String encodeResponsePeople(ResponsePeople peopleResponse){return gson.toJson(peopleResponse);}

    public String encodeResponseRegister(ResponseRegister registerResponse){return gson.toJson(registerResponse);}

    public String encodeRequestLogin(RequestLogin loginRequest){return gson.toJson(loginRequest);}

    public ModelEvents changetoModelEvents(JSONObject object){
        return gson.fromJson(gson.toJson(object), ModelEvents.class);
    }

    //have separate decode functions depending on the object to return




}
