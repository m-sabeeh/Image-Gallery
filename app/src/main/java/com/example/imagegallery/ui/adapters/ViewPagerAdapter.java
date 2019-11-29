package com.example.imagegallery.ui.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.ShapeDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.imagegallery.R;
import com.example.imagegallery.models.Hit;
import com.facebook.common.executors.UiThreadImmediateExecutorService;
import com.facebook.common.internal.Supplier;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.BaseDataSubscriber;
import com.facebook.datasource.DataSource;
import com.facebook.datasource.DataSubscriber;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableBitmap;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.BasePostprocessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import me.relex.photodraweeview.PhotoDraweeView;


public class ViewPagerAdapter extends PagedListAdapter<Hit, ViewPagerAdapter.ViewHolder> {
    private static final String TAG = "ViewPagerAdapter";
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
                .inflate(R.layout.page_view_pager2, parent, false);
        return new ViewPagerAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (!payloads.isEmpty()) {
            //Log.d(TAG, "onBindViewHolder: " + payloads.get(0).getClass());
            Palette p = ((Palette) payloads.get(0));
            //holder.viewColor1.setVisibility(View.VISIBLE);
            //holder.viewColor2.setVisibility(View.VISIBLE);
            //holder.viewColor3.setVisibility(View.VISIBLE);
            //holder.viewColor1.setBackgroundColor((p.getDarkVibrantColor(0)));
            //holder.viewColor2.setBackgroundColor((p.getLightVibrantColor(0)));
            holder.viewColor1.getBackground().setColorFilter(p.getDarkVibrantColor(0), PorterDuff.Mode.SRC_ATOP);
            holder.viewColor2.getBackground().setColorFilter(p.getLightVibrantColor(0), PorterDuff.Mode.SRC_ATOP);
            holder.viewColor3.getBackground().setColorFilter(p.getDarkMutedColor(0), PorterDuff.Mode.SRC_ATOP);
        } else
            super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewPagerAdapter.ViewHolder holder, final int position) {
        Hit hit = Objects.requireNonNull(getItem(position));
        GenericDraweeHierarchy hierarchy = holder.draweeView.getHierarchy();
        hierarchy.setProgressBarImage(new ProgressBarDrawable());
        holder.draweeView.setHierarchy(hierarchy);
        PipelineDraweeControllerBuilder controllerBuilder = Fresco.newDraweeControllerBuilder()
                .setDataSourceSupplier(() -> getDataSource(hit.getLargeImageURL(), position))
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
/*        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        DataSource<CloseableReference<CloseableImage>>
                dataSource = imagePipeline.fetchDecodedImage(request, this);*/


/*        DataSubscriber dataSubscriber2 = new BaseBitmapDataSubscriber() {
            @Override
            protected void onNewResultImpl(Bitmap bitmap) {
                if (bitmap != null)

                    createPaletteAsync(bitmap, position);
            }

            @Override
            protected void onFailureImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {

            }
        };

        try {
            dataSource.subscribe(dataSubscriber2, UiThreadImmediateExecutorService.getInstance());
        } finally {
            if (dataSource != null) {
                dataSource.close();
            }
        }*/
//////////////
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

    private DataSource<CloseableReference<CloseableImage>> getDataSource(String imageUri, int position) {
        Uri uri = Uri.parse(imageUri);
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setProgressiveRenderingEnabled(true)
                .setPostprocessor(new BasePostprocessor() {
                    @Override
                    public void process(Bitmap bitmap) {
                        super.process(bitmap);
                        createPaletteAsync(bitmap, position);
                    }
                })
                .build();
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        return imagePipeline.fetchDecodedImage(request, this);
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        //private ImageView imageView;
        //private SimpleDraweeView draweeView;
        private PhotoDraweeView draweeView;
        View viewColor1;
        View viewColor2;
        View viewColor3;
        //private ProgressBar progressBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //imageView = itemView.findViewById(R.id.main_image);
            draweeView = itemView.findViewById(R.id.main_image);
            //progressBar = itemView.findViewById(R.id.indeterminateBar);
            viewColor1 = itemView.findViewById(R.id.color1);
            viewColor2 = itemView.findViewById(R.id.color2);
            viewColor3 = itemView.findViewById(R.id.color3);
            //viewColor1.setVisibility(View.GONE);
            //viewColor2.setVisibility(View.GONE);
            //viewColor3.setVisibility(View.GONE);
        }
    }

    public void createPaletteAsync(Bitmap bitmap, final int position) {
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            public void onGenerated(Palette p) {
                notifyItemChanged(position, p);

                // Log.d(TAG, "onGenerated: " + p.toString());
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
