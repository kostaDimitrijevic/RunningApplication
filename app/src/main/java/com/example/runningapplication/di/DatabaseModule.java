package com.example.runningapplication.di;

import android.content.Context;

import com.example.runningapplication.data.RunDatabase;
import com.example.runningapplication.data.WorkoutDao;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public interface DatabaseModule {

    @Provides
    static WorkoutDao provideWorkoutDao(@ApplicationContext Context context){
        return RunDatabase.getInstance(context).workoutDao();
    }
}
