package com.example.imagegallery.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.imagegallery.R;
import com.example.imagegallery.databinding.FragmentImageListBinding;
import com.example.imagegallery.utils.Injection;
import com.example.imagegallery.utils.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

public class ImageListFragment extends Fragment implements SearchInputDialogFragment.SearchInputListener {
    private static final String TAG = "MainFragment";
    private ImageListViewModel mViewModel;
    private ImageListAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private FragmentImageListBinding binding;

    public static ImageListFragment newInstance() {
        return new ImageListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_image_list, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String search = preferences.getString(Utils.General.SEARCH_TERM, "");
        Bundle bundle = new Bundle();
        if (!search.equalsIgnoreCase("")) {
            bundle.putString(Utils.General.SEARCH_TERM, search);
        }
        binding.setViewModel(mViewModel = initViewModel(bundle));
        initAdapter();
        initFab();
        initLiveDataObservations();
    }

    // TODO: 03/12/2019 Do I really need SavedStateViewModel?
    private ImageListViewModel initViewModel(@Nullable Bundle savedInstanceState) {
        ViewModelProvider.Factory factory = Injection.getViewModelFactory(this, savedInstanceState);
        return ViewModelProviders.of(this, factory).get(ImageListViewModel.class);
    }

    private void initAdapter() {
        mAdapter = new ImageListAdapter();
        mAdapter.setOnItemInteractionListener((View view, int position) -> {
            Intent intent = Utils.IntentUtils.buildImageFragmentIntent(getContext());
            intent.putExtra(Utils.General.POSITION, position);
            startActivityForResult(intent, Utils.General.CODE_RETURN_POSITION);
        });
        mRecyclerView = binding.recyclerView;
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initFab() {
        //fall back to viewbinding?
        FloatingActionButton button = binding.fab;
        button.setOnClickListener((View view) -> {
            buildDialogFragment();
        });
    }

    private void initLiveDataObservations() {
        mViewModel.mLiveData.observe(this, hits -> {
            mAdapter.submitList(hits);
            binding.swipeRefresh.setRefreshing(false);
        });

        mViewModel.searchTermLiveData.observe(this, this::setActivityTitle);
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

    private void setActivityTitle(String s) {
        Activity activity = getActivity();
        if (activity != null)
            activity.setTitle(getResources().getString(R.string.activity_title, s));
    }

    @Override
    public void onInputFinished(String query) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Utils.General.SEARCH_TERM, query);
        editor.apply();
        mViewModel.setSearchTerm(query);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == Utils.General.CODE_RETURN_POSITION
                && resultCode == Activity.RESULT_OK
                && data != null) {
            int dataPosition = data.getIntExtra(Utils.General.RETURN_POSITION, -1);
            if (mRecyclerView != null && Integer.signum(dataPosition) == 1) {
                mRecyclerView.smoothScrollToPosition(dataPosition);
            }
        }
    }
}

