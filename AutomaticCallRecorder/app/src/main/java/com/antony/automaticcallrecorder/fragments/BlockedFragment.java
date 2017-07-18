package com.antony.automaticcallrecorder.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.antony.automaticcallrecorder.R;
import com.antony.automaticcallrecorder.activities.BlockNumberActivity;
import com.antony.automaticcallrecorder.activities.MainActivity;
import com.antony.automaticcallrecorder.app.CallRecorder_App;
import com.antony.automaticcallrecorder.data.Pojo.Blockednumber;
import com.antony.automaticcallrecorder.data.Pojo.Fragment_Instance;
import com.antony.automaticcallrecorder.recycler_Adapter.Recycler_Blockednumbersadapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlockedFragment extends Fragment implements Serializable {


    public BlockedFragment() {
        // Required empty public constructor
    }

    View view;

@InjectView(R.id.floatingActionButton)
FloatingActionButton floatingActionButton;

    @InjectView(R.id.rec_allblocked)
    RecyclerView rec_allblocked;

    Context context;

    CallRecorder_App callRecorder_app;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_blocked, container, false);




        ButterKnife.inject(BlockedFragment.this, view);

        context = getActivity();

        callRecorder_app = (CallRecorder_App) context.getApplicationContext();

        show_Blockedlistnumbers();



        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(context,BlockNumberActivity.class));
            }
        });


        return view;
    }



    public void show_Blockedlistnumbers() {

        List<Blockednumber> blockednumberList = new ArrayList<>();

        blockednumberList = callRecorder_app.getDatabaseHelper().getAllBlockednumber();


        rec_allblocked.setLayoutManager(new LinearLayoutManager(context));
        rec_allblocked.setAdapter(new Recycler_Blockednumbersadapter(context, blockednumberList, BlockedFragment.this));


    }


}
