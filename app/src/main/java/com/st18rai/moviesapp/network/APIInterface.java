package com.st18rai.moviesapp.network;

import com.st18rai.moviesapp.model.Genre;
import com.st18rai.moviesapp.model.Movie;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {

    @GET("discover/movie")
    Observable<BaseResponse<Movie>> getMovies(@Query("api_key") String apiKey,
                                              @Query("sort_by") String sortBy);

    @GET("discover/movie")
    Observable<BaseResponse<Movie>> getMoreMovies(@Query("api_key") String apiKey,
                                                  @Query("page") int page,
                                                  @Query("sort_by") String sortBy);

    @GET("discover/movie")
    Observable<BaseResponse<Movie>> getMoviesByGenre(@Query("api_key") String apiKey,
                                                     @Query("sort_by") String sortBy,
                                                     @Query("with_genres") String genresID);

    @GET("movie/{id}")
    Observable<Movie> getMovieDetails(@Path("id") int movieID,
                                      @Query("api_key") String apiKey);

    @GET("search/movie")
    Observable<BaseResponse<Movie>> searchForMovie(@Query("api_key") String apiKey,
                                                   @Query("query") String query);

    @GET("genre/movie/list")
    Observable<BaseResponse<Genre>> getGenres(@Query("api_key") String apiKey);

}
