package com.example.imagegallery.repositories;

import android.util.Log;
import android.util.Pair;

import com.example.imagegallery.api.PixabayApiService;
import com.example.imagegallery.factories.PixabayDataSourceFactory;
import com.example.imagegallery.models.Hit;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

public class PixabayPagedHitRepository implements ImageRepository {
    private static final String TAG = "PixabayPagedHitRepository";
    private static ImageRepository mImageRepository;
    private PixabayApiService pixabayApiService;
    private LiveData<PagedList<Hit>> mLiveData;
    PixabayDataSourceFactory factory;

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

 /*   //@Override
    public LiveData<PagedList<Hit>> searchImages(String query, int page_size, boolean editors_choice) {
        factory = new PixabayDataSourceFactory(pixabayApiService, query);
        PagedList.Config config = initPagedListConfig(page_size);
        mLiveData = new LivePagedListBuilder<>(factory, config).build();
        return mLiveData;
    }*/

    private PagedList.Config initPagedListConfig(int pageSize) {
        return new PagedList.Config.Builder()
                .setPageSize(pageSize)
                .setInitialLoadSizeHint(100)
                .setPrefetchDistance(100)
                .build();
    }


    @Override
    public LiveData<PagedList<Hit>> searchImages(String query, int page_size) {
        factory = new PixabayDataSourceFactory(pixabayApiService, query);
        PagedList.Config config = initPagedListConfig(page_size);
        mLiveData = new LivePagedListBuilder<>(factory, config).build();
        return mLiveData;
    }

    /**
     * as this repository is singleton and is supposed to serve data to all the activities, I am
     * writing this method to be called from container activity's view model to get currently fetched
     * data.
     *
     * @return
     */
    @Override
    public LiveData<PagedList<Hit>> getLiveHitList() {
        return mLiveData;
    }

    @Override
    public void invalidateDataSource() {
        factory.invalidateDataSource();
    }
}