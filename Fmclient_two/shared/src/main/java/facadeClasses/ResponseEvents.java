package facadeClasses;

import modelClasses.*;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by logan on 11/16/2017.
 */

public class ResponseEvents {

    /**arrayList to store to multiple person objects for request*/
    private ModelEvents[] data;
    /**message to return with response*/
    private String message;


    public void setdata(ModelEvents[] eventList){
        data = eventList;
    }
    /** defines message to be sent back with response
     *@param input message to be stored
     */
    public void setmessage(String input){
        message = input;
    }

    public ArrayList<ModelEvents> getData() {
        return new ArrayList<>(Arrays.asList(data));
    }

    public String getMessage() {
        return message;
    }

    public void setnull(){
        data = null;
    }
    //error response body?
}
