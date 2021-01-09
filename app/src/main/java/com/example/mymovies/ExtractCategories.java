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
import java.util.List;

public class ExtractCategories extends AsyncTask<String, Void, String> {
    private Context context;
    private List<MovieCategory> categoryList;
    private RecyclerView recyclerView;

    public ExtractCategories(Context context, List<MovieCategory> categoryList, RecyclerView recyclerView) {
        this.context = context;
        this.categoryList = categoryList;
        this.recyclerView = recyclerView;
    }

    @Override
    protected String doInBackground(String... strings) {
        String current = "";
        try{
            URL url;
            HttpURLConnection urlConnection = null;

            try{
                String genres = "https://api.themoviedb.org/3/genre/movie/list?api_key=47a63a793ef28004cb08a49ec20932d0";
                url = new URL(genres);
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

            JSONArray jsonArray = jsonObject.getJSONArray("genres");
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonCategory = jsonArray.getJSONObject(i);

                MovieCategory movieCategory = new MovieCategory();
                movieCategory.setCategoryId(jsonCategory.getInt("id"));
                movieCategory.setCategoryName(jsonCategory.getString("name"));

                categoryList.add(movieCategory);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        PutDataIntoRecyclerView(categoryList);
    }

    private void PutDataIntoRecyclerView(List<MovieCategory> categoryList){
        CategoryAdapter categoryAdapter = new CategoryAdapter(categoryList);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        recyclerView.setAdapter(categoryAdapter);
    }
}
