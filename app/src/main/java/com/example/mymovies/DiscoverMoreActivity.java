package com.example.mymovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class DiscoverMoreActivity extends AppCompatActivity {

    private List<Movie> movieList;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover_more);

        movieList = new ArrayList<>();
        recyclerView = findViewById(R.id.discoverMoreRecyclerView);

        ExtractDiscoverMoreMovies extractMovies = new ExtractDiscoverMoreMovies(getApplicationContext(), movieList, recyclerView);
        extractMovies.execute();
    }
}