package com.st18rai.moviesapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.st18rai.moviesapp.model.Movie;
import com.st18rai.moviesapp.repository.MoviesRepository;

import java.util.List;

public class MoviesViewModel extends AndroidViewModel {

    private final LiveData<List<Movie>> favoriteMovies;
    private MutableLiveData<Boolean> isLiked = new MutableLiveData<>();
    private MoviesRepository repository;

    public MoviesViewModel(@NonNull Application application) {
        super(application);

        repository = new MoviesRepository(application);
        favoriteMovies = repository.getAllFavoriteMovies();
    }

    private void setLiked(boolean liked) {
        isLiked.setValue(liked);
    }

    public LiveData<List<Movie>> getMovies(String sortBy) {
        repository.loadMovies(sortBy);
        return repository.getMovies();
    }

    public LiveData<Movie> getMovieDetails(int id) {
        repository.loadMovieDetails(id);
        return repository.getMovieDetails();
    }

    public LiveData<Boolean> updateLike(Movie currentMovie) {

        if (currentMovie.isLiked()){
            currentMovie.setLiked(false);
            delete(currentMovie);
            isLiked.setValue(false);
        } else {
            currentMovie.setLiked(true);
            insert(currentMovie);
            isLiked.setValue(true);
        }

        return isLiked;
    }

    // db methods
    public LiveData<List<Movie>> getFavoriteMovies() {
        return favoriteMovies;
    }

    public void insert(Movie movie) {
        repository.insert(movie);
    }

    public void delete(Movie movie) {
        repository.delete(movie);
    }

}
