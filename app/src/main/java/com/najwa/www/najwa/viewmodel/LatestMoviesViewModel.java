package com.najwa.www.najwa.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.najwa.www.najwa.api.MovieApi;
import com.najwa.www.najwa.data.MovieDB;
import com.najwa.www.najwa.data.dao.MovieDao;
import com.najwa.www.najwa.data.entities.MovieEN;
import com.najwa.www.najwa.models.Movie;
import com.najwa.www.najwa.models.MoviePopular;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LatestMoviesViewModel extends AndroidViewModel {

    private final static String TAG = "LatestMoviesViewModel";

    private MutableLiveData<List<Movie>> movieList;

    private MutableLiveData<List<Movie>> searchedMovies;

    private MutableLiveData<List<Movie>> similarMovies;

    private LiveData<List<MovieEN>> allFavoriteMovies;

    private MovieDao movieDao;

    public LatestMoviesViewModel(@NonNull Application application) {
        super(application);
        movieDao = MovieDB.getInstance(application.getBaseContext()).movieDao();
        allFavoriteMovies = movieDao.getAllFavoriteMovies();
    }

    public LiveData<List<Movie>> getSimilarMovies(int id) {
        if (similarMovies == null) {
            similarMovies = new MutableLiveData<>();
            Log.d(TAG, "getMovies: Downloading Movies");
            loadSimilarMovies(id);
        }

        return similarMovies;
    }

    private void loadSimilarMovies(int id) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MovieApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        Map<String, String> params = new HashMap<>();
        params.put("api_key", "8300ce5f76b1fd44f70373f7211df3bb");
        params.put("language", "en-US");
        params.put("page", "1");

        MovieApi api = retrofit.create(MovieApi.class);
        Call<MoviePopular> call = api.getSimilarMovies(id, params);

        call.enqueue(new Callback<MoviePopular>() {
            @Override
            public void onResponse(@NonNull Call<MoviePopular> call, @NonNull Response<MoviePopular> response) {
                if (response.body() != null) {
                    similarMovies.setValue(response.body().getPopularMovies());
                } else {
                    Log.d(TAG, "onResponse: body is null");
                }
            }

            @Override
            public void onFailure(@NonNull Call<MoviePopular> call, @NonNull Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });

    }

    public LiveData<List<Movie>> getMovies() {
        if (movieList == null) {
            movieList = new MutableLiveData<>();
            Log.d(TAG, "getMovies: Downloading Movies");
            loadMovies();
        }

        return movieList;
    }

    public LiveData<List<Movie>> getSearchedMovies(String query, String year) {
        if (searchedMovies == null) {
            searchedMovies = new MutableLiveData<>();
            Log.d(TAG, "getMovies: Downloading Movies");
            loadSearchedMovies(query, year);
        }

        loadSearchedMovies(query, year);

        return searchedMovies;
    }

    private void loadSearchedMovies(String query, String year) {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MovieApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        Map<String, String> params = new HashMap<>();
        params.put("api_key", "8300ce5f76b1fd44f70373f7211df3bb");
        params.put("language", "en-US");
        params.put("page", "1");
        params.put("query", query);
        params.put("year", year);

        MovieApi api = retrofit.create(MovieApi.class);
        Call<MoviePopular> call = api.getSearchedMovies(params);

        call.enqueue(new Callback<MoviePopular>() {
            @Override
            public void onResponse(@NonNull Call<MoviePopular> call, @NonNull Response<MoviePopular> response) {
                if (response.body() != null) {
                    searchedMovies.setValue(response.body().getPopularMovies());
                } else {
                    Log.d(TAG, "onResponse: body is null");
                }
            }

            @Override
            public void onFailure(@NonNull Call<MoviePopular> call, @NonNull Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });

    }

    public LiveData<List<MovieEN>> getAllFavoriteMovies() {
        return allFavoriteMovies;
    }

    public void insertMovieFavorite(MovieEN movieEN) {
        if (movieDao.getSingleMovie(movieEN.getId_movie()) <= 0) {
            movieDao.insertMovie(movieEN);
            Toast.makeText(getApplication().getApplicationContext(), "Movie Added To Favorites", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplication().getApplicationContext(), "Movie Already Exist In Favorites", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadMovies() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MovieApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        Map<String, String> params = new HashMap<>();
        params.put("api_key", "8300ce5f76b1fd44f70373f7211df3bb");
        params.put("language", "en-US");
        params.put("page", "1");

        MovieApi api = retrofit.create(MovieApi.class);
        Call<MoviePopular> call = api.getPopularMovies(params);

        call.enqueue(new Callback<MoviePopular>() {
            @Override
            public void onResponse(@NonNull Call<MoviePopular> call, @NonNull Response<MoviePopular> response) {
                if (response.body() != null) {
                    movieList.setValue(response.body().getPopularMovies());
                } else {
                    Log.d(TAG, "onResponse: body is null");
                }
            }

            @Override
            public void onFailure(@NonNull Call<MoviePopular> call, @NonNull Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }


}
