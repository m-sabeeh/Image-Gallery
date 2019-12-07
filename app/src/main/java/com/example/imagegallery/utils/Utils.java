package com.example.imagegallery.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.imagegallery.ContainerActivity;
import com.example.imagegallery.R;
import com.example.imagegallery.ui.containerfragments.ViewPagerFragment;

import java.util.Locale;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

public final class Utils {
    public static class IntentUtils {
        public static final String FRAGMENT_CLASS = ViewPagerFragment.class.getName();
        public static final String CONTAINER_ID = "Container ID";
        static final String ACTIVITY_TITLE = "Activity Title";




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

    public static class General {
        public static final String POSITION = "position";
        public static final int CODE_RETURN_POSITION = 100;
        public static final String RETURN_POSITION = "return position";
        public static final String SEARCH_TERM = "search";
        public static final String INTENT_PRACTICE = "intent practice";
        public static final int INTENT_PRACTICE_CODE = 200;

        public static String withSuffix(long count) {
            if (count < 1000) return "" + count;
            int exp = (int) (Math.log(count) / Math.log(1000));
            return String.format(Locale.getDefault(), "%.1f%c",
                    count / Math.pow(1000, exp),
                    "kMGTPE".charAt(exp - 1));
        }
    }
}
