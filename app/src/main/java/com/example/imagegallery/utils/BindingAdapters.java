package com.example.imagegallery.utils;

import android.content.res.Configuration;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.imagegallery.R;
import com.example.imagegallery.application.GlideApp;
import com.example.imagegallery.ui.main.SpaceItemDecoration;

import java.util.Locale;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class BindingAdapters {

    // TODO: 03/12/2019 Discuss about this implementation approach. Data-binding requires at-least one custom argument (view passed is a default argument)
    //fragment_image_list
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

    //item_recycler_view
    @BindingAdapter("android:fetchImage")
    public static void loadImage(ImageView view, String imageUrl) {
        GlideApp.with(view.getContext())
                //.asBitmap()
                .load(imageUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                //.apply(options)
                .into(view);
    }

    //item_recycler_view
    private static ConstraintSet set = new ConstraintSet();

    @BindingAdapter(value = {"android:dimensionWidth", "android:dimensionHeight"}, requireAll = true)
    public static void setDimensionRatio(ConstraintLayout layout, int width, int height) {
        String ratio = String.format(Locale.getDefault(), "%d:%d", width, height);
        set.clone(layout);
        set.setDimensionRatio(layout.findViewById(R.id.imageView).getId(), ratio);
        set.applyTo(layout);
    }
}
