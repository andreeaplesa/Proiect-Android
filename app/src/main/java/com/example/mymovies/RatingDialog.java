package com.example.mymovies;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class RatingDialog extends AppCompatDialogFragment {
    private EditText etRating;
    private DialogListener listener;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {


        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();

        View view=inflater.inflate(R.layout.rating_dialog,null);
        builder.setView(view)
                .setTitle("Your rating")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }

                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String myRating=etRating.getText().toString();
                        listener.applyTexts(myRating);
                        dialog.dismiss();

                    }
                });
        etRating=view.findViewById(R.id.etRating);
        etRating.setText(getArguments().getString("rating"));

        return builder.create();

    }
    public static RatingDialog newInstance(String msg) {
        RatingDialog fragment = new RatingDialog();

        Bundle bundle = new Bundle();
        bundle.putString("rating", msg);
        fragment.setArguments(bundle);

        return fragment;
    }
    public interface DialogListener{
        void applyTexts(String myRating);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
            listener=(DialogListener) context;
    }
}
