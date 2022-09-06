package com.example.runningapplication.routes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
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
    private NavController navController;
    private MainActivity parentActivity;

    public RouteBrowseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentActivity = (MainActivity) getParentFragment().requireActivity();

        routeViewModel = new ViewModelProvider(parentActivity).get(RouteViewModel.class);

        List<Route> routes = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            routes.add(Route.createFromResources(getResources(), i));
        }
        routeViewModel.setRoutes(routes);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentRouteBrowseBinding.inflate(inflater, container, false);

//        routeViewModel.getSelectedRoute().observe(getViewLifecycleOwner(), selectedRoute -> {
//            if(selectedRoute != null){
//                RouteBrowseFragmentDirections.ActionShowRouteDetails action = RouteBrowseFragmentDirections.actionShowRouteDetails();
//                action.setRouteIndex(0);
//                navController.navigate(action);
//            }
//        });

        RouteAdapter routeAdapter = new RouteAdapter(parentActivity, routeIndex -> {
            RouteBrowseFragmentDirections.ActionShowRouteDetails action = RouteBrowseFragmentDirections.actionShowRouteDetails();
            action.setRouteIndex(routeIndex);
            navController.navigate(action);
        });

        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setAdapter(routeAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(parentActivity));

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }
}