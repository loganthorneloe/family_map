package com.example.logan.fmclient_two.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.logan.fmclient_two.R;
import com.example.logan.fmclient_two.clientSide.Model;
import com.example.logan.fmclient_two.clientSide.PairofEvents;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

import modelClasses.ModelEvents;
import modelClasses.ModelPersons;

import static android.graphics.Color.BLUE;
import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.defaultMarker;


public class MapsFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;

    Model model = new Model();

    //set up all variable
    private boolean hasSelectedMarker;
    private Button settingsButton;
    private Button filterButton;
    private Button searchButton;
    private TextView person_name;
    private TextView event_info;
    private ImageView gender_icon;
    private String eventIDToZoom;
    private String currentPersonID;

    public static MapsFragment newInstance(String title) {
        MapsFragment fragment = new MapsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_maps, container, false);
        Bundle bundle = this.getArguments();

        //sets a marker to see if the android logo or a person logo is active
        hasSelectedMarker = false;

        //checks if we are in the map or main activity by checking the bundle sent in
        if(bundle != null){
            eventIDToZoom = bundle.getString("eventIDToZoom");
            hasSelectedMarker = true;
        }

        if(bundle == null){
            setHasOptionsMenu(true);
        }

        SupportMapFragment mapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //wiring widgets and creating a responsive UI
        person_name = (TextView) v.findViewById(R.id.name);
        event_info = (TextView) v.findViewById(R.id.event_name);
        gender_icon = (ImageView) v.findViewById(R.id.gender_icon);

        person_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hasSelectedMarker){
                    Intent intent = new Intent(getActivity(), PersonActivity.class);
                    intent.putExtra("personID", currentPersonID);
                    startActivity(intent);
                }
            }
        });

        event_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hasSelectedMarker) {
                    Intent intent = new Intent(getActivity(), PersonActivity.class);
                    intent.putExtra("personID", currentPersonID);
                    startActivity(intent);
                }
            }
        });

        gender_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hasSelectedMarker) {
                    Intent intent = new Intent(getActivity(), PersonActivity.class);
                    intent.putExtra("personID", currentPersonID);
                    startActivity(intent);
                }
            }
        });



        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if(eventIDToZoom!=null){ //means it only has up button
            inflater.inflate(R.menu.up_menu, menu);
        }else{
            inflater.inflate(R.menu.menu, menu);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings_icon:
                Intent intent = new Intent(this.getActivity(), SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.filter_icon:
                Intent intent2 = new Intent(this.getActivity(), FilterActivity.class);
                startActivity(intent2);
                return true;
            case R.id.search_icon:
                Intent intent3 = new Intent(this.getActivity(), SearchActivity.class);
                startActivity(intent3);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //creates a Listener in all markers
    void setMarkerListener() {
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                hasSelectedMarker = true;
                String eventID = (String)marker.getTag();
                validateMarker(eventID);
                return true; //return false?
            }
        });
    }

    //sets lower part of map fragment for a certain marker
    public void validateMarker(String eventID){
        ModelEvents event = model.getInstance().getEventByID(eventID);
        currentPersonID = (model.getInstance().getPersonByEvent(model.getInstance().getEventByID(eventID))).getpersonID();

        //**CHANGE THE TEXTVIEWS AND ICONS**//

        //event type for the event
        String eventInfo = model.getInstance().getEventInfo(event);
        //persons name for info
        ModelPersons person = model.getInstance().getPersonNameAtEventIndex(event);
        String personName = person.getfirstName() + " " + person.getlastName();
        //persons gender for icon
        String gender = person.getgender();
        person_name.setText(personName);
        event_info.setText(eventInfo);
        if(gender.equals("f")){
            gender_icon.setImageResource(R.drawable.ic_female);
        }else{
            gender_icon.setImageResource(R.drawable.ic_male);
        }

        //**SET UP THE LINES**//

        setLines(event, mMap);

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) { //add markers in here
        mMap = googleMap;
        setMarkerListener(); //sets the click listener on the map
        //sets map type
        initMapType(mMap);
        //set event markers
        setEventMarkers(mMap);
        if(eventIDToZoom!=null){ //will zoom camera
            ModelEvents focusEvent = model.getInstance().getEventByID(eventIDToZoom);
            LatLng zoomOn = new LatLng(focusEvent.getlatitude(), focusEvent.getlongitude());
            mMap.moveCamera(CameraUpdateFactory.newLatLng(zoomOn));
            validateMarker(eventIDToZoom);
        }
    }

    public void setEventMarkers(GoogleMap googleMap){
        model.getInstance().updateEventsToDisplay();
        ArrayList<ModelEvents> lookIn = model.getInstance().getEvents();
        int occurrence = model.getInstance().getEvents().size(); //should be 8
        for(int i = 0; i < occurrence; i++){
            int hue = model.getInstance().getHue(model.getInstance().getEventType(i));
            LatLng marker = new LatLng(model.getInstance().getEventLat(i), model.getInstance().getEventLong(i));
            MarkerOptions options = new MarkerOptions().position(marker).title(model.getInstance().getEventType(i)).icon(defaultMarker(hue));
            Marker setMarker = googleMap.addMarker(options);
            setMarker.setTag(model.getInstance().getEventAt(i).geteventID()); //set tag as eventID
        }
    }

    //sets lines based on settings
    public void setLines(ModelEvents selectedEvent, GoogleMap googleMap){
        //need to reset the markers
        googleMap.clear();
        setEventMarkers(googleMap);
        if(model.getInstance().getUserSettings().isSpouseLines()){ //only if the settings say so, set the spouse lines
            setSpouseLines(selectedEvent, googleMap); //only do this if
        }
        if(model.getInstance().getUserSettings().isLifeLines()){
            setLifeLines(selectedEvent, googleMap);
        }
        if(model.getInstance().getUserSettings().isTreeLines()){
            setTreeLines(selectedEvent, googleMap);
        }
    }

    //**FUNCTIONS: SETS EACH INDIVIDUAL TYPES OF LINES
    public void setTreeLines(ModelEvents selectedEvent, GoogleMap googleMap){
        int generation = 1;
        ModelPersons person = model.getInstance().getPersonByEvent(selectedEvent);
        setTreeLinesPerson(person, selectedEvent, googleMap, generation);
    }

    public void setTreeLinesPerson(ModelPersons person, ModelEvents selectedEvent, GoogleMap googleMap, int generation){
        int line_thickness = (15/generation);
        String mother_id = person.getmother();
        String father_id = person.getfather();
        if(father_id != null){
            ModelEvents fatherFirst = model.getInstance().findFirstEvent(model.getInstance().findPerson(father_id));
            if (fatherFirst != null) {
                PairofEvents pair = new PairofEvents(selectedEvent, fatherFirst);
                setSingleLine(pair, model.getInstance().getUserSettings().getTreeLinesColor(),googleMap, line_thickness);
                setTreeLinesPerson(model.getInstance().findPerson(father_id), fatherFirst, googleMap, generation++);
            }
        }
        if(mother_id != null){
            ModelEvents motherFirst = model.getInstance().findFirstEvent(model.getInstance().findPerson(mother_id));
            if(motherFirst!=null){
                PairofEvents pair = new PairofEvents(selectedEvent, motherFirst);
                setSingleLine(pair, model.getInstance().getUserSettings().getTreeLinesColor(),googleMap, line_thickness);
                setTreeLinesPerson(model.getInstance().findPerson(mother_id), motherFirst, googleMap, generation++);
            }
        }
    }

    public void setLifeLines(ModelEvents selectedEvent, GoogleMap googleMap){
        ModelPersons person = model.getInstance().getPersonByEvent(selectedEvent);
        ArrayList<ModelEvents> eventList = model.getInstance().getOrderLifeEvents(person);
        for(int i = 0; i < eventList.size()-1; i++){
            PairofEvents pair = new PairofEvents(eventList.get(i), eventList.get(i+1));
            setSingleLine(pair, model.getInstance().getUserSettings().getLifeLinesColor(), googleMap, 10);
        }
    }

    public void setSpouseLines(ModelEvents selectedEvent, GoogleMap googleMap){
        ModelPersons person = model.getInstance().getPersonByEvent(selectedEvent);
        ModelPersons spouse = model.getInstance().findSpouseByID(person);
        if(spouse != null){ //this person does have a spouse
            ModelEvents eventSecond = model.getInstance().findFirstEvent(spouse);
            if(eventSecond != null){
                PairofEvents newPair = new PairofEvents(selectedEvent, eventSecond);
                setSingleLine(newPair, model.getInstance().getUserSettings().getSpouseLinesColor(), googleMap, 10);
            }
        }
    }
    //**DONE FUNCTIONS: SETTING INDIVIDUAL TYPES OF LINE**//

    public void setSingleLine(PairofEvents events, String color, GoogleMap googleMap, int width){
        LatLng point1 = new LatLng(events.getFirst().getlatitude(), events.getFirst().getlongitude());
        LatLng point2 = new LatLng(events.getSecond().getlatitude(), events.getSecond().getlongitude());
        if(color.equals("r")){
            PolylineOptions options = new PolylineOptions().add(point1, point2).color(RED).width(width);
            googleMap.addPolyline(options);
        } else if(color.equals("g")){
            PolylineOptions options = new PolylineOptions().add(point1, point2).color(GREEN).width(width);
            googleMap.addPolyline(options);
        } else {
            PolylineOptions options = new PolylineOptions().add(point1, point2).color(BLUE).width(width);
            googleMap.addPolyline(options);
        }
    }

    public void initMapType(GoogleMap googleMap){
        if(model.getInstance().getUserSettings().getMapType().equals("n")){
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }else if(model.getInstance().getUserSettings().getMapType().equals("h")){
            googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        }else if(model.getInstance().getUserSettings().getMapType().equals("s")){
            googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        }else{ //means map style is terrain
            googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        }
    }
}


//imageView.setImageResource(R.drawable.play);