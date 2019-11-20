package com.example.imagegallery.ui;

import com.example.imagegallery.repositories.PagedHitRepository;
import com.example.imagegallery.ui.main.MainViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ViewModelFactory implements ViewModelProvider.Factory {
    private PagedHitRepository repository;

    public ViewModelFactory(PagedHitRepository hitRepository) {
        this.repository = hitRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class)) {
            return (T) new MainViewModel(repository);
        }
        throw new IllegalArgumentException("ViewModel class is unknown");
    }
}
