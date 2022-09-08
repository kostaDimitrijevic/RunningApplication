package com.example.runningapplication.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface WorkoutDao {

    //@Query(""), @Delete, @Update
    @Insert
    long insert(Workout workout);
}
