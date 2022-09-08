package com.example.runningapplication.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;


// koji entiteti idu u bazu podataka. Versija je bitna
@TypeConverters(value = {DateConverter.class})
@Database(entities = {Workout.class}, version = 1, exportSchema = false)
public abstract class RunDatabase extends RoomDatabase {

    // RoomDatabase ce sam definisati metodu gde vraca WorkoutDao
    public abstract WorkoutDao workoutDao();

    private static RunDatabase instance = null;
    private static final String DATABASE_NAME = "run-app.db";

    public static RunDatabase getInstance(Context context) {
        if(instance == null) {
            synchronized (RunDatabase.class){
                if(instance == null){
                    instance = Room.databaseBuilder(context.getApplicationContext(), RunDatabase.class, DATABASE_NAME).allowMainThreadQueries().build();
                }
            }

        }

        return instance;
    }
}
