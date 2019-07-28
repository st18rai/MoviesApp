package com.st18rai.moviesapp.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.st18rai.moviesapp.db.MoviesDatabase;
import com.st18rai.moviesapp.interfaces.MoviesDao;
import com.st18rai.moviesapp.model.Movie;
import com.st18rai.moviesapp.network.ApiClient;
import com.st18rai.moviesapp.utils.RxUtil;

import java.util.List;

public class MoviesRepository {

    private MoviesDao moviesDao;
    private LiveData<List<Movie>> allFavoriteMovies;

    public MoviesRepository(Application application) {
        MoviesDatabase database = MoviesDatabase.getInstance(application);
        moviesDao = database.moviesDao();
        allFavoriteMovies = moviesDao.getAllFavoriteMovies();
    }

    private final MutableLiveData<List<Movie>> movies = new MutableLiveData<>();
    private final MutableLiveData<Movie> movieDetails = new MutableLiveData<>();

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }

    private void setMovies(List<Movie> data) {
        movies.setValue(data);
    }

    private void setMovieDetails(Movie data) {
        movieDetails.setValue(data);
    }

    public LiveData<Movie> getMovieDetails() {
        return movieDetails;
    }

    // data from server
    public void loadMovies(String sort) {
        RxUtil.networkConsumer(ApiClient.getApiInterface().getMovies(ApiClient.API_KEY, sort),
                movieBaseResponse -> setMovies(movieBaseResponse.getDataList()),
                throwable -> throwable.printStackTrace());
    }

    public void loadMovieDetails(int id) {
        RxUtil.networkConsumer(ApiClient.getApiInterface().getMovieDetails(id, ApiClient.API_KEY),
                movieDetails -> setMovieDetails(movieDetails),
                throwable -> throwable.printStackTrace());
    }

    // data from db
    public void insert(Movie movie) {
        new InsertMovieAsyncTask(moviesDao).execute(movie);
    }

    public void delete(Movie movie) {
        new DeleteMovieAsyncTask(moviesDao).execute(movie);
    }

    public LiveData<List<Movie>> getAllFavoriteMovies() {
        return allFavoriteMovies;
    }

    private static class InsertMovieAsyncTask extends AsyncTask<Movie, Void, Void> {

        private MoviesDao movieDao;

        public InsertMovieAsyncTask(MoviesDao movieDao) {
            this.movieDao = movieDao;
        }

        @Override
        protected Void doInBackground(Movie... movies) {
            movieDao.insert(movies[0]);
            return null;
        }
    }

    private static class DeleteMovieAsyncTask extends AsyncTask<Movie, Void, Void> {

        private MoviesDao movieDao;

        public DeleteMovieAsyncTask(MoviesDao movieDao) {
            this.movieDao = movieDao;
        }

        @Override
        protected Void doInBackground(Movie... movies) {
            movieDao.delete(movies[0]);
            return null;
        }
    }

}
