package com.example.imagegallery.repositories;

import android.util.Log;

import com.example.imagegallery.api.PixabayApiService;
import com.example.imagegallery.models.Hit;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

public class PagedHitRepository implements HitRepository {
    private static final String TAG = "HitRepository";
    private static PagedHitRepository mHitRepository;
    private LiveData<PagedList<Hit>> mLiveData;
    private PixabayApiService pixabayApiService;
    DataSource<Integer, Hit> latestDataSource;


    public PagedHitRepository(PixabayApiService apiService) {
        Log.d(TAG, "HitRepository: Constructor " + apiService);
        pixabayApiService = apiService;
        //init();

    }

    public static PagedHitRepository getInstance(PixabayApiService pixabayApi) {
        if (mHitRepository == null) {
            mHitRepository = new PagedHitRepository(pixabayApi);
        }
        return mHitRepository;
    }

    @Override
    public LiveData<PagedList<Hit>> searchImages(String query, int page_size) {
        DataSource.Factory<Integer, Hit> factory = new PixabayDataSourceFactory(pixabayApiService, query);
        latestDataSource = factory.create();
        PagedList.Config config = initPagedListConfig(page_size);
        mLiveData = new LivePagedListBuilder<>(factory, config).build();
        return mLiveData;
    }

    private PagedList.Config initPagedListConfig(int pageSize) {
        return new PagedList.Config.Builder()
                .setPageSize(pageSize)
                .setInitialLoadSizeHint(100)
                .setPrefetchDistance(100)
                .build();
    }


    public LiveData<PagedList<Hit>> getLiveHitList() {
        Log.d(TAG, "getLiveHitList: HitRepo");
        if (mLiveData == null) {
            //init();
        }
        return mLiveData;
    }

    /*public void init() {
        //liveData = new MutableLiveData<>();
        //String s = "";

        DataSource.Factory<String, Hit> factory = new PixabayDataSourceFactory(pixabayApiService);
        latestDataSource = factory.create();
        PagedList.Config config = new PagedList.Config.Builder()
                .setPageSize(30)
                .setInitialLoadSizeHint(50)
                .setPrefetchDistance(5)
                .build();
        liveData = new LivePagedListBuilder<>(factory, config).build();

        //PagedList<Hit> hitList = new ArrayList<>();
        //liveData.setValue(hitList);
        Log.d(TAG, "init: HitRepo " + liveData.getValue());
        //fetchOnlineData();
    }*/

    private void addToRepo(List<Hit> newHits) {
        List<Hit> hitList = mLiveData.getValue();
        hitList.addAll(newHits);
        //liveData.setValue(hitList);
    }

    public void invalidate() {
        Log.d(TAG, "invalidate: Hitrepo" + latestDataSource.toString());
        latestDataSource.invalidate();
    }

    private void updateRepo(List<Hit> newHits) {
        Log.d(TAG, "updateRepo: ");
        List<Hit> hitList = mLiveData.getValue();
        hitList.clear();
        hitList.addAll(newHits);
        //liveData.setValue(hitList);
    }




