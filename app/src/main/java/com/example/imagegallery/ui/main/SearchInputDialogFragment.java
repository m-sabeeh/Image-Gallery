package com.example.imagegallery.ui.main;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imagegallery.R;
import com.example.imagegallery.utils.Injection;
import com.google.android.material.textfield.TextInputEditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

public class SearchInputDialogFragment extends DialogFragment {


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        //MainViewModel mainViewModel = initViewModel();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View viewInflated = LayoutInflater.from(getContext())
                .inflate(R.layout.user_input_dialog, (ViewGroup) getView(), false);
        final TextInputEditText input = viewInflated.findViewById(R.id.input);
        //input.requestFocus();
        final TextView titleText = viewInflated.findViewById(R.id.titleDialog);
        titleText.setText(getString(R.string.dialog_title));
        builder.setView(viewInflated);
        builder.setPositiveButton(android.R.string.ok, (DialogInterface dialog, int which) -> {
            dialog.dismiss();
            String searchTerm = input.getText().toString().trim();
            if (!searchTerm.isEmpty()) {
                //mainViewModel.setSearchTerm(searchTerm);
                sendResultBack(searchTerm);
            } else
                Toast.makeText(getActivity(), "Search term is empty", Toast.LENGTH_SHORT).show();
        });

        builder.setNegativeButton(android.R.string.cancel, (DialogInterface dialog, int which) -> {
            dialog.cancel();
        });
        return builder.create();

    }

   /* private MainViewModel initViewModel() {
        //ViewModelProvider.Factory factory = Injection.getViewModelFactory();
        return ViewModelProviders.of(this, factory).get(MainViewModel.class);
    }*/

    private void sendResultBack(String s) {
        SearchInputListener inputListener = (SearchInputListener) getTargetFragment();
        if (inputListener != null)
            inputListener.onInputFinished(s);
    }

    public interface SearchInputListener {
        void onInputFinished(String query);
    }
}
