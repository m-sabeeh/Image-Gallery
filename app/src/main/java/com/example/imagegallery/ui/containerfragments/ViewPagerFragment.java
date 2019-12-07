package com.example.imagegallery.ui.containerfragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.imagegallery.R;
import com.example.imagegallery.models.Hit;
import com.example.imagegallery.repositories.ImageRepository;
import com.example.imagegallery.repositories.PixabayPagedHitRepository;
import com.example.imagegallery.utils.Utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;
import androidx.viewpager2.widget.ViewPager2;

public class ViewPagerFragment extends Fragment {
    private static final String TAG = "ViewPagerFragment";
    private ViewPager2 mViewPager;
    private ViewPagerAdapter mPagerAdapter;
    private Intent intent = new Intent();
    private int mPosition;
    private LiveData<PagedList<Hit>> liveData;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_viewpager, container, false);
        mViewPager = view.findViewById(R.id.viewPager);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ImageRepository mImageRepo = PixabayPagedHitRepository.getInstance(null);//repository is already initialized
        // TODO: 3/12/2019 why not share the same viewmodel as mentioned in the link.
        //  The document describes calling the ViewModelProviders.of(same activity/fragment) with same
        //  activity or fragment will return the same viewmodel. but here I am in another activity.
        //  https://developer.android.com/topic/libraries/architecture/viewmodel.html#sharing
        //use the same livedata from repo for now, not liking this. I should be using the same
        //view model as described in the link for Master>Detail fragment type navigation. Consider
        //launching the viewPagerFragment directly from withing the MainFragment to implement this
        //approach.
        liveData = mImageRepo.getLiveHitList();
        mPagerAdapter = new ViewPagerAdapter();
        mViewPager.setAdapter(mPagerAdapter);
        mPagerAdapter.submitList(liveData.getValue());
        int position = getArguments().getInt(Utils.General.POSITION, 0);
        mViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mPosition = position;
                Log.d(TAG, "onPageSelected: " + position);
                intent.removeExtra(Utils.General.RETURN_POSITION);
                intent.putExtra(Utils.General.RETURN_POSITION, position);
                requireActivity().setResult(Activity.RESULT_OK, intent);
            }
        });
        mViewPager.setCurrentItem(position, false);
        mViewPager.setOffscreenPageLimit(5);
        Log.d(TAG, "onActivityCreated: " + getTargetFragment());
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.view_pager_toolbar_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_download:
                // TODO: 04/12/2019 implement download function.
                //Log.d(TAG, "onOptionsItemSelectedFragemnt: item Download " + liveData.getValue().get(mPosition).getLargeImageURL());
                intentPractice();
                return true;
            case R.id.item_share:
                shareUrl(liveData.getValue().get(mPosition).getLargeImageURL());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void shareUrl(String uri) {
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Beautiful Image");
        intent.putExtra(Intent.EXTRA_TEXT, uri);
        startActivity(Intent.createChooser(intent, "Share link"));
    }

    private void intentPractice() {//just for practicing intents
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        //intent.setType("vnd.android.cursor.item/phone");
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        startActivityForResult(Intent.createChooser(intent, "Share link"), Utils.General.INTENT_PRACTICE_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == Utils.General.INTENT_PRACTICE_CODE
                && resultCode == Activity.RESULT_OK
                && data != null) {
            Log.d(TAG, "onActivityResult: " + data.getData());
        }
    }
}

