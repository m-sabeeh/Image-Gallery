package com.example.imagegallery.repositories;

import android.util.Log;

import com.example.imagegallery.models.DataAPI;
import com.example.imagegallery.models.DataList;
import com.example.imagegallery.models.Hit;
import com.example.imagegallery.ui.adapters.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HitRepository {
    private static final String TAG = "HitRepository";
    private static HitRepository mHitRepository;
    private MutableLiveData<List<Hit>> mMutableHits;
    private RepoUpdateObserver mUpdateObserver;

    public static HitRepository getInstance() {
        if (mHitRepository == null) {
            mHitRepository = new HitRepository();
        }

        return mHitRepository;
    }


    public MutableLiveData<List<Hit>> getHits() {
        if (mMutableHits == null) {
            init();
        }
        return mMutableHits;
    }

    public void setUpdateObserver(RepoUpdateObserver observer) {
        mUpdateObserver = observer;
    }


    private void updateRepo(List<Hit> newHits) {
        List<Hit> hitList = mMutableHits.getValue();
        hitList.addAll(newHits);
        mMutableHits.setValue(hitList);
    }


    public void init() {
        mMutableHits = new MutableLiveData<>();
        List<Hit> hitList = new ArrayList<>();
        mMutableHits.setValue(hitList);
        Log.d(TAG, "init: " + mMutableHits.getValue());
        initOnline2();
        initOnline();
    }

    private void initOnline2() {
        Hit h1 = new Hit();
        h1.setPreviewURL("https://c1.staticflickr.com/5/4636/25316407448_de5fbf183d_o.jpg");
        mMutableHits.getValue().add(h1);
    }

    private void initOnline() {
        Call<DataList> dataListCall = DataAPI.getDataService().getDataList();
        dataListCall.enqueue(new Callback<DataList>() {
            @Override
            public void onResponse(Call<DataList> call, Response<DataList> response) {

                updateRepo(response.body().getHits());

                if (mUpdateObserver != null) {
                    mUpdateObserver.repoUpdated();
                }
            }

            @Override
            public void onFailure(Call<DataList> call, Throwable t) {

            }
        });
    }

    private MutableLiveData<List<RecyclerViewAdapter.Data>> initImageData2() {
        MutableLiveData<List<RecyclerViewAdapter.Data>> listMutableLiveData = new MutableLiveData<>();
        List<RecyclerViewAdapter.Data> dataList = new ArrayList<>();

        dataList.add(new RecyclerViewAdapter.Data("https://s3-us-west-1.amazonaws.com/spruce-eng-challenge-photos/images.001.png", "Havasu Falls"));
        dataList.add(new RecyclerViewAdapter.Data("https://s3-us-west-1.amazonaws.com/spruce-eng-challenge-photos/images.002.png", "Trondheim"));
        dataList.add(new RecyclerViewAdapter.Data("https://s3-us-west-1.amazonaws.com/spruce-eng-challenge-photos/images.003.png", "Portugal"));
        dataList.add(new RecyclerViewAdapter.Data("https://s3-us-west-1.amazonaws.com/spruce-eng-challenge-photos/images.004.png", "Rocky Mountain National Park"));
        dataList.add(new RecyclerViewAdapter.Data("https://s3-us-west-1.amazonaws.com/spruce-eng-challenge-photos/images.005.png", "Mahahual"));
        dataList.add(new RecyclerViewAdapter.Data("https://s3-us-west-1.amazonaws.com/spruce-eng-challenge-photos/images.006.png", "Frozen Lake"));
        dataList.add(new RecyclerViewAdapter.Data("https://s3-us-west-1.amazonaws.com/spruce-eng-challenge-photos/images.007.png", "White Sands Desert"));
        dataList.add(new RecyclerViewAdapter.Data("https://s3-us-west-1.amazonaws.com/spruce-eng-challenge-photos/images.008.png", "Austrailia"));
        dataList.add(new RecyclerViewAdapter.Data("https://s3-us-west-1.amazonaws.com/spruce-eng-challenge-photos/images.009.png", "Washington"));
        dataList.add(new RecyclerViewAdapter.Data("https://s3-us-west-1.amazonaws.com/spruce-eng-challenge-photos/images.010.png", "Washington"));
        dataList.add(new RecyclerViewAdapter.Data("https://s3-us-west-1.amazonaws.com/spruce-eng-challenge-photos/images.011.png", "Washington"));
        dataList.add(new RecyclerViewAdapter.Data("https://s3-us-west-1.amazonaws.com/spruce-eng-challenge-photos/images.012.png", "Washington"));
        dataList.add(new RecyclerViewAdapter.Data("https://s3-us-west-1.amazonaws.com/spruce-eng-challenge-photos/images.013.png", "Washington"));
        dataList.add(new RecyclerViewAdapter.Data("https://s3-us-west-1.amazonaws.com/spruce-eng-challenge-photos/images.014.png", "Washington"));
        dataList.add(new RecyclerViewAdapter.Data("https://s3-us-west-1.amazonaws.com/spruce-eng-challenge-photos/images.015.png", "Washington"));
        dataList.add(new RecyclerViewAdapter.Data("https://s3-us-west-1.amazonaws.com/spruce-eng-challenge-photos/images.016.png", "Washington"));
        dataList.add(new RecyclerViewAdapter.Data("https://s3-us-west-1.amazonaws.com/spruce-eng-challenge-photos/images.017.png", "Washington"));
        dataList.add(new RecyclerViewAdapter.Data("https://s3-us-west-1.amazonaws.com/spruce-eng-challenge-photos/images.018.png", "Washington"));
        dataList.add(new RecyclerViewAdapter.Data("https://s3-us-west-1.amazonaws.com/spruce-eng-challenge-photos/images.019.png", "Washington"));
        dataList.add(new RecyclerViewAdapter.Data("https://s3-us-west-1.amazonaws.com/spruce-eng-challenge-photos/images.020.png", "Washington"));

        listMutableLiveData.setValue(dataList);
        return listMutableLiveData;
    }

    public MutableLiveData<List<RecyclerViewAdapter.Data>> initImageData() {
        MutableLiveData<List<RecyclerViewAdapter.Data>> listMutableLiveData = new MutableLiveData<>();
        List<RecyclerViewAdapter.Data> dataList = new ArrayList<>();

        dataList.add(new RecyclerViewAdapter.Data("https://c1.staticflickr.com/5/4636/25316407448_de5fbf183d_o.jpg", "Havasu Falls"));
        dataList.add(new RecyclerViewAdapter.Data("https://i.redd.it/tpsnoz5bzo501.jpg", "Trondheim"));
        dataList.add(new RecyclerViewAdapter.Data("https://i.redd.it/qn7f9oqu7o501.jpg", "Portugal"));
        dataList.add(new RecyclerViewAdapter.Data("https://i.redd.it/j6myfqglup501.jpg", "Rocky Mountain National Park"));
        dataList.add(new RecyclerViewAdapter.Data("https://i.redd.it/0h2gm1ix6p501.jpg", "Mahahual"));
        dataList.add(new RecyclerViewAdapter.Data("https://i.redd.it/k98uzl68eh501.jpg", "Frozen Lake"));
        dataList.add(new RecyclerViewAdapter.Data("https://i.redd.it/glin0nwndo501.jpg", "White Sands Desert"));
        dataList.add(new RecyclerViewAdapter.Data("https://i.redd.it/obx4zydshg601.jpg", "Austrailia"));
        dataList.add(new RecyclerViewAdapter.Data("https://i.imgur.com/ZcLLrkY.jpg", "Washington"));

        listMutableLiveData.setValue(dataList);
        return listMutableLiveData;
    }

    public interface RepoUpdateObserver {
        void repoUpdated();
    }
}
