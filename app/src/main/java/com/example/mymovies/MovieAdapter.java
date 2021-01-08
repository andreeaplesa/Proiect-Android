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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {

    private Context context;
    private List<Movie> movieList;
    private RecyclerView recyclerView;

    public MovieAdapter(Context context, List<Movie> movieList,RecyclerView recyclerView) {
        this.context = context;
        this.movieList = movieList;
        this.recyclerView=recyclerView;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.movies_fragment_card_view, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        String posterImageString = "https://image.tmdb.org/t/p/w500";
        Glide.with(context)
                .load(posterImageString + movieList.get(position).getPoster_path())
                .into(holder.img);

        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieDB movieDB=MovieDB.getInstanta(v.getContext());
                movieDB.getMovieDao().deleteMovie(movieList.get(position));
                movieList=movieDB.getMovieDao().getAll();
                MovieAdapter adapter = new MovieAdapter(v.getContext(), movieList,recyclerView);
                recyclerView.setLayoutManager(new GridLayoutManager(v.getContext(), 3));
                recyclerView.setAdapter(adapter);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView img;
        private ImageButton imageButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.moviesImageView);
            imageButton=itemView.findViewById(R.id.moviesImgButton);
            CardView cardView = itemView.findViewById(R.id.moviesCardView);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    Intent intent = new Intent(v.getContext(), MovieDetailsActivity.class);
                    intent.putExtra("movieId",movieList.get(position).getMovieId());
                    v.getContext().startActivity(intent);
                }
            });



        }

    }
}
