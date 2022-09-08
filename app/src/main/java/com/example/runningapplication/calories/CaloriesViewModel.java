package com.example.runningapplication.calories;

import android.os.Bundle;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class CaloriesViewModel extends ViewModel {

    public static final String CALORIES_BURNED_KEY = "calories-burned";
    public static final String CALORIES_NEEDED_KEY = "calories-needed";

    private boolean dataValid = false;
    //private final MutableLiveData<Integer> caloriesBurned = new MutableLiveData<>(-1);
    //private final MutableLiveData<Integer> caloriesNeeded = new MutableLiveData<>(-1);
    private final LiveData<Integer> caloriesBurned;
    private final LiveData<Integer> caloriesNeeded;

    private SavedStateHandle savedStateHandle;

    public CaloriesViewModel(SavedStateHandle savedStateHandle){
        this.savedStateHandle = savedStateHandle;
        LiveData<Integer> caloriesBurnedSaved = savedStateHandle.getLiveData(CALORIES_BURNED_KEY, -1);
        caloriesBurned = Transformations.map(caloriesBurnedSaved, caloriesBurnedSavedValue ->{
           //slozena transformacija
           return caloriesBurnedSavedValue;
        });
        LiveData<Integer> caloriesNeededSaved = savedStateHandle.getLiveData(CALORIES_NEEDED_KEY, -1);
        caloriesNeeded = Transformations.map(caloriesNeededSaved, caloriesNeededSavedValue ->{
            //slozena transformacija
            return caloriesNeededSavedValue;
        });
    }

//    public void initByInstanceStateBundle(Bundle bundle){
//        if(bundle != null){
//            if(!dataValid){
//                if(bundle.containsKey(CALORIES_BURNED_KEY)){
//                    dataValid = true;
//                    caloriesBurned.setValue(bundle.getInt(CALORIES_BURNED_KEY));
//                }
//                if(bundle.containsKey(CALORIES_NEEDED_KEY)){
//                    dataValid = true;
//                    caloriesNeeded.setValue(bundle.getInt(CALORIES_NEEDED_KEY));
//                }
//            }
//        }
//    }

    public LiveData<Integer> getCaloriesBurned() {

        return caloriesBurned;
        //return savedStateHandle.getLiveData(CALORIES_BURNED_KEY);
    }
    public LiveData<Integer> getCaloriesNeeded() {
        return caloriesNeeded;
        //return savedStateHandle.getLiveData(CALORIES_NEEDED_KEY);
    }

    public void updateValues(double weight, double height, int age, boolean isMale, double duration, double met){
        dataValid = true;

        double caloriesNeededValue;
        if (isMale) {
            caloriesNeededValue = 66 + 13.7 * weight + 5 * height - 6.8 * age;
        } else {
            caloriesNeededValue = 655.1 + 9.6 * weight + 1.9 * height - 4.7 * age;
        }
        //caloriesNeeded.setValue((int) caloriesNeededValue);
        savedStateHandle.set(CALORIES_NEEDED_KEY, (int) caloriesNeededValue);

        double caloriesBurnedValue = duration * met * 3.5 * weight / 200;
        //caloriesBurned.setValue((int) caloriesBurnedValue);
        savedStateHandle.set(CALORIES_BURNED_KEY, (int) caloriesBurnedValue);

    }
}
