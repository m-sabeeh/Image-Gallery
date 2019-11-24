package com.example.imagegallery.factories;

import android.util.Log;

import com.example.imagegallery.api.PixabayApiService;
import com.example.imagegallery.models.Hit;
import com.example.imagegallery.repositories.PixabayDataSource;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

public class PixabayDataSourceFactory extends DataSource.Factory<Integer, Hit> {
    private PixabayApiService apiService;
    private MutableLiveData<PixabayDataSource> dataSourceLiveData = new MutableLiveData<>();
    private String searchQuery;
    private static final String TAG = "PixabayDataSourceFactor";

    public PixabayDataSourceFactory(PixabayApiService service, String query) {
        Log.d(TAG, "PixabayDataSourceFactory: Constructor");
        apiService = service;
        searchQuery = query;
    }

    @NonNull
    @Override
    public DataSource<Integer, Hit> create() {
        PixabayDataSource latestDataSource = new PixabayDataSource(apiService, searchQuery);
        //dataSourceLiveData.postValue(latestDataSource);
        return latestDataSource;
    }
}
