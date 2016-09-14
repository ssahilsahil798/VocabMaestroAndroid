package com.master.vocab.vocabmaster.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.master.vocab.vocabmaster.Api.ApiClient;
import com.master.vocab.vocabmaster.MainActivity;
import com.master.vocab.vocabmaster.R;
import com.master.vocab.vocabmaster.SharedPref.SharedPrefs;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by sahildeswal on 15/09/16.
 */
public class Register extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button registerButton;
    private EditText email;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);


        username = (EditText)findViewById(R.id.username_register);
        password = (EditText)findViewById(R.id.password_register);
        email = (EditText)findViewById(R.id.email_register);

        registerButton = (Button)findViewById(R.id.register);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkData() == true){
                    ApiClient.getFeedApiInterface().manualRegister(username.getText().toString(), password.getText().toString(), email.getText().toString(), new Callback<String>() {
                        @Override
                        public void success(String s, Response response) {

                            try {
                                JSONObject obj = new JSONObject(s);
                                int created = Integer.parseInt(obj.optString("created"));
                                String username_returned = obj.optString("username");
                                String api_key_returned = obj.optString("api_key");
                                if(created == 1){
                                    SharedPrefs.putString(getApplicationContext(), "registered", "reg");
                                    SharedPrefs.putString(getApplicationContext(), "username", username_returned);
                                    SharedPrefs.putString(getApplicationContext(), "api_key", api_key_returned);
                                    Intent intent = new Intent(Register.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void failure(RetrofitError error) {

                        }
                    });
                }
            }
        });



    }

    private boolean validEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    private boolean checkData() {
        String user = username.getText().toString();
        String pass = password.getText().toString();
        String emailid = email.getText().toString();
        if(null == user || user == ""){
            return false;
        }
        else if(null == pass || pass == ""){
            return false;
        }
        else if(null == email || emailid == ""){
            return false;
        }
        else if(validEmail(emailid) == false){
            return false;
        }else{
            return true;
        }

    }
}
