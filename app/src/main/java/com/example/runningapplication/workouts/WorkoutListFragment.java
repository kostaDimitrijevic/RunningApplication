package com.example.runningapplication.workouts;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.runningapplication.MainActivity;
import com.example.runningapplication.data.RunDatabase;
import com.example.runningapplication.data.WorkoutRepository;
import com.example.runningapplication.databinding.FragmentWorkoutListBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class WorkoutListFragment extends Fragment {

    private FragmentWorkoutListBinding binding;
    private WorkoutViewModel workoutViewModel;
    private NavController navController;
    private MainActivity mainActivity;

    public WorkoutListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) requireActivity();

        workoutViewModel = new ViewModelProvider(mainActivity).get(WorkoutViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentWorkoutListBinding.inflate(inflater, container, false);

        WorkoutAdapter workoutAdapter = new WorkoutAdapter();
        //iz workout adaptera pozovi metodu setWorkoutList i prosli parametar koji je zapravo drugi argument funckije observe(workoutList)
       workoutViewModel.getWorkoutList().observe(getViewLifecycleOwner(), workoutAdapter::setWorkoutList);

        binding.recyclerView.setAdapter(workoutAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(mainActivity));

        binding.floatingActionButton.setOnClickListener(view -> {
            NavDirections action = WorkoutListFragmentDirections.createWorkout();
            navController.navigate(action);
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }
}