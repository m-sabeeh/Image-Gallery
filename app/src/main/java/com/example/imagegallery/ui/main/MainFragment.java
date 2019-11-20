package com.example.imagegallery.ui.main;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.imagegallery.Injection;
import com.example.imagegallery.R;
import com.example.imagegallery.models.Hit;
import com.example.imagegallery.ui.adapters.CustomPagedListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class MainFragment extends Fragment {
    private static final String TAG = "MainFragment";
    private MainViewModel mViewModel;
    private CustomPagedListAdapter mAdapter;
    private LiveData<PagedList<Hit>> liveData;

    private String mSearchTerm;

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
        mViewModel = initViewModel();
        liveData = mViewModel.getLiveHitList();
        mSearchTerm = mViewModel.getSearchTermLiveData().getValue();
        getActivity().setTitle(mSearchTerm + " Gallery");
        initAdapter();
        initFab();
    }

    private MainViewModel initViewModel() {
        ViewModelProvider.Factory factory = Injection.getViewModelFactory();
        return ViewModelProviders.of(this, factory).get(MainViewModel.class);
    }


    private void initFab() {
        FloatingActionButton button = getView().findViewById(R.id.fab);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: ");
                initSearch();
            }
        });
    }

    private void initAdapter() {
        Log.d(TAG, "initAdapter: ");
        mAdapter = new CustomPagedListAdapter(getContext());
        mAdapter.setOnItemInteractionListener((View view, int position) -> {
            Toast.makeText(getContext(), position + " " + liveData.getValue().get(position).getUser(), Toast.LENGTH_SHORT).show();
            //start new activity with intent containing full url
            //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, MainFragment.newInstance()).commit();
            //Intent intent = new Intent(getContext(), ContainerActivity.class);
            //Intent intent = Utils.IntentUtils.buildImageFragmentIntent(getContext());
            //startActivity(intent);
        });

        initLiveDataObservations();

        RecyclerView recyclerView = getView().findViewById(R.id.recyclerView);
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
        Log.d(TAG, "initAdapter: end");
    }

    private void initLiveDataObservations() {
        liveData.observe(this, new Observer<PagedList<Hit>>() {
            @Override
            public void onChanged(PagedList<Hit> hits) {
                Log.d(TAG, "onChanged: ");
                mAdapter.submitList(hits);
            }
        });
    }

    private void initSearch() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Search Images on Pixabay");
        View viewInflated = LayoutInflater.from(getContext()).inflate(R.layout.user_input_dialog, (ViewGroup) getView(), false);
        final TextInputEditText input = viewInflated.findViewById(R.id.input);
        builder.setView(viewInflated);
        builder.setPositiveButton(android.R.string.ok, (DialogInterface dialog, int which) -> {
            dialog.dismiss();
            mSearchTerm = input.getText().toString();
            getActivity().setTitle(mSearchTerm + " Gallery");
            mSearchTerm = mSearchTerm.toLowerCase();
            mViewModel.fetchRequiredData(mSearchTerm);
            liveData = mViewModel.getLiveHitList();
            initLiveDataObservations();
        });

        builder.setNegativeButton(android.R.string.cancel, (DialogInterface dialog, int which) -> {
            dialog.cancel();
        });

        builder.show();
    }
}
