package com.example.imagegallery.ui.main;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.imagegallery.models.Hit;
import com.example.imagegallery.R;
import com.example.imagegallery.ui.adapters.CustomPagedListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class MainFragment extends Fragment {
    private static final String TAG = "MainFragment";
    private MainViewModel mViewModel;
    private CustomPagedListAdapter mAdapter;
    private LiveData<PagedList<Hit>> liveData;
    ProgressDialog progressDialog;
    String mSearchTerm;
    int count = 1;
    public static final String DEFAULT_SEARCH_TERM = "abstract";

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
        //mAdapter.submitList(liveData.getValue());
        liveData.observe(this, new Observer<PagedList<Hit>>() {
            @Override
            public void onChanged(PagedList<Hit> hits) {
                if (mAdapter != null && !hits.isEmpty()) {
                    mAdapter.submitList(hits);
                    //mAdapter.notifyItemRangeChanged(0, mAdapter.getItemCount());
                    //dialog.dismiss();
                    //count = hits.size();
                    mViewModel.invalidateDataSource();
                }
                Log.d(TAG, "onChanged: MainFragment" + hits);
                mAdapter.submitList(hits);

                mViewModel.invalidateDataSource();

                //mAdapter.submitList(hits);
            }
        });
        initRecyclerView();
        FloatingActionButton button = getView().findViewById(R.id.fab);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: ");
                mViewModel.invalidateDataSource();
                //initSearch();


            }
        });
    }

    private void initSearch() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Search Images on Pixabay");
        View viewInflated = LayoutInflater.from(getContext()).inflate(R.layout.user_input_dialog, (ViewGroup) getView(), false);
        final TextInputEditText input = viewInflated.findViewById(R.id.input);
        builder.setView(viewInflated);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                mSearchTerm = input.getText().toString();
                mViewModel.fetchRequiredData(mSearchTerm);
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }


    private void initRecyclerView() {
        RecyclerView recyclerView = getView().findViewById(R.id.recyclerView);
        mAdapter = new CustomPagedListAdapter(getContext(), liveData.getValue());
        mAdapter.setOnItemInteractionListener(new CustomPagedListAdapter.OnItemInteractionListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getContext(), position + " " + mViewModel.getLiveHitList().getValue().get(position).getUser(), Toast.LENGTH_SHORT).show();
                mAdapter.submitList(mViewModel.getLiveHitList().getValue());
                //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, MainFragment.newInstance()).commit();
                //Intent intent = new Intent(getContext(), ContainerActivity.class);
                //Intent intent = Utils.IntentUtils.buildImageFragmentIntent(getContext());
                //startActivity(intent);
            }
        });

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
}
