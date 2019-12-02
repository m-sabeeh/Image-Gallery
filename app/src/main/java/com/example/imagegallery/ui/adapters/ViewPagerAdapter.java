package com.example.imagegallery.ui.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.imagegallery.R;
import com.example.imagegallery.databinding.PageViewPager2Binding;
import com.example.imagegallery.models.Hit;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.BasePostprocessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.paging.PagedListAdapter;
import androidx.palette.graphics.Palette;
import androidx.palette.graphics.Target;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import me.relex.photodraweeview.PhotoDraweeView;


public class ViewPagerAdapter extends PagedListAdapter<Hit, ViewPagerAdapter.ViewHolder> {
    private static final String TAG = "ViewPagerAdapter";
    private Context mContext;
    private OnItemInteractionListener mListener;
    private LifecycleOwner lifecycleOwner;

    public ViewPagerAdapter(Context context) {
        super(DIFF_CALLBACK);
        mContext = context;
    }


    @NonNull
    @Override
    public ViewPagerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PageViewPager2Binding pager2Binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.page_view_pager2, parent, false);
        return new ViewPagerAdapter.ViewHolder(pager2Binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (!payloads.isEmpty()) {
            Palette p = ((Palette) payloads.get(0));
            holder.bindPalette(p);
        } else
            super.onBindViewHolder(holder, position, payloads);
    }

    /**
     * Image can be loaded using data binding with Fresco but I'm unable to update palette colors
     * on views afterwards, so I am generating bitmap directly and after palette generation, notifyItemChanged
     * is called with payload and colors are updated through separate holder.bindPalette(p) call.
     */
    @Override
    public void onBindViewHolder(@NonNull final ViewPagerAdapter.ViewHolder holder, final int position) {

        Hit hit = Objects.requireNonNull(getItem(position));
        holder.bind(hit);

        GenericDraweeHierarchy hierarchy = holder.draweeView.getHierarchy();
        //hierarchy.setPlaceholderImage(R.drawable.pebbles, ScalingUtils.ScaleType.FIT_CENTER);
        hierarchy.setProgressBarImage(new ProgressBarDrawable());
        holder.draweeView.setHierarchy(hierarchy);
        PipelineDraweeControllerBuilder controllerBuilder = Fresco.newDraweeControllerBuilder()
                .setImageRequest(getImageRequest(hit.getLargeImageURL(), position))
                //.setDataSourceSupplier(() -> getDataSource(hit.getLargeImageURL(), position))
                .setOldController(holder.draweeView.getController())
                .setControllerListener(new BaseControllerListener<ImageInfo>() {
                    @Override
                    public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                        super.onFinalImageSet(id, imageInfo, animatable);
                        if (imageInfo == null || holder.draweeView == null) {
                            return;
                        }
                        holder.draweeView.update(imageInfo.getWidth(), imageInfo.getHeight());
                    }
                });
        holder.draweeView.setController(controllerBuilder.build());
        //--------------------------------------------------------------------------------------
    }

    private ImageRequest getImageRequest(String imageUri, int position) {
        Uri uri = Uri.parse(imageUri);
        //ImagePipeline imagePipeline = Fresco.getImagePipeline();
        //imagePipeline.fetchDecodedImage(request, this);
        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(uri)
                .setProgressiveRenderingEnabled(true)
                .setPostprocessor(new BasePostprocessor() {
                    @Override
                    public void process(Bitmap bitmap) {
                        //super.process(bitmap);
                        if (bitmap != null)
                            createPaletteAsync(bitmap, position);
                        else Log.d(TAG, "process: null");
                    }
                })
                .build();
        return imageRequest;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final PageViewPager2Binding itemBinding;
        PhotoDraweeView draweeView;

        ViewHolder(@NonNull PageViewPager2Binding pager2Binding) {
            super(pager2Binding.getRoot());
            itemBinding = pager2Binding;
            draweeView = itemBinding.mainImage;//otherwise can't generate palette and update views
            //colors afterwards, that's why handling this one directly.
        }

        void bindPalette(Palette p) {
            itemBinding.setPalette(p);
            itemBinding.executePendingBindings();
        }

        void bind(Hit hit) {
            itemBinding.setViewModel(hit);
            itemBinding.executePendingBindings();
        }
    }

    private void createPaletteAsync(Bitmap bitmap, final int position) {
        Palette.from(bitmap)
                .clearTargets()
                .addTarget(Target.DARK_VIBRANT)
                .addTarget(Target.LIGHT_VIBRANT)
                .addTarget(Target.DARK_MUTED)
                .generate(palette -> {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            notifyItemChanged(position, palette);
                        }
                    }, 500);

                });
    }

    private static final DiffUtil.ItemCallback<Hit> DIFF_CALLBACK = new DiffUtil.ItemCallback<Hit>() {
        @Override
        public boolean areItemsTheSame(@NonNull Hit oldItem, @NonNull Hit newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Hit oldItem, @NonNull Hit newItem) {
            return oldItem.equals(newItem);
        }
    };

    public void setOnItemInteractionListener(OnItemInteractionListener listener) {
        mListener = listener;
    }

    public interface OnItemInteractionListener {
        void onItemClick(View view, int position);
    }
}
