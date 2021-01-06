package com.example.mymovies;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DiscoverMoreActivity extends AppCompatActivity {

    private List<Movie> movieList;
    private RecyclerView recyclerView;
    private int categoryId=-1;
    private String title="Top Rated Movies";

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
        ExtractDiscoverMoreMovies extractMovies = new ExtractDiscoverMoreMovies(getApplicationContext(), movieList, recyclerView, categoryId);
        extractMovies.execute();
        setTitle(title);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.stay,R.anim.slide_down);
        //setResult(Activity.RESULT_OK);
    }
}