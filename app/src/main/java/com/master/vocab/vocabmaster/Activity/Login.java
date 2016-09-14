package com.master.vocab.vocabmaster.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.master.vocab.vocabmaster.Api.ApiClient;
import com.master.vocab.vocabmaster.MainActivity;
import com.master.vocab.vocabmaster.R;
import com.master.vocab.vocabmaster.SharedPref.SharedPrefs;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by sahildeswal on 15/09/16.
 */
public class Login extends AppCompatActivity {
    private EditText password;
    private EditText username;
    private Button loginButton;
    private Button registerButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        username = (EditText)findViewById(R.id.username_login);
        password = (EditText)findViewById(R.id.password_login);
        loginButton = (Button)findViewById(R.id.login_button);
        registerButton = (Button)findViewById(R.id.register_button);


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);

            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkData() == true){
                    ApiClient.getFeedApiInterface().manualLogin(username.getText().toString(), password.getText().toString(), new Callback<String>() {
                        @Override
                        public void success(String s, Response response) {

                            try {
                                JSONObject obj = new JSONObject(s);
                                boolean error = Boolean.parseBoolean(obj.optString("error"));
                                String username_returned = obj.optString("username");
                                String api_key_returned = obj.optString("api_key");
                                if(error == false){
                                    SharedPrefs.putString(getApplicationContext(), "registered", "reg");
                                    SharedPrefs.putString(getApplicationContext(), "username", username_returned);
                                    SharedPrefs.putString(getApplicationContext(), "api_key", api_key_returned);
                                    Intent intent = new Intent(Login.this, MainActivity.class);
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

    private boolean checkData() {

        if(username.getText().toString().contentEquals("")){
            username.setError("Username cannot be blank");
            return false;
        }
        else if(password.getText().toString().contentEquals("")){
            password.setError("Password cannot be blank");
            return false;
        }
        else{
            return true;
        }

    }
}
