package com.example.runningapplication.workouts;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.runningapplication.MainActivity;
import com.example.runningapplication.R;
import com.example.runningapplication.data.Workout;
import com.example.runningapplication.databinding.FragmentWorkoutStartBinding;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicReference;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class WorkoutStartFragment extends Fragment {

    private static final String SHARED_PREFERENCES_NAME = "workout-shared-preferences";
    private static final String START_TIMESTAMP_KEY = "start-time";

    private FragmentWorkoutStartBinding binding;
    private WorkoutViewModel workoutViewModel;
    private NavController navController;
    private MainActivity mainActivity;

    private Timer timer;
    private SharedPreferences sharedPreferences;

    public WorkoutStartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainActivity = (MainActivity) requireActivity();
        workoutViewModel = new ViewModelProvider(mainActivity).get(WorkoutViewModel.class);

        sharedPreferences = mainActivity.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentWorkoutStartBinding.inflate(inflater, container, false);

        timer = new Timer();

        if(sharedPreferences.contains(START_TIMESTAMP_KEY)){
            startWorkout(sharedPreferences.getLong(START_TIMESTAMP_KEY, new Date().getTime()));
        }

        binding.start.setOnClickListener(view -> {
            startWorkout(new Date().getTime());
        });

        binding.cancel.setOnClickListener(view -> {
            cancelWorkout();
        });

        binding.finish.setOnClickListener(view -> {
            finishWorkout();
        });

        binding.power.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setClass(mainActivity, WorkoutService.class);
            intent.setAction(WorkoutService.INTENT_ACTION_POWER);
            mainActivity.startService(intent);
        });

        // ako se doda lifecyclerOwner onda ce nas dispatcher da vodi racuna da li je taj callback enable-ovan
        mainActivity.getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                stopWorkout();
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }

    private void startWorkout(long startTimestamp){
        binding.start.setEnabled(false);
        binding.finish.setEnabled(true);
        binding.cancel.setEnabled(true);
        binding.power.setEnabled(true);

        //nije mi jasno kako ce biti poslednja vrednost timera ubacena, kada ubaci samo startTimestamp
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(START_TIMESTAMP_KEY, startTimestamp);
        editor.commit();

        Handler handler = new Handler(Looper.getMainLooper());

        // 10 milisekundi
        timer.schedule(new TimerTask() {
            // SAM RUN TIMERA JE DRUGA NIT I NE MOZEMO DA DIRAMO STVARI IZ VIEW TO MORA UI NIT
            @Override
            public void run() {
                long elapsed = new Date().getTime() - startTimestamp;

                int milliseconds = (int) ((elapsed % 1000) / 10);
                int seconds = (int) ((elapsed / 1000) % 60);
                int minutes = (int) ((elapsed / (1000 * 60)) % 60);
                int hours = (int) ((elapsed / (1000 * 60 * 60)) % 24);

                StringBuilder workoutDuration = new StringBuilder();
                workoutDuration.append(String.format("%02d", hours)).append(":");
                workoutDuration.append(String.format("%02d", minutes)).append(":");
                workoutDuration.append(String.format("%02d", seconds)).append(".");
                workoutDuration.append(String.format("%02d", milliseconds));

                handler.post(() -> {
                    binding.workoutDuration.setText(workoutDuration);
                });
            }
        }, 0 , 10);

        Intent intent = new Intent();
        intent.setClass(mainActivity, WorkoutService.class);
        intent.setAction(WorkoutService.INTENT_ACTION_START);
        mainActivity.startService(intent);

        // nije dobar nacin jer ce aplikacija otici u background
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                handler.post(() -> {
//                    Toast.makeText(mainActivity, getResources().getStringArray(R.array.workout_toast_motivation)[0], Toast.LENGTH_SHORT).show();
//                });
//            }
//        }, 0, 7000);
    }

    private void stopWorkout(){
        sharedPreferences.edit().remove(START_TIMESTAMP_KEY).commit();
        Intent intent = new Intent();
        intent.setClass(mainActivity, WorkoutService.class);
        mainActivity.stopService(intent);
        navController.navigateUp();
    }

    private void cancelWorkout(){
        stopWorkout();
    }

    private void finishWorkout(){
        long startTimestamp = sharedPreferences.getLong(START_TIMESTAMP_KEY, new Date().getTime());
        long elapsed = new Date().getTime() - startTimestamp;
        double minutes = elapsed / (1000.0 * 60);
        workoutViewModel.insertWorkout(new Workout(
                0,
                new Date(),
                getText(R.string.workout_label).toString(),
                0.2 * minutes,
                minutes
        ));

        stopWorkout();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        timer.cancel();
    }


}
