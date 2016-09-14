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
    private static String username = "";
    private static String api_key = "";
    private static RestAdapter restAdapter = null;
    private SharedPrefs sharedPrefs = null;

    public static GlobalClass getInstance(){
        if(_instance == null){
            _instance = new GlobalClass();

        }
        return _instance;
    }

    public GlobalClass(){
        _instance = this;
    }


    @Override
    public void onCreate() {
        super.onCreate();

        getSharedPrefs();
        getGlobalApiParams(getApplicationContext());
    }

    public SharedPrefs getSharedPrefs(){
        if(sharedPrefs == null){
            sharedPrefs = new SharedPrefs();
        }
        return sharedPrefs;
    }

    private static void getGlobalApiParams(Context applicationContext) {
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
