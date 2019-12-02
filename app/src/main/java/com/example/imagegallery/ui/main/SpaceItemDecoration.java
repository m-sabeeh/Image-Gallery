package com.example.imagegallery.ui.main;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    private int space;
    int mColumns;
    private static final String TAG = "SpaceItemDecoration";

    public SpaceItemDecoration(int space, int columns) {
        this.space = space;
        mColumns = columns;
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        Log.d(TAG, "getItemOffsets: " + mColumns);
        StaggeredGridLayoutManager.LayoutParams lp = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
        int spanIndex = lp.getSpanIndex();
        if (mColumns == 2) {
            if (spanIndex == 1)
                outRect.left = space;
            else
                outRect.right = space;

            outRect.bottom = space * 2;
        } else {
            if (spanIndex == 2)
                outRect.left = space;
            if (spanIndex == 0)
                outRect.right = space;

            outRect.bottom = space * 2;

        }
    }
}