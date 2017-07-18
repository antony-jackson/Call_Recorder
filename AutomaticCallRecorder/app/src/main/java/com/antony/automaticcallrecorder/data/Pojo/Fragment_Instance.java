package com.antony.automaticcallrecorder.data.Pojo;

import android.support.v4.app.Fragment;

import com.antony.automaticcallrecorder.fragments.BlockedFragment;

import java.io.Serializable;

/**
 * Created by Antony on 7/3/2017.
 */

public class Fragment_Instance implements Serializable {


    Fragment blockedFragment;

    public Fragment_Instance(BlockedFragment blockedFragment) {
        this.blockedFragment = blockedFragment;
    }

    public Fragment getBlockedFragment() {
        return blockedFragment;
    }

    public void setBlockedFragment(Fragment blockedFragment) {
        this.blockedFragment = blockedFragment;
    }
}
