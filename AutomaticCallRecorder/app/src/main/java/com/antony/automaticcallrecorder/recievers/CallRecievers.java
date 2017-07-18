package com.antony.automaticcallrecorder.recievers;

import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.internal.telephony.ITelephony;
import com.antony.automaticcallrecorder.R;
import com.antony.automaticcallrecorder.Utilities.Static_keys;
import com.antony.automaticcallrecorder.activities.SplashActivity;
import com.antony.automaticcallrecorder.data.DatabaseHelper;
import com.antony.automaticcallrecorder.data.Pojo.Blockednumber;
import com.antony.automaticcallrecorder.data.Pojo.Lockednumber;
import com.antony.automaticcallrecorder.preference.Callrecorder_Preference;
import com.antony.automaticcallrecorder.services.RecordService;
import com.antony.automaticcallrecorder.views.Customviews.CustomButton;
import com.antony.automaticcallrecorder.views.Customviews.CustomEdittext;
import com.antony.automaticcallrecorder.views.Customviews.CustomTextview;

import java.io.File;
import java.lang.reflect.Method;
import java.util.List;


import static android.content.Context.TELEPHONY_SERVICE;


public class CallRecievers extends BroadcastReceiver {


    TelephonyManager telephonyManager;

    String LOG_TAG = "CallREceiver";
    // MediaRecorder recorder ;

    boolean isrecording = false;

    String phnumber = "", name = "", phnumber_outgoing = "";

    Intent intent;

    int callstatus;
    boolean is_blocked=false;

    public static int notificationID = 0;

    @Override
    public void onReceive(Context context, Intent intent) {

        telephonyManager = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);

        phnumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);

        phnumber_outgoing = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
        if (new Callrecorder_Preference(context).get_Boolean(Static_keys.Preferencekeys.preference_record_key)) {

            if (!TextUtils.isEmpty(phnumber_outgoing)) {
                callstatus = 1;

                //  Toast.makeText(context, "outgoing : " + phnumber_outgoing, Toast.LENGTH_SHORT).show();
                telephonyManager.listen(new CustomPhone_Statelistener(context), PhoneStateListener.LISTEN_CALL_STATE);
            } else if (!TextUtils.isEmpty(phnumber)) {
                if (!is_bLocked(phnumber, context)) {

                    callstatus = 2;


                    telephonyManager.listen(new CustomPhone_Statelistener(context), PhoneStateListener.LISTEN_CALL_STATE);
                } else {

                    endcall(context, phnumber);


                }


            }

        } else {

            if (!is_bLocked(phnumber, context)) {

                callstatus = 2;


                telephonyManager.listen(new CustomPhone_Statelistener(context), PhoneStateListener.LISTEN_CALL_STATE);
            } else {

                endcall(context, phnumber);


            }
        }


    }


    public boolean is_bLocked(String phnumber, Context context) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);

        boolean isbolcked = false;

        List<Blockednumber> blockednumberList = databaseHelper.getAllBlockednumber();

        for (Blockednumber blockednumber : blockednumberList
                ) {

            if (blockednumber.getPhno().equals(phnumber)) {


                isbolcked = true;
                break;
            } else {
                isbolcked = false;
            }

        }

        return isbolcked;
    }


    public void endcall(Context context, String phno) {
        try {

            AudioManager audioManager = (AudioManager) context.getSystemService(context.AUDIO_SERVICE);
            //Turn ON the mute
            //audioManager.setStreamMute(AudioManager.STREAM_RING, true);


            TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);

            Toast.makeText(context, "telephony manager", Toast.LENGTH_SHORT).show();
            Class c = Class.forName(tm.getClass().getName());


            Method m = c.getDeclaredMethod("getITelephony");

            // Method m = c.getDeclaredMethod("com.android.internal.telephony.ITelephony");

            // Toast.makeText(context, "methode telephony manager", Toast.LENGTH_SHORT).show();
            m.setAccessible(true);

            final ITelephony telephonyService = (ITelephony) m.invoke(tm);


            // telephonyService.silenceRinger();


            //sendMessage(incoming_number, context);
            telephonyService.endCall();


            String message = "Call Blocked \n" + phno;

if(!is_blocked) {
    shownotification(context, message);
}


        } catch (Exception e) {

            Log.e("msg", e.toString());
            // Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();


        }
    }


    public class CustomPhone_Statelistener extends PhoneStateListener {

        Context context;


        public CustomPhone_Statelistener(Context context) {
            this.context = context;
        }

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);


            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE:
                    Log.i(LOG_TAG, "onCallStateChanged: CALL_STATE_IDLE");
                    // Toast.makeText(context, "onCallStateChanged: idle", Toast.LENGTH_SHORT).show();

                    if (isrecording) {
                        // recorder.stop();
                        isrecording = false;


                        context.stopService(intent);
                    }
                    //  recorder.release();


                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                    Log.i(LOG_TAG, "onCallStateChanged: CALL_STATE_RINGING");
                    // Toast.makeText(context, "onCallStateChanged: Ringing", Toast.LENGTH_SHORT).show();

                    if (!isrecording) {
                        String[] projection = new String[]{
                                ContactsContract.PhoneLookup.DISPLAY_NAME,
                                ContactsContract.PhoneLookup.NUMBER,
                                ContactsContract.PhoneLookup.HAS_PHONE_NUMBER};

                        // encode the phone number and build the filter URI
                        Uri contactUri = Uri.withAppendedPath(
                                ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
                                Uri.encode(phnumber));

                        // query time
                        Cursor cursor = context.getContentResolver().query(contactUri,
                                projection, null, null, null);

                        if (cursor.moveToFirst()) {
                            name = cursor.getString(cursor
                                    .getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
                            // Toast.makeText(context, "name:" + name, Toast.LENGTH_SHORT).show();
                        }


                        intent = new Intent(context, RecordService.class);

                        if (!TextUtils.isEmpty(phnumber)) {
                            intent.putExtra("phonenumber", phnumber);
                        }

                        if (!TextUtils.isEmpty(name)) {
                            intent.putExtra("Name", name);
                        }
                        intent.putExtra("Callstatus", callstatus);

                        context.startService(intent);

                        isrecording = true;
                    }

                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    Log.i(LOG_TAG, "onCallStateChanged: CALL_STATE_OFFHOOK");

                    // Toast.makeText(context, "onCallStateChanged: CALL_STATE_OFFHOOK", Toast.LENGTH_SHORT).show();

                    if (!isrecording) {

                        if (!TextUtils.isEmpty(phnumber_outgoing)) {

                            String[] projection1 = new String[]{
                                    ContactsContract.PhoneLookup.DISPLAY_NAME,
                                    ContactsContract.PhoneLookup.NUMBER,
                                    ContactsContract.PhoneLookup.HAS_PHONE_NUMBER};

                            // encode the phone number and build the filter URIl
                            Uri contactUri1 = Uri.withAppendedPath(
                                    ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
                                    Uri.encode(phnumber_outgoing));

                            // query time
                            Cursor cursor1 = context.getContentResolver().query(contactUri1,
                                    projection1, null, null, null);

                            if (cursor1.moveToFirst()) {
                                name = cursor1.getString(cursor1
                                        .getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
                                //  Toast.makeText(context, "name:" + name, Toast.LENGTH_SHORT).show();
                            }

                            intent = new Intent(context, RecordService.class);


                            intent.putExtra("phonenumber", phnumber_outgoing);


                            if (!TextUtils.isEmpty(name)) {
                                intent.putExtra("Name", name);
                            }

                            intent.putExtra("Callstatus", callstatus);

                            isrecording = true;
                            context.startService(intent);

                        }

                    }


                    break;
                default:
                    Log.i(LOG_TAG, "UNKNOWN_STATE: " + state);
                    break;
            }
        }
    }


    public void shownotification(Context context, String message) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);

        is_blocked=true;

        mBuilder.setSmallIcon(R.drawable.ic_launcher);
        mBuilder.setContentTitle("Call Blocked");
        mBuilder.setContentText(message);

        Intent resultIntent = new Intent(context, SplashActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(SplashActivity.class);

// Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

// notificationID allows you to update the notification later on.
        mNotificationManager.notify(notificationID++, mBuilder.build());
    }


}
