
package facadeClasses;

/**Class to store information returned from server facade in response to registration request*/
public class ResponseRegister{


    /**stores authToken variable to respond with*/
    private String authToken;
    /**stores username variable to respond with*/
    private String userName;
    /**stores personID variable to respond with*/
    private String personID;
    /**stores message variable to respond with*/
    private String message;

    public String getAuthToken() {
        return authToken;
    }

    public String getUserName() {
        return userName;
    }

    public String getPersonID() {
        return personID;
    }

    public String getMessage() {
        return message;
    }

    /**stores username that will be sent back as response
     *@param name username that will be returned
     */
    public void setuserName(String name){
        userName = name;
    }
    /**stores authToken that will be sent back as response
     *@param token authToken that will be returned
     */
    public void setauthToken(String token){
        authToken = token;
    }
    /**stores personID that will be sent back as response
     *@param id personID that will be returned
     */
    public void setpersonID(String id){
        personID = id;
    }
    /**stores message that will be sent back as response
     *@param input message that will be returned
     */
    public void setmessage(String input){
        message = input;
    }

}
