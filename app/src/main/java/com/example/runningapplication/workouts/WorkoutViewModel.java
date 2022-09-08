package com.example.runningapplication.workouts;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.runningapplication.data.Workout;
import com.example.runningapplication.data.WorkoutRepository;

import java.util.List;

public class WorkoutViewModel extends ViewModel {

    private final WorkoutRepository workoutRepository;

    public WorkoutViewModel(WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }

    public void insertWorkout(Workout workout){
        workoutRepository.insert(workout);
    }

    public LiveData<List<Workout>> getWorkoutList(){
        return workoutRepository.getAllLiveData();
    }
}
