package com.example.runningapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.runningapplication.calories.CaloriesActivity;
import com.example.runningapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonCalories.setOnClickListener(view -> {
            Intent explicitIntent = new Intent();
            explicitIntent.setClass(this, CaloriesActivity.class);
            startActivity(explicitIntent);
        });
    }
}
