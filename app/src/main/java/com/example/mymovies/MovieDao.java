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

    @Query("UPDATE MOVIES SET my_rating = :my_rating, " +
            "vote_average =  (((vote_count * vote_average)-my_rating)+ :my_rating) / (vote_count) " +
            "WHERE movieId = :id")
    void update(long id, double my_rating);

    @Query("UPDATE MOVIES SET vote_count = vote_count+1 WHERE movieId = :id")
    void updateVoteCount(long id);

    @Query("SELECT * FROM MOVIES")
    List<Movie> getAll();

    @Query("SELECT * FROM MOVIES WHERE MOVIEID = :id")
    Movie getMovieById(Long id);

    @Query("DELETE FROM MOVIES")
    void deleteAll();

    @Delete
    void deleteMovie(Movie movie);
}
