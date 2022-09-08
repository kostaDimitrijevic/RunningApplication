package com.example.runningapplication.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface WorkoutDao {

    //@Query(""), @Delete, @Update
    @Insert
    long insert(Workout workout);

    @Query("SELECT * FROM Workout")
    List<Workout> getAll();

    @Query("SELECT * FROM Workout")
    LiveData<List<Workout>> getAllLiveData();
}
