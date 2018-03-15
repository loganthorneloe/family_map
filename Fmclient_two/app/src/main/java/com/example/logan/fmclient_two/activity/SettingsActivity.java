package com.example.logan.fmclient_two.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.logan.fmclient_two.MainActivity;
import com.example.logan.fmclient_two.R;
import com.example.logan.fmclient_two.clientSide.Model;
import com.example.logan.fmclient_two.clientSide.SyncTaskEvents;
import com.example.logan.fmclient_two.clientSide.SyncTaskPeople;

import facadeClasses.ResponseEvents;
import facadeClasses.ResponsePeople;

/**
 * Created by logan on 12/11/2017.
 */

public class SettingsActivity extends AppCompatActivity implements SyncTaskEvents.myContext, SyncTaskPeople.myContext{

    Model model = new Model();

//  **WIRING SWITCHES**
    Switch life_stories_switch;
    Switch family_tree_switch;
    Switch spouse_switch;

//  **WIRING SPINNERS**
    Spinner life_stories_spinner;
    Spinner family_tree_spinner;
    Spinner spouse_spinner;
    Spinner map_type_spinner;

//  **WIRING TEXTVIEWS**
    TextView Resync;
    TextView Resync2;
    TextView Logout;
    TextView Logout2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setTitle("Family Map: Settings");

        //**SWITCHES ARE SET UP HERE**//
        life_stories_switch = (Switch) findViewById(R.id.life_stories_switch);
        family_tree_switch = (Switch) findViewById(R.id.family_tree_switch);
        spouse_switch = (Switch) findViewById(R.id.spouse_switch);

        if(model.getInstance().getUserSettings().isLifeLines()){
            life_stories_switch.setChecked(true);
        }else{
            life_stories_switch.setChecked(false);
        }

        if(model.getInstance().getUserSettings().isTreeLines()){
            family_tree_switch.setChecked(true);
        }else{
            family_tree_switch.setChecked(false);
        }

        if(model.getInstance().getUserSettings().isSpouseLines()){
            spouse_switch.setChecked(true);
        }else{
            spouse_switch.setChecked(false);
        }


