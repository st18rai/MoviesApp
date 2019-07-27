package com.st18rai.moviesapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.st18rai.moviesapp.model.Movie;
import com.st18rai.moviesapp.repository.MoviesRepository;

import java.util.List;

public class MoviesViewModel extends AndroidViewModel {

    private MoviesRepository repository;

    public MoviesViewModel(@NonNull Application application) {
        super(application);

        repository = new MoviesRepository(application);
    }

    public LiveData<List<Movie>> getMovies(String sortBy) {
        repository.loadMovies(sortBy);
        return repository.getMovies();
    }

}
