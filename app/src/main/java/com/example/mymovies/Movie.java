package com.example.mymovies;

import java.util.List;

public class Movie {
    private long id;

    private String title;
    private String overview;
    private int runtime;
    private String release_date;
    private List<Integer> genres;

    private double vote_average;
    private int vote_count;

    private String backdrop_path;
    private String poster_path;

    public Movie() {
    }

    public Movie(long id, String title, String overview, int runtime, String release_date, List<Integer> genres, double vote_average,
                 int vote_count, String backdrop_path, String poster_path) {
        this.id = id;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
                "id=" + id +
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


