package com.antony.automaticcallrecorder.Utilities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatDrawableManager;
import android.widget.ImageView;

import com.antony.automaticcallrecorder.R;

/**
 * Created by Antony on 6/28/2017.
 */

public class Apputils {


public static int last_tabposition=-1;

    public static int passwordlength=6;


    public static boolean is_Permissionenabled(String permission, Context context)
    {
        boolean a=false;

      if(ContextCompat.checkSelfPermission(context,permission)!= PackageManager.PERMISSION_GRANTED)
      {
          a=false;
      }
      else {
          a=true;
      }

      return a;



    }



    public static void show_Alert(Context context, String message, final Dialog_Eventlistener dialog_eventlistener)
    {

        AlertDialog.Builder builder=new AlertDialog.Builder(context);

        String app=context.getString(R.string.app_name);

        builder.setTitle(app);
        builder.setMessage(message);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

                if(dialog_eventlistener!=null)
                {
                    dialog_eventlistener.on_Positiveevent();
                }

            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
                if(dialog_eventlistener!=null)
                {
                    dialog_eventlistener.on_NegativeEvent();
                }
            }
        });

        builder.show();




    }






}
