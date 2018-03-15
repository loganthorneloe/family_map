package com.example.logan.fmclient_two;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.logan.fmclient_two.activity.LoginFragment;
import com.example.logan.fmclient_two.activity.MapsFragment;
import com.example.logan.fmclient_two.clientSide.ASyncRegister;
import com.example.logan.fmclient_two.clientSide.ASyncTask;
import com.example.logan.fmclient_two.clientSide.Model;
import com.example.logan.fmclient_two.clientSide.SyncTaskEvents;
import com.example.logan.fmclient_two.clientSide.SyncTaskPeople;

import facadeClasses.ResponseEvents;
import facadeClasses.ResponseLogin;
import facadeClasses.ResponsePeople;
import facadeClasses.ResponseRegister;

public class MainActivity extends AppCompatActivity implements ASyncTask.myContext, ASyncRegister.myContext, SyncTaskEvents.myContext, SyncTaskPeople.myContext{

    Model model = new Model();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTitle("Family Map");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if(model.getInstance().getUserSettings().isLoggedIn()){ //if someone is already logged in, display map
            fragment = new MapsFragment();
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.fragment_container, fragment);
            transaction.commit();
        }else{ //should default to no one being logged in, display log in fragment
            if (fragment == null) {
                fragment = new LoginFragment();
                fm.beginTransaction()
                        .add(R.id.fragment_container, fragment)
                        .commit();
            }
        }
    }

    public void onCallBack(Object obj, ASyncTask task){
        ResponseLogin newObject = (ResponseLogin)obj;
        if(newObject.getAuthToken() ==  null){ //null
            Toast.makeText(this,"Unable to login",Toast.LENGTH_SHORT).show();
            return;
        }
        //passed here means its valid and we need to grab all people and events related to the user using AyncTasks
        SyncTaskEvents taskEvents = task.getEvents();
        SyncTaskPeople taskPeople = task.getPeople();
        taskEvents.execute(newObject.getAuthToken());
        taskPeople.execute(newObject.getAuthToken());
        onLogin();
    }

    public void onCallBackRegister(Object obj, ASyncRegister task){
        ResponseRegister newObject = (ResponseRegister)obj;
        if(newObject.getAuthToken() ==  null){ //null
            Toast.makeText(this,"Unable to register",Toast.LENGTH_SHORT).show();
            return;
        }
        //passed here means its valid and we need to grab all people and events related to the user using AyncTasks
        SyncTaskEvents taskEvents = task.getEvents();
        SyncTaskPeople taskPeople = task.getPeople();
        taskEvents.execute(newObject.getAuthToken());
        taskPeople.execute(newObject.getAuthToken());
        onLogin();
    }

    public void onCallBackEvents(ResponseEvents obj, String name){
        //check validity of ResponseEvents
        if(obj.getMessage() != null){
            Toast.makeText(this,"Invalid events auth_token",Toast.LENGTH_SHORT).show();
            return;
        }else{
            model.getInstance().setEvents(obj.getData());
            model.getInstance().determineEventTypes(); //creates list of event types
        }
    }

    public void onCallBackPeople(ResponsePeople obj, String name){
        //check validity of ResponsePeople
        if(obj.getMessage() != null){
            Toast.makeText(this,"Invalid people auth_token",Toast.LENGTH_SHORT).show();
            return;
        }else{
            //sets up the model for the rest of the app's life
            model.getInstance().setPeople(obj.getData());
            //sorts people and events to organize the model class
            model.getInstance().organizeModel();
            //sorts necessary info for spouse lines
            model.getInstance().connectSpouseEvents();
            String full_name = model.getInstance().getFirstName();
            Toast.makeText(this,full_name,Toast.LENGTH_SHORT).show();
        }
    }

    //transition to map fragment here
    public void onLogin(){
        model.getInstance().getUserSettings().setLoggedIn(true);
        Fragment fragment = new MapsFragment();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();

    }

}
