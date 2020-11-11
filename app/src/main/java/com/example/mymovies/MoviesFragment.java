package com.example.mymovies;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MoviesFragment extends Fragment {

    private List<Movie> movieList;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_discover, container, false);

        movieList = new ArrayList<>();
        recyclerView = rootView.findViewById(R.id.recyclerView);

        ExtractMovies extractMovies = new ExtractMovies(getContext(), movieList, recyclerView);
        extractMovies.execute();

        return rootView;
    }
}