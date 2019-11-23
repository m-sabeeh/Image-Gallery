package com.example.imagegallery.ui.containerfragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
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

import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.paging.PagedListAdapter;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;


public class ViewPagerAdapter extends PagedListAdapter<Hit, ViewPagerAdapter.ViewHolder> {
    private static final String TAG = "CustomPagedListAdapter";
    //private PagedList<Hit> mDataList;
    private Context mContext;
    private com.example.imagegallery.ui.adapters.CustomPagedListAdapter.OnItemInteractionListener mListener;
    //private ConstraintSet set = new ConstraintSet();
    //String ratio;
    //private ConstraintSet set = new ConstraintSet();
    public ViewPagerAdapter(Context mContext) {
        super(DIFF_CALLBACK);
        this.mContext = mContext;
    }

    public void setOnItemInteractionListener(com.example.imagegallery.ui.adapters.CustomPagedListAdapter.OnItemInteractionListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewPagerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.page_view_pager, parent, false);
        return new ViewPagerAdapter.ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewPagerAdapter.ViewHolder holder, final int position) {
        Hit hit = getItem(position);
        //ratio = String.format(Locale.getDefault(), "%d:%d", hit.getPreviewWidth(), hit.getPreviewHeight());
        //set.clone(holder.constraintLayout);
        //set.setDimensionRatio(holder.imageView.getId(), ratio);
        //set.applyTo(holder.constraintLayout);
        holder.progressBar.setVisibility(View.VISIBLE);
        GlideApp.with(mContext)
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
                .into(holder.imageView);

/*        GlideApp.with(mContext)
                //.asBitmap()
                .load(hit.getPreviewURL())
                .transition(DrawableTransitionOptions.withCrossFade())
                //.apply(options)
                .into(holder.imageView);*/


        //holder.imageTitle.setText(mDataList.get(position).getUser());
    }

    public static final DiffUtil.ItemCallback<Hit> DIFF_CALLBACK = new DiffUtil.ItemCallback<Hit>() {
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

    class ViewHolder extends RecyclerView.ViewHolder {
        //private CardView cardView;
        private ImageView imageView;
        ProgressBar progressBar;
        //private TextView imageTitle;
        //private ConstraintLayout constraintLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //constraintLayout = itemView.findViewById(R.id.constraintLayout);
            //cardView = itemView.findViewById(R.id.cardView);
            imageView = itemView.findViewById(R.id.main_image);
            progressBar = itemView.findViewById(R.id.indeterminateBar);
            //imageTitle = itemView.findViewById(R.id.imageTitle);

       /*     cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                        Log.d(TAG, "onClick: Adapter" + getItem(getAdapterPosition()));
                        mListener.onItemClick(v, getAdapterPosition());
                    }
                }
            });*/
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
}
