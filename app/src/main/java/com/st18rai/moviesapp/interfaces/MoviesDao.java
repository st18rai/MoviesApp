package com.st18rai.moviesapp.interfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.st18rai.moviesapp.model.Movie;

import java.util.List;

@Dao
public interface MoviesDao {

    @Insert
    void insert(Movie movie);

    @Delete
    void delete(Movie movie);

    @Query("SELECT * FROM movie_table ORDER BY title ASC")
    LiveData<List<Movie>> getAllFavoriteMovies();
}

