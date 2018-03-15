package com.example.logan.fmclient_two.clientSide;

import android.os.AsyncTask;

import facadeClasses.*;

/**
 * Created by logan on 11/15/2017.
 */

public class ASyncRegister extends AsyncTask<Object, Void, Object> {

    private myContext c;

    ServerProxy proxy;
    SyncTaskEvents sEvents;
    SyncTaskPeople sPeople;
    String full_name;

    public interface myContext {
        void onCallBackRegister(Object obj, ASyncRegister task);
    }

    //need to pass a context in here
    public ASyncRegister(String host, String port, myContext con, String name){
        full_name = name;
        proxy = new ServerProxy(host, port);
        sEvents = new SyncTaskEvents(host, port, (SyncTaskEvents.myContext)con, name);
        sPeople = new SyncTaskPeople(host, port, (SyncTaskPeople.myContext)con, name);
        c = con;
    }

    @Override
    public Object doInBackground(Object... object) { //needs to check object type for server proxy
        RequestLogin login = new RequestLogin();
        return proxy.register((facadeClasses.RequestRegister)object[0]);
    }

    protected void onPostExecute(Object returnObject) {
        //call another ASyncTask class to retrieve people
        c.onCallBackRegister(returnObject, this);
    }

    public SyncTaskEvents getEvents(){
        return sEvents;
    }

    public SyncTaskPeople getPeople(){
        return sPeople;
    }
}

