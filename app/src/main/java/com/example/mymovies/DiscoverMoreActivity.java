package com.example.mymovies;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DiscoverMoreActivity extends AppCompatActivity {

    private List<Movie> movieList;
    private RecyclerView recyclerView;
    private int categoryId=-1;
    private String title="Top Rated Movies";

    //new
    SearchView sv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover_more);
        overridePendingTransition(R.anim.slide_up,R.anim.stay);



        movieList = new ArrayList<>();
        recyclerView = findViewById(R.id.discoverMoreRecyclerView);
        Intent intent = getIntent();

        if (intent.hasExtra("categoryId")) {
            Bundle bundle = intent.getExtras();
            categoryId=bundle.getInt("categoryId");
            title=bundle.getString("categoryName");
        }
        sv=findViewById(R.id.sv);

        ExtractDiscoverMoreMovies extractMovies = new ExtractDiscoverMoreMovies(getApplicationContext(), movieList, recyclerView, categoryId,sv);
        extractMovies.execute();
        setTitle(title);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.stay,R.anim.slide_down);

    }
}