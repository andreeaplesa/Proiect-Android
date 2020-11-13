package com.example.mymovies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MovieDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        overridePendingTransition(R.anim.slide_up,R.anim.stay);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.stay,R.anim.slide_down);
    }
}