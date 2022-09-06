package com.example.runningapplication.routes;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.runningapplication.MainActivity;
import com.example.runningapplication.R;
import com.example.runningapplication.databinding.FragmentRouteDetailsBinding;


public class RouteDetailsFragment extends Fragment {

    private FragmentRouteDetailsBinding binding;

    private RouteViewModel routeViewModel;

    public RouteDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentRouteDetailsBinding.inflate(inflater, container, false);

        MainActivity parentActivity = (MainActivity) getParentFragment().getActivity();

        routeViewModel = new ViewModelProvider(parentActivity).get(RouteViewModel.class);

        Route selectedRoute = routeViewModel.getRoutes().get(RouteDetailsFragmentArgs.fromBundle(requireArguments()).getRouteIndex());

        // prosledjujemo lifecycleowner(this fragment) za koji zelimo da posmatra selekciju rute
//        routeViewModel.getSelectedRoute().observe(getViewLifecycleOwner(), selectedRoute -> {
//            if(selectedRoute != null){
                binding.routeImage.setImageDrawable(selectedRoute.getImage());
                binding.routeLabel.setText(selectedRoute.getLabel());
                binding.routeName.setText(selectedRoute.getName());
                binding.routeLength.setText(selectedRoute.getLength() + "km");
                binding.routeDifficulty.setText(selectedRoute.getDifficulty());
                binding.routeDescription.setText(selectedRoute.getDescription());
//            }
//        });

        return binding.getRoot();
    }
}