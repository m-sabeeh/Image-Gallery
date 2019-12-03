package com.example.imagegallery.utils;

import android.content.Intent;
import android.content.res.Configuration;
import android.view.View;

import com.example.imagegallery.ui.adapters.CustomPagedListAdapter;
import com.example.imagegallery.ui.main.SearchInputDialogFragment;
import com.example.imagegallery.ui.main.SpaceItemDecoration;

import androidx.databinding.BindingAdapter;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class BindingAdaptersMainFragment {

    // TODO: 03/12/2019 Discuss about this implementation approach. Data-binding requires at-least one custom argument (view passed is a default argument)
    @BindingAdapter("android:initRecyclerView")
    public static void initRecyclerView(final RecyclerView recyclerView, float spacing) {
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
