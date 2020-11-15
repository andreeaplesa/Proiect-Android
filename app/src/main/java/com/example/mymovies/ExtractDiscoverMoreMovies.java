package com.example.mymovies;

import android.content.Context;
import android.os.AsyncTask;

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

public class ExtractDiscoverMoreMovies extends AsyncTask<String, Void, String> {
    private Context context;
    private List<Movie> movieList;
    private RecyclerView recyclerView;

    public ExtractDiscoverMoreMovies(Context context, List<Movie> movieList, RecyclerView recyclerView) {
        this.context = context;
        this.movieList = movieList;
        this.recyclerView = recyclerView;
    }

    @Override
    protected String doInBackground(String... strings) {
        String current = "";
        try{
            URL url;
            HttpURLConnection urlConnection = null;

            try{
                String top_rated_movies = "https://api.themoviedb.org/3/movie/top_rated?api_key=47a63a793ef28004cb08a49ec20932d0";
                url = new URL(top_rated_movies);
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

            JSONArray jsonArray = jsonObject.getJSONArray("results");
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonMovie = jsonArray.getJSONObject(i);

                Movie movie = new Movie();
                movie.setId(jsonMovie.getLong("id"));
                movie.setTitle(jsonMovie.getString("title"));
                movie.setOverview(jsonMovie.getString("overview"));

                // !!! Pentru runtime ar trebui sa cautam filmul dupa id si sa gasim runtime !!!
                //movie.setRuntime(jsonMovie.getInt("runtime"));

                movie.setRelease_date(jsonMovie.getString("release_date"));

                JSONArray genres = jsonMovie.getJSONArray("genre_ids");
                List<Integer> genres_ids = new ArrayList<>();
                for(int j=0;j<genres.length(); j++){
                    genres_ids.add(genres.getInt(j));
                }

                movie.setGenres(genres_ids);

                movie.setVote_average(jsonMovie.getDouble("vote_average"));
                movie.setVote_count(jsonMovie.getInt("vote_count"));

                movie.setBackdrop_path(jsonMovie.getString("backdrop_path"));
                movie.setPoster_path(jsonMovie.getString("poster_path"));

                movieList.add(movie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        PutDataIntoRecyclerView(movieList);
    }

    private void PutDataIntoRecyclerView(List<Movie> movieList){
        DiscoverMoreMovieAdapter adapter = new DiscoverMoreMovieAdapter(context, movieList);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        recyclerView.setAdapter(adapter);
    }

}
