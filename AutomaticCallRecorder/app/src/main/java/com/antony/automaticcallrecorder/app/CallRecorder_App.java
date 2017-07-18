package com.antony.automaticcallrecorder.app;

import android.app.Application;
import android.content.Context;

import com.antony.automaticcallrecorder.data.DatabaseHelper;

/**
 * Created by Antony on 7/1/2017.
 */

public class CallRecorder_App extends Application {

DatabaseHelper databaseHelper;


    @Override
    public void onCreate() {
        super.onCreate();

        set_Databasehelper(this);
    }


    private void set_Databasehelper(Context context)
    {
       databaseHelper=new DatabaseHelper(context);
    }


    public DatabaseHelper getDatabaseHelper()
    {
        return databaseHelper;
    }
}
