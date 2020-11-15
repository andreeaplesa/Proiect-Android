package com.example.mymovies;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {

    private Context context;
    private List<Movie> movieList;
    private String className;

    public MovieAdapter(Context context, List<Movie> movieList, String className) {
        this.context = context;
        this.movieList = movieList;
        this.className = className;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if(className.contains("MoviesFragment"))
            view = inflater.inflate(R.layout.movies_fragment_card_view, parent, false);
        else
            view = inflater.inflate(R.layout.discover_fragment_card_view, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if(className.contains("MoviesFragment")){
            String posterImageString = "https://image.tmdb.org/t/p/w500";
            Glide.with(context)
                    .load(posterImageString + movieList.get(position).getPoster_path())
                    .into(holder.img);
        } else{
            //holder.title.setText(movieList.get(position).getTitle());
            String backdropImageString = "https://image.tmdb.org/t/p/original";
            Glide.with(context)
                    .load(backdropImageString + movieList.get(position).getBackdrop_path())
                    .into(holder.img);
        }

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private ImageView img;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            if(className.contains("MoviesFragment")){
                img = itemView.findViewById(R.id.moviesImageView);
                CardView cardView = itemView.findViewById(R.id.moviesCardView);

                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), MovieDetailsActivity.class);
                        v.getContext().startActivity(intent);
                    }
                });
            }else{
                //title = itemView.findViewById(R.id.tvDiscoverCategory);
                img = itemView.findViewById(R.id.discoverImageView);
            }
        }
    }
}
