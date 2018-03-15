package com.example.logan.fmclient_two.clientSide;

/**
 * Created by logan on 12/6/2017.
 */

public class Settings {

    Settings(){
        setDefaults();
    }

    //**DECLATE VARIABLES DETERMINING LINE COLORS AND WHETHER OR NOT LINES SHOULD BE SET**//
    private boolean isLifeLines;
    private String lifeLinesColor;

    private boolean isTreeLines;
    private String treeLinesColor;

    private boolean isSpouseLines;
    private String spouseLinesColor;

    private String MapType;

    private boolean isLoggedIn;


    public void setDefaults(){

        setLifeLines(true);
        setLifeLinesColor("g");
        setTreeLines(true);
        setTreeLinesColor("b");
        setSpouseLines(true);
        setSpouseLinesColor("r");

        setMapType("n");

        setLoggedIn(false);
    }

    public void setLifeLines(boolean lifeLines) {
        isLifeLines = lifeLines;
    }

    public void setLifeLinesColor(String lifeLinesColor) {
        this.lifeLinesColor = lifeLinesColor;
    }

    public void setTreeLines(boolean treeLines) {
        isTreeLines = treeLines;
    }

    public void setTreeLinesColor(String treeLinesColor) {
        this.treeLinesColor = treeLinesColor;
    }

    public void setSpouseLines(boolean spouseLines) {
        isSpouseLines = spouseLines;
    }

    public void setSpouseLinesColor(String spouseLinesColor) {
        this.spouseLinesColor = spouseLinesColor;
    }

    public void setMapType(String mapType) {
        MapType = mapType;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public boolean isLifeLines() {
        return isLifeLines;
    }

    public String getLifeLinesColor() {
        return lifeLinesColor;
    }

    public boolean isTreeLines() {
        return isTreeLines;
    }

    public String getTreeLinesColor() {
        return treeLinesColor;
    }

    public boolean isSpouseLines() {
        return isSpouseLines;
    }

    public String getSpouseLinesColor() {
        return spouseLinesColor;
    }

    public String getMapType() {
        return MapType;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }
}
