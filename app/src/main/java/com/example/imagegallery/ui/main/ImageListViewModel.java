package com.example.imagegallery.ui.main;

import android.util.Log;
import android.util.Pair;

import com.example.imagegallery.models.Hit;
import com.example.imagegallery.repositories.ImageRepository;
import com.example.imagegallery.utils.Utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

public class ImageListViewModel extends ViewModel {
    private static final String TAG = "MainViewModel";
    private ImageRepository mImageRepo;
    private static final String DEFAULT_SEARCH_TERM = "Colors";
    private static final int page_size = 200;

    private MutableLiveData<String> mutableSearchTerm = new MutableLiveData<>();
    LiveData<String> searchTermLiveData = Transformations.map(mutableSearchTerm, input -> input);
    LiveData<PagedList<Hit>> mLiveData = Transformations.switchMap(searchTermLiveData,
            (String input) -> mImageRepo.searchImages(input.toLowerCase(), page_size));


    /*private MutableLiveData<SearchParams> searchParamsMutable = new MutableLiveData<>();
    LiveData<SearchParams> searchParamsLiveData = Transformations.map(searchParamsMutable, input -> input);//simple conversion to LiveData
    LiveData<PagedList<Hit>> mLiveData = Transformations.switchMap(searchParamsLiveData,
            (SearchParams input) -> mImageRepo.searchImages(input.mSearch.toLowerCase(), page_size, input.arguments));*/


    public ImageListViewModel(ImageRepository repository, SavedStateHandle savedStateHandle) {
        if (savedStateHandle.contains(Utils.General.SEARCH_TERM))
            setSearchTerm(savedStateHandle.get(Utils.General.SEARCH_TERM));
        else
            initializeSampleData();
        mImageRepo = repository;
    }

    private void initializeSampleData() {
        setSearchTerm(DEFAULT_SEARCH_TERM);
    }

    void setSearchTerm(String searchTerm) {
        Log.d(TAG, "setSearchTerm: ");
        //searchParamsMutable.setValue(new SearchParams(searchTerm, params));
        ///////////
        mutableSearchTerm.setValue(searchTerm);
    }

    public void invalidateSource() {
        mImageRepo.invalidateDataSource();
    }

    class SearchParams {
        String mSearch;
        Pair<String, Boolean>[] arguments;

        SearchParams(String search, @Nullable Pair<String, Boolean>... params) {
            mSearch = search;
            arguments = params;
        }
    }
}

