package com.example.mymovies;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
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
        recyclerView = rootView.findViewById(R.id.moviesRecyclerView);
        Button discoverMoreButton = rootView.findViewById(R.id.btnDiscoverMore);
        discoverMoreButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(getActivity(), DiscoverMoreActivity.class);
               startActivity(intent);
           }
        });

        MovieDB movieDB=MovieDB.getInstanta(getContext());
        movieList=movieDB.getMovieDao().getAll();
        MovieAdapter adapter = new MovieAdapter(getContext(), movieList);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setAdapter(adapter);

        return rootView;

    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if ((resultCode == Activity.RESULT_OK)){
//            FragmentTransaction ft = getFragmentManager().beginTransaction();
//             ft.detach(this).attach(this).commit();}
//    }
}