package com.example.imagegallery.ui.main;

import android.util.Log;

import com.example.imagegallery.models.DataAPI;
import com.example.imagegallery.models.DataList;
import com.example.imagegallery.models.Hit;
import com.example.imagegallery.repositories.HitRepository;
import com.example.imagegallery.ui.adapters.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {
    private static final String TAG = "MainViewModel";
    private MutableLiveData<List<Hit>> mHitList;
    private HitRepository mHitRepo;


    //private MutableLiveData<List<RecyclerViewAdapter.Data>> mutableLiveData;

    public void initOnlineData() {
        if (mHitList != null) {
            Log.d(TAG, "initOnlineData: ");
            return;
        }
        Log.d(TAG, "initOnlineData: ");
        mHitRepo = HitRepository.getInstance();
        mHitRepo.setUpdateObserver(new HitRepository.RepoUpdateObserver() {
            @Override
            public void repoUpdated() {
                updateList(mHitRepo.getHits().getValue());
            }
        });
        mHitRepo.init();
        mHitList = mHitRepo.getHits();
    }

    public void addNewValue(Hit hit) {
        List<Hit> hitList = mHitList.getValue();
        hitList.add(hit);
        mHitList.setValue(hitList);
    }

    public void updateList(List<Hit> hits) {
        Log.d(TAG, "updateList: ");
        List<Hit> hitList = mHitList.getValue();
        hitList.addAll(hits);
        mHitList.setValue(hitList);
    }


   /* public void initLocal() {
        if (mutableLiveData == null) {
            mutableLiveData = initImageData2();
        }
    }*/

/*
    public LiveData<List<RecyclerViewAdapter.Data>> getLocalData() {
        return mutableLiveData;
    }*/

    public LiveData<List<Hit>> getHitList() {
        Log.d(TAG, "getHitList: " + mHitList.getValue());
        return mHitList;
    }


}
