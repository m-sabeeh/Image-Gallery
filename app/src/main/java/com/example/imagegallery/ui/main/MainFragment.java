package com.example.imagegallery.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.ProgressDialog;
import android.content.res.Configuration;
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
import android.widget.Button;
import android.widget.Toast;

import com.example.imagegallery.models.DataList;
import com.example.imagegallery.models.Hit;
import com.example.imagegallery.R;
import com.example.imagegallery.ui.adapters.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {
    private static final String TAG = "MainFragment";
    private MainViewModel mViewModel;
    private RecyclerViewAdapter mAdapter;
    private LiveData<List<Hit>> liveData;
    ProgressDialog progressDialog;

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
        //final ProgressDialog dialog = ProgressDialog.show(getActivity(), "Loading...", "Please wait...", true);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mViewModel.initOnlineData();
        liveData = mViewModel.getLiveHitList();
        liveData.observe(this, new Observer<List<Hit>>() {
            @Override
            public void onChanged(List<Hit> hits) {
                //dialog.dismiss();
                if (!hits.isEmpty() && mAdapter != null) {
                    mAdapter.notifyDataSetChanged();
                }

                Log.d(TAG, "onChanged: " + hits);
            }
        });
        initRecyclerView();
        Button button = getView().findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: ");
                mViewModel.fetchRequiredData("red cars");

            }
        });
    }


    private void initRecyclerView() {
        RecyclerView recyclerView = getView().findViewById(R.id.recyclerView);
        mAdapter = new RecyclerViewAdapter(getContext(), liveData.getValue());
        mAdapter.setOnItemInteractionListener(new RecyclerViewAdapter.OnItemInteractionListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getContext(), position + " " + mViewModel.getLiveHitList().getValue().get(position).getUser(), Toast.LENGTH_SHORT).show();
                //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, MainFragment.newInstance()).commit();
                //Intent intent = new Intent(getContext(), ContainerActivity.class);
                //Intent intent = Utils.IntentUtils.buildImageFragmentIntent(getContext());
                //startActivity(intent);
            }
        });

        int columns = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 3 : 2;
        RecyclerView.LayoutManager staggeredGridManager =
                new StaggeredGridLayoutManager(columns, LinearLayoutManager.VERTICAL);
        //RecyclerView.LayoutManager linearLayoutManger = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(staggeredGridManager);
        recyclerView.setAdapter(mAdapter);
    }
}
