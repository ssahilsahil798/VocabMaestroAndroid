package com.master.vocab.vocabmaster.SharedPref;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.master.vocab.vocabmaster.GlobalClass;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sahildeswal on 02/09/16.
 */
public class SharedPrefs {
    public static final String PREF_NAME = "com.styledome.one.PREF_NAME";

    protected static Map<String, SharedPreferences> sharedPrefs =
            new HashMap<String, SharedPreferences>(5);
    private static PreferenceManager _instance;



    private static String getStoreNameBase() {
        return PREF_NAME;
    }
    public static String getKeyValueStoreName() {
        return getStoreNameBase() + "_misc";
    }

    protected static SharedPreferences getSharedPreferences(Context context, String storeName){
        if(sharedPrefs.get(storeName) == null){
            synchronized (sharedPrefs){
                sharedPrefs.put(storeName, context.getSharedPreferences(storeName, Context.MODE_PRIVATE));
            }
        }
        return sharedPrefs.get(storeName);
    }

    public static void clearSharedPrefs(){
        SharedPreferences s = getSharedPreferences(GlobalClass.get_instance().getApplicationContext(), getKeyValueStoreName());
        s.edit().clear().commit();
    }

    public static String getString(Context context, String key, String defaultValue) {
        SharedPreferences s = getSharedPreferences(context, getKeyValueStoreName());
        return s.getString(key, defaultValue);
    }

    public static void putString(Context context, String key, String value) {
        SharedPreferences s = getSharedPreferences(context, getKeyValueStoreName());
        SharedPreferences.Editor e = s.edit();
        if (value == null) {
            e.remove(key);
        } else {
            e.putString(key, value);
        }
        e.commit();
    }

    public static void putBoolean(Context context, String key, Boolean value) {
        SharedPreferences s = getSharedPreferences(context, getKeyValueStoreName());
        SharedPreferences.Editor e = s.edit();
        e.putBoolean(key, value);
        e.commit();
    }

    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        SharedPreferences s = getSharedPreferences(context, getKeyValueStoreName());
        return s.getBoolean(key, defaultValue);
    }

    public static void putInt(Context context, String key, int value) {
        SharedPreferences s = getSharedPreferences(context, getKeyValueStoreName());
        SharedPreferences.Editor e = s.edit();
        e.putInt(key, value);
        e.commit();
    }

    public static void putLong(Context context, String key, Long value) {
        SharedPreferences s = getSharedPreferences(context, getKeyValueStoreName());
        SharedPreferences.Editor e = s.edit();
        e.putLong(key, value);
        e.commit();
    }

    public static int getInt(Context context, String key) {
        SharedPreferences s = getSharedPreferences(context, getKeyValueStoreName());
        return s.getInt(key, -1);
    }

    public static Long getLong(Context context, String key) {
        SharedPreferences s = getSharedPreferences(context, getKeyValueStoreName());
        return s.getLong(key, -1);
    }

    public static Long getLong(Context context, String key, long defaultValue) {
        SharedPreferences s = getSharedPreferences(context, getKeyValueStoreName());
        return s.getLong(key, defaultValue);
    }

    public static void putDouble(Context context, String key, double value){
        SharedPreferences s = getSharedPreferences(context, getKeyValueStoreName());
        SharedPreferences.Editor e = s.edit();
        e.putLong(key, Double.doubleToRawLongBits(value));
        e.commit();
    }

    public static Double getDouble(Context context, String key, double defaultValue){
        SharedPreferences s = getSharedPreferences(context, getKeyValueStoreName());
        return Double.longBitsToDouble(s.getLong(key, Double.doubleToLongBits(defaultValue)));
    }
}
