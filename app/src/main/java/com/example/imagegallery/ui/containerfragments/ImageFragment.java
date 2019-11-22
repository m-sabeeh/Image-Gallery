package com.example.imagegallery.ui.containerfragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.imagegallery.R;
import com.example.imagegallery.models.Hit;
import com.example.imagegallery.utils.GlideApp;
import com.example.imagegallery.utils.Utils;
import com.google.gson.Gson;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * A placeholder fragment containing a simple view.
 */
public class ImageFragment extends Fragment {
    private static final String TAG = "ImageFragment";
    private ImageView imageView;
    private ProgressBar progressBar;

    public ImageFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_container, container, false);
        imageView = view.findViewById(R.id.main_image);
        progressBar = view.findViewById(R.id.indeterminateBar);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated: " + getArguments().getString(Utils.IntentUtils.HIT_JSON));
        Gson gson = new Gson();
        progressBar.setVisibility(View.VISIBLE);
        Hit hit = gson.fromJson(getArguments().getString(Utils.IntentUtils.HIT_JSON), Hit.class);
        GlideApp.with(getContext())
                //.asBitmap()
                .load(hit.getLargeImageURL())
                .transition(DrawableTransitionOptions.withCrossFade())
                //.apply(options)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(imageView);
    }
}
