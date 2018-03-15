
package modelClasses;

/**represents a row of the events table, variables in class relate to columns in table*/
public class ModelEvents{

    /**represents descendant column in table*/
    private String descendant;
    /**represents eventID column in table*/
    private String eventID;
    /**represents personID column in table*/
    private String personID;
    /**represents latitude column in table*/
    private Double latitude;
    /**represents longitude column in table*/
    private Double longitude;
    /**represents country column in table*/
    private String country;
    /**represents city column in table*/
    private String city;
    /**represents eventType column in table*/
    private String eventType;
    /**represents year column in table*/
    private Integer year;

    /**ModelEvents constructor, takes in all variable and assigns them to the proper columns in table*/
    public ModelEvents(String eventIDin,
                String personIDin,
                String descendantin,
                Double latitudein,
                Double longitudein,
                String countryin,
                String cityin,
                String eventTypein,
                Integer yearin){

        eventID = eventIDin;
        personID = personIDin;
        descendant = descendantin;
        latitude = latitudein;
        longitude = longitudein;
        country = countryin;
        city = cityin;
        eventType = eventTypein;
        year = yearin;

    }

    public void setNull(){
        latitude = null;
        longitude = null;
        year = null;
    }
    /**sets eventID variable
     * @param input  value to set eventID to
     */
    public void seteventID(String input){
        eventID = input;
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
    /**sets latitude variable
     * @param input  value to set latitude to
     */
    public void setlatitude(Double input){
        latitude = (Double)input;
    }
    /**sets longitude variable
     * @param input  value to set longitude to
     */
    public void setlongitude(double input){
        longitude = (Double)input;
    }
    /**sets country variable
     * @param input  value to set country to
     */
    public void setcountry(String input){
        country = input;
    }
    /**sets city variable
     * @param input  value to set city to
     */
    public void setcity(String input){
        city = input;
    }
    /**sets eventType variable
     * @param input  value to set eventType to
     */
    public void seteventType(String input){
        eventType = input;
    }
    /**sets year variable
     * @param input  value to set year to
     */
    public void setyear(int input){
        year = input;
    }

    public String geteventID(){
        return eventID;
    }

    public String getpersonID(){
        return personID;
    }

    public String getdescendant(){
        return descendant;
    }

    public double getlatitude(){
        return latitude;
    }

    public double getlongitude(){
        return longitude;
    }

    public String getcountry(){
        return country;
    }

    public String getcity(){
        return city;
    }

    public String geteventType(){
        return eventType;
    }

    public int getyear(){
        return year;
    }


}