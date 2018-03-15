package com.example.logan.fmclient_two.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.logan.fmclient_two.R;
import com.example.logan.fmclient_two.clientSide.Model;
import com.example.logan.fmclient_two.clientSide.displayObject;
import java.util.ArrayList;
import modelClasses.ModelPersons;

public class PersonActivity extends AppCompatActivity {

    Model model = new Model();

    //Creates variables
    Context context;
    TextView firstname;
    TextView lastname;
    TextView gender;
    Button events;
    Button people;

    //creates two recycler views and two adapters, one for events and one for people
    private RecyclerView eventRecyclerView;
    private RecyclerView.Adapter eventAdapter;
    private RecyclerView peopleRecyclerView;
    private RecyclerView.Adapter peopleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        context = this;

        //gets person activity from previous activity
        String personID;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                personID = null;
            } else {
                personID = extras.getString("personID");
            }
        } else {
            personID = (String) savedInstanceState.getSerializable("personID");
        }

        //**GET ALL PEOPLE**// These may be null, check later
        ModelPersons person = model.getInstance().getPersonByID(personID); //may return null
        ArrayList<displayObject> personRelatives = new ArrayList<displayObject>();

        displayObject motherObj = new displayObject();
        if(model.getInstance().findMother(person) != null) {
            motherObj.setPerson(model.getInstance().findMother(person));
            motherObj.setRelation("mother");
            if (motherObj.getPerson() != null) {
                personRelatives.add(motherObj);
            }
        }

        displayObject fatherObj = new displayObject();
        if(model.getInstance().findFather(person) != null) {
            fatherObj.setPerson(model.getInstance().findFather(person));
            fatherObj.setRelation("father");
            if (fatherObj.getPerson() != null) {
                personRelatives.add(fatherObj);
            }
        }

        displayObject childObj = new displayObject();
        if(model.getInstance().determineChild(person) != null){
            childObj.setPerson(model.getInstance().determineChild(person));
            childObj.setRelation("child");
            if(childObj.getPerson()!=null){
                personRelatives.add(childObj);
            }
        }


        displayObject spouseObj = new displayObject();
        if(model.getInstance().findSpouseByID(person) != null) {
            spouseObj.setPerson(model.getInstance().findSpouseByID(person));
            spouseObj.setRelation("spouse");
            if (spouseObj.getPerson() != null) {
                personRelatives.add(spouseObj);
            }
        }

        //**GET ALL EVENTS**//
        ArrayList<displayObject> personEvents = model.getInstance().getOrderDisplayObjects(person);

        //**HOOK UP WIDGETS AND CREATE RESPONSIVE UI
        eventRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        eventRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        peopleRecyclerView = (RecyclerView) findViewById(R.id.recycler_view2);
        peopleRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        firstname = (TextView) findViewById(R.id.person_first);
        firstname.setText(person.getfirstName());
        lastname = (TextView) findViewById(R.id.person_last);
        lastname.setText(person.getlastName());
        gender = (TextView) findViewById(R.id.person_gender);
        if (person.getgender().equals("f")) { //means they are female
            gender.setText("Female");
        } else {
            gender.setText("Male");
        }

        events = (Button) findViewById(R.id.eventsButton);
        people = (Button) findViewById(R.id.peopleButton);

        events.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (eventRecyclerView.getVisibility() == View.VISIBLE) {
                    eventRecyclerView.setVisibility(View.GONE);
                } else {
                    eventRecyclerView.setVisibility(View.VISIBLE);
                }
            }
        });

        people.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (peopleRecyclerView.getVisibility() == View.VISIBLE) {
                    peopleRecyclerView.setVisibility(View.GONE);
                } else {
                    peopleRecyclerView.setVisibility(View.VISIBLE);
                }
            }
        });



        //update recyclerViews
        updateUIEvents(personEvents);
        updateUIPeople(personRelatives);

    }


    void updateUIEvents(ArrayList<displayObject> list) {

        PersonActivity.Adapter adapter = new PersonActivity.Adapter(this, list);
        eventRecyclerView.setAdapter(adapter);

    }

    void updateUIPeople(ArrayList<displayObject> list) {

        PersonActivity.Adapter adapter = new PersonActivity.Adapter(this, list);
        peopleRecyclerView.setAdapter(adapter);

    }

    class Adapter extends RecyclerView.Adapter<PersonActivity.Holder> {

        private ArrayList<displayObject> list = new ArrayList<displayObject>();
        private LayoutInflater inflater;

        public Adapter(Context context, ArrayList<displayObject> list) {
            this.list = list;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public PersonActivity.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.expandable_list_item, parent, false);
            PersonActivity.Holder thisHolder = new PersonActivity.Holder(view);
            return thisHolder;
        }

        @Override
        public void onBindViewHolder(PersonActivity.Holder holder, int position) {
            displayObject item = list.get(position);
            holder.bind(item);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView topView;
        private TextView bottomView;
        private ImageView image;
        private displayObject outer_item;

        public Holder(View view) {
            super(view);
            topView = (TextView) view.findViewById(R.id.textTop);
            bottomView = (TextView) view.findViewById(R.id.textBottom);
            image = (ImageView) view.findViewById(R.id.imageView);
        }

        void bind(displayObject item) {
            outer_item = item;
            if (item.getEvent() == null) { //this means it must be a person display
                if (item.getGender().equals("m")) { //this is a male
                    image.setImageResource(R.drawable.ic_male);
                } else {
                    image.setImageResource(R.drawable.ic_female);
                }
                topView.setText(item.getName());
                bottomView.setText(item.getRelation());

                //set listener

                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) { //sign the person in here
                        Intent intent = new Intent(context, PersonActivity.class);
                        intent.putExtra("personID", outer_item.getPerson().getpersonID());
                        startActivity(intent);
                    }
                });

                topView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) { //sign the person in here
                        Intent intent = new Intent(context, PersonActivity.class);
                        intent.putExtra("personID", outer_item.getPerson().getpersonID());
                        startActivity(intent);
                    }
                });

                bottomView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) { //sign the person in here
                        Intent intent = new Intent(context, PersonActivity.class);
                        intent.putExtra("personID", outer_item.getPerson().getpersonID());
                        startActivity(intent);
                    }
                });


            } else { //this means it must be an event display
                image.setImageResource(R.drawable.ic_event);
                topView.setText(item.getEventdetails());
                bottomView.setText(item.getName());

                //set listener

                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) { //sign the person in here
                        //change to map view here
                        Intent intent = new Intent(context, MapsActivity.class);
                        intent.putExtra("eventIDToZoom", outer_item.getEvent().geteventID());
                        startActivity(intent);
                    }
                });

                topView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) { //sign the person in here
                        //change to map view here
                        Intent intent = new Intent(context, MapsActivity.class);
                        intent.putExtra("eventIDToZoom", outer_item.getEvent().geteventID());
                        startActivity(intent);
                    }
                });

                bottomView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) { //sign the person in here
                        //change to map view here
                        Intent intent = new Intent(context, MapsActivity.class);
                        intent.putExtra("eventIDToZoom", outer_item.getEvent().geteventID());
                        startActivity(intent);
                    }
                });


            }


        }

        //have to implement
        @Override
        public void onClick(View view) {


        }

    }

}
