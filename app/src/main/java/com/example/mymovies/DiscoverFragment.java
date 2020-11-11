package com.example.mymovies;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import java.util.ArrayList;
import java.util.List;

public class DiscoverFragment extends Fragment {

    private static String JSON_URL = "https://run.mocky.io/v3/7108e07d-f6cd-4ecc-bca9-74d9a5fa0330";

    List<Movie> movieList;
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_discover, container, false);

        movieList = new ArrayList<>();
        recyclerView = rootView.findViewById(R.id.recyclerView);

        GetMoviesData getMoviesData = new GetMoviesData();
        getMoviesData.execute();

        return rootView;
    }

    public class GetMoviesData extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... strings) {

            String current = "";
            try{
                URL url;
                HttpURLConnection urlConnection = null;

                try{
                    url = new URL(JSON_URL);
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

                JSONArray jsonArray = jsonObject.getJSONArray("movies");
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jsonMovie = jsonArray.getJSONObject(i);
                    Movie movie = new Movie();
                    movie.setId(jsonMovie.getString("id"));
                    movie.setName(jsonMovie.getString("name"));
                    movie.setImage(jsonMovie.getString("image"));

                    movieList.add(movie);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            PutDataIntoRecyclerView(movieList);
        }
    }

    private void PutDataIntoRecyclerView(List<Movie> movieList){
        MoviesAdapter adapter = new MoviesAdapter(getActivity(), movieList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.setAdapter(adapter);
    }
}