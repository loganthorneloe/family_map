package com.example.logan.fmclient_two.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.logan.fmclient_two.R;
import com.example.logan.fmclient_two.clientSide.Model;
import com.example.logan.fmclient_two.clientSide.displayObject;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    Model model = new Model();

    //set variables
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    EditText search_bar;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        context = this;

        setTitle("Family Map: Search");

        //hook up wdigets and create responsive UI
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        search_bar = (EditText)findViewById(R.id.search_bar);

        //everytime user presses enter on textview, it will update the UI and the model
        search_bar.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent keyevent) {
                //If the keyevent is a key-down event on the "enter" button
                if ((keyevent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    //update the model
                    model.getInstance().search(search_bar.getText().toString());
                    //update the UI
                    updateUI(model.getInstance().getSearchList());

                    return true;
                }
                return false;
            }
        });


    }


    void updateUI(ArrayList<displayObject> list) {

        Adapter adapter = new Adapter(this, list);
        recyclerView.setAdapter(adapter);

    }

    class Adapter extends RecyclerView.Adapter<SearchActivity.Holder> {

        private ArrayList<displayObject> list = new ArrayList<displayObject>();
        private LayoutInflater inflater;

        public Adapter(Context context, ArrayList<displayObject> list) {
            this.list = list;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public SearchActivity.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.expandable_list_item, parent, false);
            Holder thisHolder = new Holder(view);
            return thisHolder;
        }

        @Override
        public void onBindViewHolder(SearchActivity.Holder holder, int position) {
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
            if(item.getEvent() == null){ //this means it must be a person display
                if(item.getGender().equals("m")){ //this is a female
                    image.setImageResource(R.drawable.ic_male);
                }else{
                    image.setImageResource(R.drawable.ic_female);
                }
                topView.setText(item.getName());
                bottomView.setText("");

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


            }else{ //this means it must be an event display
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

        //mandatory implementation
        @Override
        public void onClick(View view) {

        }

    }
}
