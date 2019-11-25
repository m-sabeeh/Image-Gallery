package com.example.imagegallery;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import androidx.annotation.ColorRes;

/**
 * This Activity allows the user to edit a note's title. It displays a floating window
 * containing an EditText.
 * <p>
 * NOTE: Notice that the provider operations in this Activity are taking place on the UI thread.
 * This is not a good practice. It is only done here to make the code more readable. A real
 * application should use the {@link android.content.AsyncQueryHandler}
 * or {@link android.os.AsyncTask} object to perform operations asynchronously on a separate thread.
 */
public class CustomButton extends Button {


    String backGroundTintA = "0";


    String strokeTintColor = "0";

    public String getBackGroundTintA() {
        return backGroundTintA;
    }

    public String getStrokeTintColor() {
        return strokeTintColor;
    }

    public void setBackGroundTintA(String backGroundTintA) {
        this.backGroundTintA = backGroundTintA;
    }

    public void setStrokeTintColor(String strokeTintColor) {
        this.strokeTintColor = strokeTintColor;
    }

    public void setAnInt(String anInt) {
        this.anInt = anInt;
    }

    public String getAnInt() {
        return anInt;
    }

    String anInt = "0";



    public CustomButton(Context context) {
        super(context);
    }

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

}
