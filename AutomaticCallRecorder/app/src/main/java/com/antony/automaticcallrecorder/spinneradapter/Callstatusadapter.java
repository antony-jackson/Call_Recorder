package com.antony.automaticcallrecorder.spinneradapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.antony.automaticcallrecorder.R;
import com.antony.automaticcallrecorder.data.Pojo.CallStatus;
import com.antony.automaticcallrecorder.fragments.RecordingFragment;
import com.antony.automaticcallrecorder.views.Customviews.CustomTextview;

import java.util.List;

/**
 * Created by Antony on 7/1/2017.
 */

public class Callstatusadapter extends BaseAdapter {

    Context context;

    List<CallStatus>callStatuses;

    RecordingFragment recordingFragment;


    public Callstatusadapter(Context context, List<CallStatus>callStatuses, RecordingFragment recordingFragment) {
        this.context = context;
        this.callStatuses = callStatuses;
        this.recordingFragment=recordingFragment;
    }

    @Override
    public int getCount() {
        return callStatuses.size();
    }

    @Override
    public Object getItem(int position) {
        return callStatuses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        LayoutInflater layoutInflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);


        View view=layoutInflater.inflate(R.layout.layout_spinner_callstatusadapter,parent,false);

        CustomTextview textView_callstatus=(CustomTextview) view.findViewById(R.id.textView_callstatus);

        textView_callstatus.setText(callStatuses.get(position).getCallstatus());

        return view;
    }
}
