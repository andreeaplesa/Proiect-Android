package com.example.mymovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

public class DiscoverMoreActivity extends AppCompatActivity {

    private List<Movie> movieList;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover_more);
        overridePendingTransition(R.anim.slide_up,R.anim.stay);

        movieList = new ArrayList<>();
        recyclerView = findViewById(R.id.discoverMoreRecyclerView);

        ExtractDiscoverMoreMovies extractMovies = new ExtractDiscoverMoreMovies(getApplicationContext(), movieList, recyclerView);
        extractMovies.execute();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.stay,R.anim.slide_down);
    }
}