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
import android.widget.TextView;

import com.example.imagegallery.R;
import com.example.imagegallery.databinding.PageViewPager2Binding;
import com.example.imagegallery.models.Hit;
import com.example.imagegallery.utils.Utils;
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
import java.util.Locale;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.paging.PagedListAdapter;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import me.relex.photodraweeview.PhotoDraweeView;


public class ViewPagerAdapter extends PagedListAdapter<Hit, ViewPagerAdapter.ViewHolder> {
    private static final String TAG = "ViewPagerAdapter";
    private Context mContext;
    private OnItemInteractionListener mListener;
    private LifecycleOwner lifecycleOwner;

    public ViewPagerAdapter(Context context, LifecycleOwner owner) {
        super(DIFF_CALLBACK);
        mContext = context;
        lifecycleOwner = owner;
    }

    public void setOnItemInteractionListener(OnItemInteractionListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewPagerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.page_view_pager2, parent, false);
        PageViewPager2Binding pager2Binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.page_view_pager2, parent, false);
        //pager2Binding.setLifecycleOwner(lifecycleOwner);
        return new ViewPagerAdapter.ViewHolder(pager2Binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (!payloads.isEmpty()) {
            //Log.d(TAG, "onBindViewHolder: " + payloads.get(0).getClass());
            Palette p = ((Palette) payloads.get(0));
            holder.bindPalette(p);
            /*holder.viewColor1.getBackground().setColorFilter(p.getDarkVibrantColor(0), PorterDuff.Mode.SRC_ATOP);
            holder.viewColor2.getBackground().setColorFilter(p.getLightVibrantColor(0), PorterDuff.Mode.SRC_ATOP);
            holder.viewColor3.getBackground().setColorFilter(p.getDarkMutedColor(0), PorterDuff.Mode.SRC_ATOP);*/
        } else
            super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewPagerAdapter.ViewHolder holder, final int position) {

        Hit hit = Objects.requireNonNull(getItem(position));
        holder.bind(hit);
        /*GenericDraweeHierarchy hierarchy = holder.draweeView.getHierarchy();
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
        holder.draweeView.setController(controllerBuilder.build());*/


        //--------------------------------------------------------------------------------------

        //holder.likes.setText(withSuffix(hit.getLikes()));
        //holder.shares.setText(withSuffix(hit.getFavorites()));
        //holder.comments.setText(withSuffix(hit.getComments()));
        //holder.views.setText(withSuffix(hit.getViews()));
        //holder.downloads.setText(withSuffix(hit.getDownloads()));

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

    //private DataSource<CloseableReference<CloseableImage>> getImageRequest(String imageUri, int position) {
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
                        createPaletteAsync(bitmap, position);
                    }
                })
                .build();
        return imageRequest;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        //private ImageView imageView;
        //private SimpleDraweeView draweeView;
        private final PageViewPager2Binding itemBinding;
        private PhotoDraweeView draweeView;
        View viewColor1, viewColor2, viewColor3;
        TextView likes, shares, comments, views, downloads;

        //private ProgressBar progressBar;

        public ViewHolder(@NonNull PageViewPager2Binding pager2Binding) {
            super(pager2Binding.getRoot());
            itemBinding = pager2Binding;
            //draweeView = viewItem.findViewById(R.id.main_image);
            //progressBar = viewItem.findViewById(R.id.indeterminateBar);
            //viewColor1 = viewItem.findViewById(R.id.color1);
            //viewColor2 = viewItem.findViewById(R.id.color2);
            //viewColor3 = viewItem.findViewById(R.id.color3);

        }

        public void bindPalette(Palette p) {
            itemBinding.setPalette(p);
            itemBinding.executePendingBindings();

        }

        public void bind(Hit hit) {
            itemBinding.setViewModel(hit);
            itemBinding.executePendingBindings();
        }
    }

    private void createPaletteAsync(Bitmap bitmap, final int position) {
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            public void onGenerated(Palette p) {
                /*for (Palette.Swatch swatch : p.getSwatches()) {
                    //Color color = Color.valueOf(swatch.getRgb());
                    //Log.d(TAG, "onGenerated: " + position + " " + p.getTargets().size() + " " + p.getSwatches().size());
                    //Log.d(TAG, "onGenerated: " + position + " " + color.red() * 255 + " " + color.green() * 255 + " " + color.blue() * 255);
                }*/
                //Log.d(TAG, "onGenerated: " + p.getSwatches());
                notifyItemChanged(position, p);
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

    @BindingAdapter({"android:imageUri"})
    public void loadImage(PhotoDraweeView view, String imageUrl) {
        GenericDraweeHierarchy hierarchy = view.getHierarchy();
        hierarchy.setProgressBarImage(new ProgressBarDrawable());
        view.setHierarchy(hierarchy);
        Uri uri = Uri.parse(imageUrl);
        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(uri)
                .setProgressiveRenderingEnabled(true)
                .setPostprocessor(new BasePostprocessor() {
                    @Override
                    public void process(Bitmap bitmap) {
                        //super.process(bitmap);
                        //createPaletteAsync(bitmap, position);
                    }
                })
                .build();

        PipelineDraweeControllerBuilder controllerBuilder = Fresco.newDraweeControllerBuilder()
                //.setImageRequest(getImageRequest(imageUrl, position))
                .setImageRequest(imageRequest)
                //.setDataSourceSupplier(() -> getDataSource(hit.getLargeImageURL(), position))
                .setOldController(view.getController())
                .setControllerListener(new BaseControllerListener<ImageInfo>() {
                    @Override
                    public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                        super.onFinalImageSet(id, imageInfo, animatable);
                        if (imageInfo == null || view == null) {
                            return;
                        }
                        view.update(imageInfo.getWidth(), imageInfo.getHeight());
                    }
                });
        view.setController(controllerBuilder.build());
    }
}
