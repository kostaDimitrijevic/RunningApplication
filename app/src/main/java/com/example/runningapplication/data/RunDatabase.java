package com.example.runningapplication.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;


// koji entiteti idu u bazu podataka. Versija je bitna
@TypeConverters(value = {DateConverter.class})
@Database(entities = {Workout.class}, version = 1, exportSchema = false)
public abstract class RunDatabase extends RoomDatabase {

    // RoomDatabase ce sam definisati metodu gde vraca WorkoutDao
    public abstract WorkoutDao workoutDao();
}
