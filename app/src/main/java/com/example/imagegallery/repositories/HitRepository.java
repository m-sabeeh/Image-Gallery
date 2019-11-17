package com.example.imagegallery.repositories;

import android.util.Log;

import com.example.imagegallery.models.DataService;
import com.example.imagegallery.models.DataList;
import com.example.imagegallery.models.Hit;
import com.example.imagegallery.ui.adapters.CustomListAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HitRepository {
    private static final String TAG = "HitRepository";
    private static HitRepository mHitRepository;
    private MutableLiveData<List<Hit>> mutableLiveData;

    public static HitRepository getInstance() {
        if (mHitRepository == null) {
            mHitRepository = new HitRepository();
        }
        return mHitRepository;
    }

    public LiveData<List<Hit>> getLiveHitList() {
        Log.d(TAG, "getLiveHitList: ");
        if (mutableLiveData == null) {
            init();
        }
        return mutableLiveData;
    }

    public void init() {
        mutableLiveData = new MutableLiveData<>();
        List<Hit> hitList = new ArrayList<>();
        mutableLiveData.setValue(hitList);
        Log.d(TAG, "init: " + mutableLiveData.getValue());
        fetchOnlineData();
    }

    private void addToRepo(List<Hit> newHits) {
        List<Hit> hitList = mutableLiveData.getValue();
        hitList.addAll(newHits);
        mutableLiveData.setValue(hitList);
    }

    private void updateRepo(List<Hit> newHits) {
        Log.d(TAG, "updateRepo: ");
        List<Hit> hitList = mutableLiveData.getValue();
        hitList.clear();
        hitList.addAll(newHits);
        mutableLiveData.setValue(hitList);
    }


    private void fetchOnlineData() {
        Log.d(TAG, "fetchOnlineData: ");
        Call<DataList> dataListCall = DataService.getPixabayAPIServiceInstance().getSearchedData("Abstract");
        dataListCall.enqueue(new Callback<DataList>() {
            @Override
            public void onResponse(Call<DataList> call, Response<DataList> response) {
                if (response.body() != null && !response.body().getHits().isEmpty()) {
                    Log.d(TAG, "onResponse: ");
                    updateRepo(response.body().getHits());
                }
            }

            @Override
            public void onFailure(Call<DataList> call, Throwable t) {
                Log.d(TAG, "onFailure: " + call + t.getMessage());
            }
        });

    }

    public void fetchRequestedData(String q) {
        Log.d(TAG, "fetchRequestedData: ");
        Call<DataList> dataListCall = DataService.getPixabayAPIServiceInstance().getSearchedData(q);
        dataListCall.enqueue(new Callback<DataList>() {
            @Override
            public void onResponse(Call<DataList> call, Response<DataList> response) {
                Log.d(TAG, "onResponse: " + response.body().getHits());
                if (response.body() != null && !response.body().getHits().isEmpty()) {
                    updateRepo(response.body().getHits());
                }
            }

            @Override
            public void onFailure(Call<DataList> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());

            }
        });
    }

    private MutableLiveData<List<CustomListAdapter.Data>> initImageData2() {
        MutableLiveData<List<CustomListAdapter.Data>> listMutableLiveData = new MutableLiveData<>();
        List<CustomListAdapter.Data> dataList = new ArrayList<>();

        dataList.add(new CustomListAdapter.Data("https://s3-us-west-1.amazonaws.com/spruce-eng-challenge-photos/images.001.png", "Havasu Falls"));
        dataList.add(new CustomListAdapter.Data("https://s3-us-west-1.amazonaws.com/spruce-eng-challenge-photos/images.002.png", "Trondheim"));
        dataList.add(new CustomListAdapter.Data("https://s3-us-west-1.amazonaws.com/spruce-eng-challenge-photos/images.003.png", "Portugal"));
        dataList.add(new CustomListAdapter.Data("https://s3-us-west-1.amazonaws.com/spruce-eng-challenge-photos/images.004.png", "Rocky Mountain National Park"));
        dataList.add(new CustomListAdapter.Data("https://s3-us-west-1.amazonaws.com/spruce-eng-challenge-photos/images.005.png", "Mahahual"));
        dataList.add(new CustomListAdapter.Data("https://s3-us-west-1.amazonaws.com/spruce-eng-challenge-photos/images.006.png", "Frozen Lake"));
        dataList.add(new CustomListAdapter.Data("https://s3-us-west-1.amazonaws.com/spruce-eng-challenge-photos/images.007.png", "White Sands Desert"));
        dataList.add(new CustomListAdapter.Data("https://s3-us-west-1.amazonaws.com/spruce-eng-challenge-photos/images.008.png", "Austrailia"));
        dataList.add(new CustomListAdapter.Data("https://s3-us-west-1.amazonaws.com/spruce-eng-challenge-photos/images.009.png", "Washington"));
        dataList.add(new CustomListAdapter.Data("https://s3-us-west-1.amazonaws.com/spruce-eng-challenge-photos/images.010.png", "Washington"));
        dataList.add(new CustomListAdapter.Data("https://s3-us-west-1.amazonaws.com/spruce-eng-challenge-photos/images.011.png", "Washington"));
        dataList.add(new CustomListAdapter.Data("https://s3-us-west-1.amazonaws.com/spruce-eng-challenge-photos/images.012.png", "Washington"));
        dataList.add(new CustomListAdapter.Data("https://s3-us-west-1.amazonaws.com/spruce-eng-challenge-photos/images.013.png", "Washington"));
        dataList.add(new CustomListAdapter.Data("https://s3-us-west-1.amazonaws.com/spruce-eng-challenge-photos/images.014.png", "Washington"));
        dataList.add(new CustomListAdapter.Data("https://s3-us-west-1.amazonaws.com/spruce-eng-challenge-photos/images.015.png", "Washington"));
        dataList.add(new CustomListAdapter.Data("https://s3-us-west-1.amazonaws.com/spruce-eng-challenge-photos/images.016.png", "Washington"));
        dataList.add(new CustomListAdapter.Data("https://s3-us-west-1.amazonaws.com/spruce-eng-challenge-photos/images.017.png", "Washington"));
        dataList.add(new CustomListAdapter.Data("https://s3-us-west-1.amazonaws.com/spruce-eng-challenge-photos/images.018.png", "Washington"));
        dataList.add(new CustomListAdapter.Data("https://s3-us-west-1.amazonaws.com/spruce-eng-challenge-photos/images.019.png", "Washington"));
        dataList.add(new CustomListAdapter.Data("https://s3-us-west-1.amazonaws.com/spruce-eng-challenge-photos/images.020.png", "Washington"));

        listMutableLiveData.setValue(dataList);
        return listMutableLiveData;
    }

    public MutableLiveData<List<CustomListAdapter.Data>> initImageData() {
        MutableLiveData<List<CustomListAdapter.Data>> listMutableLiveData = new MutableLiveData<>();
        List<CustomListAdapter.Data> dataList = new ArrayList<>();

        dataList.add(new CustomListAdapter.Data("https://c1.staticflickr.com/5/4636/25316407448_de5fbf183d_o.jpg", "Havasu Falls"));
        dataList.add(new CustomListAdapter.Data("https://i.redd.it/tpsnoz5bzo501.jpg", "Trondheim"));
        dataList.add(new CustomListAdapter.Data("https://i.redd.it/qn7f9oqu7o501.jpg", "Portugal"));
        dataList.add(new CustomListAdapter.Data("https://i.redd.it/j6myfqglup501.jpg", "Rocky Mountain National Park"));
        dataList.add(new CustomListAdapter.Data("https://i.redd.it/0h2gm1ix6p501.jpg", "Mahahual"));
        dataList.add(new CustomListAdapter.Data("https://i.redd.it/k98uzl68eh501.jpg", "Frozen Lake"));
        dataList.add(new CustomListAdapter.Data("https://i.redd.it/glin0nwndo501.jpg", "White Sands Desert"));
        dataList.add(new CustomListAdapter.Data("https://i.redd.it/obx4zydshg601.jpg", "Austrailia"));
        dataList.add(new CustomListAdapter.Data("https://i.imgur.com/ZcLLrkY.jpg", "Washington"));

        listMutableLiveData.setValue(dataList);
        return listMutableLiveData;
    }

}
