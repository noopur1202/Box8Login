package com.workstation.box8login;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class ActivitySignup extends AppCompatActivity {

    Button goToLogin,signup;
    EditText name,phone,email,password;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        goToLogin=(Button)findViewById(R.id.go_to_login);
        signup=(Button)findViewById(R.id.signup);
        name=(EditText) findViewById(R.id.name);
        phone=(EditText) findViewById(R.id.mobile);
        email=(EditText) findViewById(R.id.email);
        password=(EditText) findViewById(R.id.password);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                if (validation()){
                    doSignup(name.getText().toString(),phone.getText().toString(),email.getText().toString(),
                             password.getText().toString());
                }
            }
        });

        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivitySignup.this,MainActivity.class));
            }
        });
    }

    private void doSignup(String name,String phone, String email, String password) {

        JSONObject signupObj = new JSONObject();
        try {
            signupObj.put("name","Noopur");
            signupObj.put("phone","8888888888");
            signupObj.put("email","email@gmail.com");
            signupObj.put("password","tanwar");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONArray usersArray = new JSONArray();
        usersArray.put(signupObj);

        writeToFile(usersArray.toString(),getApplicationContext());
    }

    private boolean validation() {
        if(name!=null&&phone!=null&&email!=null&&password!=null){
            return true;
        }else return false;
    }

    private void writeToFile(String data,Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("login.txt", Context.MODE_PRIVATE));
            outputStreamWriter.append(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    private String readFromFile(Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("login.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }
}
