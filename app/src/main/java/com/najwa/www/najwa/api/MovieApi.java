package com.najwa.www.najwa.api;

import com.najwa.www.najwa.models.MoviePopular;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface MovieApi {

    String BASE_URL = "https://api.themoviedb.org/3/";
    String IMAGE_BASE_URL = "https:/image.tmdb.org/t/p/w500";

    @GET("movie/popular")
    Call<MoviePopular> getPopularMovies(@QueryMap Map<String, String> params);

    @GET("search/movie")
    Call<MoviePopular> getSearchedMovies(@QueryMap Map<String, String> params);

    @GET("movie/{id}/similar")
    Call<MoviePopular> getSimilarMovies(@Path("id") int movie_id, @QueryMap Map<String, String> params);
}
