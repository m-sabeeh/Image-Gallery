package com.example.imagegallery.repositories;

import android.util.Log;

import com.example.imagegallery.api.PixabayApiService;
import com.example.imagegallery.models.Hit;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

public class PixabayDataSourceFactory extends DataSource.Factory<Integer, Hit> {
    private PixabayApiService.PixabayApi api;
    private MutableLiveData<PixabayDataSource> dataSourceLiveData = new MutableLiveData<>();
    private String searchQuery;
    private static final String TAG = "PixabayDataSourceFactor";

    public PixabayDataSourceFactory(PixabayApiService.PixabayApi pixabayApi, String query) {
        Log.d(TAG, "PixabayDataSourceFactory: Constructor");
        this.api = pixabayApi;
        searchQuery = query;
    }

    @NonNull
    @Override
    public DataSource<Integer, Hit> create() {
        PixabayDataSource latestDataSource = new PixabayDataSource(api, searchQuery);
        dataSourceLiveData.postValue(latestDataSource);
        return latestDataSource;
    }

}
