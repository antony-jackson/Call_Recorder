package com.antony.automaticcallrecorder.pageradapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Antony on 6/30/2017.
 */

public class TabPagerAdapter extends FragmentPagerAdapter {

    List<Fragment>fragments;
    String arr_title[];

    public TabPagerAdapter(FragmentManager fm,List<Fragment>fragments,String arr_title[]) {
        super(fm);
        this.fragments=fragments;
        this.arr_title=arr_title;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return arr_title[position];
    }
}
