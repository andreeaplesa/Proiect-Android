package com.example.mymovies;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "movieCategory")
public class MovieCategory {
    @PrimaryKey
    private int categoryId;
    private String categoryName;
    @Ignore
    private List<Movie> movieList;

    @Ignore
    public MovieCategory() {
    }

    @Ignore
    public MovieCategory(int id, String categoryName, List<Movie> movieList) {
        this.categoryId = id;
        this.categoryName = categoryName;
        this.movieList = movieList;
    }

    public MovieCategory(int categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<Movie> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }
}
