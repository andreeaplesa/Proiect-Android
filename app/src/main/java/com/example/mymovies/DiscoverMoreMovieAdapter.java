package com.example.mymovies;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.nio.charset.MalformedInputException;
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

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        private ImageView img;
        private ImageButton checkImgButton;
        private boolean pressed = false;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.discoverMoreItemView);
            cardView = itemView.findViewById(R.id.discoverMoreCardView);
            checkImgButton = itemView.findViewById(R.id.discoverMoreCheckImageButton);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), MovieDetailsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    v.getContext().startActivity(intent);
                }
            });

            checkImgButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(pressed)
                        v.setBackground(v.getContext().getDrawable(R.drawable.ic_add_16_with_background));
                    else
                        v.setBackground(v.getContext().getDrawable(R.drawable.ic_check_16_with_background));
                    pressed=!pressed;
                }
            });
        }
    }
}
