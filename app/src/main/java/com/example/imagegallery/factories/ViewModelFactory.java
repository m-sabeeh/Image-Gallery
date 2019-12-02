package com.example.imagegallery.factories;

import android.os.Bundle;
import android.util.Log;

import com.example.imagegallery.repositories.ImageRepository;
import com.example.imagegallery.ui.main.MainViewModel;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.lifecycle.AbstractSavedStateViewModelFactory;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.savedstate.SavedStateRegistryOwner;

public class ViewModelFactory extends AbstractSavedStateViewModelFactory {
    private ImageRepository repository;
    private static final String TAG = "ViewModelFactory";

   /* public ViewModelFactory(ImageRepository hitRepository) {
        this.repository = hitRepository;
    }*/

    /**
     * Constructs this factory.
     *
     * @param owner       {@link SavedStateRegistryOwner} that will provide restored state for created
     *                    {@link ViewModel ViewModels}
     * @param defaultArgs values from this {@code Bundle} will be used as defaults by
     *                    {@link SavedStateHandle} passed in {@link ViewModel ViewModels}
     *                    if there is no previously saved state
     */
    public ViewModelFactory(@NonNull SavedStateRegistryOwner owner, @Nullable Bundle defaultArgs, ImageRepository hitRepository) {
        super(owner, defaultArgs);
        this.repository = hitRepository;
        Log.d(TAG, "ViewModelFactory: " +defaultArgs);
    }

/*    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class)) {
            return (T) new MainViewModel(repository);
        }
        throw new IllegalArgumentException("ViewModel class is unknown");
    }*/

    @NonNull
    @Override
    protected <T extends ViewModel> T create(@NonNull String key, @NonNull Class<T> modelClass, @NonNull SavedStateHandle handle) {
        Log.d(TAG, "create: "+handle.contains("search"));
        if (modelClass.isAssignableFrom(MainViewModel.class)) {
            return (T) new MainViewModel(repository, handle);
        }
        throw new IllegalArgumentException("ViewModel class is unknown");
    }
}
