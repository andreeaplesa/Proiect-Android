package com.example.mymovies;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MovieDao {

    @Insert
    void insert(Movie movie);

    @Insert
    void insert(List<Movie> movieList);

    @Query("UPDATE MOVIES SET my_rating = :my_rating, " +
            "vote_count = vote_count+1, vote_average =  ((vote_count * vote_average) + :my_rating) / (vote_count + 1) " +
            "WHERE movieId = :id")
    void update(int id, double my_rating);

    @Query("SELECT * FROM MOVIES")
    List<Movie> getAll();

    @Query("SELECT * FROM MOVIES WHERE title= :title")
    Movie getMovieByTitle(String title);

    @Query("SELECT * FROM MOVIES WHERE MOVIEID = :id")
    Movie getMovieById(Long id);

    @Query("DELETE FROM MOVIES")
    void deleteAll();

    @Delete
    void deleteMovie(Movie movie);
}
