package com.antony.automaticcallrecorder.recycler_Adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.antony.automaticcallrecorder.R;
import com.antony.automaticcallrecorder.app.CallRecorder_App;
import com.antony.automaticcallrecorder.data.Pojo.Blockednumber;
import com.antony.automaticcallrecorder.fragments.BlockedFragment;
import com.antony.automaticcallrecorder.views.Customviews.CustomTextview;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Antony on 7/2/2017.
 */

public class Recycler_Blockednumbersadapter extends RecyclerView.Adapter<Recycler_Blockednumbersadapter.Recycler_Blockedholder>{


    Context context;

    List<Blockednumber>blockednumbers;

    BlockedFragment blockedFragment;

    public Recycler_Blockednumbersadapter(Context context, List<Blockednumber> blockednumbers, BlockedFragment blockedFragment) {
        this.context = context;
        this.blockednumbers = blockednumbers;
        this.blockedFragment = blockedFragment;
    }

    public class Recycler_Blockedholder extends RecyclerView.ViewHolder
    {
        @InjectView(R.id.textView_blockdetails)
        CustomTextview textView_blockdetails;

        @InjectView(R.id.imgbtn_delete)
        ImageButton imgbtn_delete;



        public Recycler_Blockedholder(View itemView) {
            super(itemView);


            ButterKnife.inject(Recycler_Blockedholder.this,itemView);
        }
    }

    @Override
    public Recycler_Blockedholder onCreateViewHolder(ViewGroup parent, int viewType) {


        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(R.layout.layout_blockednumber, parent, false);


        return new Recycler_Blockedholder(view);
    }

    @Override
    public void onBindViewHolder(Recycler_Blockedholder holder, final int position) {

        StringBuilder stringBuilder=new StringBuilder();



        if(!TextUtils.isEmpty(blockednumbers.get(position).getName()))
        {
            stringBuilder.append(blockednumbers.get(position).getName());
        }
        if(!TextUtils.isEmpty(blockednumbers.get(position).getPhno()))
        {
            stringBuilder.append("\n"+blockednumbers.get(position).getPhno());
        }

        holder.textView_blockdetails.setText(stringBuilder.toString());

        holder.imgbtn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CallRecorder_App callRecorder_app=(CallRecorder_App) context.getApplicationContext();

                callRecorder_app.getDatabaseHelper().delete_Blockednumber(blockednumbers.get(position));

                blockednumbers.remove(position);

                Toast.makeText(context,"Dleted succesfully",Toast.LENGTH_SHORT).show();

                blockedFragment.show_Blockedlistnumbers();

            }
        });



    }

    @Override
    public int getItemCount() {
        return blockednumbers.size();
    }
}
