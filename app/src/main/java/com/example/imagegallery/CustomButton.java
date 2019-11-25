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


    @ColorRes
    int backGroundTintA = 0;

    @ColorRes
    int strokeTintColor = 0;

    public CustomButton(Context context) {
        super(context);
    }

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public int getBackGroundTintA() {
        return backGroundTintA;
    }

    public int getStrokeTintColor() {
        return strokeTintColor;
    }

    public void setBackGroundTintA(int backGroundTintA) {
        this.backGroundTintA = backGroundTintA;
    }

    public void setStrokeTintColor(int strokeTintColor) {
        this.strokeTintColor = strokeTintColor;
    }
}
