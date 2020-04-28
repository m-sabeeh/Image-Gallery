package com.example.imagegallery;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.imagegallery.ui.main.ImageListFragment;
import com.example.imagegallery.ui.main.MainEntryFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainEntryFragment.newInstance())
                    .commitNow();
        }
/*        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, ImageListFragment.newInstance())
                    .commitNow();
        }*/
    }
}
