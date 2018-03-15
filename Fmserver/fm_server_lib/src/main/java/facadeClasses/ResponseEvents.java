
package facadeClasses;

import java.util.*;
import modelClasses.ModelEvents;

/**Class to store information returned from server facade in response to multiple event requests*/
public class ResponseEvents{

    /**arrayList to store to multiple event objects for request*/
    private ArrayList<ModelEvents> data = new ArrayList<ModelEvents>();
    /**message to return with response*/
    private String message;

    /** defines events to be sent back with response
     *@param list an arraylist of events to be stored and sent back
     */
    public void setevents(ArrayList<ModelEvents> list){
        data = list;
    }

    public void setnull(){
        data = null;
    }
    /** defines message to be sent back with response
     *@param input message to be stored
     */
    public void setmessage(String input){
        message = input;
    }

    public ArrayList<ModelEvents> getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}