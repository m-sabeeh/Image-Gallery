package com.example.imagegallery;

import android.os.Bundle;

import com.bumptech.glide.util.Util;
import com.example.imagegallery.Utils.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class ContainerActivity extends AppCompatActivity {
    private static final String TAG = "ContainerActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            finish();
            return;
        }
        if (!bundle.containsKey(Utils.IntentUtils.IMAGE_FRAGMENT_CLASS) || !bundle.containsKey(Utils.IntentUtils.CONTAINER_ID)){
            Log.e(TAG, "Intent should contain a bundle having fragment class name and container ID");
            finish();
            return;
        }

        if (savedInstanceState == null) {
            Utils.IntentUtils.instantiateFragment(this, getIntent());
        }

    }

}
