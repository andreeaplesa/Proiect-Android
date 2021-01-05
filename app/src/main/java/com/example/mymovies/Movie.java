package com.example.mymovies;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "movies")
public class Movie {
    @PrimaryKey
    private long movieId;

    private String title;
    private String backdrop_path;
    private String poster_path;
    private String overview;
    private String release_date;
    private int runtime;
    private int revenue;

    @Ignore
    private List<Integer> genres;

    private double my_rating;
    private double vote_average;
    private int vote_count;

    @Ignore
    public Movie() {
    }

    @Ignore
    public Movie(long id, String title, String overview, int runtime, String release_date, List<Integer> genres, double vote_average,
                 int vote_count, String backdrop_path, String poster_path) {
        this.movieId = id;
        this.title = title;
        this.overview = overview;
        this.runtime = runtime;
        this.release_date = release_date;
        this.genres = genres;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
        this.backdrop_path = backdrop_path;
        this.poster_path = poster_path;
    }

    public Movie(long movieId, String title, String backdrop_path, String poster_path, String overview, String release_date, int runtime, int revenue, double vote_average, int vote_count) {
        this.movieId = movieId;
        this.title = title;
        this.backdrop_path = backdrop_path;
        this.poster_path = poster_path;
        this.overview = overview;
        this.release_date = release_date;
        this.runtime = runtime;
        this.revenue = revenue;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
    }

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public int getRevenue() {
        return revenue;
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public List<Integer> getGenres() {
        return genres;
    }

    public void setGenres(List<Integer> genres) {
        this.genres = genres;
    }

    public double getMy_rating() {
        return my_rating;
    }

    public void setMy_rating(double my_rating) {
        this.my_rating = my_rating;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + movieId +
                ", title='" + title + '\'' +
                ", overview='" + overview + '\'' +
                ", runtime=" + runtime +
                ", release_date='" + release_date + '\'' +
                ", genres=" + genres +
                ", vote_average=" + vote_average +
                ", vote_count=" + vote_count +
                ", backdrop_path='" + backdrop_path + '\'' +
                ", poster_path='" + poster_path + '\'' +
                '}';
    }
}


