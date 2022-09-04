package com.example.runningapplication.routes;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.runningapplication.MainActivity;
import com.example.runningapplication.R;
import com.example.runningapplication.databinding.FragmentRouteBrowseBinding;

import java.util.ArrayList;
import java.util.List;


public class RouteBrowseFragment extends Fragment {

    private FragmentRouteBrowseBinding binding;
    private RouteViewModel routeViewModel;

    public RouteBrowseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentRouteBrowseBinding.inflate(inflater, container, false);

        MainActivity parentActivity = (MainActivity) getParentFragment().getActivity();

        routeViewModel = new ViewModelProvider(parentActivity).get(RouteViewModel.class);

        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setAdapter(new RouteAdapter(parentActivity));
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(parentActivity));

        return binding.getRoot();
    }
}