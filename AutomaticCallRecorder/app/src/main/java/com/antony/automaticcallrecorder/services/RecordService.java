package com.antony.automaticcallrecorder.services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.Context;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.text.TextUtils;
import android.widget.Toast;

import com.antony.automaticcallrecorder.R;
import com.antony.automaticcallrecorder.activities.SplashActivity;
import com.antony.automaticcallrecorder.app.CallRecorder_App;
import com.antony.automaticcallrecorder.data.Pojo.Record;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class RecordService extends Service {
    MediaRecorder recorder = new MediaRecorder();
    String ts = "";
    File file;

    String phnumber = null, name = null;
    int callstatus;

    public static int notificationID = 0;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Toast.makeText(RecordService.this, "Recording started", Toast.LENGTH_SHORT).show();

        phnumber = intent.getStringExtra("phonenumber");
        name = intent.getStringExtra("Name");
        callstatus = intent.getIntExtra("Callstatus", 0);

        Toast.makeText(RecordService.this, "Record service  : " + phnumber, Toast.LENGTH_SHORT).show();

        Toast.makeText(RecordService.this, "Record service  : " + name, Toast.LENGTH_SHORT).show();

        recorder.reset();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        Long tsLong = System.currentTimeMillis() / 1000;
        ts = tsLong.toString();

        File file_dir = new File(Environment.getExternalStorageDirectory() + "/" + "Callhandler");

        if (!file_dir.exists()) {
            file_dir.mkdir();

        }
        file = new File(file_dir.getPath() + "/" + ts + ".3gp");


        recorder.setOutputFile(file.getPath());
        try {
            recorder.prepare();
        } catch (Exception e) {

        }


        recorder.start();


        return Service.START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();



        recorder.stop();

        Calendar c = Calendar.getInstance();
        System.out.println("Current time => "+c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy ");
        String formattedDate = df.format(c.getTime());

        int second = c.get(Calendar.SECOND);
        int minute = c.get(Calendar.MINUTE);
        //12 hour format
        int hour = c.get(Calendar.HOUR);


       // Toast.makeText(RecordService.this, "Recording stopped", Toast.LENGTH_SHORT).show();


        CallRecorder_App callRecorder_app = (CallRecorder_App) RecordService.this.getApplicationContext();


        Record record = new Record();
        record.setCallstatus(callstatus);
        record.setFavourite(0);
        record.setFilepath(file.getPath());
        record.setName(name);
        record.setPhonenumber(phnumber);
        record.setDate(formattedDate);
        record.setTime(hour+":"+minute+":"+second);


        callRecorder_app.getDatabaseHelper().addRecording(record);
        shownotification();

    }


    public void shownotification() {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(RecordService.this);
        StringBuilder stringBuilder = new StringBuilder();

        if (!TextUtils.isEmpty(name)) {
            stringBuilder.append(name + "\n");

        }
        if (!TextUtils.isEmpty(phnumber)) {
            stringBuilder.append(phnumber);
        }


        mBuilder.setSmallIcon(R.drawable.ic_launcher);
        mBuilder.setContentTitle("Call Recorded");
        mBuilder.setContentText(stringBuilder.toString());

        Intent resultIntent = new Intent(this, SplashActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(SplashActivity.class);

// Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

// notificationID allows you to update the notification later on.
        mNotificationManager.notify(notificationID++, mBuilder.build());
    }
}
