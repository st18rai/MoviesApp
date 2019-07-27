package com.st18rai.moviesapp.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.st18rai.moviesapp.model.Movie;
import com.st18rai.moviesapp.network.ApiClient;
import com.st18rai.moviesapp.utils.RxUtil;

import java.util.List;

public class MoviesRepository {

    public MoviesRepository(Application application) {

    }

    private final MutableLiveData<List<Movie>> movies = new MutableLiveData<>();

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }

    private void setMovies(List<Movie> data) {
        movies.setValue(data);
    }

    public void loadMovies(String sort) {
        RxUtil.networkConsumer(ApiClient.getApiInterface().getMovies(ApiClient.API_KEY, sort),
                movieBaseResponse ->
                        setMovies(movieBaseResponse.getDataList()),
                throwable -> throwable.printStackTrace());
    }
}
