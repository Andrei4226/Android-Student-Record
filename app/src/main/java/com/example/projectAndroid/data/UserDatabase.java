package com.example.projectAndroid.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Profil.class}, version = 1)
// If we add more tables to the database, we need to modify the version from 1 to 2, etc.
// entities -> all classes annotated with Entity
public abstract class UserDatabase extends RoomDatabase {
    // It's abstract because we don't have the implementation of dao methods since they are implemented by the library
    private static UserDatabase instance;

    public static UserDatabase getInstance(Context context) {

        if (instance == null) {
            instance = Room.databaseBuilder(context, UserDatabase.class, "app.db")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration() // Deletes the existing database and replaces it with the new one to modify the version
                    .build();
        }

        return instance;
    }

    public abstract ProfilDao getProfilDao();
}
