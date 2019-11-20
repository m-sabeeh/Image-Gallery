package com.example.imagegallery.ui.main;

import android.util.Log;

import com.example.imagegallery.models.Hit;
import com.example.imagegallery.repositories.PagedHitRepository;

import java.util.concurrent.Executor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.DataSource;
import androidx.paging.PagedList;

public class MainViewModel extends ViewModel {
    private static final String TAG = "MainViewModel";
    private LiveData<PagedList<Hit>> mLiveData;// = new MutableLiveData<>();
    private PagedHitRepository mHitRepo;
    private MutableLiveData<String> mSearchTermLiveData = new MutableLiveData<>();
    DataSource latestDataSource;
    private Executor executor;
    //private MutableLiveData<String> searchQuery = new MutableLiveData<>();
    public static final String DEFAULT_SEARCH_TERM = "Colors";
    public static final int page_size = 200;
    public MutableLiveData<String> searchQuery = new MutableLiveData<>();


    //map(searchQuery) {
    //repository.searchUsers(it, PAGE_SIZE)
    // }

    public MainViewModel(PagedHitRepository repository) {
        //mHitRepo = HitRepository.getInstance();
        mHitRepo = repository;
        initializeSampleData();

    }

    public LiveData<PagedList<Hit>> getLiveHitList() {
        Log.d(TAG, "getLiveHitList ViewModel: " + mLiveData + " ");

        if (mLiveData == null) {
            initializeSampleData();
        }
        return mLiveData;
    }

    private void initializeSampleData() {
        mSearchTermLiveData.setValue(DEFAULT_SEARCH_TERM);
        fetchRequiredData(DEFAULT_SEARCH_TERM.toLowerCase());
    }


    public MutableLiveData<String> getSearchTermLiveData() {
        return mSearchTermLiveData;
    }

    public void fetchRequiredData(String query) {
        searchQuery.setValue(query);
        // mHitRepo.fetchRequestedData(query);
        //LiveData<PagedList<Hit>> liveData;

        mLiveData = mHitRepo.searchImages(query, page_size);
        /*Log.d(TAG, "fetchRequiredData: " + query);
        DataSource.Factory<Integer, Hit> factory = new PixabayDataSourceFactory(, query);
        latestDataSource = factory.create();
        PagedList.Config config = new PagedList.Config.Builder()
                .setPageSize(200)
                .setInitialLoadSizeHint(100)
                .setPrefetchDistance(100)
                .build();
        mLiveData = new LivePagedListBuilder<>(factory, config).build();*/
    }

/*    public void initOnlineData() {
        if (mLiveData != null) {
            return;
        }
        Log.d(TAG, "initOnlineData: ViewModel");
        mHitRepo = PagedHitRepository.getInstance(PixabayApiImp.getPixabayApi());

        //mHitRepo.init();
        //mLiveData = mHitRepo.getLiveHitList();

    }*/
}
