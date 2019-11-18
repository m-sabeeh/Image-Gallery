package com.example.imagegallery.repositories;

import com.example.imagegallery.api.PixabayApiService;
import com.example.imagegallery.models.Hit;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

public class PixabayDataSourceFactory extends DataSource.Factory<String, Hit> {
    private PixabayApiService.PixabayApi api;

    PixabayDataSourceFactory(PixabayApiService.PixabayApi pixabayApi) {
        this.api = pixabayApi;
    }

    @NonNull
    @Override
    public DataSource<String, Hit> create() {
        return new PixabayDataSource(api);
    }

}
