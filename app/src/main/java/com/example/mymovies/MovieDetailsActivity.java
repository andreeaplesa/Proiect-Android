package com.example.mymovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailsActivity extends AppCompatActivity implements RatingDialog.DialogListener {
    Movie movie;
    RatingBar ratingBar;
    TextView tvVoteCount;
    MovieDB movieDB;
    Button btnVote;
    ImageView imageView;
    Long movieId;
    TextView tvVoteAverage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        overridePendingTransition(R.anim.slide_up, R.anim.stay);

        Bundle bundle = getIntent().getExtras();
        movieId = bundle.getLong("movieId");
        final Button btnAddWatchlist=findViewById(R.id.btnAddWatchlist);
        btnVote=findViewById(R.id.btnVote);
        ratingBar=findViewById(R.id.ratingBar);
        tvVoteCount=findViewById(R.id.tvVoteCount);
        movieDB=MovieDB.getInstanta(getApplicationContext());
        movie=movieDB.getMovieDao().getMovieById(movieId);
        tvVoteAverage=findViewById(R.id.tvVoteAverage);
        if (movie!=null){
            btnAddWatchlist.setVisibility(View.GONE);
            imageView=findViewById(R.id.imgViewPoster);
            String imageString = "https://image.tmdb.org/t/p/original";
            Glide.with(getApplication())
                    .load(imageString + movie.getBackdrop_path())
                    .into(imageView);

            TextView tvTitle=findViewById(R.id.tvTitle);
            tvTitle.setText(movie.getTitle());

            TextView tvRuntime=findViewById(R.id.tvRuntime);
            tvRuntime.setText(movie.getRuntime()+"");
            List<MovieCategoriesCrossRef> movieCategoriesCrossRef=movieDB.getMovieWithCategoriesDao().getMovieCategoryByMovieId(movieId);

            List<MovieCategory> categories=new ArrayList<MovieCategory>();

            for(MovieCategoriesCrossRef movieCategoriesCR:movieCategoriesCrossRef){
                categories.add( movieDB.getMovieCategoryDao().getMovieCategoryById((int)(movieCategoriesCR.getCategoryId())));
            }
            String allCategories=categories.get(0).getCategoryName()+", ";
            for (int i=1;i<categories.size()-1;i++){
                allCategories+=categories.get(i).getCategoryName()+", ";
            }
            allCategories+=categories.get(categories.size()-1).getCategoryName();
            TextView tvCategories=findViewById(R.id.tvCategories);
            tvCategories.setText(allCategories);
            TextView tvReleaseDate=findViewById(R.id.tvReleaseDate);
            tvReleaseDate.setText(movie.getRelease_date());
            RatingBar ratingBar=findViewById(R.id.ratingBar);
            ratingBar.setNumStars(5);
            ratingBar.setRating((float) (movie.getVote_average()*0.5));

            double voteAverage=movie.getVote_average()*0.5;


            tvVoteAverage.setText(String.format("%.3f",voteAverage)+"/5");

            TextView tvVoteCount=findViewById(R.id.tvVoteCount);
            tvVoteCount.setText(movie.getVote_count()+"");
            TextView tvOverview=findViewById(R.id.overview);
            tvOverview.setText(movie.getOverview());

            if (movie.getMy_rating()!=0){
                btnVote.setText("Edit your vote");
            }
            btnVote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    float rating=(float)(movie.getMy_rating()*0.5);
                    openDialog(rating);
                }
            });

        }
        //daca l-am luat din API
        else{
            final ExtractMovie extractMovie = new ExtractMovie(movieId, false,this);
            extractMovie.execute();
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
                    movie=movieDB.getMovieDao().getMovieById(movieId);
                    float rating=(float)(movie.getMy_rating()*0.5);
                    openDialog(rating);

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
    public void applyTexts(String myRating,boolean newVote) {
        if (newVote){
            movieDB.getMovieDao().updateVoteCount(movieId);
        }
        movieDB.getMovieDao().update(movie.getMovieId(),Double.valueOf(myRating)*2);
        movie=movieDB.getMovieDao().getMovieById(movieId);
        btnVote.setText("Change your rating");
        ratingBar.setNumStars(5);
        ratingBar.setRating((float)(movie.getVote_average()*0.5));
        tvVoteCount.setText(String.valueOf(movie.getVote_count()));
        tvVoteAverage.setText(String.format("%.3f",movie.getVote_average()*0.5)+"/5");

    }

}