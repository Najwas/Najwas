package com.najwa.www.najwa.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.najwa.www.najwa.data.entities.MovieEN;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM MovieEN")
    LiveData<List<MovieEN>> getAllFavoriteMovies();

    @Query("SELECT COUNT(id) FROM MovieEN WHERE id_movie=:id")
    int getSingleMovie(int id);

    @Insert
    void insertMovie(MovieEN movieEN);

    @Delete
    void deleteMovie(MovieEN movieEN);
}
