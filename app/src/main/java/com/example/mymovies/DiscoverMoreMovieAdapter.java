package com.example.mymovies;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class DiscoverMoreMovieAdapter extends RecyclerView.Adapter<DiscoverMoreMovieAdapter.MyViewHolder> {

    private Context context;
    private List<Movie> movieList;

    public DiscoverMoreMovieAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);

        view = inflater.inflate(R.layout.discover_more_card_view, parent, false);


        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // Using Glide library to display the image
        // https://image.tmdb.org/t/p/w500
        holder.tvMovieTitle.setText(movieList.get(position).getTitle());

        String imageString = "https://image.tmdb.org/t/p/original";
        Glide.with(context)
                .load(imageString + movieList.get(position).getBackdrop_path())
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvMovieTitle;
        private TextView tvMovieRuntime;
        private TextView tvMovieGenres;
        private ImageView img;
        private boolean pressed = false;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvMovieTitle = itemView.findViewById(R.id.tvDiscoverMore_Title);
            tvMovieTitle = itemView.findViewById(R.id.tvDiscoverMore_Title);
            tvMovieTitle = itemView.findViewById(R.id.tvDiscoverMore_Title);

            img = itemView.findViewById(R.id.discoverMoreImageView);
            CardView cardView = itemView.findViewById(R.id.discoverMoreCardView);
            ImageButton checkImgButton = itemView.findViewById(R.id.discoverMoreCheckImageButton);

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
                        v.setBackground(ContextCompat.getDrawable(v.getContext(), R.drawable.ic_add_16_with_background));
                    else
                        v.setBackground(ContextCompat.getDrawable(v.getContext(), R.drawable.ic_check_16_with_background));
                    pressed=!pressed;
                }
            });
        }
    }
}
