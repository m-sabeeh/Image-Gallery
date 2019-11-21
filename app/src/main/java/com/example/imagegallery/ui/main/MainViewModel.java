package com.example.imagegallery.ui.main;

import com.example.imagegallery.models.Hit;
import com.example.imagegallery.repositories.ImageRepository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

public class MainViewModel extends ViewModel {
    private static final String TAG = "MainViewModel";
    private LiveData<PagedList<Hit>> mLiveData;// = new MutableLiveData<>();
    private ImageRepository mImageRepo;
    private String mSearchTerm;
    public static final String DEFAULT_SEARCH_TERM = "Colors";
    public static final int page_size = 200;

    public MainViewModel(ImageRepository repository) {
        //mImageRepo = ImageRepository.getInstance();
        mImageRepo = repository;
        initializeSampleData();

    }

    public LiveData<PagedList<Hit>> getLiveHitList() {
        if (mLiveData == null) {
            initializeSampleData();
        }
        return mLiveData;
    }

    private void initializeSampleData() {
        mSearchTerm = DEFAULT_SEARCH_TERM;
        setSearchTerm(mSearchTerm);
    }

    public String getSearchTerm() {
        return mSearchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        mSearchTerm = searchTerm;
        MutableLiveData<String> searchTermLiveData = new MutableLiveData<>();
        searchTermLiveData.setValue(searchTerm);
        mLiveData = Transformations.switchMap(searchTermLiveData,
                (String input) -> mImageRepo.searchImages(input.toLowerCase(), page_size));
    }
}
