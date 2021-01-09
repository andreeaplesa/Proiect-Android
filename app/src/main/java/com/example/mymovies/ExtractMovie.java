package com.example.mymovies;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ExtractMovie extends AsyncTask<String, Void, String> {

    private MovieDB movieDB;
    private long id;
    private boolean source;
    private Movie movie;
    private Activity activity;


    public ExtractMovie(long id, MovieDB movieDB,boolean source) {
        this.id = id;
        this.movieDB = movieDB;
        this.source=source;
    }
    public ExtractMovie(long id,boolean source,Activity activity) {
        this.id = id;
        this.source=source;
        this.activity=activity;
    }


    @Override
    protected String doInBackground(String... strings) {
        String current = "";
        try{
            URL url;
            HttpURLConnection urlConnection = null;

            try{
                String movie = "https://api.themoviedb.org/3/movie/" + id + "?api_key=47a63a793ef28004cb08a49ec20932d0";
                url = new URL(movie);
                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream is = urlConnection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);

                int data = isr.read();
                while(data != -1){
                    current += (char) data;
                    data = isr.read();
                }

                return current;
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(urlConnection != null){
                    urlConnection.disconnect();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return current;
    }

    @Override
    protected void onPostExecute(String s) {
        try{
            JSONObject jsonObject = new JSONObject(s);

            movie=new Movie();
            movie.setMovieId(jsonObject.getLong("id"));
            movie.setTitle(jsonObject.getString("title"));
            movie.setOverview(jsonObject.getString("overview"));

            movie.setRuntime(jsonObject.getInt("runtime"));

            movie.setRelease_date(jsonObject.getString("release_date"));

            movie.setVote_average(jsonObject.getDouble("vote_average"));
            movie.setVote_count(jsonObject.getInt("vote_count"));

            movie.setBackdrop_path(jsonObject.getString("backdrop_path"));
            movie.setPoster_path(jsonObject.getString("poster_path"));

            movie.setMy_rating(0);

            JSONArray genres = jsonObject.getJSONArray("genres");
            List<MovieCategory> categories = new ArrayList<>();
            if (source){
                List<MovieCategoriesCrossRef> movieCategoriesCrossRefsList = new ArrayList<>();
                List<MovieCategory> categoriesDB=movieDB.getMovieCategoryDao().getAllCategories();
                for(int j=0;j<genres.length(); j++){
                    JSONObject genre = genres.getJSONObject(j);

                    MovieCategory movieCategory = new MovieCategory();
                    movieCategory.setCategoryId(genre.getInt("id"));
                    movieCategory.setCategoryName(genre.getString("name"));

                    if (categoriesDB.size()==0){
                        categories.add(movieCategory);
                    }else{
                        boolean verify=true;
                        for (MovieCategory mc:categoriesDB){
                            if (movieCategory.getCategoryId()==mc.getCategoryId()){
                                verify=false;
                            }
                        }
                        if (verify){
                            categories.add(movieCategory);
                        }
                    }

                    MovieCategoriesCrossRef movieCategoriesCrossRef = new MovieCategoriesCrossRef();
                    movieCategoriesCrossRef.setMovieId(id);
                    movieCategoriesCrossRef.setCategoryId(genre.getInt("id"));

                    movieCategoriesCrossRefsList.add(movieCategoriesCrossRef);
                }

                movieDB.getMovieDao().insert(movie);
                movieDB.getMovieCategoryDao().insert(categories);

                movieDB.getMovieWithCategoriesDao().insert(movieCategoriesCrossRefsList);

            }else{
                for(int j=0;j<genres.length(); j++){
                    JSONObject genre = genres.getJSONObject(j);

                    MovieCategory movieCategory = new MovieCategory();
                    movieCategory.setCategoryId(genre.getInt("id"));
                    movieCategory.setCategoryName(genre.getString("name"));
                    categories.add(movieCategory);
                }
                ImageView imageView=activity.findViewById(R.id.imgViewPoster);
                String imageString = "https://image.tmdb.org/t/p/original";
                Glide.with(activity.getApplication())
                        .load(imageString + movie.getBackdrop_path())
                        .into(imageView);

                TextView tvTitle=activity.findViewById(R.id.tvTitle);
                tvTitle.setText(movie.getTitle());

                TextView tvRuntime=activity.findViewById(R.id.tvRuntime);
                tvRuntime.setText(String.valueOf(movie.getRuntime()/60) + " h " + String.valueOf(movie.getRuntime()%60) + " min");

                String allCategories=categories.get(0).getCategoryName()+", ";
                for (int i=1;i<categories.size()-1;i++){
                    allCategories+=categories.get(i).getCategoryName()+", ";
                }
                allCategories+=categories.get(categories.size()-1).getCategoryName();
                TextView tvCategories=activity.findViewById(R.id.tvCategories);
                tvCategories.setText(allCategories);

                TextView tvReleaseDate=activity.findViewById(R.id.tvReleaseDate);
                tvReleaseDate.setText(movie.getRelease_date());

                RatingBar ratingBar=activity.findViewById(R.id.ratingBar);
                ratingBar.setNumStars(5);
                ratingBar.setRating((float) (movie.getVote_average()*0.5));

                double voteAverage=movie.getVote_average()*0.5;
                TextView tvVoteAverage=activity.findViewById(R.id.tvVoteAverage);
                tvVoteAverage.setText(voteAverage+"/5");

                TextView tvVoteCount=activity.findViewById(R.id.tvVoteCount);
                tvVoteCount.setText(movie.getVote_count()+"");


                TextView tvOverview=activity.findViewById(R.id.overview);
                tvOverview.setText(movie.getOverview());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public Activity getActivity() {
        return activity;
    }
}
