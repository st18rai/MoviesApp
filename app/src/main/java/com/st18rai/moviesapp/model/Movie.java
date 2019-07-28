package com.st18rai.moviesapp.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;
import com.st18rai.moviesapp.interfaces.Constants;
import com.st18rai.moviesapp.utils.MovieUtils;

import java.util.List;

@Entity(tableName = Constants.MOVIE_TABLE_NAME)
public class Movie {
    @PrimaryKey
    private int id;
    private String title;
    @SerializedName("poster_path")
    private String posterPath;
    private String overview;
    @SerializedName("release_date")
    private String releaseDate;
    @SerializedName("vote_average")
    private double voteAverage;
    @SerializedName("vote_count")
    private int voteCount;

    private String movieGenres;
    private boolean liked;

    @Ignore
    private List<Genre> genres = null;

    public Movie(int id, String title, String posterPath, String overview, String releaseDate,
                 double voteAverage, int voteCount, String movieGenres, boolean liked) {
        this.id = id;
        this.title = title;
        this.posterPath = posterPath;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
        this.movieGenres = movieGenres;
        this.liked = liked;
    }

    public String getMovieGenres() {
        if (genres != null)
            movieGenres = MovieUtils.prepareGenres(genres);
        return movieGenres;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }


    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public String getPosterPath() {
        return posterPath;
    }


    public String getOverview() {
        return overview;
    }

}
