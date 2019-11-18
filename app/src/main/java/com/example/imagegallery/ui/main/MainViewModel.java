package com.example.imagegallery.ui.main;

import android.util.Log;

import com.example.imagegallery.api.PixabayApiService;
import com.example.imagegallery.models.Hit;
import com.example.imagegallery.repositories.HitRepository;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

public class MainViewModel extends ViewModel {
    private static final String TAG = "MainViewModel";
    private LiveData<PagedList<Hit>> liveData;
    private HitRepository mHitRepo;

    public void initOnlineData() {
        if (liveData != null) {
            return;
        }
        Log.d(TAG, "initOnlineData: ViewModel");
        mHitRepo = HitRepository.getInstance(PixabayApiService.getPixabayApi());
        mHitRepo.init();
        liveData = mHitRepo.getLiveHitList();
    }

    public void fetchRequiredData(String q) {
        mHitRepo.fetchRequestedData(q);
    }

    public LiveData<PagedList<Hit>> getLiveHitList() {
        Log.d(TAG, "getLiveHitList ViewModel: " + liveData.getValue());
        if (liveData == null) {
            initOnlineData();
        }
        return liveData;
    }
}
