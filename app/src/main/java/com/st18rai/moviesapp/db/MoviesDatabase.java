package com.st18rai.moviesapp.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.st18rai.moviesapp.interfaces.MoviesDao;
import com.st18rai.moviesapp.model.Movie;

@Database(entities = Movie.class, version = 1)
public abstract class MoviesDatabase extends RoomDatabase {
    private static MoviesDatabase instance;

    public abstract MoviesDao moviesDao();

    public static synchronized MoviesDatabase getInstance(Context context) {

        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    MoviesDatabase.class, "movies_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }

}
