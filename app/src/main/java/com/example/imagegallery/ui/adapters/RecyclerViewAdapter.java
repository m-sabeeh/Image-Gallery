package com.example.imagegallery.ui.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.imagegallery.models.Hit;
import com.example.imagegallery.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.RecyclerView;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";
    private List<Hit> mDataList;
    private Context mContext;
    private OnItemInteractionListener mListener;
    private ConstraintSet set = new ConstraintSet();


    public RecyclerViewAdapter(Context context, @NonNull List<Hit> dataList) {
        mDataList = dataList;
        mContext = context;

    }

    public void setOnItemInteractionListener(OnItemInteractionListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        return new ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewAdapter.ViewHolder holder, final int position) {
        //Log.d(TAG, "onBindViewHolder: " + holder.cardView.getMeasuredWidth()+" "+holder.imageView.getMeasuredHeight());
        //RequestOptions options = new RequestOptions();
        //options.placeholder(R.drawable.ic_launcher_background);
        //int w = mDataList.get(position).getPreviewWidth();
        //int h = mDataList.get(position).getPreviewHeight();
        //Drawable d = mContext.getResources().getDrawable(R.drawable.ic_launcher_background);
        //d.setBounds(0, 0, w, h);
        //holder.
        //ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) holder.imageView.getLayoutParams();
        //layoutParams.dimensionRatio = 1:2;
        String ratio = String.format("%d:%d",mDataList.get(position).getPreviewWidth(),mDataList.get(position).getPreviewHeight());
        set.clone(holder.constraintLayout);
        Log.d(TAG, "onBindViewHolder: "+set);

        set.setDimensionRatio(holder.imageView.getId(),ratio);
        set.applyTo(holder.constraintLayout);
        //getPercentLayoutInfo().aspectRatio = (float) w / (float) h;
        //mImageView.setLayoutParams(layoutParams);
        //holder.imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_launcher_background));
        int W = holder.imageView.getLayoutParams().width;
        //(int) ((float) h / (float) w) * W;
        //holder.imageView.getLayoutParams().height = h;
        Log.d(TAG, "onBindViewHolder: " + W + " " + ratio + " " +  " " + holder.imageView.getLayoutParams().height);


        Glide.with(mContext)
                //.asBitmap()
                .load(mDataList.get(position).getPreviewURL())
                .transition(DrawableTransitionOptions.withCrossFade())                //.apply(options)
                //.placeholder(R.drawable.ic_launcher_background)
                .into(holder.imageView);
        //holder.imageTitle.setText(mDataList.get(position).getUser());
    }


    @Override
    public int getItemCount() {
        return mDataList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private ImageView imageView;
        private TextView imageTitle;
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
