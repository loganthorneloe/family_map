package com.example.logan.fmclient_two.clientSide;

import android.os.AsyncTask;

import facadeClasses.ResponsePeople;

/**
 * Created by logan on 11/16/2017.
 */

public class SyncTaskPeople extends AsyncTask<String, Void, ResponsePeople> {

    private SyncTaskPeople.myContext c;

    Model model = new Model();

    private String full_name;

    ServerProxy proxy;

    public interface myContext {
        void onCallBackPeople(ResponsePeople obj, String name);
    }

    //need to pass a context in here
    public SyncTaskPeople(String host, String port, SyncTaskPeople.myContext con, String name){
        full_name = name;
        proxy = new ServerProxy(host, port);
        c = con;
        model.getInstance().setPort(port);
        model.getInstance().setHost(host);
    }

    public ResponsePeople doInBackground(String... authToken) {
        model.getInstance().storeAuthToken(authToken[0]);

        return proxy.people(authToken[0]);

    }

    protected void onPostExecute(ResponsePeople returnObject) {
        c.onCallBackPeople(returnObject, full_name);
    }

}
