package com.example.runningapplication.routes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.example.runningapplication.databinding.ActivityRouteBrowseBinding;

import java.util.ArrayList;
import java.util.List;

public class RouteBrowseActivity extends AppCompatActivity {

    private ActivityRouteBrowseBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRouteBrowseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        List<Route> routes = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            routes.add(Route.createFromResources(getResources(), i));
        }

        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setAdapter(new RouteAdapter(this, routes));
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}