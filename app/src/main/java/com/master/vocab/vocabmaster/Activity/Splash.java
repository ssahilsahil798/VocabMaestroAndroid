package com.master.vocab.vocabmaster.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.master.vocab.vocabmaster.MainActivity;
import com.master.vocab.vocabmaster.R;
import com.master.vocab.vocabmaster.SharedPref.SharedPrefs;

/**
 * Created by sahildeswal on 15/09/16.
 */
public class Splash extends AppCompatActivity {

    private String isLoggedIn;
    private ImageView splashImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        splashImage = (ImageView)findViewById(R.id.splash_image);

        splashImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_menu_gallery));



        isLoggedIn = SharedPrefs.getString(this, "registered", "");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(isLoggedIn.contentEquals("")){

                    Intent intent = new Intent(getApplicationContext(), Login.class);
                    startActivity(intent);
                    finish();
                }
                else if(isLoggedIn.contentEquals("logout")){
                    Intent intent = new Intent(getApplicationContext(), Login.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 2000);



    }

}
