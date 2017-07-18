package com.antony.automaticcallrecorder.controllers;

import android.content.Context;

import com.antony.automaticcallrecorder.views.MainView;

/**
 * Created by Antony on 6/28/2017.
 */

public class MainControllers {



    Context context;

    MainView mainView;

    public MainControllers(Context context, MainView mainView) {
        this.context = context;
        this.mainView = mainView;
    }
}
