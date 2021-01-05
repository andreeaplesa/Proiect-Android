package com.example.mymovies;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface MovieWithCategoriesDao {
    @Transaction
    @Query("SELECT * FROM MOVIES")
    List<MovieWithCategories> getMovieWithCategories();
}