        life_stories_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) { //means the switch has been set to on
                    model.getInstance().getUserSettings().setLifeLines(true);
                } else {
                    model.getInstance().getUserSettings().setLifeLines(false);
                }
            }
        });

        family_tree_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) { //means the switch has been set to on
                    model.getInstance().getUserSettings().setTreeLines(true);
                } else {
                    model.getInstance().getUserSettings().setTreeLines(false);
                }
            }
        });

        spouse_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){ //means the switch has been set to on
                    model.getInstance().getUserSettings().setSpouseLines(true);
                }else{ //this doesn't seem to be working
                    model.getInstance().getUserSettings().setSpouseLines(false);
                }
            }
        });
        //**SWITCHES ARE DONE BEING SET**//

        //**SETTING SPINNERS**//
        life_stories_spinner = (Spinner) findViewById(R.id.colors_spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.color_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        life_stories_spinner.setAdapter(adapter);

        if(model.getInstance().getUserSettings().getLifeLinesColor().equals("r")){
            life_stories_spinner.setSelection(0);
        }else if(model.getInstance().getUserSettings().getLifeLinesColor().equals("g")){
            life_stories_spinner.setSelection(1);
        }else if(model.getInstance().getUserSettings().getLifeLinesColor().equals("b")){
            life_stories_spinner.setSelection(2);
        }


        life_stories_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                switch (position){
                    case 0:
                        model.getInstance().getUserSettings().setLifeLinesColor("r");
                        break;
                    case 1:
                        model.getInstance().getUserSettings().setLifeLinesColor("g");
                        break;
                    case 2:
                        model.getInstance().getUserSettings().setLifeLinesColor("b");
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                //mandatory implementation
            }

        });

        family_tree_spinner = (Spinner) findViewById(R.id.colors_spinner2);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.color_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        family_tree_spinner.setAdapter(adapter2);
        if(model.getInstance().getUserSettings().getTreeLinesColor().equals("r")){
            family_tree_spinner.setSelection(0);
        }else if(model.getInstance().getUserSettings().getTreeLinesColor().equals("g")){
            family_tree_spinner.setSelection(1);
        }else if(model.getInstance().getUserSettings().getTreeLinesColor().equals("b")){
            family_tree_spinner.setSelection(2);
        }

        family_tree_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                switch (position){
                    case 0:
                        model.getInstance().getUserSettings().setTreeLinesColor("r");
                        break;
                    case 1:
                        model.getInstance().getUserSettings().setTreeLinesColor("g");
                        break;
                    case 2:
                        model.getInstance().getUserSettings().setTreeLinesColor("b");
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                //mandatory implementation
            }

        });

        spouse_spinner = (Spinner) findViewById(R.id.colors_spinner3);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,
                R.array.color_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spouse_spinner.setAdapter(adapter3);
        if(model.getInstance().getUserSettings().getSpouseLinesColor().equals("r")){
            spouse_spinner.setSelection(0);
        }else if(model.getInstance().getUserSettings().getSpouseLinesColor().equals("g")){
            spouse_spinner.setSelection(1);
        }else if(model.getInstance().getUserSettings().getSpouseLinesColor().equals("b")){
            spouse_spinner.setSelection(2);
        }

        spouse_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                switch (position){
                    case 0:
                        model.getInstance().getUserSettings().setSpouseLinesColor("r");
                        break;
                    case 1:
                        model.getInstance().getUserSettings().setSpouseLinesColor("g");
                        break;
                    case 2:
                        model.getInstance().getUserSettings().setSpouseLinesColor("b");
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                //mandatory implementation
            }

        });

        map_type_spinner = (Spinner) findViewById(R.id.map_type_spinner);
        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(this,
                R.array.mapSpinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        map_type_spinner.setAdapter(adapter4);
        map_type_spinner.setSelection(0);

        if(model.getInstance().getUserSettings().getMapType().equals("n")){
            map_type_spinner.setSelection(0);
        }else if(model.getInstance().getUserSettings().getMapType().equals("s")){
            map_type_spinner.setSelection(1);
        }else if(model.getInstance().getUserSettings().getMapType().equals("t")){
            map_type_spinner.setSelection(2);
        }else if(model.getInstance().getUserSettings().getMapType().equals("h")){
            map_type_spinner.setSelection(3);
        }

        map_type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                switch (position){
                    case 0:
                        model.getInstance().getUserSettings().setMapType("n");
                        break;
                    case 1:
                        model.getInstance().getUserSettings().setMapType("s");
                        break;
                    case 2:
                        model.getInstance().getUserSettings().setMapType("t");
                        break;
                    case 4:
                        model.getInstance().getUserSettings().setMapType("h");
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                //mandatory implementation
            }

        });
        //**DONE SETTING SPINNERS**//

        //**SETTING TEXTVIEWS**//
        Resync = (TextView) findViewById(R.id.resync);
        Resync2 = (TextView) findViewById(R.id.resync2);
        Logout = (TextView) findViewById(R.id.logout);
        Logout2 = (TextView) findViewById(R.id.logout2);

        Resync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.getInstance().clear();
                model.getInstance().getUserSettings().setLoggedIn(true);
                SyncTaskPeople task = new SyncTaskPeople(model.getInstance().getHost(), model.getInstance().getPort(), (SettingsActivity)SettingsActivity.this, "");
                SyncTaskEvents task2 = new SyncTaskEvents(model.getInstance().getHost(), model.getInstance().getPort(), (SettingsActivity)SettingsActivity.this, "");
                task2.execute(model.getInstance().getAuthToken());
                task.execute(model.getInstance().getAuthToken());
            }
        });

        Resync2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.getInstance().clear();
                model.getInstance().getUserSettings().setLoggedIn(true);
                SyncTaskPeople task = new SyncTaskPeople(model.getInstance().getHost(), model.getInstance().getPort(), (SettingsActivity)SettingsActivity.this, "");
                SyncTaskEvents task2 = new SyncTaskEvents(model.getInstance().getHost(), model.getInstance().getPort(), (SettingsActivity)SettingsActivity.this, "");
                task2.execute(model.getInstance().getAuthToken());
                task.execute(model.getInstance().getAuthToken());
            }
        });

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //sign person out here
            logout();
            }
        });

        Logout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //sign person out here
            logout();
            }
        });
    }


    public void logout(){
        model.getInstance().clear();
        model.getInstance().getUserSettings().setLoggedIn(false);
        //change to main activity
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    //**CALLBACKS FOR ASYNCTASKS WHEN RESYNCING**//
    public void onCallBackEvents(ResponseEvents obj, String name){
        //check validity of ResponseEvents
        if(obj.getMessage() != null){ //didn't work
            Toast.makeText(this,"Re-Sync Failed",Toast.LENGTH_SHORT).show();
            return;
        }else{
            model.getInstance().setEvents(obj.getData());
            model.getInstance().determineEventTypes(); //creates list of event types

        }
    }
    public void onCallBackPeople(ResponsePeople obj, String name){
        //check validity of ResponsePeople
        if(obj.getMessage() != null){
            Toast.makeText(this,"Re-Sync Failed",Toast.LENGTH_SHORT).show();
            return;
        }else{
            model.getInstance().setPeople(obj.getData());
            //sorts people and events to organize the model class
            model.getInstance().organizeModel();
            //sorts necessary info for spouse lines
            model.getInstance().connectSpouseEvents();
            NavUtils.navigateUpFromSameTask(this); //should return to the map
        }
    }

}
