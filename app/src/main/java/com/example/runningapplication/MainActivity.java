package com.example.runningapplication;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.example.runningapplication.calories.CaloriesFragment;
import com.example.runningapplication.databinding.ActivityMainBinding;
import com.example.runningapplication.routes.RouteBrowseFragment;
import com.example.runningapplication.routes.RouteFragment;
import com.example.runningapplication.routes.RouteViewModel;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private FragmentManager fragmentManager;

    private static final String CALORIES_TAG = "fragment-calories-tag";
    private CaloriesFragment caloriesFragment;

    private static final String ROUTE_TAG = "fragment-route-tag";
    private RouteFragment routeFragment;

    private RouteViewModel routeViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        routeViewModel = new ViewModelProvider(this).get(RouteViewModel.class);

        fragmentManager = getSupportFragmentManager();

        if(fragmentManager.getFragments().size() == 0){
            caloriesFragment = new CaloriesFragment();
            routeFragment = new RouteFragment();
            //container je ono gde cemo da smestimo taj fragment
            fragmentManager.beginTransaction()
                    .add(R.id.frame_layout, routeFragment, ROUTE_TAG)
                    .add(R.id.frame_layout, caloriesFragment, CALORIES_TAG)
                    .hide(caloriesFragment)
                    .show(routeFragment)
                    .commit();
        } else{
            caloriesFragment = (CaloriesFragment) fragmentManager.findFragmentByTag(CALORIES_TAG);
            routeFragment = (RouteFragment) fragmentManager.findFragmentByTag(ROUTE_TAG);
        }


        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.menu_item_routes:
                    fragmentManager.beginTransaction()
//                            .replace(R.id.frame_layout, routeBrowseFragment, ROUTE_BROWSE_TAG)
                            .show(routeFragment)
                            .hide(caloriesFragment)
                            .commit();
                    return true;
                case R.id.menu_item_calories:
                    fragmentManager.beginTransaction()
//                                   .replace(R.id.frame_layout, new CaloriesFragment(), CALORIES_TAG)
                            .show(caloriesFragment)
                            .hide(routeFragment)
                            .commit();

                    return true;
            }
            return false;
        });
    }

    @Override
    public void onBackPressed() {

        if(binding.bottomNavigation.getSelectedItemId() == R.id.menu_item_routes){
            if(routeFragment.getChildFragmentManager().getBackStackEntryCount() > 0){
                routeViewModel.setSelectedRoute(null);
                routeFragment.getChildFragmentManager().popBackStack();
                return;
            }
        }
        super.onBackPressed();
    }
}
