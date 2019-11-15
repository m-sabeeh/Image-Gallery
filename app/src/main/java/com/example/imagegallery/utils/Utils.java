package com.example.imagegallery.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.imagegallery.ContainerActivity;
import com.example.imagegallery.R;
import com.example.imagegallery.ui.containerfragments.ImageFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

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
        public static final String IMAGE_FRAGMENT_CLASS = ImageFragment.class.getName();
        public static final String CONTAINER_ID = "Container ID";
        static final String ACTIVITY_TITLE = "Activity Title";


        public static void instantiateFragment(FragmentActivity activity, Intent intent) {
            Bundle bundle = intent.getExtras();
            String fragmentClass = bundle.getString(IMAGE_FRAGMENT_CLASS);
            int containerID = bundle.getInt(CONTAINER_ID, 0);

            Fragment fragment = activity.getSupportFragmentManager().getFragmentFactory().instantiate(activity.getClassLoader(), fragmentClass);

            fragment.setArguments(bundle);
            activity.getSupportFragmentManager().beginTransaction().add(containerID, fragment, fragmentClass).commit();
        }


        public static Intent buildImageFragmentIntent(Context context) {

            Intent intent = new Intent(context, ContainerActivity.class);
            intent.putExtra(IMAGE_FRAGMENT_CLASS, ImageFragment.class.getName());
            intent.putExtra(CONTAINER_ID, R.id.secondaryContainer);
            //intent.putExtra(ACTIVITY_TITLE, context.getString(R.string.imageFragmentActivityTitle));
    //<include layout="@layout/content_container" />


            return intent;

        }
    }

}
