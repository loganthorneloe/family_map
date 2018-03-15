package com.example.logan.fmclient_two.clientSide;

import java.util.ArrayList;

/**
 * Created by logan on 12/9/2017.
 */

public class Filter {

    //this list contains an array telling whether or not display that type of event in order
    ArrayList<Boolean> filterArray = new ArrayList<Boolean>();
    //store a local copy of the event types to use in filter activity
    ArrayList<String> eventFilterTypes = new ArrayList<String>();

    public boolean maleEvents;
    public boolean femaleEvents;
    public boolean motherEvents;
    public boolean fatherEvents;

    public Filter(){
        setDefaults();
    }

    public void setDefaults(){
        setMaleEvents(true);
        setFemaleEvents(true);
        setMotherEvents(true);
        setFatherEvents(true);
        //the filter array for event types will set to default by methods in the model
    }

    public boolean getIndexItemInFilterBoolByEventName(String eventType){
        int index = -1;
        for(int i = 0; i < eventFilterTypes.size(); i++){
            if(eventFilterTypes.get(i).equalsIgnoreCase(eventType)){
                index = i;
            }
        }
        return filterArray.get(index);
    }

    public int getIndexInFilterBoolByEventName(String eventType){
        int index = -1;
        for(int i = 0; i < eventFilterTypes.size(); i++){
            if(eventFilterTypes.get(i).equalsIgnoreCase(eventType)){
                index = i;
            }
        }
        return index;
    }

    //successfully completes the size of the filter array and defaults all of the filter settings to be true
    public void createFilterArray(){
        for(int i = 0; i < eventFilterTypes.size(); i++){
            filterArray.add(true);
        }
    }

    public void copyEventsTypesArray(ArrayList<String> eventTypes){
        eventFilterTypes = eventTypes;
        createFilterArray();
    }

    //necessary to clear the entire model
    public void clear(){
        eventFilterTypes.clear();
        filterArray.clear();
    }

    public ArrayList<Boolean> getFilterArray() {
        return filterArray;
    }

    public ArrayList<String> getEventFilterTypes() {
        return eventFilterTypes;
    }

    public void setFilterTypeFalse(int index){
        filterArray.set(index, false);
    }

    public void setFilterTypeTrue(int index){
        filterArray.set(index, true);
    }

    public boolean isMaleEvents() {
        return maleEvents;
    }

    public boolean isFemaleEvents() {
        return femaleEvents;
    }

    public boolean isMotherEvents() {
        return motherEvents;
    }

    public boolean isFatherEvents() {
        return fatherEvents;
    }

    public void setMaleEvents(boolean maleEvents) {
        this.maleEvents = maleEvents;
    }

    public void setFemaleEvents(boolean femaleEvents) {
        this.femaleEvents = femaleEvents;
    }

    public void setMotherEvents(boolean motherEvents) {
        this.motherEvents = motherEvents;
    }

    public void setFatherEvents(boolean fatherEvents) {
        this.fatherEvents = fatherEvents;
    }
}
