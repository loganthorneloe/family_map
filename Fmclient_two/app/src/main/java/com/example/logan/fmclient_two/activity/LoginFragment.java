package com.example.logan.fmclient_two.activity;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import facadeClasses.*;

import com.example.logan.fmclient_two.MainActivity;
import com.example.logan.fmclient_two.R;
import com.example.logan.fmclient_two.clientSide.ASyncRegister;
import com.example.logan.fmclient_two.clientSide.ASyncTask;

/**
 * Created by logan on 11/15/2017.
 */

public class LoginFragment extends Fragment {

    private static final String ARG_TITLE = "title";

    //declare variables
    private EditText serverHost;
    private EditText serverPort;
    private EditText userName;
    private EditText password;
    private EditText firstName;
    private EditText lastName;
    private EditText email;

    private String full_name;

    private RadioGroup radioGroup;

    private Button signIn;
    private Button register;

    private String gender = "";

    public LoginFragment(){
        //necessary empty constructor
    }

    public static LoginFragment newInstance(String title) { //Singleton?
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        //wire all widgets
        serverHost = (EditText) v.findViewById(R.id.serverHostFill);
        serverPort = (EditText)v.findViewById(R.id.serverPortFill);
        userName = (EditText)v.findViewById(R.id.userNameFill);
        password = (EditText)v.findViewById(R.id.passwordFill);
        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        firstName = (EditText)v.findViewById(R.id.firstNameFill);
        lastName = (EditText)v.findViewById(R.id.lastNameFill);
        full_name = lastName.getText().toString() + " " + firstName.getText().toString();
        email = (EditText)v.findViewById(R.id.emailFill);
        radioGroup = (RadioGroup)v.findViewById(R.id.radioGroup);

        //creating a responsive display here
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if(checkedId == R.id.male){
                    gender = "m";
                }else{
                    gender = "f";
                }
            }
        });

        //setting up sign in button
        signIn = (Button)v.findViewById(R.id.signInButton);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //sign the person in here
                //check if field contain empty string, toast not all field full (create function below)
                if(checkEmptyLogin()){
                    Toast.makeText(getActivity(),"Missing Fields",Toast.LENGTH_SHORT).show();
                }else{
                    RequestLogin login = new RequestLogin();
                    login.setUserName(userName.getText().toString()); //how do i pass the server info through
                    login.setPassword(password.getText().toString());
                    String name = firstName.getText().toString() + " " + lastName.getText().toString();
                    ASyncTask task = new ASyncTask(serverHost.getText().toString(), serverPort.getText().toString(), (MainActivity)LoginFragment.this.getActivity(), name);
                    task.execute(login);
                }
            }
        });

        //setting up register button
        register = (Button)v.findViewById(R.id.registerButton);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //register the person here
                if(checkEmptyRegister()){
                    Toast.makeText(getActivity(),"Missing Fields",Toast.LENGTH_SHORT).show();
                }else{
                    RequestRegister register = new RequestRegister();
                    register.setfirstName(firstName.getText().toString());
                    register.setuserName(userName.getText().toString());
                    register.setpassword(password.getText().toString());
                    register.setlastName(lastName.getText().toString());
                    register.setemail(email.getText().toString());
                    register.setgender(gender);
                    String name = firstName.getText().toString() + " " + lastName.getText().toString();
                    ASyncRegister task = new ASyncRegister(serverHost.getText().toString(), serverPort.getText().toString(), (MainActivity)LoginFragment.this.getActivity(), name);
                    task.execute(register);
                }
            }
        });

        return v;
    }

    public boolean checkEmptyLogin(){
        if(serverHost.getText().toString().equals("")
                || serverPort.getText().toString().equals("")
                || userName.getText().toString().equals("")
                || password.getText().toString().equals("")){
            return true;
        }
        return false;
    }

    public boolean checkEmptyRegister(){
        if(serverHost.getText().toString().equals("")
                || serverPort.getText().toString().equals("")
                || userName.getText().toString().equals("")
                || password.getText().toString().equals("")
                || firstName.getText().toString().equals("")
                || lastName.getText().toString().equals("")
                || email.getText().toString().equals("")
                || gender.equals("")) {
            return true;
        }
        return false;
    }





}
