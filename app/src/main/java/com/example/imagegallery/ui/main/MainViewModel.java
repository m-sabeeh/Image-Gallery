package com.example.imagegallery.ui.main;

import android.util.Log;

import com.example.imagegallery.api.PixabayApiService;
import com.example.imagegallery.models.Hit;
import com.example.imagegallery.repositories.HitRepository;
import com.example.imagegallery.repositories.PixabayDataSourceFactory;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

public class MainViewModel extends ViewModel {
    private static final String TAG = "MainViewModel";
    private LiveData<PagedList<Hit>> liveData;
    private HitRepository mHitRepo;
    DataSource latestDataSource;
    public void initOnlineData() {
        if (liveData != null) {
            return;
        }
        Log.d(TAG, "initOnlineData: ViewModel");
        mHitRepo = HitRepository.getInstance(PixabayApiService.getPixabayApi());
        //mHitRepo.init();
        //liveData = mHitRepo.getLiveHitList();
        DataSource.Factory<String, Hit> factory = new PixabayDataSourceFactory(PixabayApiService.getPixabayApi());
        latestDataSource = factory.create();
        PagedList.Config config = new PagedList.Config.Builder()
                .setPageSize(30)
                .setInitialLoadSizeHint(50)
                .setPrefetchDistance(5)
                .build();
        liveData = new LivePagedListBuilder<>(factory, config).build();
    }

    public void invalidateDataSource() {
        Log.d(TAG, "invalidateDataSource: ViewModel");
        //mHitRepo.invalidate();
        latestDataSource.invalidate();
    }

    public void fetchRequiredData(String q) {
        mHitRepo.fetchRequestedData(q);
    }

    public LiveData<PagedList<Hit>> getLiveHitList() {
        Log.d(TAG, "getLiveHitList ViewModel: " +liveData +" "+ liveData.getValue());
        if (liveData == null) {
            initOnlineData();
        }
        return liveData;
    }
}
