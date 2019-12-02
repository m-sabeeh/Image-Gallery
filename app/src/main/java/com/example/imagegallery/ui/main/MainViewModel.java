package com.example.imagegallery.ui.main;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import com.example.imagegallery.R;
import com.example.imagegallery.models.Hit;
import com.example.imagegallery.repositories.ImageRepository;

import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class MainViewModel extends ViewModel {
    private static final String TAG = "MainViewModel";
    private ImageRepository mImageRepo;
    private static final String DEFAULT_SEARCH_TERM = "Colors";
    public static final int page_size = 200;
    private static int orientation;


    private MutableLiveData<String> mutableSearchTerm = new MutableLiveData<>();
    LiveData<String> searchTermLiveData = Transformations.map(mutableSearchTerm, input -> input);
    LiveData<PagedList<Hit>> mLiveData = Transformations.switchMap(searchTermLiveData,
            (String input) -> mImageRepo.searchImages(input.toLowerCase(), page_size));

    public MainViewModel(ImageRepository repository, SavedStateHandle savedStateHandle) {
        initializeSampleData();
        //orientation = savedStateHandle.get("orientation");
        mImageRepo = repository;
    }


    private void initializeSampleData() {
        setSearchTerm(DEFAULT_SEARCH_TERM);
    }

    public void setSearchTerm(String searchTerm) {
        Log.d(TAG, "setSearchTerm: ");
        mutableSearchTerm.setValue(searchTerm);
    }

    public void invalidateSource() {
        mImageRepo.invalidateDataSource();
    }


    public static RecyclerView.LayoutManager getLayoutManager(RecyclerView recyclerView) {
        /*RecyclerView recyclerView1 = (RecyclerView) view;
        mAdapter = new CustomPagedListAdapter(getContext());
        mAdapter.setOnItemInteractionListener((View view, int position) -> {
            //start new activity with intent containing full url
            Intent intent = Utils.IntentUtils.buildImageFragmentIntent(getContext());
            intent.putExtra(Utils.IntentUtils.POSITION, position);
            startActivityForResult(intent, Utils.IntentUtils.CODE_RETURN_POSITION);
        });*/
        //recyclerView = Objects.requireNonNull(getView()).findViewById(R.id.recyclerView);
        int columns = orientation == Configuration.ORIENTATION_LANDSCAPE ? 3 : 2;
        StaggeredGridLayoutManager staggeredGridManager =
                new StaggeredGridLayoutManager(columns, LinearLayoutManager.VERTICAL) {
                    /* *
                     * Disable predictive animations. There is a bug in RecyclerView which causes views that
                     * are being reloaded to pull invalid ViewHolders from the internal recycler stack if the
                     * adapter size has decreased since the ViewHolder was recycled.*/

                    @Override
                    public boolean supportsPredictiveItemAnimations() {
                        return false;
                    }
                };
        staggeredGridManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);//to correctly handle the decorations.
        return staggeredGridManager;
    }

    // TODO: 03/12/2019 Discuss about this implementation approach.
    @BindingAdapter("android:initRecyclerView")
    public static void initRecyclerView(RecyclerView recyclerView, float spacing) {
        int columns = recyclerView.getResources().getConfiguration()
                .orientation == Configuration.ORIENTATION_LANDSCAPE ? 3 : 2;
        //int columns = isLandscape ? 3 : 2;
        StaggeredGridLayoutManager staggeredGridManager =
                new StaggeredGridLayoutManager(columns, LinearLayoutManager.VERTICAL) {
                    /* *
                     * Disable predictive animations. There is a bug in RecyclerView which causes views that
                     * are being reloaded to pull invalid ViewHolders from the internal recycler stack if the
                     * adapter size has decreased since the ViewHolder was recycled.*/

                    @Override
                    public boolean supportsPredictiveItemAnimations() {
                        return false;
                    }
                };
        staggeredGridManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);//to correctly handle the decorations.
        //int spacingPixels = recyclerView.getContext().getResources().getDimensionPixelSize(R.dimen.recycler_view_spacing);
        int spacingPixels = Math.round(spacing);
        while (recyclerView.getItemDecorationCount() > 0) {
            recyclerView.removeItemDecorationAt(0);
        }
        recyclerView.addItemDecoration(new SpaceItemDecoration(spacingPixels, columns));
        recyclerView.setLayoutManager(staggeredGridManager);
    }
}
