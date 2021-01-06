package com.example.mymovies;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface MovieWithCategoriesDao {
//    @Transaction
//    @Query("SELECT * FROM MOVIES")
//    List<MovieCategoriesCrossRef> getMovieWithCategories();

    @Transaction
    @Insert
    void insert(List<MovieCategoriesCrossRef> movieWithCategoriesList);
}
