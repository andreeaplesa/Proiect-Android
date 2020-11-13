package com.example.mymovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class DiscoverMoreMovieAdapter extends RecyclerView.Adapter<DiscoverMoreMovieAdapter.MyViewHolder> {

    private Context context;
    private List<Movie> movieList;
    private final String imageString = "https://image.tmdb.org/t/p/original";

    public DiscoverMoreMovieAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);

        view = inflater.inflate(R.layout.discover_more_movie_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //holder.id.setText(movieList.get(position).getId());
        //holder.name.setText(movieList.get(position).getName());

        // Using Glide library to display the image
        // https://image.tmdb.org/t/p/w500

        Glide.with(context)
                .load(imageString + movieList.get(position).getImage())
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        //private TextView id;
        //private TextView name;
        private ImageView img;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            //id = itemView.findViewById(R.id.id_tvt);
            //name = itemView.findViewById(R.id.discoverMoreNameTextView);
            img = itemView.findViewById(R.id.discoverMoreItemView);
        }
    }
}