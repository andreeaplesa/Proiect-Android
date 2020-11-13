package com.example.mymovies;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class ExtractDiscoverMoreMovies extends AsyncTask<String, Void, String> {
    // Custom movies
    //private String JSON_URL = "https://run.mocky.io/v3/7108e07d-f6cd-4ecc-bca9-74d9a5fa0330";

    private String top_rated_movies = "https://api.themoviedb.org/3/movie/top_rated?api_key=47a63a793ef28004cb08a49ec20932d0";


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

            } catch (MalformedURLException e) {
                e.printStackTrace();
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
                movie.setId(jsonMovie.getString("vote_average"));
                movie.setName(jsonMovie.getString("title"));
                movie.setImage(jsonMovie.getString("backdrop_path"));

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
