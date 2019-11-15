package com.example.imagegallery.ui.main;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.imagegallery.models.DataAPI;
import com.example.imagegallery.models.DataList;
import com.example.imagegallery.models.Hit;
import com.example.imagegallery.R;
import com.example.imagegallery.ui.adapters.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {
    private static final String TAG = "MainFragment";
    private MainViewModel mViewModel;
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;
    private static int COLUMN = 2;
    private List<Hit> mHitList = new ArrayList<>();

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
        //mViewModel.initLocal();
        mViewModel.initOnlineData();
    /*    mViewModel.getLocalData().observe(this, new Observer<List<RecyclerViewAdapter.Data>>() {
            @Override
            public void onChanged(List<RecyclerViewAdapter.Data> data) {
                mAdapter.notifyDataSetChanged();
            }
        });*/
        mViewModel.getHitList().observe(this, new Observer<List<Hit>>() {
            @Override
            public void onChanged(List<Hit> hits) {
                mAdapter.notifyDataSetChanged();
                Log.d(TAG, "onChanged: " + hits);
            }
        });
        initRecyclerView();

        Button button = getView().findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: ");
                /*Hit h1 = new Hit();
                h1.setPreviewURL("https://c1.staticflickr.com/5/4636/25316407448_de5fbf183d_o.jpg");
                mViewModel.addNewValue(h1);*/
            }
        });
    }


    private void initRecyclerView() {
        mRecyclerView = getView().findViewById(R.id.recyclerView);
        //Hit hit = new Hit();
        //hit.setPreviewURL("https://pixabay.com/get/52e5dc424b50ad14f6da8c7dda79367a1736dde451566c48702878dc9545c450ba_1280.jpg");
        // mHitList.add(hit);
        mAdapter = new RecyclerViewAdapter(getContext(), mViewModel.getHitList().getValue());
        mAdapter.setOnItemInteractionListener(new RecyclerViewAdapter.OnItemInteractionListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getContext(), position + " " + mViewModel.getHitList().getValue().get(position).getUser(), Toast.LENGTH_SHORT).show();
                //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, MainFragment.newInstance()).commit();
                //Intent intent = new Intent(getContext(), ContainerActivity.class);
                //Intent intent = Utils.IntentUtils.buildImageFragmentIntent(getContext());
                //startActivity(intent);
            }
        });
        RecyclerView.LayoutManager staggeredGridManager =
                new StaggeredGridLayoutManager(COLUMN, LinearLayoutManager.VERTICAL);
        //RecyclerView.LayoutManager linearLayoutManger = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(staggeredGridManager);
        mRecyclerView.setAdapter(mAdapter);
        //init();
        //getFragmentManager().beginTransaction().replace(R.id.container, MainFragment.newInstance()).commit();
    }

  /*  public void init() {
        Call<DataList> dataListCall = DataAPI.getDataService().getDataList();
        dataListCall.enqueue(new Callback<DataList>() {
            @Override
            public void onResponse(Call<DataList> call, Response<DataList> response) {
                *//*for (Hit h : response.body().getHits()) {
                    Log.d(TAG, "onResponse: " + h.getLargeImageURL().toString());
                }*//*


            }

            @Override
            public void onFailure(Call<DataList> call, Throwable t) {

            }
        });
    }*/


}
