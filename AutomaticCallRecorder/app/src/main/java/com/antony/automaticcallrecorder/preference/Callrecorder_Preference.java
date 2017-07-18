package com.antony.automaticcallrecorder.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Antony on 6/30/2017.
 */

public class Callrecorder_Preference {


    SharedPreferences sharedPreferences;


    Context context;


    public Callrecorder_Preference(Context context) {
        this.context = context;

        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
    }




    public void put_Boolean(String key,boolean val)
    {
        sharedPreferences.edit().putBoolean(key,val).commit();
    }

    public boolean get_Boolean(String key)
    {
        return sharedPreferences.getBoolean(key,false);
    }

}
