package com.example.imagegallery.ui.containerfragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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
    private ViewPagerAdapter mAdapter;
    public LiveData<PagedList<Hit>> liveData;
    private ViewPager2 viewPager;
    private int dataPosition;
    private static final String TAG = "ViewPagerFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_viewpager, container, false);
        viewPager = view.findViewById(R.id.viewPager);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ImageRepository mImageRepo = PixabayPagedHitRepository.getInstance(null);
        liveData = mImageRepo.getLiveHitList();
        dataPosition = getArguments().getInt(Utils.IntentUtils.POSITION);
        //mAdapter = new MyAdapter(getActivity().getSupportFragmentManager(),dataPosition);
        mAdapter = new ViewPagerAdapter(getContext());
        viewPager.setAdapter(mAdapter);
        mAdapter.submitList(liveData.getValue());
        int position = getArguments().getInt(Utils.IntentUtils.POSITION, 0);
        viewPager.setCurrentItem(position, false);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            /*@Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                Log.d(TAG, "onPageScrolled: " + " " + position + " " + positionOffset + " " + positionOffsetPixels);
            }*/

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                Log.d(TAG, "onPageSelected: " + position);
                Intent intent = new Intent();
                intent.putExtra(Utils.IntentUtils.RETURN_POSITION, position);
                requireActivity().setResult(Utils.IntentUtils.RETURN_POSITION_CODE, intent);
            }

          /*  @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                Log.d(TAG, "onPageScrollStateChanged: " + state);
            }*/
        });
        viewPager.setOffscreenPageLimit(5);
        //  mViewModel = initViewModel();
        // initAdapter();
        // initFab();
        // initLiveDataObservations();
        //setActivityTitle();
    }


}

