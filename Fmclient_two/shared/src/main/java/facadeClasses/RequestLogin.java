
package facadeClasses;

/**Class to store information sent into server facade in order to login*/
public class RequestLogin{

    /** username for login attempt */
    private String userName;
    /** password for login attempt */
    private String password;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**stores the username given to client by user for login attempt
     *@param user username sent in
     */

    public void setuserName(String user){
        userName = user;
    }
    /**stores the password given to client by user for login attempt
     *@param input password sent in
     */

    public void setpassword(String input){
        password = input;
    }

    public String getuserName(){return userName;}

    public String getpassword() {return password;}

}