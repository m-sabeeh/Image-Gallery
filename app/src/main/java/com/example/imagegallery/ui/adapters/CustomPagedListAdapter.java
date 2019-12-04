package com.example.imagegallery.ui.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.imagegallery.R;
import com.example.imagegallery.databinding.ItemRecyclerViewBinding;
import com.example.imagegallery.models.Hit;
import com.example.imagegallery.utils.GlideApp;

import java.util.Locale;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;


public class CustomPagedListAdapter extends PagedListAdapter<Hit, CustomPagedListAdapter.ViewHolder> {
    private static final String TAG = "CustomPagedListAdapter";
    private Context mContext;
    private OnItemInteractionListener mListener;
    //private ConstraintSet set = new ConstraintSet();

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
        ItemRecyclerViewBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_recycler_view, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomPagedListAdapter.ViewHolder holder, final int position) {
        Hit hit = Objects.requireNonNull(getItem(position));
        holder.bind(hit);
        /*String ratio = String.format(Locale.getDefault(), "%d:%d", hit.getPreviewWidth(), hit.getPreviewHeight());
        set.clone(holder.constraintLayout);
        set.setDimensionRatio(holder.imageView.getId(), ratio);
        set.applyTo(holder.constraintLayout);*/
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        //private ImageView imageView;
        //private ConstraintLayout constraintLayout;
        ItemRecyclerViewBinding viewBinding;

        ViewHolder(@NonNull ItemRecyclerViewBinding itemRecyclerViewBinding) {
            super(itemRecyclerViewBinding.getRoot());
            viewBinding = itemRecyclerViewBinding;
            //constraintLayout = viewBinding.constraintLayout;
            CardView cardView = viewBinding.cardView;
            //imageView = viewBinding.imageView;

            cardView.setOnClickListener(v -> {
                if (mListener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    Log.d(TAG, "onClick: Adapter" + " " + getAdapterPosition() + " " + getItem(getAdapterPosition()));
                    mListener.onItemClick(v, getAdapterPosition());
                }
            });
        }

        void bind(Hit hit) {
            viewBinding.setHit(hit);
            viewBinding.executePendingBindings();
        }
    }

    public interface OnItemInteractionListener {
        void onItemClick(View view, int position);
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
}
