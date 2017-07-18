package com.antony.automaticcallrecorder.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.antony.automaticcallrecorder.R;

public class SplashActivity extends AppCompatActivity
{

Thread thread;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();

        thread =new Thread(new Runnable() {
            @Override
            public void run() {


                try{

                    thread.sleep(4000);


                    startActivity(new Intent(SplashActivity.this,MainActivity.class));
                    finish();

                }catch (Exception e)
                {

                }






            }
        });


        thread.start();



    }
}
