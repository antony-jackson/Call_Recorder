package com.antony.automaticcallrecorder.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.antony.automaticcallrecorder.R;
import com.antony.automaticcallrecorder.activities.LockpasswordActivity;
import com.antony.automaticcallrecorder.activities.MainActivity;
import com.antony.automaticcallrecorder.data.DatabaseHelper;
import com.antony.automaticcallrecorder.data.Pojo.Lockednumber;
import com.antony.automaticcallrecorder.recycler_Adapter.Recycler_lockednumberAdapter;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A simple {@link Fragment} subclass.
 */
public class LockedFragment extends Fragment {


    public LockedFragment() {
        // Required empty public constructor
    }

    View view;

    @InjectView(R.id.fab_locked)
    FloatingActionButton fab_locked;

    @InjectView(R.id.rec_locked)
    RecyclerView rec_locked;

    Context context;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_locked, container, false);

        context=getActivity();

        ButterKnife.inject(LockedFragment.this,view);

        show_lockednumbers();



        fab_locked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, LockpasswordActivity.class));

                ((MainActivity)context).finish();
            }
        });



        return view;
    }

    public void show_lockednumbers()
    {
        DatabaseHelper databaseHelper=new DatabaseHelper(context);


        List<Lockednumber>lockednumbers=databaseHelper.getAll_Lockednumber();

        if(lockednumbers.size()>0) {

            rec_locked.setLayoutManager(new LinearLayoutManager(context));

            rec_locked.setAdapter(new Recycler_lockednumberAdapter(context, lockednumbers, LockedFragment.this));

        }

    }

}
