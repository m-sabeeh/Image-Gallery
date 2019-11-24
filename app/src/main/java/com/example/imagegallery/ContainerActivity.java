package com.example.imagegallery;

import android.os.Bundle;
import android.util.Log;

import com.example.imagegallery.utils.Utils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ContainerActivity extends AppCompatActivity {
    private static final String TAG = "ContainerActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            finish();
            return;
        }
        if (!bundle.containsKey(Utils.IntentUtils.FRAGMENT_CLASS) || !bundle.containsKey(Utils.IntentUtils.CONTAINER_ID)) {
            Log.e(TAG, "Intent should contain a bundle having fragment class name and container ID");
            finish();
            return;
        }

        if (savedInstanceState == null) {
            Utils.IntentUtils.instantiateFragment(this, getIntent());
        }
        setTitle("Photos");//should be set by the attached fragment, anyway leave it for now.
    }
}
