package com.example.mymovies;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MovieCategoryDao {

    @Insert
    void insert(MovieCategory movieCategory);

    @Insert
    void insert(List<MovieCategory> movieCategoryList);

    @Query("SELECT * FROM MOVIECATEGORY")
    List<MovieCategory> getAllCategories();

    @Query("SELECT * FROM MOVIECATEGORY WHERE categoryName= :categoryName")
    MovieCategory getMovieCategoryByName(String categoryName);

    @Query("DELETE FROM MOVIECATEGORY")
    void deleteAllCategories();

    @Delete
    void deleteMovieCategory(MovieCategory movieCategory);
}
