package com.example.mymovies;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MovieCategoryDao {

    @Insert
    void insert(List<MovieCategory> movieCategoryList);

    @Query("SELECT * FROM MOVIECATEGORY")
    List<MovieCategory> getAllCategories();

    @Query("DELETE FROM MOVIECATEGORY")
    void deleteAllCategories();

    @Query("SELECT * FROM movieCategory WHERE categoryId = :id")
    MovieCategory getMovieCategoryById(int id);

    @Delete
    void deleteMovieCategory(MovieCategory movieCategory);
}
