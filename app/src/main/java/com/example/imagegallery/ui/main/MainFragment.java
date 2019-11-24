package com.example.imagegallery.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.imagegallery.utils.Injection;
import com.example.imagegallery.R;
import com.example.imagegallery.models.Hit;
import com.example.imagegallery.ui.adapters.CustomPagedListAdapter;
import com.example.imagegallery.utils.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class MainFragment extends Fragment implements SearchInputDialogFragment.SearchInputListener {
    private static final String TAG = "MainFragment";
    private MainViewModel mViewModel;
    private CustomPagedListAdapter mAdapter;
    RecyclerView recyclerView;
    private LiveData<PagedList<Hit>> liveData;
    private int dataPosition = -1;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = initViewModel();
        initAdapter();
        initFab();
        initLiveDataObservations();
        setActivityTitle();
    }

    private MainViewModel initViewModel() {
        ViewModelProvider.Factory factory = Injection.getViewModelFactory();
        return ViewModelProviders.of(this, factory).get(MainViewModel.class);
    }

    private void initAdapter() {
        Log.d(TAG, "initAdapter: ");
        mAdapter = new CustomPagedListAdapter(getContext());
        mAdapter.setOnItemInteractionListener((View view, int position) -> {
            //Toast.makeText(getContext(), position + " " + liveData.getValue().get(position)
            //        .getUser(), Toast.LENGTH_SHORT).show();
            //start new activity with intent containing full url
            Intent intent = Utils.IntentUtils.buildImageFragmentIntent(getContext());
            //Gson gson = new Gson();
            //intent.putExtra(Utils.IntentUtils.HIT_JSON, gson.toJson(liveData.getValue().get(position)));
            intent.putExtra(Utils.IntentUtils.POSITION, position);
            startActivityForResult(intent, Utils.IntentUtils.CODE_RETURN_POSITION);

        });

        recyclerView = Objects.requireNonNull(getView()).findViewById(R.id.recyclerView);
        int columns = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 3 : 2;
        RecyclerView.LayoutManager staggeredGridManager =
                new StaggeredGridLayoutManager(columns, LinearLayoutManager.VERTICAL) {
                    /**
                     * Disable predictive animations. There is a bug in RecyclerView which causes views that
                     * are being reloaded to pull invalid ViewHolders from the internal recycler stack if the
                     * adapter size has decreased since the ViewHolder was recycled.
                     */
                    @Override
                    public boolean supportsPredictiveItemAnimations() {
                        return false;
                    }
                };
        //RecyclerView.LayoutManager linearLayoutManger = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(staggeredGridManager);
        recyclerView.setAdapter(mAdapter);
    }

    private void initFab() {
        FloatingActionButton button = getView().findViewById(R.id.fab);
        button.setOnClickListener((View view) -> {
            buildDialogFragment();
        });
    }

    private void initLiveDataObservations() {
        Log.d(TAG, "initLiveDataObservations: ");
        liveData = mViewModel.getLiveHitList();
        liveData.observe(this, new Observer<PagedList<Hit>>() {
            @Override
            public void onChanged(PagedList<Hit> hits) {
                Log.d(TAG, "onChanged: MainFragment");
                mAdapter.submitList(hits);
            }
        });
    }

    private void buildDialogFragment() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        DialogFragment dialogFragment = new SearchInputDialogFragment();
        dialogFragment.setTargetFragment(this, 300);
        dialogFragment.show(ft, "dialog");
    }

    private void setActivityTitle() {
        String searchTerm = mViewModel.getSearchTerm();
        getActivity().setTitle(searchTerm + " Gallery");
    }

    @Override
    public void onInputFinished(String query) {
        mViewModel.setSearchTerm(query);
        setActivityTitle();
        initLiveDataObservations();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == Utils.IntentUtils.CODE_RETURN_POSITION) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                dataPosition = data.getIntExtra(Utils.IntentUtils.RETURN_POSITION, -1);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (recyclerView != null && Integer.signum(dataPosition) == 1) {
            recyclerView.smoothScrollToPosition(dataPosition);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        dataPosition = -1;
    }
}
