package com.example.imagegallery;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.imagegallery.ui.main.MainFragment;
import com.example.imagegallery.utils.Utils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private int dataPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();
        }
        Log.d(TAG, "onCreate: mainacivity");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Integer.signum(dataPosition) == 1) {
            Log.d(TAG, "onResume: " + dataPosition);

        }

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Log.d(TAG, "onPostResume: ");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //Log.d(TAG, "onActivityResult: " + requestCode + " " + resultCode + " " + data.getIntExtra(Utils.IntentUtils.RETURN_POSITION, 0));
        //dataPosition = data.getIntExtra(Utils.IntentUtils.RETURN_POSITION, -1);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
