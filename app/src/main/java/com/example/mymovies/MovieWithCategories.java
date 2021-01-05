package com.example.mymovies;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class MovieWithCategories  {
    @Embedded
    public Movie movie;
    @Relation(
            parentColumn = "movieId",
            entityColumn = "categoryId",
            associateBy = @Junction(MovieCategoriesCrossRef.class)
    )
    List<MovieCategory> movieCategoryList;
}
