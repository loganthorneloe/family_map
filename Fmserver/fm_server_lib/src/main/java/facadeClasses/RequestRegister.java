
package facadeClasses;

/**Class to store information sent into server facade in order to register user*/
public class RequestRegister{

    /** username for registration */
    private String userName;
    /** password for registration */
    private String password;
    /** email for registration */
    private String email;
    /** first name for registration */
    private String firstName;
    /** last name for registration */
    private String lastName;
    /** gender for registration */
    private String gender;

    /**stores the username for registration request
     *@param user username sent in
     */
    public void setuserName(String user){
        userName = user;
    }
    /**stores the password for registration request
     *@param input password sent in
     */
    public void setpassword(String input){
        password = input;
    }
    /**stores the email for registration request
     *@param newemail email sent in
     */
    public void setemail(String newemail){
        email = newemail;
    }
    /**stores the first name for registration request
     *@param name first name sent in
     */
    public void setfirstName(String name){
        firstName = name;
    }
    /**stores the last name for registration request
     *@param name last name sent in
     */
    public void setlastName(String name){
        lastName = name;
    }
    /**stores the gender for registration request
     *@param sex gender sent in
     */
    public void setgender(String sex){
        gender = sex;
    }


}