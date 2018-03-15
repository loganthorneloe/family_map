package com.example.logan.fmclient_two.clientSide;

import android.os.AsyncTask;

import facadeClasses.*;

/**
 * Created by logan on 11/15/2017.
 */

public class ASyncTask extends AsyncTask<Object, Void, Object> {

    private myContext c;

    ServerProxy proxy;
    SyncTaskEvents sEvents;
    SyncTaskPeople sPeople;
    String full_name;

    public interface myContext {
        void onCallBack(Object obj, ASyncTask task);
    }

    //need to pass a context in here
    public ASyncTask(String host, String port, myContext con, String name){
        full_name = name;
        proxy = new ServerProxy(host, port);
        sEvents = new SyncTaskEvents(host, port, (SyncTaskEvents.myContext)con, name);
        sPeople = new SyncTaskPeople(host, port, (SyncTaskPeople.myContext)con, name);
        c = con;
    }

    @Override
    public Object doInBackground(Object... object) { //needs to check object type for server proxy
        RequestLogin login = new RequestLogin();
        return proxy.login((facadeClasses.RequestLogin)object[0]);
    }

    //need onPostExecute with if statement for if it worked on didn't work
    //

    protected void onPostExecute(Object returnObject) {
        c.onCallBack(returnObject, this);
    }

    public SyncTaskEvents getEvents(){
        return sEvents;
    }

    public SyncTaskPeople getPeople(){
        return sPeople;
    }
}
