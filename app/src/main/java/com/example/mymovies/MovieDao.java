package com.example.mymovies;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MovieDao {

    @Insert
    void insert(Movie movie);

    @Insert
    void insert(List<Movie> movieList);

    @Query("SELECT * FROM MOVIES")
    List<Movie> getAll();

    @Query("SELECT * FROM MOVIES WHERE title= :title")
    Movie getMovieByTitle(String title);

    @Query("DELETE FROM MOVIES")
    void deleteAll();

    @Delete
    void deleteMovie(Movie movie);
}
