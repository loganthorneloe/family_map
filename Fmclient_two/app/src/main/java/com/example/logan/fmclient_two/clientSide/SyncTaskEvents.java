package com.example.logan.fmclient_two.clientSide;

import android.os.AsyncTask;

import facadeClasses.ResponseEvents;

/**
 * Created by logan on 11/16/2017.
 */

public class SyncTaskEvents extends AsyncTask<String, Void, ResponseEvents> {

    private SyncTaskEvents.myContext c;

    private String full_name;

    ServerProxy proxy;

    public interface myContext {
        void onCallBackEvents(ResponseEvents obj, String name);
    }

    //need to pass a context in here
    public SyncTaskEvents(String host, String port, SyncTaskEvents.myContext con, String name){
        full_name = name;
        proxy = new ServerProxy(host, port);
        c = con;
    }

    public ResponseEvents doInBackground(String... authToken) { //needs to check object type for server proxy
        return proxy.events(authToken[0]);
    }

    protected void onPostExecute(ResponseEvents returnObject) {
        c.onCallBackEvents(returnObject, full_name);
    }

}
