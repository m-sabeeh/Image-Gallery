package com.example.imagegallery.ui.main;

import android.util.Log;

import com.example.imagegallery.ui.adapters.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    private static final String TAG = "MainViewModel";

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d(TAG, "onCleared: ");
    }

    public List<RecyclerViewAdapter.Data> getDataList() {
        return initImageData2();
    }

    public List<RecyclerViewAdapter.Data> initImageData() {
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

        return dataList;
    }

    public List<RecyclerViewAdapter.Data> initImageData2() {
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

        return dataList;
    }


}
