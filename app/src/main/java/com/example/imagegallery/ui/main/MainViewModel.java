package com.example.imagegallery.ui.main;

import android.util.Log;

import com.example.imagegallery.models.Hit;
import com.example.imagegallery.repositories.ImageRepository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

public class MainViewModel extends ViewModel {
    private static final String TAG = "MainViewModel";
    private LiveData<PagedList<Hit>> mLiveData;
    private ImageRepository mImageRepo;
    private String mSearchTerm;
    private static final String DEFAULT_SEARCH_TERM = "Colors";
    private static final int page_size = 200;

    public MainViewModel(ImageRepository repository) {
        //mImageRepo = ImageRepository.getInstance();
        mImageRepo = repository;
        initializeSampleData();

    }

    public LiveData<PagedList<Hit>> getLiveHitList() {
        Log.d(TAG, "getLiveHitList: " + mLiveData);
        if (mLiveData == null) {
            Log.d(TAG, "getLiveHitList: Null");
            initializeSampleData();
        }
        return mLiveData;
    }

    private void initializeSampleData() {
        mSearchTerm = DEFAULT_SEARCH_TERM;
        setSearchTerm(mSearchTerm);
    }

    public String getSearchTerm() {
        if (mSearchTerm == null)
            mSearchTerm = DEFAULT_SEARCH_TERM;
        return mSearchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        mSearchTerm = searchTerm;
        MutableLiveData<String> searchTermLiveData = new MutableLiveData<>();
        searchTermLiveData.setValue(searchTerm);
        mLiveData = Transformations.switchMap(searchTermLiveData,
                (String input) -> mImageRepo.searchImages(input.toLowerCase(), page_size));
    }

    public void invalidateSource() {
        mImageRepo.invalidateDataSource();
    }
}
