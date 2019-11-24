package com.example.imagegallery.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.imagegallery.ContainerActivity;
import com.example.imagegallery.R;
import com.example.imagegallery.ui.containerfragments.ViewPagerFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

/**
 * This Activity allows the user to edit a note's title. It displays a floating window
 * containing an EditText.
 * <p>
 * NOTE: Notice that the provider operations in this Activity are taking place on the UI thread.
 * This is not a good practice. It is only done here to make the code more readable. A real
 * application should use the {@link android.content.AsyncQueryHandler}
 * or {@link android.os.AsyncTask} object to perform operations asynchronously on a separate thread.
 */
public class Utils {
    public static class IntentUtils {
        public static final String FRAGMENT_CLASS = ViewPagerFragment.class.getName();
        public static final String CONTAINER_ID = "Container ID";
        static final String ACTIVITY_TITLE = "Activity Title";
        public static final String POSITION = "position";
        public static final int CODE_RETURN_POSITION = 100;
        public static final String RETURN_POSITION = "return position";


        public static void instantiateFragment(FragmentActivity activity, Intent intent) {
            Bundle bundle = intent.getExtras();
            String fragmentClass = bundle.getString(FRAGMENT_CLASS);
            int containerID = bundle.getInt(CONTAINER_ID, 0);
            FragmentManager fragmentManager = activity.getSupportFragmentManager();
            Fragment fragment = fragmentManager.getFragmentFactory().
                    instantiate(activity.getClassLoader(), fragmentClass);

            fragment.setArguments(bundle);
            fragmentManager.beginTransaction().add(containerID, fragment, fragmentClass).commit();
        }


        public static Intent buildImageFragmentIntent(Context context) {
            Intent intent = new Intent(context, ContainerActivity.class);
            intent.putExtra(FRAGMENT_CLASS, ViewPagerFragment.class.getName());
            intent.putExtra(CONTAINER_ID, R.id.secondaryContainer);
            //intent.putExtra(ACTIVITY_TITLE, context.getString(R.string.imageFragmentActivityTitle));
            return intent;
        }
    }
}
