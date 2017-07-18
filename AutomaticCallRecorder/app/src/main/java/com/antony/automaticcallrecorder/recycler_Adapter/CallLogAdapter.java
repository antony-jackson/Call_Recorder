package com.antony.automaticcallrecorder.recycler_Adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.antony.automaticcallrecorder.R;
import com.antony.automaticcallrecorder.data.Pojo.Call_Log;
import com.antony.automaticcallrecorder.views.Customviews.CustomTextview;

import java.util.List;

/**
 * Created by Antony on 7/2/2017.
 */

public class CallLogAdapter extends BaseAdapter {


    Context context;

    List<Call_Log> call_logs;

    public CallLogAdapter(Context context, List<Call_Log> call_logs) {
        this.context = context;
        this.call_logs = call_logs;
    }

    @Override
    public int getCount() {
        return call_logs.size();
    }

    @Override
    public Object getItem(int position) {
        return call_logs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.layout_calllog, parent, false);

        AppCompatImageView img_callstatus = (AppCompatImageView) view.findViewById(R.id.img_callstatus);

        CustomTextview textView_call_details = (CustomTextview) view.findViewById(R.id.textView_call_details);
        if (call_logs.get(position).getCalltype() == 1) {
            img_callstatus.setImageResource(R.drawable.ic_outgoing_call);


        } else if (call_logs.get(position).getCalltype() == 2) {
            img_callstatus.setImageResource(R.drawable.ic_incoming_phone);
        } else if (call_logs.get(position).getCalltype() == 3) {
            img_callstatus.setImageResource(R.drawable.missedcall);


        }

        StringBuilder stringBuilder=new StringBuilder();

        if(!TextUtils.isEmpty(call_logs.get(position).getName()))
        {
            stringBuilder.append(call_logs.get(position).getName());
        }
        stringBuilder.append("\n"+call_logs.get(position).getPhno());

        textView_call_details.setText(stringBuilder.toString());

        return view;
    }
}
