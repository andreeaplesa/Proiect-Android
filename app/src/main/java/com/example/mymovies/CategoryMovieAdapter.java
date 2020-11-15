package com.example.mymovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class CategoryMovieAdapter extends RecyclerView.Adapter<CategoryMovieAdapter.MyViewHolder> {

    private Context context;
    private List<Movie> movieList;

    public CategoryMovieAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.discover_fragment_card_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String posterImageString = "https://image.tmdb.org/t/p/w780";
        Glide.with(context)
                .load(posterImageString + movieList.get(position).getPoster_path())
                .into(holder.discoverImageView);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView discoverImageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            discoverImageView = itemView.findViewById(R.id.discoverImageView);
        }
    }
}
