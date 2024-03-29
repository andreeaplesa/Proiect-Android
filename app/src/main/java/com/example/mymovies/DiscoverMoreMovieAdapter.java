package com.example.mymovies;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DiscoverMoreMovieAdapter extends RecyclerView.Adapter<DiscoverMoreMovieAdapter.MyViewHolder> implements Filterable {
    private Context context;
    private List<Movie> movieList;
    private List<Movie> movieListAll;

    public DiscoverMoreMovieAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
        this.movieListAll = new ArrayList<>(movieList);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);

        view = inflater.inflate(R.layout.discover_more_card_view, parent, false);

        return new MyViewHolder(view, movieList);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvMovieTitle.setText(movieList.get(position).getTitle());

        long id = movieList.get(position).getMovieId();

        final MovieDB movieDB = MovieDB.getInstanta(context);

        Movie movie = movieDB.getMovieDao().getMovieById(id);

        if(movie != null){
            holder.checkImgButton.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_check_16_with_background));
            holder.pressed = true;
        } else {
            holder.checkImgButton.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_add_16_with_background));
            holder.pressed = false;
        }

        String imageString = "https://image.tmdb.org/t/p/original";
        Glide.with(context)
                .load(imageString + movieList.get(position).getBackdrop_path())
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Movie> filteredList=new ArrayList<>();
            if (constraint.toString().isEmpty()){
                filteredList.addAll(movieListAll);
            }else{
                for (Movie movie:movieListAll){
                    if (movie.getTitle().toLowerCase().contains(constraint.toString().toLowerCase())){
                        filteredList.add(movie);
                    }
                }
            }
            FilterResults filterResults=new FilterResults();
            filterResults.values=filteredList;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            movieList.clear();
            movieList.addAll((Collection<? extends Movie>) results.values);
            notifyDataSetChanged();
        }
    };

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvMovieTitle;
        private ImageButton checkImgButton;
        private ImageView img;

        private boolean pressed;


        public MyViewHolder(@NonNull final View itemView, final List<Movie> movieList) {
            super(itemView);

            tvMovieTitle = itemView.findViewById(R.id.tvDiscoverMore_Title);

            img = itemView.findViewById(R.id.discoverMoreImageView);
            CardView cardView = itemView.findViewById(R.id.discoverMoreCardView);
            checkImgButton = itemView.findViewById(R.id.discoverMoreCheckImageButton);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    Intent intent = new Intent(v.getContext(), MovieDetailsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("movieId",movieList.get(position).getMovieId());
                    v.getContext().startActivity(intent);
                }
            });

            checkImgButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int position = getAdapterPosition();
                    final MovieDB movieDB = MovieDB.getInstanta(v.getContext());

                    if(pressed){
                        v.setBackground(ContextCompat.getDrawable(v.getContext(), R.drawable.ic_add_16_with_background));

                        movieDB.getMovieDao().deleteMovie(movieList.get(position));
                    }
                    else {
                        v.setBackground(ContextCompat.getDrawable(v.getContext(), R.drawable.ic_check_16_with_background));

                        ExtractMovie extractMovie = new ExtractMovie(movieList.get(position).getMovieId(), movieDB,true);
                        extractMovie.execute();
                    }

                    pressed=!pressed;
                }
            });
        }
    }
}
