package com.example.imagegallery.ui.main;

import android.util.Log;

import com.example.imagegallery.models.Hit;
import com.example.imagegallery.repositories.ImageRepository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

public class MainViewModel extends ViewModel {
    private static final String TAG = "MainViewModel";
    private ImageRepository mImageRepo;
    private static final String DEFAULT_SEARCH_TERM = "Colors";
    private static final int page_size = 200;

    private MutableLiveData<String> mutableSearchTerm = new MutableLiveData<>();
    LiveData<String> searchTermLiveData = Transformations.map(mutableSearchTerm, input -> input);
    LiveData<PagedList<Hit>> mLiveData = Transformations.switchMap(searchTermLiveData,
            (String input) -> mImageRepo.searchImages(input.toLowerCase(), page_size));

    public MainViewModel(ImageRepository repository, SavedStateHandle savedStateHandle) {
        initializeSampleData();
        mImageRepo = repository;
    }

    private void initializeSampleData() {
        setSearchTerm(DEFAULT_SEARCH_TERM);
    }

    void setSearchTerm(String searchTerm) {
        Log.d(TAG, "setSearchTerm: ");
        mutableSearchTerm.setValue(searchTerm);
    }

    public void invalidateSource() {
        mImageRepo.invalidateDataSource();
    }
}
