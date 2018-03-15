
package modelClasses;

/**represents a row of the persons table, variables in class relate to columns in table*/
public class ModelPersons{

    /**represents personID column in table*/
    private String personID;
    /**represents descendant column in table*/
    private String descendant;
    /**represents first name column in table*/
    private String firstName;
    /**represents last name column in table*/
    private String lastName;
    /**represents gender column in table*/
    private String gender;
    /**represents father column in table*/
    private String father;
    /**represents mother column in table*/
    private String mother;
    /**represents spouse column in table*/
    private String spouse;

    /**ModelPersons constructor, takes in all variable and assigns them to the proper columns in table*/
    public ModelPersons(String personIDin,
                 String descendantin,
                 String firstNamein,
                 String lastNamein,
                 String genderin,
                 String fatherin,
                 String motherin,
                 String spousein){

        personID = personIDin;
        descendant = descendantin;
        firstName = firstNamein;
        lastName = lastNamein;
        gender = genderin;
        father = fatherin;
        mother = motherin;
        spouse = spousein;

    }
    /**sets personID variable
     * @param input  value to set personID to
     */
    public void setpersonID(String input){
        personID = input;
    }
    /**sets descendant variable
     * @param input  value to set descendant to
     */
    public void setdescendant(String input){
        descendant = input;
    }
    /**sets firstName variable
     * @param input  value to set firstName to
     */
    public void setfirstName(String input){
        firstName = input;
    }
    /**sets lastName variable
     * @param input  value to set lastName to
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
    /**sets father variable
     * @param input  value to set father to
     */
    public void setfather(String input){
        father = input;
    }
    /**sets mother variable
     * @param input  value to set mother to
     */
    public void setmother(String input){
        mother = input;
    }
    /**sets spouse variable
     * @param input  value to set spouse to
     */
    public void setspouse(String input){
        spouse = input;
    }

    public String getpersonID(){
        return personID;
    }

    public String getdescendant(){
        return descendant;
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

    public String getfather(){
        return father;
    }

    public String getmother(){
        return mother;
    }

    public String getspouse(){
        return spouse;
    }


}