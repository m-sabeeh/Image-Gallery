package com.example.imagegallery.utils;

import com.example.imagegallery.api.PixabayApi;
import com.example.imagegallery.api.PixabayApiService;
import com.example.imagegallery.repositories.ImageRepository;
import com.example.imagegallery.repositories.PixabayPagedHitRepository;
import com.example.imagegallery.factories.ViewModelFactory;

import androidx.lifecycle.ViewModelProvider;

public class Injection {
    private static PixabayApiService getPixabayApiService() {
        return new PixabayApiService(PixabayApi.getPixabayApi());
    }

    private static ImageRepository getPixabayHitRepository() {
        return PixabayPagedHitRepository.getInstance(getPixabayApiService());
    }

    public static ViewModelProvider.Factory getViewModelFactory() {
        return new ViewModelFactory(getPixabayHitRepository());
    }


    /*    private static InstagramApiService getInstagramApiService() {
        return new PixabayApiService(PixabayApi.getPixabayApi());
    }

    private static ImageRepository getInstagramImageRepository() {
        return PagedInstagramRepository.getInstance(getInstagramApiService());
    }*/

}
