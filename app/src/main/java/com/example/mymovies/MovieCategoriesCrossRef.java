package com.example.mymovies;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "movieCategoriesCR", primaryKeys = {"movieId", "categoryId"})
public class MovieCategoriesCrossRef {
    private long movieId;
    @ColumnInfo(index = true)
    private long categoryId;

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }
}
