package com.example.mymovies;

import android.content.Context;
import android.os.AsyncTask;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    public ExtractMovie(long id, MovieDB movieDB) {
        this.id = id;
        this.movieDB = movieDB;
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

            Movie movie = new Movie();
            movie.setMovieId(jsonObject.getLong("id"));
            movie.setTitle(jsonObject.getString("title"));
            movie.setOverview(jsonObject.getString("overview"));

            movie.setRuntime(jsonObject.getInt("runtime"));

            movie.setRelease_date(jsonObject.getString("release_date"));

            movie.setVote_average(jsonObject.getDouble("vote_average"));
            movie.setVote_count(jsonObject.getInt("vote_count"));

            movie.setBackdrop_path(jsonObject.getString("backdrop_path"));
            movie.setPoster_path(jsonObject.getString("poster_path"));

            movie.setRevenue(jsonObject.getInt("revenue"));
            movie.setMy_rating(0);

            JSONArray genres = jsonObject.getJSONArray("genres");
            List<MovieCategory> categories = new ArrayList<>();
            List<MovieCategoriesCrossRef> movieCategoriesCrossRefsList = new ArrayList<>();
            for(int j=0;j<genres.length(); j++){
                JSONObject genre = genres.getJSONObject(j);

                MovieCategory movieCategory = new MovieCategory();
                movieCategory.setCategoryId(genre.getInt("id"));
                movieCategory.setCategoryName(genre.getString("name"));

                categories.add(movieCategory);

                MovieCategoriesCrossRef movieCategoriesCrossRef = new MovieCategoriesCrossRef();
                movieCategoriesCrossRef.setMovieId(id);
                movieCategoriesCrossRef.setCategoryId(genre.getInt("id"));

                movieCategoriesCrossRefsList.add(movieCategoriesCrossRef);
            }

            movieDB.getMovieDao().insert(movie);

            movieDB.getMovieWithCategoriesDao().insert(movieCategoriesCrossRefsList);

            movieDB.getMovieCategoryDao().insert(categories);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
