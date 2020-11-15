package com.example.mymovies;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

    private Context context;
    private List<MovieCategory> categoryList;
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();

    public CategoryAdapter(Context context, List<MovieCategory> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.discover_fragment_category_recycler_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvCategoryName.setText(categoryList.get(position).getCategoryName());

        List<Movie> movieListExtracted = new ArrayList<>();

        ExtractMoviesForCategory extractMoviesForCategory = new ExtractMoviesForCategory(context, movieListExtracted, categoryList.get(position).getId());
        extractMoviesForCategory.execute();

        for(int i=0;i<movieListExtracted.size();i++){
            Log.d("On bind", movieListExtracted.get(i).toString());
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(holder.recyclerViewAdapter.getContext(), LinearLayoutManager.HORIZONTAL, false);

        layoutManager.setInitialPrefetchItemCount(movieListExtracted.size());

        CategoryMovieAdapter categoryMovieAdapter = new CategoryMovieAdapter(context, movieListExtracted);

        holder.recyclerViewAdapter.setLayoutManager(layoutManager);
        holder.recyclerViewAdapter.setAdapter(categoryMovieAdapter);

        holder.recyclerViewAdapter.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvCategoryName;
        private RecyclerView recyclerViewAdapter;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCategoryName = itemView.findViewById(R.id.tvDiscoverCategory);
            recyclerViewAdapter = itemView.findViewById(R.id.discoverFragmentCategoryRecyclerView);
        }
    }
}
