package com.antony.automaticcallrecorder.views.Customviews;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Antony on 6/28/2017.
 */

public class CustomTextview extends AppCompatTextView {


    public CustomTextview(Context context) {
        super(context);



        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Light.ttf");
        this.setTypeface(font);

    }

    public CustomTextview(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Light.ttf");
        this.setTypeface(font);
    }

    public CustomTextview(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Light.ttf");
        this.setTypeface(font);


    }
}
