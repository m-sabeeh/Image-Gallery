package com.example.imagegallery.repositories;

import com.example.imagegallery.models.Hit;

import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

public interface HitRepository {

    LiveData<PagedList<Hit>> searchImages(String query, int page_size);
}
