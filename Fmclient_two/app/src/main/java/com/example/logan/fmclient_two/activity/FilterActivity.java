package com.example.logan.fmclient_two.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.logan.fmclient_two.R;
import com.example.logan.fmclient_two.clientSide.Model;

import java.util.ArrayList;

public class FilterActivity extends AppCompatActivity {

    Model model = new Model();

    //create variables
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    Switch fatherSwitch;
    Switch motherSwitch;
    Switch maleSwitch;
    Switch femaleSwitch;
    ArrayList<String> eventTypes = new ArrayList<String>();
    ArrayList<Boolean> filterList = new ArrayList<Boolean>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        setTitle("Family Map: Filter");

        //wire variables
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fatherSwitch = (Switch)findViewById(R.id.fatherSwitch);
        motherSwitch = (Switch)findViewById(R.id.motherSwitch);
        maleSwitch = (Switch)findViewById(R.id.maleSwitch);
        femaleSwitch = (Switch)findViewById(R.id.femaleSwitch);

        //Two arrays, one lists types of events, one contains boolean at matching index to show if they're true
        eventTypes = model.getInstance().getUserFilter().getEventFilterTypes();
        filterList = model.getInstance().getUserFilter().getFilterArray();

        //**HOOK UP SWITCHES**//
        if(model.getInstance().getUserFilter().isFatherEvents()){
            fatherSwitch.setChecked(true);
        }else{
            fatherSwitch.setChecked(false);
        }

        fatherSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) { //means the switch has been set to on
                    model.getInstance().getUserFilter().setFatherEvents(true);
                } else {
                    model.getInstance().getUserFilter().setFatherEvents(false);
                }
            }
        });

        if(model.getInstance().getUserFilter().isMotherEvents()){
            motherSwitch.setChecked(true);
        }else{
            motherSwitch.setChecked(false);
        }

        motherSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) { //means the switch has been set to on
                    model.getInstance().getUserFilter().setMotherEvents(true);
                } else {
                    model.getInstance().getUserFilter().setMotherEvents(false);
                }
            }
        });

        if(model.getInstance().getUserFilter().isMaleEvents()){
            maleSwitch.setChecked(true);
        }else{
            maleSwitch.setChecked(false);
        }

        maleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) { //means the switch has been set to on
                    model.getInstance().getUserFilter().setMaleEvents(true);
                } else {
                    model.getInstance().getUserFilter().setMaleEvents(false);
                }
            }
        });

        if(model.getInstance().getUserFilter().isFemaleEvents()){
            femaleSwitch.setChecked(true);
        }else{
            femaleSwitch.setChecked(false);
        }

        femaleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) { //means the switch has been set to on
                    model.getInstance().getUserFilter().setFemaleEvents(true);
                } else {
                    model.getInstance().getUserFilter().setFemaleEvents(false);
                }
            }
        });

        //**DONE WIRING SWITCHES**//

        updateUI(eventTypes);

    }

    void updateUI(ArrayList<String> list) {

        adapter = new Adapter(this, list);
        recyclerView.setAdapter(adapter);

    }

    class Adapter extends RecyclerView.Adapter<Holder> {

        private ArrayList<String> list = new ArrayList<String>();
        private LayoutInflater inflater;

        public Adapter(Context context, ArrayList<String> list) {
            this.list = list;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.list_item, parent, false);
            return new Holder(view);
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            String item = list.get(position);
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
        private Switch aSwitch;
        private String item;
        private int index;

        public Holder(View view) {
            super(view);
            topView = (TextView) view.findViewById(R.id.textview);
            bottomView = (TextView) view.findViewById(R.id.textview2);
            aSwitch = (Switch) view.findViewById(R.id.aSwitch);
        }

        void bind(String item) {

            index = model.getInstance().getUserFilter().getIndexInFilterBoolByEventName(item);
            boolean isOn = model.getInstance().getUserFilter().getIndexItemInFilterBoolByEventName(item);

            //wiring each switch individually
            if(isOn){
                aSwitch.setChecked(true);
            }else{
                aSwitch.setChecked(false);
            }

            this.item = item;
            topView.setText(item + " Events");
            bottomView.setText("Filter by " + item + " Events");
            aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) { //means the switch has been set to on
                        model.getInstance().getUserFilter().setFilterTypeTrue(index);
                    } else {
                        model.getInstance().getUserFilter().setFilterTypeFalse(index);
                    }
                }
            });


        }

        //needs to implement
        @Override
        public void onClick(View view) {

        }

    }
}
