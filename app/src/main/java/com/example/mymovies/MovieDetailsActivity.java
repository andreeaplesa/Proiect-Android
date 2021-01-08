package com.example.mymovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class MovieDetailsActivity extends AppCompatActivity implements RatingDialog.DialogListener {
    Movie currentMovie;
    Movie movie;
    Movie movieJustAdded;
    RatingBar ratingBar;
    TextView tvVoteCount;
    MovieDB movieDB;
    Button btnVote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        overridePendingTransition(R.anim.slide_up, R.anim.stay);

        Bundle bundle = getIntent().getExtras();
        final long movieId = bundle.getLong("movieId");
        final ExtractMovie extractMovie = new ExtractMovie(movieId, false,this);
        extractMovie.execute();
        final Button btnAddWatchlist=findViewById(R.id.btnAddWatchlist);
        btnVote=findViewById(R.id.btnVote);
        ratingBar=findViewById(R.id.ratingBar);
        tvVoteCount=findViewById(R.id.tvVoteCount);
        movieDB=MovieDB.getInstanta(getApplicationContext());
        movie=movieDB.getMovieDao().getMovieById(movieId);
        if(movie!=null){
            btnAddWatchlist.setVisibility(View.GONE);
            ratingBar.setNumStars(5);
            ratingBar.setRating((float)(movie.getVote_average()*0.5));
            tvVoteCount.setText(String.valueOf(movie.getVote_count()));
            String text=tvVoteCount.getText().toString();
            btnVote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openDialog((float)(movie.getMy_rating()));
                }
            });


        }else{

            btnVote.setVisibility(View.GONE);
            btnAddWatchlist.setOnClickListener(new View.OnClickListener() {
            @Override
                    public void onClick(View v) {
                        ExtractMovie extractMovie1=new ExtractMovie(movieId,movieDB,true);
                        extractMovie1.execute();
                        btnAddWatchlist.setVisibility(View.GONE);
                        btnVote.setVisibility(View.VISIBLE);

                    }
                });
                btnVote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    movieJustAdded=movieDB.getMovieDao().getMovieById(movieId);
                    openDialog((float)(movieJustAdded.getMy_rating()));

                }
            });

        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.stay,R.anim.slide_down);
    }
    private void openDialog(float myRating){
        RatingDialog ratingDialog=new RatingDialog().newInstance(String.valueOf(myRating));
        ratingDialog.show(getSupportFragmentManager(),"ratingDialog");

    }


    @Override
    public void applyTexts(String myRating) {
        if (movie!=null){
            currentMovie=movie;
        }else{
            currentMovie=movieJustAdded;
        }
        if (currentMovie!=null){
        movieDB.getMovieDao().update(currentMovie.getMovieId(),Double.valueOf(myRating));
        btnVote.setText("Change your rating");
        ratingBar.setNumStars(5);
        ratingBar.setRating((float)(currentMovie.getVote_average()*0.5));
        tvVoteCount.setText(String.valueOf(currentMovie.getVote_count()));
        }
    }

}