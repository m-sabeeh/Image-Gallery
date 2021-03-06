package com.example.imagegallery.network;

import com.example.imagegallery.models.DataList;

import retrofit2.Call;

public class PixabayApiService {
    private PixabayApi pixabayApi;

    public PixabayApiService(PixabayApi pixabayApi) {
        this.pixabayApi = pixabayApi;
    }

    public Call<DataList> searchImages(String query, int page, int per_page) {
        // TODO: 06/12/2019 improve this part
        return pixabayApi.searchImages(query, page, per_page, true, null);
    }
}

