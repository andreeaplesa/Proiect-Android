package com.example.mymovies;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Movie.class, MovieCategory.class, MovieCategoriesCrossRef.class}, version = 5, exportSchema = false)
public abstract class MovieDB extends RoomDatabase {
    private final static String DB_NAME = "movies.db";
    private static MovieDB instanta;

    public static MovieDB getInstanta(Context context) {
        if(instanta == null) {
            instanta = Room.databaseBuilder(context, MovieDB.class, DB_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instanta;
    }

    public abstract MovieDao getMovieDao();

    public abstract MovieWithCategoriesDao getMovieWithCategoriesDao();

    public abstract MovieCategoryDao getMovieCategoryDao();
}
