package com.example.imagegallery.utils;

import android.os.Bundle;

import com.example.imagegallery.api.PixabayApi;
import com.example.imagegallery.api.PixabayApiService;
import com.example.imagegallery.repositories.ImageRepository;
import com.example.imagegallery.repositories.PixabayPagedHitRepository;
import com.example.imagegallery.factories.ViewModelFactory;

import androidx.lifecycle.ViewModelProvider;
import androidx.savedstate.SavedStateRegistryOwner;

public class Injection {
    private static PixabayApiService getPixabayApiService() {
        return new PixabayApiService(PixabayApi.getPixabayApi());
    }

    private static ImageRepository getPixabayHitRepository() {
        return PixabayPagedHitRepository.getInstance(getPixabayApiService());
    }

    public static ViewModelProvider.Factory getViewModelFactory(SavedStateRegistryOwner owner, Bundle defaultArgs) {
        return new ViewModelFactory(owner, defaultArgs, getPixabayHitRepository());
    }


    /*    private static InstagramApiService getInstagramApiService() {
        return new PixabayApiService(PixabayApi.getPixabayApi());
    }

    private static ImageRepository getInstagramImageRepository() {
        return PagedInstagramRepository.getInstance(getInstagramApiService());
    }*/

}
