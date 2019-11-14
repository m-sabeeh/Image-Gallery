package com.example.imagegallery.ui.main;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.imagegallery.ContainerActivity;
import com.example.imagegallery.MainActivity;
import com.example.imagegallery.R;
import com.example.imagegallery.Utils.Utils;
import com.example.imagegallery.ui.adapters.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {
    private static final String TAG = "MainFragment";
    private MainViewModel mViewModel;
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;
    private static int COLUMN = 2;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        initRecyclerView();
    }


    private void initRecyclerView() {
        mRecyclerView = getView().findViewById(R.id.recyclerView);
        mAdapter = new RecyclerViewAdapter(getContext(), mViewModel.getDataList());
        mAdapter.setOnItemInteractionListener(new RecyclerViewAdapter.OnItemInteractionListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getContext(), mViewModel.getDataList().get(position).getImageTile(), Toast.LENGTH_SHORT).show();
                //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, MainFragment.newInstance()).commit();
                //Intent intent = new Intent(getContext(), ContainerActivity.class);
                Intent intent = Utils.IntentUtils.buildImageFragmentIntent(getContext());
                startActivity(intent);
            }
        });
        RecyclerView.LayoutManager staggeredGridManager =
                new StaggeredGridLayoutManager(COLUMN, LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(staggeredGridManager);
        mRecyclerView.setAdapter(mAdapter);

        //getFragmentManager().beginTransaction().replace(R.id.container, MainFragment.newInstance()).commit();
    }


}
