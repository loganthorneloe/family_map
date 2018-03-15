
package facadeClasses;

/**Class to store information returned from server facade in response to fill requests*/
public class ResponseFill{

    /** message to be sent back*/
    private String message;

    public String getMessage() {
        return message;
    }

    /** sets message to be sent back
     *@param input message to be sent back
     */
    public void setmessage(String input){
        message = input;
    }
}