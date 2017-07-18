package com.antony.automaticcallrecorder.views.Customviews;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by Antony on 7/2/2017.
 */

public class CustomButton extends AppCompatButton {

    public CustomButton(Context context) {
        super(context);

        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Light.ttf");
        this.setTypeface(font);
    }

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Light.ttf");
        this.setTypeface(font);
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Light.ttf");
        this.setTypeface(font);
    }
}
