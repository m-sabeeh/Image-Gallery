package com.example.imagegallery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.TextView;

public class MyMotionActivity extends AppCompatActivity {
    private ConstraintSet constraintSet = new ConstraintSet();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        constraintSet.clone(this, R.layout.end_layout);

        setContentView(R.layout.start_layout);

        findViewById(R.id.imageView2).setBackgroundColor(getResources().getColor(R.color.colorAccent, getTheme()));
        ((TextView) findViewById(R.id.textView)).setText("hello world");
        startAnimation();
    }

    private void startAnimation() {
        ConstraintLayout constraintLayoutStart = findViewById(R.id.constraintLayoutStart);
        constraintLayoutStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionManager.beginDelayedTransition(constraintLayoutStart);
                constraintSet.applyTo(constraintLayoutStart);
            }
        });
    }
}
