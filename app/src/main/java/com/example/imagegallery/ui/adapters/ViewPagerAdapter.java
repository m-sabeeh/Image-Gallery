package com.example.imagegallery.ui.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.Locale;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.paging.PagedListAdapter;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import me.relex.photodraweeview.PhotoDraweeView;

import static android.provider.CalendarContract.CalendarCache.URI;


public class ViewPagerAdapter extends PagedListAdapter<Hit, ViewPagerAdapter.ViewHolder> {
    private static final String TAG = "CustomPagedListAdapter";
    private Context mContext;
    private OnItemInteractionListener mListener;

    public ViewPagerAdapter(Context context) {
        super(DIFF_CALLBACK);
        mContext = context;
    }

    public void setOnItemInteractionListener(OnItemInteractionListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewPagerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.page_view_pager, parent, false);
        return new ViewPagerAdapter.ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewPagerAdapter.ViewHolder holder, final int position) {
        Hit hit = Objects.requireNonNull(getItem(position));

        //holder.progressBar.setVisibility(View.VISIBLE);
        GenericDraweeHierarchy hierarchy = holder.draweeView.getHierarchy();
        hierarchy.setProgressBarImage(new ProgressBarDrawable());
        /*GenericDraweeHierarchyBuilder.newInstance(mContext.getResources())
                .setProgressBarImage(new ProgressBarDrawable())
                .setProgressBarImageScaleType(ScalingUtils.ScaleType.CENTER_INSIDE)
                .build();*/
        holder.draweeView.setHierarchy(hierarchy);
////
        Uri uri = Uri.parse(hit.getLargeImageURL());
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setProgressiveRenderingEnabled(true)
                .build();
        PipelineDraweeControllerBuilder controllerBuilder = Fresco.newDraweeControllerBuilder()
                //.setUri(hit.getLargeImageURL())
                .setImageRequest(request)
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
        //////////


/*        Uri uri = Uri.parse(hit.getLargeImageURL());
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setProgressiveRenderingEnabled(true)
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(holder.draweeView.getController())
                .build();
        holder.draweeView.setController(controller);*/

        // holder.draweeView.setImageURI(hit.getLargeImageURL());
/*        GlideApp.with(mContext)
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
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(holder.imageView);*/
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        //private ImageView imageView;
        //private SimpleDraweeView draweeView;
        private PhotoDraweeView draweeView;
        //private ProgressBar progressBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //imageView = itemView.findViewById(R.id.main_image);
            draweeView = itemView.findViewById(R.id.main_image);
            //progressBar = itemView.findViewById(R.id.indeterminateBar);
        }
    }

    public void createPaletteAsync(Bitmap bitmap, final int position) {
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            public void onGenerated(Palette p) {
                notifyItemChanged(position, p);
                Log.d(TAG, "onGenerated: " + p.toString());
            }
        });
    }

    public interface OnItemInteractionListener {
        void onItemClick(View view, int position);
    }

    private static final DiffUtil.ItemCallback<Hit> DIFF_CALLBACK = new DiffUtil.ItemCallback<Hit>() {
        @Override
        public boolean areItemsTheSame(@NonNull Hit oldItem, @NonNull Hit newItem) {
            Log.d(TAG, "areItemsTheSame: ");
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Hit oldItem, @NonNull Hit newItem) {
            Log.d(TAG, "areContentsTheSame: ");
            return oldItem.equals(newItem);
        }
    };
}
