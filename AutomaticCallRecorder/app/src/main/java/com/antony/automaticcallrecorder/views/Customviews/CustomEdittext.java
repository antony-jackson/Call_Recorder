package com.antony.automaticcallrecorder.views.Customviews;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by Antony on 7/2/2017.
 */

public class CustomEdittext extends AppCompatEditText {

    public CustomEdittext(Context context) {
        super(context);
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Light.ttf");
        this.setTypeface(font);
    }

    public CustomEdittext(Context context, AttributeSet attrs) {
        super(context, attrs);

        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Light.ttf");
        this.setTypeface(font);
    }

    public CustomEdittext(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Light.ttf");
        this.setTypeface(font);
    }
}
