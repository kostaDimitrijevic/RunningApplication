package com.example.runningapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.example.runningapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private MyViewModel myViewModel;

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        if(myViewModel.getCaloriesBurned().getValue() != null){
            outState.putInt(MyViewModel.CALORIES_BURNED_KEY, myViewModel.getCaloriesBurned().getValue());
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        this.getLifecycle().addObserver(new MyLifecycleAwareComponent());

        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);
        myViewModel.initByInstanceStateBundle(savedInstanceState);

        myViewModel.getCaloriesBurned().observe(this,
                caloriesBurned -> binding.textView.setText(caloriesBurned + "kcal")
        );

        binding.button.setOnClickListener(view -> {
            double duration = Double.parseDouble(binding.editText.getText().toString());
            double met = 9.8;
            double weight = 80;
            int caloriesBurned = (int) (duration * met * 3.5 * weight / 200);
            myViewModel.setCaloriesBurned(caloriesBurned);
        });
    }
}