
package facadeClasses;

/**Class to store information returned from server facade in response to people request*/
public class ResponsePerson{


    /**stores descendant variable to respond with*/
    private String descendant;
    /**stores personID variable to respond with*/
    private String personID;
    /**stores firstName variable to respond with*/
    private String firstName;
    /**stores lastName variable to respond with*/
    private String lastName;
    /**stores gender variable to respond with*/
    private String gender;
    /**stores father variable to respond with*/
    private String father;
    /**stores motherID variable to respond with*/
    private String mother;

    public String getDescendant() {
        return descendant;
    }

    public String getPersonID() {
        return personID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public String getFather() {
        return father;
    }

    public String getMother() {
        return mother;
    }

    public String getSpouse() {
        return spouse;
    }

    public String getMessage() {
        return message;
    }

    /**stores spouse variable to respond with*/
    private String spouse;
    /**stores message variable to respond with*/
    private String message;

    /**stores personID that will be sent back as response
     *@param person personID that will be returned
     */
    public void setpersonID(String person){
        personID = person;
    }
    /**stores descendant that will be sent back as response
     *@param name descendant information that will be returned
     */
    public void setdescendant(String name){
        descendant = name;
    }
    /**stores firstName that will be sent back as response
     *@param name key firstName that will be returned
     */
    public void setfirstName(String name){
        firstName = name;
    }
    /**stores lastName that will be sent back as response
     *@param name lastName that will be returned
     */
    public void setlastName(String name){
        lastName = name;
    }
    /**stores gender that will be sent back as response
     *@param input gender that will be returned
     */
    public void setgender(String input){
        gender = input;
    }
    /**stores father that will be sent back as response
     *@param id father that will be returned
     */
    public void setfather(String id){
        father = id;
    }
    /**stores motherID that will be sent back as response
     *@param id motherID that will be returned
     */
    public void setmother(String id){
        mother = id;
    }
    /**stores spouse that will be sent back as response
     *@param id spouse that will be returned
     */
    public void setspouse(String id){
        spouse = id;
    }
    /**stores message that will be sent back as response
     *@param input message that will be returned
     */
    public void setmessage(String input){
        message = input;
    }
}