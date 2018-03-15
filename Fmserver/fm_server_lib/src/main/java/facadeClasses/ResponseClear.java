package facadeClasses;

/**Class to store information returned from server facade in response to a clear request*/
public class ResponseClear{

    /**variable to store message sent back to user */
    private String message;

    /**stores message that will be sent as response
     *@param input input message
     */
    public void setmessage(String input){
        message = input;
    }

}