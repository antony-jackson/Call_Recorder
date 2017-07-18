package com.antony.automaticcallrecorder.recycler_Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.antony.automaticcallrecorder.R;
import com.antony.automaticcallrecorder.data.Pojo.Record;
import com.antony.automaticcallrecorder.fragments.RecordingFragment;
import com.antony.automaticcallrecorder.views.Customviews.CustomTextview;

import java.io.File;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Antony on 7/1/2017.
 */

public class Recycler_Recordingadapter extends RecyclerView.Adapter<Recycler_Recordingadapter.Record_Holder> {


    Context context;
    List<Record> records;

    RecordingFragment recordingFragment;

    public Recycler_Recordingadapter(Context context, List<Record> records, RecordingFragment recordingFragment) {
        this.context = context;
        this.records = records;
        this.recordingFragment = recordingFragment;
    }

    public class Record_Holder extends RecyclerView.ViewHolder {

        @InjectView(R.id.textViewdate_time)
        CustomTextview textViewdate_time;

        @InjectView(R.id.imgbtn_like)
        ImageButton imgbtn_like;

        @InjectView(R.id.textViewcalldetail)
        CustomTextview textViewcalldetail;


        @InjectView(R.id.imageButton_play)
        ImageButton imageButton_play;

        @InjectView(R.id.textView_play)
        CustomTextview textView_play;

        public Record_Holder(View itemView) {
            super(itemView);


            ButterKnife.inject(Record_Holder.this, itemView);
        }
    }


    @Override
    public Record_Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(R.layout.layout_calladapter, parent, false);


        return new Record_Holder(view);
    }

    @Override
    public void onBindViewHolder(final Record_Holder holder, final int position) {

        StringBuilder stringBuilder=new StringBuilder();

        if(!TextUtils.isEmpty(records.get(position).getName()))
        {
            stringBuilder.append(records.get(position).getName());
        }
        if(!TextUtils.isEmpty(records.get(position).getPhonenumber()))
        {
            stringBuilder.append("\n"+records.get(position).getPhonenumber());
        }




        holder.textViewcalldetail.setText(stringBuilder.toString());

        holder.textViewdate_time.setText(records.get(position).getDate() + " " + records.get(position).getTime());

        if(records.get(position).getFavourite()==1)
        {
            PorterDuffColorFilter porterDuffColorFilter = new PorterDuffColorFilter(Color.parseColor("#f24343"),
                    PorterDuff.Mode.SRC_ATOP);
            holder.imgbtn_like.setColorFilter(porterDuffColorFilter);
        }


        holder.imgbtn_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(records.get(position).getFavourite()==0) {


                    PorterDuffColorFilter porterDuffColorFilter = new PorterDuffColorFilter(Color.parseColor("#f24343"),
                            PorterDuff.Mode.SRC_ATOP);
                    holder.imgbtn_like.setColorFilter(porterDuffColorFilter);
                    recordingFragment.add_to_Favourite(records.get(position));
                }
                else {

                    PorterDuffColorFilter porterDuffColorFilter = new PorterDuffColorFilter(Color.parseColor("#757575"),
                            PorterDuff.Mode.SRC_ATOP);
                    holder.imgbtn_like.setColorFilter(porterDuffColorFilter);

                    recordingFragment.remove_Favourite(records.get(position));

                }


            }
        });

        holder.imageButton_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    File file=new File(records.get(position).getFilepath());

                    if(file.exists()) {


//                        MediaPlayer mediaPlayer = new MediaPlayer();
//
//                        mediaPlayer.setDataSource(records.get(position).getFilepath());
//                        mediaPlayer.prepare();
//                        mediaPlayer.start();

                        Uri uri = Uri.parse(records.get(position).getFilepath());
                        Intent it = new Intent(Intent.ACTION_VIEW, uri);
                        it.setDataAndType(uri,"audio/3gp");
                        context.startActivity(it);
                    }
                    else {

                        Toast.makeText(context,"File not found",Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e)
                {

                }



            }
        });

        holder.textView_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    File file=new File(records.get(position).getFilepath());

                    if(file.exists()) {


//                        MediaPlayer mediaPlayer = new MediaPlayer();
//
//                        mediaPlayer.setDataSource(records.get(position).getFilepath());
//                        mediaPlayer.prepare();
//                        mediaPlayer.start();

                        Uri uri = Uri.parse(records.get(position).getFilepath());
                        Intent it = new Intent(Intent.ACTION_VIEW, uri);
                        it.setDataAndType(uri,"video/3gpp");
                        context.startActivity(it);
                    }
                    else {

                        Toast.makeText(context,"File not found",Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e)
                {

                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return records.size();
    }
}
