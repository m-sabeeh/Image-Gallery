package com.example.imagegallery.repositories;

import android.util.Log;

import com.example.imagegallery.api.PixabayApiService;
import com.example.imagegallery.models.Hit;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

public class PixabayPagedHitRepository implements ImageRepository {
    private static final String TAG = "ImageRepository";
    private static ImageRepository mImageRepository;
    private PixabayApiService pixabayApiService;
    private DataSource<Integer, Hit> latestDataSource;

    private PixabayPagedHitRepository(PixabayApiService apiService) {
        Log.d(TAG, "ImageRepository: Constructor " + apiService);
        pixabayApiService = apiService;
    }

    public static ImageRepository getInstance(PixabayApiService pixabayApi) {
        if (mImageRepository == null) {
            mImageRepository = new PixabayPagedHitRepository(pixabayApi);
        }
        return mImageRepository;
    }

    @Override
    public LiveData<PagedList<Hit>> searchImages(String query, int page_size) {
        DataSource.Factory<Integer, Hit> factory = new PixabayDataSourceFactory(pixabayApiService, query);
        latestDataSource = factory.create();
        PagedList.Config config = initPagedListConfig(page_size);
        return new LivePagedListBuilder<>(factory, config).build();
    }

    private PagedList.Config initPagedListConfig(int pageSize) {
        return new PagedList.Config.Builder()
                .setPageSize(pageSize)
                .setInitialLoadSizeHint(100)
                .setPrefetchDistance(100)
                .build();
    }
}