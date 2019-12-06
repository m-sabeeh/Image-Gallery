package com.example.imagegallery.repositories;

import android.util.Pair;

import com.example.imagegallery.models.Hit;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

public interface ImageRepository {

    LiveData<PagedList<Hit>> searchImages(String query, int page_size);

    LiveData<PagedList<Hit>> getLiveHitList();

    void invalidateDataSource();
}
