package com.example.projectAndroid.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ProfilDao {
    // DAO -> used with interfaces or abstract classes

    @Insert
    long insert(Profil profil);

    @Insert
    void insert(List<Profil> profil);

    @Query("DELETE FROM users")
    void deleteAll();

    @Query("SELECT * FROM users")
    List<Profil> getAll();

    @Query("SELECT * FROM users WHERE age > 21")
    List<Profil> getUsersOlderThan21();
}