    /*private void fetchOnlineData() {
        Log.d(TAG, "fetchOnlineData: ");
        Call<DataList> dataListCall = PixabayApiImp.getPixabayApi().searchImages("Abstract", 1);
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

    }*/

/*    public void fetchRequestedData(String q) {
        Log.d(TAG, "fetchRequestedData: ");
        Call<DataList> dataListCall = PixabayApiImp.getPixabayApi().searchImages(q,1);
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
                Log.d(TAG, "onFailure: " + t.getMessage());

            }
        });
    }*/
/*
    private MutableLiveData<List<Data>> initImageData2() {
        MutableLiveData<List<Data>> listMutableLiveData = new MutableLiveData<>();
        List<Data> dataList = new ArrayList<>();

        dataList.add(new Data("https://s3-us-west-1.amazonaws.com/spruce-eng-challenge-photos/images.001.png", "Havasu Falls"));
        dataList.add(new Data("https://s3-us-west-1.amazonaws.com/spruce-eng-challenge-photos/images.002.png", "Trondheim"));
        dataList.add(new Data("https://s3-us-west-1.amazonaws.com/spruce-eng-challenge-photos/images.003.png", "Portugal"));
        dataList.add(new Data("https://s3-us-west-1.amazonaws.com/spruce-eng-challenge-photos/images.004.png", "Rocky Mountain National Park"));
        dataList.add(new Data("https://s3-us-west-1.amazonaws.com/spruce-eng-challenge-photos/images.005.png", "Mahahual"));
        dataList.add(new Data("https://s3-us-west-1.amazonaws.com/spruce-eng-challenge-photos/images.006.png", "Frozen Lake"));
        dataList.add(new Data("https://s3-us-west-1.amazonaws.com/spruce-eng-challenge-photos/images.007.png", "White Sands Desert"));
        dataList.add(new Data("https://s3-us-west-1.amazonaws.com/spruce-eng-challenge-photos/images.008.png", "Austrailia"));
        dataList.add(new Data("https://s3-us-west-1.amazonaws.com/spruce-eng-challenge-photos/images.009.png", "Washington"));
        dataList.add(new Data("https://s3-us-west-1.amazonaws.com/spruce-eng-challenge-photos/images.010.png", "Washington"));
        dataList.add(new Data("https://s3-us-west-1.amazonaws.com/spruce-eng-challenge-photos/images.011.png", "Washington"));
        dataList.add(new Data("https://s3-us-west-1.amazonaws.com/spruce-eng-challenge-photos/images.012.png", "Washington"));
        dataList.add(new Data("https://s3-us-west-1.amazonaws.com/spruce-eng-challenge-photos/images.013.png", "Washington"));
        dataList.add(new Data("https://s3-us-west-1.amazonaws.com/spruce-eng-challenge-photos/images.014.png", "Washington"));
        dataList.add(new Data("https://s3-us-west-1.amazonaws.com/spruce-eng-challenge-photos/images.015.png", "Washington"));
        dataList.add(new Data("https://s3-us-west-1.amazonaws.com/spruce-eng-challenge-photos/images.016.png", "Washington"));
        dataList.add(new Data("https://s3-us-west-1.amazonaws.com/spruce-eng-challenge-photos/images.017.png", "Washington"));
        dataList.add(new Data("https://s3-us-west-1.amazonaws.com/spruce-eng-challenge-photos/images.018.png", "Washington"));
        dataList.add(new Data("https://s3-us-west-1.amazonaws.com/spruce-eng-challenge-photos/images.019.png", "Washington"));
        dataList.add(new Data("https://s3-us-west-1.amazonaws.com/spruce-eng-challenge-photos/images.020.png", "Washington"));

        listMutableLiveData.setValue(dataList);
        return listMutableLiveData;
    }

    public MutableLiveData<List<Data>> initImageData() {
        MutableLiveData<List<Data>> listMutableLiveData = new MutableLiveData<>();
        List<Data> dataList = new ArrayList<>();

        dataList.add(new Data("https://c1.staticflickr.com/5/4636/25316407448_de5fbf183d_o.jpg", "Havasu Falls"));
        dataList.add(new Data("https://i.redd.it/tpsnoz5bzo501.jpg", "Trondheim"));
        dataList.add(new Data("https://i.redd.it/qn7f9oqu7o501.jpg", "Portugal"));
        dataList.add(new Data("https://i.redd.it/j6myfqglup501.jpg", "Rocky Mountain National Park"));
        dataList.add(new Data("https://i.redd.it/0h2gm1ix6p501.jpg", "Mahahual"));
        dataList.add(new Data("https://i.redd.it/k98uzl68eh501.jpg", "Frozen Lake"));
        dataList.add(new Data("https://i.redd.it/glin0nwndo501.jpg", "White Sands Desert"));
        dataList.add(new Data("https://i.redd.it/obx4zydshg601.jpg", "Austrailia"));
        dataList.add(new Data("https://i.imgur.com/ZcLLrkY.jpg", "Washington"));

        listMutableLiveData.setValue(dataList);
        return listMutableLiveData;
    }

    public static class Data {
        private String imageUri;
        private String imageTile;

        public Data(String imageUri, String imageTile) {
            this.imageUri = imageUri;
            this.imageTile = imageTile;
        }

        public String getImageUri() {
            return imageUri;
        }

        public String getImageTile() {
            return imageTile;
        }
    }*/

}
