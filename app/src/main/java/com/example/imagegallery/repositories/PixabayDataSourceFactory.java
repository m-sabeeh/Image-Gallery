package com.example.imagegallery.repositories;

import com.example.imagegallery.api.PixabayApiService;
import com.example.imagegallery.models.Hit;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

public class PixabayDataSourceFactory extends DataSource.Factory<String, Hit> {
    private PixabayApiService.PixabayApi api;
    private MutableLiveData<PixabayDataSource> dataSourceLiveData = new MutableLiveData<>();

    public PixabayDataSourceFactory(PixabayApiService.PixabayApi pixabayApi) {
        this.api = pixabayApi;
    }

    @NonNull
    @Override
    public DataSource<String, Hit> create() {
        PixabayDataSource latestDataSource = new PixabayDataSource(api);
        dataSourceLiveData.postValue(latestDataSource);
        return latestDataSource;
    }

}
