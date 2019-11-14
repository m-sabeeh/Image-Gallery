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
        // TODO: Use the ViewModel


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerView(view);
    }

    private void initRecyclerView(@NonNull View view) {
        final List<RecyclerViewAdapter.Data> dataList = getDataList();
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mAdapter = new RecyclerViewAdapter(getContext(), dataList);
        mAdapter.setOnItemInteractionListener(new RecyclerViewAdapter.OnItemInteractionListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getContext(), dataList.get(position).getImageTile(), Toast.LENGTH_SHORT).show();
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


    private List<RecyclerViewAdapter.Data> getDataList() {
        return initImageBitmaps();
    }

    private List<RecyclerViewAdapter.Data> initImageBitmaps() {

        List<RecyclerViewAdapter.Data> dataList = new ArrayList<>();

        dataList.add(new RecyclerViewAdapter.Data("https://c1.staticflickr.com/5/4636/25316407448_de5fbf183d_o.jpg", "Havasu Falls"));
        dataList.add(new RecyclerViewAdapter.Data("https://i.redd.it/tpsnoz5bzo501.jpg", "Trondheim"));
        dataList.add(new RecyclerViewAdapter.Data("https://i.redd.it/qn7f9oqu7o501.jpg", "Portugal"));
        dataList.add(new RecyclerViewAdapter.Data("https://i.redd.it/j6myfqglup501.jpg", "Rocky Mountain National Park"));
        dataList.add(new RecyclerViewAdapter.Data("https://i.redd.it/0h2gm1ix6p501.jpg", "Mahahual"));
        dataList.add(new RecyclerViewAdapter.Data("https://i.redd.it/k98uzl68eh501.jpg", "Frozen Lake"));
        dataList.add(new RecyclerViewAdapter.Data("https://i.redd.it/glin0nwndo501.jpg", "White Sands Desert"));
        dataList.add(new RecyclerViewAdapter.Data("https://i.redd.it/obx4zydshg601.jpg", "Austrailia"));
        dataList.add(new RecyclerViewAdapter.Data("https://i.imgur.com/ZcLLrkY.jpg", "Washington"));

        return dataList;
    }
}
