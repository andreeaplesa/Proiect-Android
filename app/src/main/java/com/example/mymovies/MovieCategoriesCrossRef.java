package com.example.mymovies;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "movieCategoriesCR", primaryKeys = {"movieId", "categoryId"},
        foreignKeys = {
                @ForeignKey(
                        entity = Movie.class,
                        parentColumns = "movieId",
                        childColumns = "movieId",
                        onDelete = CASCADE),
                @ForeignKey(
                        entity = MovieCategory.class,
                        parentColumns = "categoryId",
                        childColumns = "categoryId",
                        onDelete = CASCADE)
        })
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
