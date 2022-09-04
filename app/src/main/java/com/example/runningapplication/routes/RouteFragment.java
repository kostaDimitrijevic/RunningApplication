package com.example.runningapplication.routes;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.runningapplication.MainActivity;
import com.example.runningapplication.R;
import com.example.runningapplication.databinding.FragmentRouteBinding;

import java.util.ArrayList;
import java.util.List;

public class RouteFragment extends Fragment {

    private FragmentRouteBinding binding;
    // ovo treba da bude licni fragment manager ne njegove parent aktivnosti
    private FragmentManager childFragmentManager;

    private static final String ROUTE_BROWSE_TAG = "fragment-route-browse-tag";
    private RouteBrowseFragment routeBrowseFragment;

    private RouteViewModel routeViewModel;

    public RouteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRouteBinding.inflate(inflater, container, false);

        childFragmentManager = getChildFragmentManager();

        MainActivity parentActivity = (MainActivity) getActivity();

        routeViewModel = new ViewModelProvider(parentActivity).get(RouteViewModel.class);

        List<Route> routes = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            routes.add(Route.createFromResources(getResources(), i));
        }
        routeViewModel.setRoutes(routes);


        if(childFragmentManager.getFragments().size() == 0){
            routeBrowseFragment = new RouteBrowseFragment();
            childFragmentManager.beginTransaction()
                    .add(R.id.frame_layout, routeBrowseFragment, ROUTE_BROWSE_TAG)
                    .commit();
        } else{
            routeBrowseFragment = (RouteBrowseFragment) childFragmentManager.findFragmentByTag(ROUTE_BROWSE_TAG);
        }

        routeViewModel.getSelectedRoute().observe(getViewLifecycleOwner(), selectedRoute -> {
            // jer moramo da obezbedimo da se ne stavi dva puta na stack ovaj fragment
            if(selectedRoute != null && childFragmentManager.getBackStackEntryCount() == 0){
                // problem je ovde sto addToBackStack je backstack childFragmentManager-a ne fragmentManager aktivnosti
                // kada se klikne back onda se prvo gleda da li na fragment manager-u aktivnosti postoji nesto na backstacku
                childFragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, new RouteDetailsFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        return binding.getRoot();
    }
}