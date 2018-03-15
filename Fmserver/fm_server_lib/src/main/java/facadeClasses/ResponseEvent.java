
package facadeClasses;

/**Class to store information returned from server facade in response to an event request*/
public class ResponseEvent{

    /**variable to store descendant sent back to user */
    private String descendant;
    /**variable to store eventID sent back to user */
    private String eventID;
    /**variable to store personID sent back to user */
    private String personID;
    /**variable to store latitude sent back to user */
    private Double latitude;

    public String getDescendant() {
        return descendant;
    }

    public String getEventID() {
        return eventID;
    }

    public String getPersonID() {
        return personID;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getEventType() {
        return eventType;
    }

    public Integer getYear() {
        return year;
    }

    public String getMessage() {
        return message;
    }

    /**variable to store longitude sent back to user */
    private Double longitude;
    /**variable to store country sent back to user */
    private String country;
    /**variable to store city sent back to user */
    private String city;
    /**variable to store eventType sent back to user */
    private String eventType;
    /**variable to store year sent back to user */
    private Integer year;
    /**variable to store message sent back to user */
    private String message;

    /**stores eventID that will be sent back as response
     *@param id eventID that will be returned
     */
    public void seteventID(String id){
        eventID = id;
    }
    /**stores personID that will be sent back as response
     *@param id personID to return
     */
    public void setpersonID(String id){
        personID = id;
    }
    /**stores descendant name that will be sent back as response
     *@param name name to store
     */
    public void setdescendant(String name){
        descendant = name;
    }
    /**stores latitude that will be sent back as response
     *@param lat coordinates to be sent back with response
     */
    public void setlatitude(Double lat){ latitude = lat;
    }
    /**stores longitude that will be sent back as response
     *@param longit coordinates to be sent back with response
     */
    public void setlongitude(Double longit){
        longitude = longit;
    }
    /**stores country that will be sent back as response
     *@param name country name
     */
    public void setcountry(String name){
        country = name;
    }
    /**stores city that will be sent back as response
     *@param name city name
     */
    public void setcity(String name){
        city = name;
    }
    /**stores event type that will be sent back as response
     *@param event event type to store
     */
    public void seteventType(String event){
        eventType = event;
    }
    /**stores year that will be sent back as response
     *@param annual year to store
     */
    public void setyear(int annual){
        year = annual;
    }
    /**stores message that will be sent back as response
     *@param input input message
     */
    public void setmessage(String input){
        message = input;
    }
}