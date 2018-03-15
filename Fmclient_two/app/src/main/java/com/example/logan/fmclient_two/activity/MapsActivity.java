package com.example.logan.fmclient_two.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.logan.fmclient_two.R;

public class MapsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        setTitle("Family Map");

        //grabs Event object to zoom in on from extras in intent
        String eventIDToZoom;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                eventIDToZoom= null;
            } else {
                eventIDToZoom= extras.getString("eventIDToZoom"); //have to put it into event id
            }
        } else {
            eventIDToZoom= (String) savedInstanceState.getSerializable("eventIDToZoom");
        }

        Bundle bundle = new Bundle();
        bundle.putString("eventIDToZoom", eventIDToZoom);

        //LOADS MAP FRAGMENT
        Fragment map_fragment = new MapsFragment();
        //passes Event to fragment
        map_fragment.setArguments(bundle);
        FragmentManager fm2 = getSupportFragmentManager();
        FragmentTransaction transaction = fm2.beginTransaction();
        transaction.replace(R.id.fragment_container, map_fragment);
        transaction.commit();
    }
}
