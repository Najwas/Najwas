package com.najwa.www.najwa.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.najwa.www.najwa.data.dao.MovieDao;
import com.najwa.www.najwa.data.entities.MovieEN;
import com.najwa.www.najwa.models.Movie;

@Database(entities = MovieEN.class, version = 1, exportSchema = false)
public abstract class MovieDB extends RoomDatabase {

    private static MovieDB INSTANCE;

    public abstract MovieDao movieDao();

    public static synchronized MovieDB getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    MovieDB.class, "movie_database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }

        return INSTANCE;
    }
}
