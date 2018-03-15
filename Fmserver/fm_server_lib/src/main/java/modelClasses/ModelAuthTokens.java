
package modelClasses;

/**represents a row of the auth_authTokens table, variables in class relate to columns in table*/
public class ModelAuthTokens{

    /**represents userName column in table*/
    private String userName;
    /**represents authToken column in table*/
    private String authToken;

    /**ModelAuthTokens constructor, takes in all variable and assigns them to the proper columns in table*/
    public ModelAuthTokens(String userNamein, String authTokenin){

        userName = userNamein;
        authToken = authTokenin;

    }
    /**sets userName variable
     * @param input  value to set userName to
     */
    public void setuserName(String input){
        userName = input;
    }
    /**sets authToken variable
     * @param input  value to set authToken to
     */
    public void setauthToken(String input){
        authToken = input;
    }

    public String getuserName(){
        return userName;
    }

    public String getauthToken(){
        return authToken;
    }
}