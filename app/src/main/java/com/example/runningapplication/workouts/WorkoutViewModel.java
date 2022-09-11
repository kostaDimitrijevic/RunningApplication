package com.example.runningapplication.workouts;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.runningapplication.data.Workout;
import com.example.runningapplication.data.WorkoutRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.assisted.Assisted;
import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class WorkoutViewModel extends ViewModel {

    private final WorkoutRepository workoutRepository;
    private final SavedStateHandle savedStateHandle;

    private static final String SORTED_KEY = "sorted-key";

    private final LiveData<List<Workout>> workouts;
    private boolean sorted = false;

    @Inject
    public WorkoutViewModel(SavedStateHandle savedStateHandle, WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
        this.savedStateHandle = savedStateHandle;

        workouts = Transformations.switchMap(
                savedStateHandle.getLiveData(SORTED_KEY, false),
                sorted ->{
                    if(!sorted){
                        return workoutRepository.getAllLiveData();
                    }
                    else{
                        return workoutRepository.getAllSortedLiveData();
                    }
                }
        );
    }

    public void insertWorkout(Workout workout){
        workoutRepository.insert(workout);
    }

    public LiveData<List<Workout>> getWorkoutList(){

        return workouts;
    }

    public void invertSorted(){
        savedStateHandle.set(SORTED_KEY, sorted = !sorted);
    }
}
