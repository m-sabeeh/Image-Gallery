package com.example.imagegallery;

import com.example.imagegallery.api.PixabayApi;
import com.example.imagegallery.api.PixabayApiService;
import com.example.imagegallery.repositories.PagedHitRepository;
import com.example.imagegallery.ui.ViewModelFactory;

import androidx.lifecycle.ViewModelProvider;

public class Injection {


    private static PixabayApiService getPixabayApiService() {
        return new PixabayApiService(PixabayApi.getPixabayApi());


    }

    private static PagedHitRepository getHitRepository() {
        return new PagedHitRepository(getPixabayApiService());
    }

    public static ViewModelProvider.Factory getViewModelFactory() {
        return new ViewModelFactory(getHitRepository());

    }




}
