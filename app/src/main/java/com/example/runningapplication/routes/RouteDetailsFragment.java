package com.example.runningapplication.routes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.runningapplication.MainActivity;
import com.example.runningapplication.databinding.FragmentRouteDetailsBinding;

public class RouteDetailsFragment extends Fragment {

    private FragmentRouteDetailsBinding binding;

    private RouteViewModel routeViewModel;
    private NavController navController;
    private MainActivity mainActivity;

    public RouteDetailsFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) requireActivity();

        routeViewModel = new ViewModelProvider(mainActivity).get(RouteViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentRouteDetailsBinding.inflate(inflater, container, false);

        Route selectedRoute = routeViewModel.getRoutes().get(RouteDetailsFragmentArgs.fromBundle(requireArguments()).getRouteIndex());

        // prosledjujemo lifecycleowner(this fragment) za koji zelimo da posmatra selekciju rute
//        routeViewModel.getSelectedRoute().observe(getViewLifecycleOwner(), selectedRoute -> {
//            if(selectedRoute != null){
                binding.toolbar.setTitle(selectedRoute.getLabel());
                binding.toolbar.setNavigationOnClickListener(view -> {
                    navController.navigateUp();
                });

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }
}