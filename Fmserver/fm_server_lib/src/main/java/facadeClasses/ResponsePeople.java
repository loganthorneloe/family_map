
package facadeClasses;

import java.util.*;
import modelClasses.ModelPersons;

/**Class to store information returned from server facade in response to people request*/
public class ResponsePeople{

    /**arrayList to store to multiple person objects for request*/
    private ArrayList<ModelPersons> data = new ArrayList<ModelPersons>();
    /**message to return with response*/
    private String message;

    /** defines people to be sent back with response
     *@param personList an arraylist of persons to be stored and sent back
     */
    public void setdata(ArrayList<ModelPersons> personList){
        data = personList;
    }
    /** defines message to be sent back with response
     *@param input message to be stored
     */
    public void setmessage(String input){
        message = input;
    }

    public ArrayList<ModelPersons> getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public void setnull(){
        data = null;
    }
    //error response body?
}