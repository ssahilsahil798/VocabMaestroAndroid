package com.master.vocab.vocabmaster;

import android.app.Application;
import android.content.Context;

import com.master.vocab.vocabmaster.Api.StringConverter;
import com.master.vocab.vocabmaster.SharedPref.SharedPrefs;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.android.AndroidLog;

/**
 * Created by sahildeswal on 02/09/16.
 */
public class GlobalClass extends Application{

    private static GlobalClass _instance = null;
    private String username = "sahil";
    private String api_key = "fd5a54d8b3a1a927014b61df9ab79ec4316fe650";
    private static RestAdapter restAdapter = null;

    private GlobalClass(){

    }

    public static GlobalClass get_instance(){
        if(_instance == null){
            _instance = new GlobalClass();

        }
        return _instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        getGlobalApiParams(getApplicationContext());
    }

    private void getGlobalApiParams(Context applicationContext) {
        api_key = SharedPrefs.getString(applicationContext, "api_key", "");
        username = SharedPrefs.getString(applicationContext, "username", "");
    }

    public RestAdapter getRestAdapter(){
        if(restAdapter==null){
            restAdapter = new RestAdapter.Builder()
                    .setEndpoint(URLClass.BASE_URL)
                    .setLogLevel(RestAdapter.LogLevel.FULL).setLog(new AndroidLog("vocab"))
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setConverter(new StringConverter())
                    .setRequestInterceptor(new RequestInterceptor() {
                        @Override
                        public void intercept(RequestFacade request) {

                            request.addQueryParam("api_key", api_key);
                            request.addQueryParam("username", username);
                        }
                    })
                    .build();
        }
        return restAdapter;
    }


}
