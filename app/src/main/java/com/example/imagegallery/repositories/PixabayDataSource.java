package com.example.imagegallery.repositories;

import android.util.Log;

import com.example.imagegallery.api.PixabayApiService;
import com.example.imagegallery.models.DataList;
import com.example.imagegallery.models.Hit;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PixabayDataSource extends PageKeyedDataSource<String, Hit> {
    PixabayApiService.PixabayApi pixabayApi;
    private static final java.lang.String TAG = "PixabayDataSource";

    public PixabayDataSource(PixabayApiService.PixabayApi api) {
        pixabayApi = api;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull final LoadInitialCallback callback) {
        Log.d(TAG, "loadInitial: called"+params.toString());
        Call<DataList> dataListCall = pixabayApi.serchRepo("abstract");
        dataListCall.enqueue(new Callback<DataList>() {
            @Override
            public void onResponse(Call<DataList> call, Response<DataList> response) {
                if (response.body() != null && !response.body().getHits().isEmpty()) {
                    Log.d(TAG, "onResponse: DataSource");
                    List<Hit> hitList = response.body().getHits();
                    Log.d(TAG, "onResponse: "+hitList);

                    callback.onResult(hitList, null, null);
                    Log.d(TAG, "onResponse: "+callback.toString());
                    //updateRepo(response.body().getHits());
                }
            }

            @Override
            public void onFailure(Call<DataList> call, Throwable t) {
                Log.d(TAG, "onFailure: " + call + t.getMessage());
            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams params, @NonNull LoadCallback callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams params, @NonNull LoadCallback callback) {

    }
}
