package com.example.mymovies;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface MovieWithCategoriesDao {

    @Transaction
    @Insert
    void insert(List<MovieCategoriesCrossRef> movieWithCategoriesList);

    @Query("SELECT * FROM movieCategoriesCR WHERE movieId = :id")
    List<MovieCategoriesCrossRef> getMovieCategoryByMovieId(long id);

}
