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

public class DiscoverCategoryAdapter extends RecyclerView.Adapter<DiscoverCategoryAdapter.MyViewHolder> {

    private Context context;
    private List<String> categoryList;

    public DiscoverCategoryAdapter(Context context, List<String> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);

        view = inflater.inflate(R.layout.discover_fragment_category_recycler_view, parent, false);


        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // Nume categorie
        holder.tvCategoryName.setText(categoryList.get(position));


    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvCategoryName;
        private RecyclerView recyclerViewCategoryMovies;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCategoryName = itemView.findViewById(R.id.tvDiscoverCategory);
            recyclerViewCategoryMovies = itemView.findViewById(R.id.discoverFragmentCategoryRecyclerView);
        }
    }
}
