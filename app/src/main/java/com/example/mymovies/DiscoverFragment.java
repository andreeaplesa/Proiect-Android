package com.example.mymovies;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DiscoverFragment extends Fragment {
    private List<MovieCategory> movieCategories;

    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView =  inflater.inflate(R.layout.fragment_discover, container, false);

        movieCategories = new ArrayList<>();
        recyclerView = rootView.findViewById(R.id.discoverRecyclerView);

        ExtractCategories extractCategories = new ExtractCategories(getActivity(), movieCategories, recyclerView);
        extractCategories.execute();

        return rootView;
    }
}