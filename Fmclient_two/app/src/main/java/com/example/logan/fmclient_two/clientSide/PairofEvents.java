package com.example.logan.fmclient_two.clientSide;

import modelClasses.ModelEvents;

/**
 * Created by logan on 12/10/2017.
 */

public class PairofEvents {

    private ModelEvents first;
    private ModelEvents second;

    public PairofEvents(ModelEvents f, ModelEvents s){
        first = f;
        second = s;
    }

    public ModelEvents getFirst() {
        return first;
    }

    public void setFirst(ModelEvents first) {
        this.first = first;
    }

    public ModelEvents getSecond() {
        return second;
    }

    public void setSecond(ModelEvents second) {
        this.second = second;
    }
}
