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
import com.example.imagegallery.ui.adapters.ViewPagerAdapter;
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
    ViewPagerAdapter mPagerAdapter;

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
        LiveData<PagedList<Hit>> liveData = mImageRepo.getLiveHitList();
        mPagerAdapter = new ViewPagerAdapter(getContext());
        mViewPager.setAdapter(mPagerAdapter);
        mPagerAdapter.submitList(liveData.getValue());
        int position = getArguments().getInt(Utils.IntentUtils.POSITION, 0);
        mViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                Log.d(TAG, "onPageSelected: "+position);
                Intent intent = new Intent();
                intent.putExtra(Utils.IntentUtils.RETURN_POSITION, position);
                requireActivity().setResult(Activity.RESULT_OK, intent);
            }
        });
        mViewPager.setCurrentItem(position, false);
        mViewPager.setOffscreenPageLimit(5);
        //setActivityTitle();
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
                Log.d(TAG, "onOptionsItemSelectedFragemnt: item Download");

                return true;
            case R.id.item_share:
                Log.d(TAG, "onOptionsItemSelectedFragemnt: item Share");
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

