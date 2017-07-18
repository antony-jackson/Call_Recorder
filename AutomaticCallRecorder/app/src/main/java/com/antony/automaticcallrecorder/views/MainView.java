package com.antony.automaticcallrecorder.views;

import android.Manifest;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.antony.automaticcallrecorder.R;
import com.antony.automaticcallrecorder.Utilities.Apputils;
import com.antony.automaticcallrecorder.activities.MainActivity;
import com.antony.automaticcallrecorder.fragments.BlockedFragment;
import com.antony.automaticcallrecorder.fragments.FavouriteFragment;
import com.antony.automaticcallrecorder.fragments.LockedFragment;
import com.antony.automaticcallrecorder.fragments.RecordingFragment;
import com.antony.automaticcallrecorder.pageradapter.TabPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Antony on 6/28/2017.
 */

public class MainView extends LinearLayout {

    Context context;

    String name[] = {"Recording", "Blocked"};


    public MainView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;
    }


    public void setup_Fragments() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new RecordingFragment());
        fragments.add(new BlockedFragment());
//    fragments.add(new FavouriteFragment());
       // fragments.add(new LockedFragment());


        ((TabLayout) this.findViewById(R.id.tab_maintab)).addTab(((TabLayout) this.findViewById(R.id.tab_maintab)).newTab().setText("Records"));

        ((TabLayout) this.findViewById(R.id.tab_maintab)).addTab(((TabLayout) this.findViewById(R.id.tab_maintab)).newTab().setText("Blocked"));


       // ((TabLayout) this.findViewById(R.id.tab_maintab)).addTab(((TabLayout) this.findViewById(R.id.tab_maintab)).newTab().setText("Locked"));


        ((ViewPager) this.findViewById(R.id.pager)).setAdapter(new TabPagerAdapter(((MainActivity) context).getSupportFragmentManager(), fragments, name));




        ((ViewPager) this.findViewById(R.id.pager)).addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(((TabLayout) this.findViewById(R.id.tab_maintab))));

        ((TabLayout) this.findViewById(R.id.tab_maintab)).addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                ((ViewPager) findViewById(R.id.pager)).setCurrentItem(tab.getPosition());

               // Apputils.last_tabposition=tab.getPosition();

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        if (Apputils.last_tabposition != -1) {
            ((TabLayout) this.findViewById(R.id.tab_maintab)).getTabAt(Apputils.last_tabposition).select();
        } else {

            ((TabLayout) this.findViewById(R.id.tab_maintab)).getTabAt(1).select();

        }


    }


}
