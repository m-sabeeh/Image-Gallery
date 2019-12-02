package com.example.imagegallery.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.imagegallery.R;
import com.example.imagegallery.databinding.FragmentMainBinding;
import com.example.imagegallery.ui.adapters.CustomPagedListAdapter;
import com.example.imagegallery.utils.Injection;
import com.example.imagegallery.utils.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class MainFragment extends Fragment implements SearchInputDialogFragment.SearchInputListener {
    private static final String TAG = "MainFragment";
    private MainViewModel mViewModel;
    private CustomPagedListAdapter mAdapter;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private int dataPosition = -1;
    FragmentMainBinding fragmentMainBinding;
    Bundle bundle = new Bundle();

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        fragmentMainBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_main, container, false);

        /*FragmentMainBinding fragmentMainBinding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.page_view_pager2, parent, false);*/

        return fragmentMainBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        /*Toolbar toolbar = getView().findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);*/
        Log.d(TAG, "onActivityCreated: " + savedInstanceState);
        mViewModel = initViewModel(savedInstanceState);
        //MainViewModel.orientation = getResources().getConfiguration().orientation;
        fragmentMainBinding.setMainViewModel(mViewModel);
        initAdapter();
        initFab();
        initLiveDataObservations();
        initSwipeRefreshLayout();
    }

    private void initSwipeRefreshLayout() {
        refreshLayout = Objects.requireNonNull(getView()).findViewById(R.id.swipeRefresh);
        refreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorPrimaryLight);
        refreshLayout.setOnRefreshListener(() -> {
            mViewModel.invalidateSource();
        });
    }

    // TODO: 03/12/2019 Do I really need SavedStateViewModel?
    private MainViewModel initViewModel(Bundle savedInstanceState) {
        Log.d(TAG, "initViewModel: ");
        ViewModelProvider.Factory factory = Injection.getViewModelFactory(this, savedInstanceState);
        return ViewModelProviders.of(this, factory).get(MainViewModel.class);
    }

    //@BindingAdapter("android:initAdapter")
    // @BindingAdapter("android:initAdapter")
    public void initAdapter() {
        mAdapter = new CustomPagedListAdapter(getContext());
        mAdapter.setOnItemInteractionListener((View view, int position) -> {
            //start new activity with intent containing full url
            Intent intent = Utils.IntentUtils.buildImageFragmentIntent(getContext());
            intent.putExtra(Utils.IntentUtils.POSITION, position);
            startActivityForResult(intent, Utils.IntentUtils.CODE_RETURN_POSITION);
        });
        recyclerView = fragmentMainBinding.recyclerView;
        //recyclerView = Objects.requireNonNull(getView()).findViewById(R.id.recyclerView);
        //int columns = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 3 : 2;
        /*StaggeredGridLayoutManager staggeredGridManager =
                new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL) {
                    *//* *
         * Disable predictive animations. There is a bug in RecyclerView which causes views that
         * are being reloaded to pull invalid ViewHolders from the internal recycler stack if the
         * adapter size has decreased since the ViewHolder was recycled.*//*

                    @Override
                    public boolean supportsPredictiveItemAnimations() {
                        return false;
                    }
                };*/
        //staggeredGridManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);//to correctly handle the decorations.
        //RecyclerView.LayoutManager linearLayoutManger = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        //recyclerView.setLayoutManager(staggeredGridManager);
        recyclerView.setAdapter(mAdapter);
        //int spacingPixels = getResources().getDimensionPixelSize(R.dimen.recycler_view_spacing);
        //recyclerView.addItemDecoration(new SpaceItemDecoration(spacingPixels, 2));
    }


   /* public static void initAdapter(View view, int i) {
        mAdapter = new CustomPagedListAdapter(getContext());
        mAdapter.setOnItemInteractionListener((View view, int position) -> {
            //start new activity with intent containing full url
            Intent intent = Utils.IntentUtils.buildImageFragmentIntent(getContext());
            intent.putExtra(Utils.IntentUtils.POSITION, position);
            startActivityForResult(intent, Utils.IntentUtils.CODE_RETURN_POSITION);
        });

        recyclerView = Objects.requireNonNull(getView()).findViewById(R.id.recyclerView);
        int columns = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 3 : 2;
        StaggeredGridLayoutManager staggeredGridManager =
                new StaggeredGridLayoutManager(columns, LinearLayoutManager.VERTICAL) {
                    *//* *
     * Disable predictive animations. There is a bug in RecyclerView which causes views that
     * are being reloaded to pull invalid ViewHolders from the internal recycler stack if the
     * adapter size has decreased since the ViewHolder was recycled.*//*

                    @Override
                    public boolean supportsPredictiveItemAnimations() {
                        return false;
                    }
                };
        staggeredGridManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);//to correctly handle the decorations.
        //RecyclerView.LayoutManager linearLayoutManger = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(staggeredGridManager);
        recyclerView.setAdapter(mAdapter);
        int spacingPixels = getResources().getDimensionPixelSize(R.dimen.recycler_view_spacing);
        recyclerView.addItemDecoration(new SpaceItemDecoration(spacingPixels, columns));
    }*/

    private void initFab() {
        FloatingActionButton button = getView().findViewById(R.id.fab);
        button.setOnClickListener((View view) -> {
            buildDialogFragment();

            //Intent intent = new Intent(getContext(), MyMotionActivity2.class);
            //startActivity(intent);
            //shareTextUrl();

        });
    }

    private void initLiveDataObservations() {
        mViewModel.mLiveData.observe(this, hits -> {
            mAdapter.submitList(hits);
            refreshLayout.setRefreshing(false);
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
        getActivity().setTitle(s + " Gallery");
    }

    @Override
    public void onInputFinished(String query) {
        mViewModel.setSearchTerm(query);
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
            recyclerView.scrollToPosition(dataPosition);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        dataPosition = -1;
    }
}
