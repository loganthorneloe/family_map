
package modelClasses;

/**represents a row of the users table, variables in class relate to columns in table*/
public class ModelUsers{

    /**represents userName column in table*/
    private String userName;
    /**represents password column in table*/
    private String password;
    /**represents email column in table*/
    private String email;
    /**represents first name column in table*/
    private String firstName;
    /**represents last name column in table*/
    private String lastName;
    /**represents gender column in table*/
    private String gender;
    /**represents personID column in table*/
    private String personID;

    /**ModelUsers constructor, takes in all variable and assigns them to the proper columns in table*/
    public ModelUsers(String userNamein,
               String passwordin,
               String emailin,
               String firstNamein,
               String lastNamein,
               String genderin,
               String personIDin){

        userName = userNamein;
        password = passwordin;
        email = emailin;
        firstName = firstNamein;
        lastName = lastNamein;
        gender = genderin;
        personID = personIDin;

    }
    /**sets userName variable
     * @param input  value to set userName to
     */
    public void setuserName(String input){
        userName = input;
    }
    /**sets password variable
     * @param input  value to set password to
     */
    public void setpassword(String input){
        password  = input;
    }
    /**sets email variable
     * @param input  value to set email to
     */
    public void setemail(String input){
        email  = input;
    }
    /**sets first name variable
     * @param input  value to set first name to
     */
    public void setfirstName(String input){
        firstName = input;
    }
    /**sets last name variable
     * @param input  value to set last name to
     */
    public void setlastName(String input){
        lastName = input;
    }
    /**sets gender variable
     * @param input  value to set gender to
     */
    public void setgender(String input){
        gender = input;
    }
    /**sets personID variable
     * @param input  value to set personID to
     */
    public void setpersonID(String input){
        personID = input;
    }

    public String getuserName(){
        return userName;
    }

    public String getpassword(){
        return password;
    }

    public String getemail(){
        return email;
    }

    public String getfirstName(){
        return firstName;
    }

    public String getlastName(){
        return lastName;
    }

    public String getgender(){
        return gender;
    }

    public String getpersonID(){
        return personID;
    }

}