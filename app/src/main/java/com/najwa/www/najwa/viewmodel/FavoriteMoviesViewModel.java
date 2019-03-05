package com.najwa.www.najwa.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.najwa.www.najwa.data.MovieDB;
import com.najwa.www.najwa.data.dao.MovieDao;
import com.najwa.www.najwa.data.entities.MovieEN;

import java.util.List;

public class FavoriteMoviesViewModel extends AndroidViewModel {

    private LiveData<List<MovieEN>> allFavoriteMovies;

    private MovieDao movieDao;

    public FavoriteMoviesViewModel(@NonNull Application application) {
        super(application);
        movieDao = MovieDB.getInstance(application.getBaseContext()).movieDao();
        allFavoriteMovies = movieDao.getAllFavoriteMovies();
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

    public void removeFromFavorites(MovieEN movieEN) {
        movieDao.deleteMovie(movieEN);
    }
}
