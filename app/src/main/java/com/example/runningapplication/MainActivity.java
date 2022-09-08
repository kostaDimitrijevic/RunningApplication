package com.example.runningapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.runningapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if(savedInstanceState == null){
            setupBottomNavigation();
        }
    }

    // ne poziva se na prvom stvaranju aktivnosti, vec nakon ubijanja aktivnosti od strane os pa ponovnog stvaranja
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        setupBottomNavigation();
    }

    private void setupBottomNavigation(){
        int[] navResourceIds = new int[]{
                R.navigation.nav_graph_routes,
                R.navigation.nav_graph_workouts,
                R.navigation.nav_graph_calories
        };

        BottomNavigationUtil.setup(
                binding.bottomNavigation,
                getSupportFragmentManager(),
                navResourceIds,
                R.id.nav_host_conatiner
        );
    }
}
