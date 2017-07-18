package com.antony.automaticcallrecorder.activities;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.antony.automaticcallrecorder.R;
import com.antony.automaticcallrecorder.Utilities.Apputils;
import com.antony.automaticcallrecorder.Utilities.Dialog_Eventlistener;
import com.antony.automaticcallrecorder.app.CallRecorder_App;
import com.antony.automaticcallrecorder.data.Pojo.Blockednumber;
import com.antony.automaticcallrecorder.data.Pojo.Call_Log;
import com.antony.automaticcallrecorder.data.Pojo.Lockednumber;
import com.antony.automaticcallrecorder.recycler_Adapter.CallLogAdapter;
import com.antony.automaticcallrecorder.views.Customviews.CustomButton;
import com.antony.automaticcallrecorder.views.Customviews.CustomEdittext;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class LockpasswordActivity extends AppCompatActivity {


    @InjectView(R.id.imageButton_pickcontact)
    ImageButton imageButton_pickcontact;

    @InjectView(R.id.editText)
    CustomEdittext editText_phone;

    @InjectView(R.id.editText_name)
    CustomEdittext editText_name;


    @InjectView(R.id.editTextpassword)
    CustomEdittext editTextpassword;

    @InjectView(R.id.editText_confirm_password)
    CustomEdittext editText_confirm_password;

    @InjectView(R.id.button_addintoblockedlist)
    CustomButton button_addintoblockedlist;


    public static final int callog = 11;

    public static final int contact = 111;

    Context context;

    CallRecorder_App callRecorder_app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lockpassword);

        context = LockpasswordActivity.this;

        callRecorder_app=(CallRecorder_App) context.getApplicationContext();

        ButterKnife.inject(LockpasswordActivity.this);
    }


    @OnClick(R.id.button_addintoblockedlist)
    public void add_into_blocklist() {


        if (!TextUtils.isEmpty(editText_phone.getText().toString())) {


            if (!TextUtils.isEmpty(editTextpassword.getText().toString())) {

                if (!TextUtils.isEmpty(editText_confirm_password.getText().toString())) {


                    if (editTextpassword.getText().toString().trim().length() >= 6)

                    {

                        if (editTextpassword.getText().toString().trim().equals(editText_confirm_password.getText().toString().trim()))

                        {


                            Apputils.show_Alert(context, "You must remember this password for unlocking i", new Dialog_Eventlistener() {
                                @Override
                                public void on_Positiveevent() {




                                    Lockednumber lockednumber=new Lockednumber();
                                    lockednumber.setName(editText_name.getText().toString());
                                    lockednumber.setNumber(editText_phone.getText().toString());
                                    lockednumber.setPassword(editTextpassword.getText().toString());


                                    callRecorder_app.getDatabaseHelper().addLockednumber(lockednumber);

                                    editText_phone.setText("");
                                    editText_name.setText("");
                                    editTextpassword.setText("");
                                    editText_confirm_password.setText("");



                                }

                                @Override
                                public void on_NegativeEvent() {

                                }
                            });

                        } else {


                            Apputils.show_Alert(context, "password confirmation failed", null);


                        }


                    } else {


                        Apputils.show_Alert(context, "password must have atleast 6 characters", null);


                    }


                } else {


                    Apputils.show_Alert(context, "please cofirm your password", null);


                }


            } else {


                Apputils.show_Alert(context, "please enter password", null);


            }


        } else {

            Apputils.show_Alert(context, "please enter phone number", new Dialog_Eventlistener() {
                @Override
                public void on_Positiveevent() {


                }

                @Override
                public void on_NegativeEvent() {

                }
            });

        }


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(context, MainActivity.class));

        finish();





    }

    @OnClick(R.id.imageButton_pickcontact)
    public void click_topickcontact() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);

        // Set the alert dialog title
        builder.setTitle("Pick number From");

        // Initializing an array of flowers
        final String[] flowers = new String[]{
                "Call Log",
                "Contact list",

        };

        builder.setSingleChoiceItems(flowers, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();


                if (flowers[which].equals("Call Log")) {

                    if (Apputils.is_Permissionenabled(Manifest.permission.READ_CALL_LOG, context)) {


                        show_Call_Logs();

                    } else {

                        ActivityCompat.requestPermissions(LockpasswordActivity.this, new String[]{Manifest.permission.READ_CALL_LOG}, callog);

                    }
                } else {
                    if (Apputils.is_Permissionenabled(Manifest.permission.READ_CONTACTS, LockpasswordActivity.this)) {

                        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                        startActivityForResult(contactPickerIntent, contact);


                    } else {

                        ActivityCompat.requestPermissions(LockpasswordActivity.this, new String[]{Manifest.permission.READ_CONTACTS}, contact);

                    }

                }


            }
        });

        builder.show();
    }

    public void show_Call_Logs() {

        final List<Call_Log> call_logs = new ArrayList<>();

        Cursor managedCursor = managedQuery(CallLog.Calls.CONTENT_URI, null,
                null, null, null);
        int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
        int name = managedCursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
        int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);

        while (managedCursor.moveToNext()) {
            String phNumber = managedCursor.getString(number);
            String callType = managedCursor.getString(type);
            String callDate = managedCursor.getString(date);

            String nam = managedCursor.getString(name);
            String callDuration = managedCursor.getString(duration);
            int dir = 0;
            int dircode = Integer.parseInt(callType);

            switch (dircode) {

                case CallLog.Calls.OUTGOING_TYPE:
                    dir = 1;
                    break;

                case CallLog.Calls.INCOMING_TYPE:
                    dir = 2;
                    break;

                case CallLog.Calls.MISSED_TYPE:
                    dir = 3;
                    break;
            }

            Call_Log call_log = new Call_Log(nam, phNumber, dir);
            call_logs.add(call_log);


        }


        managedCursor.close();


        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.layout_calllist);
        dialog.setTitle("Call Log");
        ListView listView = (ListView) dialog.findViewById(R.id.list_callLogs);

        if (call_logs.size() > 0) {

            CallLogAdapter callLogAdapter = new CallLogAdapter(context, call_logs);

            listView.setAdapter(callLogAdapter);


        }


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                dialog.dismiss();
                Call_Log call_log = call_logs.get(position);


                if (!TextUtils.isEmpty(call_log.getName())) {
                    editText_name.setText(call_log.getName());

                } else {
                    editText_name.setText("");
                }

                editText_phone.setText(call_log.getPhno());
            }
        });


        dialog.show();


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == callog) {

            if (grantResults.length > 0 || grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                show_Call_Logs();
            }


        }
        if (requestCode == contact) {

            if (grantResults.length > 0 || grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(contactPickerIntent, contact);
            }

        }


    }


    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if (reqCode == contact) {
            if (resultCode == RESULT_OK && data != null) {

                try {


                    Uri uri = data.getData();
                    String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME};

                    Cursor cursor = context.getContentResolver().query(uri, projection,
                            null, null, null);
                    cursor.moveToFirst();

                    int numberColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    String number = cursor.getString(numberColumnIndex);
                    if (!TextUtils.isEmpty(number)) {
                        editText_phone.setText(number);
                    }

                    int nameColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                    String name = cursor.getString(nameColumnIndex);
                    if (!TextUtils.isEmpty(name)) {
                        editText_name.setText(name);
                    }


                    cursor.close();

                } catch (Exception e) {

                }

            }
        }
    }
}
