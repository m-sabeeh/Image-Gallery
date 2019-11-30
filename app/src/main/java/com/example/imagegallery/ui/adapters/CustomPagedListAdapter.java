package com.example.imagegallery.ui.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.imagegallery.R;
import com.example.imagegallery.models.Hit;
import com.example.imagegallery.utils.GlideApp;

import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.paging.PagedListAdapter;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;


public class CustomPagedListAdapter extends PagedListAdapter<Hit, CustomPagedListAdapter.ViewHolder> {
    private static final String TAG = "CustomPagedListAdapter";
    //private PagedList<Hit> mDataList;
    private Context mContext;
    private OnItemInteractionListener mListener;
    private ConstraintSet set = new ConstraintSet();
    String ratio;

    public CustomPagedListAdapter(Context mContext) {
        super(DIFF_CALLBACK);
        this.mContext = mContext;
    }

    public void setOnItemInteractionListener(OnItemInteractionListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public CustomPagedListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view, parent, false);
        return new ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull final CustomPagedListAdapter.ViewHolder holder, final int position) {
        Hit hit = getItem(position);
        ratio = String.format(Locale.getDefault(), "%d:%d", hit.getPreviewWidth(), hit.getPreviewHeight());
        set.clone(holder.constraintLayout);
        set.setDimensionRatio(holder.imageView.getId(), ratio);
        set.applyTo(holder.constraintLayout);

        GlideApp.with(mContext)
                //.asBitmap()
                .load(hit.getPreviewURL())
                .transition(DrawableTransitionOptions.withCrossFade())
                //.apply(options)
                .into(holder.imageView);


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
        private CardView cardView;
        private ImageView imageView;
        //private TextView imageTitle;
        private ConstraintLayout constraintLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            constraintLayout = itemView.findViewById(R.id.constraintLayout);
            cardView = itemView.findViewById(R.id.cardView);
            imageView = itemView.findViewById(R.id.imageView);
            //imageTitle = itemView.findViewById(R.id.imageTitle);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                        Log.d(TAG, "onClick: Adapter" + " " + getAdapterPosition() + " " + getItem(getAdapterPosition()));
                        mListener.onItemClick(v, getAdapterPosition());
                    }
                }
            });
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
