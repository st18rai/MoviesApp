package com.st18rai.moviesapp.network;

import com.st18rai.moviesapp.model.MovieDetails;
import com.st18rai.moviesapp.model.Movie;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {

    @GET("discover/movie")
    Observable<BaseResponse<Movie>> getMovies(@Query("api_key") String apiKey,
                                              @Query("sort_by") String sortBy);

    @GET("movie/{id}")
    Observable<MovieDetails> getMovieDetails(@Path("id") int movieID,
                                             @Query("api_key") String apiKey);

}
