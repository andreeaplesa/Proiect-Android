package com.example.mymovies;

import java.util.List;

public class MovieCategory {

    private int id;
    private String categoryName;
    private List<Movie> movieList;

    public MovieCategory() {
    }

    public MovieCategory(int id, String categoryName, List<Movie> movieList) {
        this.id = id;
        this.categoryName = categoryName;
        this.movieList = movieList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
