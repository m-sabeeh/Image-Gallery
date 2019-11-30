package com.example.imagegallery.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.imagegallery.MyMotionActivity;
import com.example.imagegallery.MyMotionActivity2;
import com.example.imagegallery.utils.Injection;
import com.example.imagegallery.R;
import com.example.imagegallery.models.Hit;
import com.example.imagegallery.ui.adapters.CustomPagedListAdapter;
import com.example.imagegallery.utils.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class MainFragment extends Fragment implements SearchInputDialogFragment.SearchInputListener {
    private static final String TAG = "MainFragment";
    private MainViewModel mViewModel;
    private CustomPagedListAdapter mAdapter;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
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
        /*Toolbar toolbar = getView().findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);*/
        mViewModel = initViewModel();
        initAdapter();
        initFab();
        initLiveDataObservations();
        initSwipeRefreshLayout();
        setActivityTitle();
    }

    private void initSwipeRefreshLayout() {
        refreshLayout = Objects.requireNonNull(getView()).findViewById(R.id.swipeRefresh);
        refreshLayout.setColorSchemeResources(R.color.colorPrimary,R.color.colorPrimaryDark,R.color.colorPrimaryLight);
        refreshLayout.setOnRefreshListener(() -> {
            mViewModel.invalidateSource();
        });
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
        StaggeredGridLayoutManager staggeredGridManager =
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
        staggeredGridManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);//to correctly handle the decorations.
        //RecyclerView.LayoutManager linearLayoutManger = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(staggeredGridManager);
        recyclerView.setAdapter(mAdapter);
        int spacingPixels = getResources().getDimensionPixelSize(R.dimen.recycler_view_spacing);
        recyclerView.addItemDecoration(new SpaceItemDecoration(spacingPixels, columns));
    }

    private void initFab() {
        FloatingActionButton button = getView().findViewById(R.id.fab);
        button.setOnClickListener((View view) -> {
            buildDialogFragment();

            //Intent intent = new Intent(getContext(), MyMotionActivity2.class);
            //startActivity(intent);
            //shareTextUrl();

        });
    }

    private void shareTextUrl() {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        // Add data to the intent, the receiving app will decide
        // what to do with it.
        share.putExtra(Intent.EXTRA_SUBJECT, "Title Of The Post");
        share.putExtra(Intent.EXTRA_TEXT, "http://www.codeofaninja.com");

        startActivity(Intent.createChooser(share, "Share link!"));
    }

    private void initLiveDataObservations() {
        Log.d(TAG, "initLiveDataObservations: ");
        liveData = mViewModel.getLiveHitList();
        liveData.observe(this, new Observer<PagedList<Hit>>() {
            @Override
            public void onChanged(PagedList<Hit> hits) {
                Log.d(TAG, "onChanged: MainFragment");
                mAdapter.submitList(hits);
                refreshLayout.setRefreshing(false);
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
            recyclerView.scrollToPosition(dataPosition);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        dataPosition = -1;
    }
}
