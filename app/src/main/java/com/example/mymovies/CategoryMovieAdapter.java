package com.example.mymovies;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Objects;

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
        long id = movieList.get(position).getMovieId();

        final MovieDB movieDB = MovieDB.getInstanta(context);

//        Log.d("MovieAdapter", String.valueOf(id));

        Movie movie = movieDB.getMovieDao().getMovieById(id);
//        Log.d("MovieAdapter", movie.toString());

        if(movie != null){
            holder.discoverImageButton.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_check_16_with_background));
            holder.pressed = true;
        } else {
            holder.discoverImageButton.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_add_16_with_background));
            holder.pressed = false;
        }

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
        private ImageButton discoverImageButton;

        private boolean pressed;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            discoverImageView = itemView.findViewById(R.id.discoverImageView);
            discoverImageButton = itemView.findViewById(R.id.discoverImageButton);


            discoverImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int position = getAdapterPosition();
                    final MovieDB movieDB = MovieDB.getInstanta(v.getContext());

                    if (pressed) {
                        v.setBackground(ContextCompat.getDrawable(v.getContext(), R.drawable.ic_add_16_with_background));

                        movieDB.getMovieDao().deleteMovie(movieList.get(position));
                    } else {
                        v.setBackground(ContextCompat.getDrawable(v.getContext(), R.drawable.ic_check_16_with_background));

                        movieDB.getMovieDao().insert(movieList.get(position));
                    }
                }
            });
        }
    }
}
