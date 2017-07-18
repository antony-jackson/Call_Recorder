package com.antony.automaticcallrecorder.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.antony.automaticcallrecorder.R;
import com.antony.automaticcallrecorder.Utilities.Static_keys;
import com.antony.automaticcallrecorder.activities.MainActivity;
import com.antony.automaticcallrecorder.app.CallRecorder_App;
import com.antony.automaticcallrecorder.data.Pojo.CallStatus;
import com.antony.automaticcallrecorder.data.Pojo.Record;
import com.antony.automaticcallrecorder.pageradapter.Pager;
import com.antony.automaticcallrecorder.pageradapter.TabPagerAdapter;
import com.antony.automaticcallrecorder.preference.Callrecorder_Preference;
import com.antony.automaticcallrecorder.recycler_Adapter.Recycler_Recordingadapter;
import com.antony.automaticcallrecorder.spinneradapter.Callstatusadapter;
import com.antony.automaticcallrecorder.views.Customviews.CustomTextview;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnCheckedChanged;


public class RecordingFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    String arr_callstatus[] = {"Incoming", "Outgoing", "Favourite"};

    int arr_status[] = {2, 1, 3};


    View view;

    @InjectView(R.id.recycler_items)
    RecyclerView recycler_items;
    //
    @InjectView(R.id.spinner_callstatus)
    Spinner spinner_callstatus;

    @InjectView(R.id.switchcompat_record)
    SwitchCompat switchcompat_record;


    @InjectView(R.id.textView_recordtext)
    CustomTextview textView_recordtext;

//    @InjectView(R.id.recycler_recordings)
//    RecyclerView recycler_recordings;

    Context context;

    CallRecorder_App callRecorder_app;

    CallStatus callStatus;


    public RecordingFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_recording, container, false);

        ButterKnife.inject(RecordingFragment.this, view);

        context = getActivity();
        check_App_Firsttime();


        callRecorder_app = (CallRecorder_App) context.getApplicationContext();
        check_Recordingstatus();


        set_SpinnerAdapter();


        spinner_callstatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                callStatus = (CallStatus) spinner_callstatus.getSelectedItem();

                if (callStatus.getStatus() == 3) {
                    show_Favouritecalls();
                } else {

                    show_Calls(callStatus);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return view;
    }


    private void check_Recordingstatus() {
        if (new Callrecorder_Preference(context).get_Boolean(Static_keys.Preferencekeys.preference_record_key)) {
            switchcompat_record.setChecked(true);
            textView_recordtext.setText("Recording enabled");
        } else {
            switchcompat_record.setChecked(false);
            textView_recordtext.setText("Recording Disabled");
        }
    }


    @OnCheckedChanged(R.id.switchcompat_record)
    public void check_switch() {
        if (switchcompat_record.isChecked()) {
            textView_recordtext.setText("Recording enabled");
            new Callrecorder_Preference(context).put_Boolean(Static_keys.Preferencekeys.preference_record_key, true);
        } else {
            textView_recordtext.setText("Recording Disabled");
            new Callrecorder_Preference(context).put_Boolean(Static_keys.Preferencekeys.preference_record_key, false);
        }
    }


    private void set_SpinnerAdapter() {

        List<CallStatus> callStatuses = new ArrayList<>();

        for (int i = 0; i < arr_callstatus.length; i++) {
            CallStatus callStatus = new CallStatus();
            callStatus.setCallstatus(arr_callstatus[i]);
            callStatus.setStatus(arr_status[i]);
            callStatuses.add(callStatus);
        }

        spinner_callstatus.setAdapter(new Callstatusadapter(context, callStatuses, RecordingFragment.this));
    }

    private void show_Calls(CallStatus callStatus) {
        List<Record> records = new ArrayList<>();

        List<Record> records_status = new ArrayList<>();

        records = callRecorder_app.getDatabaseHelper().getAllRecord();

        for (Record record : records
                ) {


            if (record.getCallstatus() == callStatus.getStatus()) {
                records_status.add(record);
            }

        }

        recycler_items.setLayoutManager(new LinearLayoutManager(context));
        recycler_items.setAdapter(new Recycler_Recordingadapter(context, records_status, RecordingFragment.this));


    }

    private void show_Favouritecalls() {
        List<Record> records = new ArrayList<>();
        List<Record> records_status = new ArrayList<>();
        records = callRecorder_app.getDatabaseHelper().getAllRecord();

        for (Record record : records
                ) {


            if (record.getFavourite() == 1) {
                records_status.add(record);
            }

        }

        recycler_items.setLayoutManager(new LinearLayoutManager(context));
        recycler_items.setAdapter(new Recycler_Recordingadapter(context, records_status, RecordingFragment.this));

    }


    public void add_to_Favourite(Record record) {

        record.setFavourite(1);


        callRecorder_app.getDatabaseHelper().update_Recording(record);


    }

    public void remove_Favourite(Record record) {

        record.setFavourite(0);


        callRecorder_app.getDatabaseHelper().update_Recording(record);


    }


    private void check_App_Firsttime() {

        if (!new Callrecorder_Preference(context).get_Boolean(Static_keys.Preferencekeys.appfirsttime_key)) {

            switchcompat_record.setChecked(true);

            new Callrecorder_Preference(context).put_Boolean(Static_keys.Preferencekeys.preference_record_key, true);

            new Callrecorder_Preference(context).put_Boolean(Static_keys.Preferencekeys.appfirsttime_key, true);
        }

    }


}
