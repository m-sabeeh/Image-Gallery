package com.example.imagegallery.ui.main;

import android.util.Log;

import com.example.imagegallery.api.PixabayApiService;
import com.example.imagegallery.models.Hit;
import com.example.imagegallery.repositories.HitRepository;
import com.example.imagegallery.repositories.PixabayDataSourceFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

public class MainViewModel extends ViewModel {
    private static final String TAG = "MainViewModel";
    private LiveData<PagedList<Hit>> mLiveData;// = new MutableLiveData<>();
    private HitRepository mHitRepo;
    DataSource latestDataSource;
    private Executor executor;
    private MutableLiveData<String> searchQuery = new MutableLiveData<>();

    public void initOnlineData() {
        if (mLiveData != null) {
            return;
        }
        Log.d(TAG, "initOnlineData: ViewModel");
        mHitRepo = HitRepository.getInstance(PixabayApiService.getPixabayApi());

        //mHitRepo.init();
        //mLiveData = mHitRepo.getLiveHitList();

    }

    public void invalidateDataSource() {
        Log.d(TAG, "invalidateDataSource: ViewModel");
        //mHitRepo.invalidate();
        Log.d(TAG, "invalidateDataSource: "+latestDataSource.isInvalid());
        latestDataSource.invalidate();
        Log.d(TAG, "invalidateDataSource: "+latestDataSource.isInvalid());
    }

    public void fetchRequiredData(String q) {
        this.searchQuery.setValue(q);
        // mHitRepo.fetchRequestedData(q);
        LiveData<PagedList<Hit>> liveData;
        Log.d(TAG, "fetchRequiredData: " + q);
        executor = Executors.newFixedThreadPool(5);
        DataSource.Factory<Integer, Hit> factory = new PixabayDataSourceFactory(PixabayApiService.getPixabayApi(), q);
        latestDataSource = factory.create();
        PagedList.Config config = new PagedList.Config.Builder()
                .setPageSize(30)
                .setInitialLoadSizeHint(20)
                .setPrefetchDistance(20)
                .setEnablePlaceholders(true)
                .build();
        liveData = new LivePagedListBuilder<>(factory, config).setFetchExecutor(executor).build();

        mLiveData = liveData;
        /*mLiveData = Transformations.map(liveData, new Function<PagedList<Hit>, PagedList<Hit>>() {
            @Override
            public PagedList<Hit> apply(PagedList<Hit> input) {

                return input;
            }
        });*/

    }

    public LiveData<PagedList<Hit>> getLiveHitList(String q) {
        Log.d(TAG, "getLiveHitList ViewModel: " + mLiveData + " ");
        this.searchQuery.setValue(q);
        //if (mLiveData == null) {
        fetchRequiredData(searchQuery.getValue());
        //latestDataSource.invalidate();

        // }
        return mLiveData;
    }
}
