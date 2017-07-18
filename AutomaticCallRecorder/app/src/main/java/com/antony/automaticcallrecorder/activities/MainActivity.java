package com.antony.automaticcallrecorder.activities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.antony.automaticcallrecorder.R;
import com.antony.automaticcallrecorder.Utilities.Apputils;
import com.antony.automaticcallrecorder.controllers.MainControllers;
import com.antony.automaticcallrecorder.views.MainView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    MainView mainView;

    Context context;

    public static final int Check_permission = 11;

    MainControllers mainControllers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = MainActivity.this;

        handle_Permissions();

        mainView = (MainView) this.findViewById(R.id.activity_main);

        mainControllers = new MainControllers(MainActivity.this, mainView);

        mainView.setup_Fragments();
    }


    public void handle_Permissions() {
        List<String> strings_permission = new ArrayList<>();


        if (Build.VERSION.SDK_INT >= 23) {

            if (!Apputils.is_Permissionenabled(Manifest.permission.READ_CONTACTS, context)) {
                strings_permission.add(Manifest.permission.READ_CONTACTS);
            }
            if (!Apputils.is_Permissionenabled(Manifest.permission.READ_CALL_LOG, context)) {
                strings_permission.add(Manifest.permission.READ_CALL_LOG);
            }

            if (!Apputils.is_Permissionenabled(Manifest.permission.READ_PHONE_STATE, context)) {
                strings_permission.add(Manifest.permission.READ_PHONE_STATE);
            }

            if (!Apputils.is_Permissionenabled(Manifest.permission.READ_EXTERNAL_STORAGE, context)) {
                strings_permission.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }

            if (!Apputils.is_Permissionenabled(Manifest.permission.WRITE_EXTERNAL_STORAGE, context)) {
                strings_permission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }

            if (!Apputils.is_Permissionenabled(Manifest.permission.RECORD_AUDIO, context)) {
                strings_permission.add(Manifest.permission.RECORD_AUDIO);
            }

            if (!Apputils.is_Permissionenabled(Manifest.permission.ACCESS_COARSE_LOCATION, context)) {
                strings_permission.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }

            if (!Apputils.is_Permissionenabled(Manifest.permission.PROCESS_OUTGOING_CALLS, context)) {
                strings_permission.add(Manifest.permission.PROCESS_OUTGOING_CALLS);
            }

            if (!Apputils.is_Permissionenabled(Manifest.permission.CALL_PHONE, context)) {
                strings_permission.add(Manifest.permission.CALL_PHONE);
            }
            if (strings_permission.size() > 0) {
                ActivityCompat.requestPermissions(MainActivity.this, strings_permission.toArray(new String[strings_permission.size()]), Check_permission);

            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == Check_permission) {

            if (grantResults.length > 0 || grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Log.d("Mainactivity", "enabled");


            }


        }


    }
}
