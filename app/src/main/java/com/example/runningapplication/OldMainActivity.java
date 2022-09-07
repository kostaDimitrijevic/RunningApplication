//package com.example.runningapplication;
//
//
//import android.os.Bundle;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.FragmentManager;
//import androidx.lifecycle.ViewModelProvider;
//import androidx.navigation.NavController;
//import androidx.navigation.NavDirections;
//import androidx.navigation.fragment.NavHostFragment;
//
//import com.example.runningapplication.calories.CaloriesFragmentDirections;
//import com.example.runningapplication.databinding.ActivityMainBinding;
//import com.example.runningapplication.routes.RouteViewModel;
//
//public class MainActivity extends AppCompatActivity {
//
//    private ActivityMainBinding binding;
//    private FragmentManager fragmentManager;
//
//    private RouteViewModel routeViewModel;
//    private NavController navController;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        binding = ActivityMainBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//        routeViewModel = new ViewModelProvider(this).get(RouteViewModel.class);
//
//        fragmentManager = getSupportFragmentManager();
//
//        NavHostFragment navHost = (NavHostFragment) fragmentManager.findFragmentById(R.id.nav_host_fragment);
//
//        navController = navHost.getNavController();
//
//        binding.bottomNavigation.setOnItemSelectedListener(item -> {
//            switch (item.getItemId()){
//                case R.id.menu_item_routes:
//                    switch (navController.getCurrentDestination().getId()){
//                        case R.id.calories:
//                            NavDirections action = CaloriesFragmentDirections.actionCaloriesPop();
//                            navController.navigate(action);
//                            break;
//                        default:
//                            break;
//                    }
//
//                    return true;
//                case R.id.menu_item_calories:
//                    switch (navController.getCurrentDestination().getId()){
//                        case R.id.route_browse:
//                        case R.id.route_details:
//                            NavDirections action = NavGraphDirections.actionGlobalCalories();
//                            navController.navigate(action);
//                            break;
//                        default:
//                            break;
//                    }
//
//                    return true;
//            }
//            return false;
//        });
//    }
//
//    @Override
//    public void onBackPressed() {
//        switch (navController.getCurrentDestination().getId()){
//            case R.id.route_details:
//                routeViewModel.setSelectedRoute(null);
//                break;
//            case R.id.calories:
//                // pozvace se i item selected listener nakon instrukcije!
//                // binding.bottomNavigation.setSelectedItemId(R.id.menu_item_routes);
//                binding.bottomNavigation.getMenu().getItem(0).setChecked(true);
//                break;
//        }
//        super.onBackPressed();
//    }
//}
