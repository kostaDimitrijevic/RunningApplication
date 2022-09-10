package com.example.runningapplication.workouts;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.runningapplication.data.Workout;
import com.example.runningapplication.data.WorkoutRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class WorkoutViewModel extends ViewModel {

    private final WorkoutRepository workoutRepository;

    private final MutableLiveData<Boolean> sorted = new MutableLiveData<>(false);

    @Inject
    public WorkoutViewModel(WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }

    public void insertWorkout(Workout workout){
        workoutRepository.insert(workout);
    }

    public LiveData<List<Workout>> getWorkoutList(){

        return Transformations.switchMap(sorted, sorted -> {
            if(!sorted){
                return workoutRepository.getAllLiveData();
            }
            else{
                return workoutRepository.getAllSortedLiveData();
            }
        });
    }

    public void invertSorted(){
        sorted.setValue(!sorted.getValue());
    }
}
