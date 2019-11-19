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

public class PixabayDataSource extends PageKeyedDataSource<Integer, Hit> {
    PixabayApiService.PixabayApi pixabayApi;
    private static final java.lang.String TAG = "PixabayDataSource";
    private List<Hit> hitList;
    private String searchQuery;

    public PixabayDataSource(PixabayApiService.PixabayApi api, String query) {
        Log.d(TAG, "PixabayDataSource: Constructor");
        pixabayApi = api;
        searchQuery = query;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, Hit> callback) {
        Log.d(TAG, "loadInitial: called" + params);
        int currentPage = 1;
        int nextPage = currentPage + 1;
        sendLoadInitialRequest(params, callback, currentPage, nextPage);
    }

    private void sendLoadInitialRequest(LoadInitialParams<Integer> params, final LoadInitialCallback<Integer, Hit> callback, int currentPage, final int nextPage) {
        Call<DataList> dataListCall = pixabayApi.serchRepo(searchQuery, currentPage, params.requestedLoadSize);
        dataListCall.enqueue(new Callback<DataList>() {
            @Override
            public void onResponse(Call<DataList> call, Response<DataList> response) {
                if (response.body() != null && !response.body().getHits().isEmpty()) {
                    Log.d(TAG, "onResponse: Load Initial");
                    hitList = response.body().getHits();
                    Log.d(TAG, "onResponse: " + hitList);

                    callback.onResult(hitList, null, nextPage);
                    //callback.onResult(hitList, null, null);

                    Log.d(TAG, "onResponse: " + callback.toString());
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
        Log.d(TAG, "loadBefore: ");

    }

    @Override
    public void loadAfter(@NonNull LoadParams params, @NonNull LoadCallback callback) {
        Log.d(TAG, "loadAfter: ");
        int currentPage = (int) params.key;
        int nextPage = currentPage + 1;

        sendLoadAfterRequest(params, callback, currentPage, nextPage);
    }

    private void sendLoadAfterRequest(LoadParams params, final LoadCallback callback, int currentPage, final int nextPage) {
        Call<DataList> dataListCall = pixabayApi.serchRepo(searchQuery, currentPage, params.requestedLoadSize);
        dataListCall.enqueue(new Callback<DataList>() {
            @Override
            public void onResponse(Call<DataList> call, Response<DataList> response) {
                Log.d(TAG, "onResponse: Load After");
                if (response.body() != null && !response.body().getHits().isEmpty()) {
                    hitList = response.body().getHits();
                    callback.onResult(hitList, nextPage);
                }
            }

            @Override
            public void onFailure(Call<DataList> call, Throwable t) {
                Log.d(TAG, "onFailure: " + call + t.getMessage());
            }
        });


    }
}