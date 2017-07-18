package com.antony.automaticcallrecorder.recycler_Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.antony.automaticcallrecorder.R;
import com.antony.automaticcallrecorder.data.Pojo.Lockednumber;
import com.antony.automaticcallrecorder.fragments.LockedFragment;
import com.antony.automaticcallrecorder.views.Customviews.CustomTextview;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Antony on 7/7/2017.
 */

public class Recycler_lockednumberAdapter extends RecyclerView.Adapter<Recycler_lockednumberAdapter.Recycler_lockedholder> {



    Context context;

    List<Lockednumber>lockednumbers;

    LockedFragment lockedFragment;

    public Recycler_lockednumberAdapter(Context context, List<Lockednumber> lockednumbers, LockedFragment lockedFragment) {
        this.context = context;
        this.lockednumbers = lockednumbers;
        this.lockedFragment = lockedFragment;
    }

    public class Recycler_lockedholder extends RecyclerView.ViewHolder
    {
        @InjectView(R.id.textView_blockdetails)
        CustomTextview textView_blockdetails;

        @InjectView(R.id.imgbtn_delete)
        ImageButton imgbtn_delete;

        public Recycler_lockedholder(View itemView) {
            super(itemView);

            ButterKnife.inject(Recycler_lockedholder.this,itemView);
        }
    }


    @Override
    public Recycler_lockedholder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(R.layout.layout_blockednumber, parent, false);

        return new Recycler_lockedholder(view);
    }

    @Override
    public void onBindViewHolder(Recycler_lockedholder holder, int position) {

        StringBuilder stringBuilder=new StringBuilder();

        if(!TextUtils.isEmpty(lockednumbers.get(position).getName()))
        {
            stringBuilder.append(lockednumbers.get(position).getName());
        }
        if(!TextUtils.isEmpty(lockednumbers.get(position).getNumber()))
        {
            stringBuilder.append("\n"+lockednumbers.get(position).getNumber());
        }

        holder.textView_blockdetails.setText(stringBuilder.toString());

    }

    @Override
    public int getItemCount() {
        return lockednumbers.size();
    }
}
