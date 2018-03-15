
package facadeClasses;

/**Class to store information returned from server facade in response to login request*/
public class ResponseLogin{

    /** username to be responded with*/
    private String userName;
    /** authToken to be responded with*/
    private String authToken;
    /** personID to be responded with*/
    private String personID;
    /** message to be responded with*/
    private String message;

    public String getUserName() {
        return userName;
    }

    public String getAuthToken() {
        return authToken;
    }

    public String getPersonID() {
        return personID;
    }

    public String getMessage() {
        return message;
    }

    /** sets username to be responded with
     *@param user username to be responded with
     */
    public void setuserName(String user){
        userName = user;
    }
    /**sets authToken to be responded with
     *@param token auth token to be responded with
     */
    public void setauthToken(String token){
        authToken = token;
    }
    /** sets personID to be responded with
     *@param id personID to be responded with
     */
    public void setpersonID(String id){
        personID = id;
    }
    /** sets message to be responded with
     *@param input message to be responded with
     */
    public void setmessage(String input){
        message = input;
    }


}