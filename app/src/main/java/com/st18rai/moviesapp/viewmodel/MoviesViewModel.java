package com.st18rai.moviesapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.st18rai.moviesapp.model.Genre;
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

    public LiveData<List<Movie>> getMovies(String sortBy) {
        repository.loadMovies(sortBy);
        return repository.getMovies();
    }

    public LiveData<List<Movie>> getMoviesByGenre(String sortBy, String genresID) {
        repository.loadMoviesByGenre(sortBy, genresID);
        return repository.getMoviesByGenre();
    }

    public LiveData<Movie> getMovieDetails(int id) {
        repository.loadMovieDetails(id);
        return repository.getMovieDetails();
    }

    public LiveData<List<Movie>> searchForMovie(String query) {
        repository.searchForMovie(query);
        return repository.getFoundMovies();
    }

    public LiveData<List<Genre>> getGenres() {
        repository.loadGenres();
        return repository.getGenres();
    }

    public LiveData<Boolean> updateLike(Movie currentMovie) {

        if (currentMovie.isLiked()) {
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

    public String[] prepareGenresArray(List<Genre> genres) {
        String[] genresArray = new String[genres.size()];
        for (int i = 0; i < genres.size(); i++) {
            genresArray[i] = genres.get(i).getName();
        }
        return genresArray;
    }

    public String prepareSelectedGenres(List<Genre> genres, int type) {
        StringBuilder prepared = new StringBuilder();
        for (int i = 0; i < genres.size(); i++) {
            if (genres.get(i).isSelected()) {
                if (type == 0) // prepare names
                    prepared.append(", ").append(genres.get(i).getName());
                if (type == 1) // prepare IDs
                    prepared.append(", ").append(genres.get(i).getId());
            }
        }
        prepared.deleteCharAt(0);

        return prepared.toString();
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
