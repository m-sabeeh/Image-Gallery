package com.example.imagegallery.ui.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.imagegallery.models.Hit;
import com.example.imagegallery.R;

import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.paging.PagedListAdapter;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;


public class CustomListAdapter extends PagedListAdapter<Hit, CustomListAdapter.ViewHolder> {
    private static final String TAG = "CustomListAdapter";
    private List<Hit> mDataList;
    private Context mContext;
    private OnItemInteractionListener mListener;
    private ConstraintSet set = new ConstraintSet();
    String ratio;

    public CustomListAdapter(Context mContext, List<Hit> mDataList) {
        super(DIFF_CALLBACK);
        this.mDataList = mDataList;
        this.mContext = mContext;
    }

/*    public CustomListAdapter(@NonNull DiffUtil.ItemCallback<List<Hit>> diffCallback, List<Hit> mDataList, Context mContext) {
        super(DIFF_CALLBACK);
        this.mDataList = mDataList;
        this.mContext = mContext;
    }*/

/*    public CustomListAdapter(Context context, @NonNull List<Hit> dataList) {
        mDataList = dataList;
        mContext = context;

    }*/

    public void setOnItemInteractionListener(OnItemInteractionListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public CustomListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        return new ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull final CustomListAdapter.ViewHolder holder, final int position) {

        ratio = String.format(Locale.getDefault(), "%d:%d", mDataList.get(position).getPreviewWidth(), mDataList.get(position).getPreviewHeight());
        set.clone(holder.constraintLayout);
        set.setDimensionRatio(holder.imageView.getId(), ratio);
        set.applyTo(holder.constraintLayout);

        Glide.with(mContext)
                //.asBitmap()
                .load(mDataList.get(position).getPreviewURL())
                .transition(DrawableTransitionOptions.withCrossFade())
                //.apply(options)
                .into(holder.imageView);
        //holder.imageTitle.setText(mDataList.get(position).getUser());
    }


    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public static final DiffUtil.ItemCallback<Hit> DIFF_CALLBACK = new DiffUtil.ItemCallback<Hit>() {
        @Override
        public boolean areItemsTheSame(@NonNull Hit oldItem, @NonNull Hit newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Hit oldItem, @NonNull Hit newItem) {
            return oldItem.getId() == newItem.getId();
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

    public static class Data {
        private String imageUri;
        private String imageTile;

        public Data(String imageUri, String imageTile) {
            this.imageUri = imageUri;
            this.imageTile = imageTile;
        }

        public String getImageUri() {
            return imageUri;
        }

        public String getImageTile() {
            return imageTile;
        }
    }
}
