package com.example.imagegallery.ui.main;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.imagegallery.R;
import com.example.imagegallery.databinding.ItemRecyclerViewBinding;
import com.example.imagegallery.models.Hit;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;


public class ImageListAdapter extends PagedListAdapter<Hit, ImageListAdapter.ViewHolder> {
    private static final String TAG = "CustomPagedListAdapter";
    private OnItemInteractionListener mListener;
    //private ConstraintSet set = new ConstraintSet();

    public ImageListAdapter() {
        super(DIFF_CALLBACK);
    }

    public void setOnItemInteractionListener(OnItemInteractionListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public ImageListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRecyclerViewBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_recycler_view, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final ImageListAdapter.ViewHolder holder, final int position) {
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
        ImageViewModel recyclerViewItemModel;

        ViewHolder(@NonNull ItemRecyclerViewBinding itemRecyclerViewBinding) {
            super(itemRecyclerViewBinding.getRoot());
            recyclerViewItemModel = new ImageViewModel();
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
            recyclerViewItemModel.bind(hit);
            viewBinding.setPageViewModel(recyclerViewItemModel);
            //viewBinding.setHit(hit);
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
