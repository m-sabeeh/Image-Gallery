package com.example.imagegallery.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.imagegallery.MainActivity;
import com.example.imagegallery.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";
    private List<Data> mDataList;
    private Context mContext;
    private OnItemInteractionListener mListener;

    public RecyclerViewAdapter(Context context, List<Data> dataList) {
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
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: " + mDataList.get(position).imageUri);
        RequestOptions options = new RequestOptions();
        options.placeholder(R.drawable.ic_launcher_background);
        Glide.with(mContext)
                .load(mDataList.get(position).getImageUri())
                .apply(options)
                .into(holder.imageView);
        holder.imageTitle.setText(mDataList.get(position).getImageTile());
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private ImageView imageView;
        private TextView imageTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            imageView = itemView.findViewById(R.id.imageView);
            imageTitle = itemView.findViewById(R.id.imageTitle);

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
