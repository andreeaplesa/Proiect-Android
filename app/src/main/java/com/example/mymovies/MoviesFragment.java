package com.example.mymovies;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MoviesFragment extends Fragment{

    private List<Movie> movieList;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_movies, container, false);

        movieList = new ArrayList<>();
        recyclerView = rootView.findViewById(R.id.moviesRecyclerView);

//        ExtractMovies extractMovies = new ExtractMovies(getActivity(), movieList, recyclerView, this.getClass().toString());
//        extractMovies.execute();



        Button discoverMoreButton = rootView.findViewById(R.id.btnDiscoverMore);
        discoverMoreButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(getActivity(), DiscoverMoreActivity.class);
               startActivity(intent);
           }
        });
        return rootView;
    }
}