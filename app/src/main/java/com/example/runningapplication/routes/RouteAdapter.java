package com.example.runningapplication.routes;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.runningapplication.MainActivity;
import com.example.runningapplication.databinding.ViewHolderRouteBinding;

import java.util.List;

public class RouteAdapter extends RecyclerView.Adapter<RouteAdapter.RouteViewHolder> {

    private final MainActivity mainActivity;
    private final RouteViewModel routeViewModel;

    public RouteAdapter(MainActivity mainActivity){
        this.mainActivity = mainActivity;
        this.routeViewModel = new ViewModelProvider(mainActivity).get(RouteViewModel.class);
    }

    @NonNull
    @Override
    public RouteAdapter.RouteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewHolderRouteBinding viewHolderRouteBinding = ViewHolderRouteBinding.inflate(layoutInflater, parent, false);
        return new RouteViewHolder(viewHolderRouteBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RouteAdapter.RouteViewHolder holder, int position) {
        Route route = routeViewModel.getRoutes().get(position);
        ViewHolderRouteBinding binding = holder.binding;

        binding.routeImage.setImageDrawable(route.getImage());
        binding.routeLabel.setText(route.getLabel());
        binding.routeName.setText(route.getName());
        binding.routeLength.setText(route.getLength() + "km");
        binding.routeDifficulty.setText(route.getDifficulty());

    }

    @Override
    public int getItemCount() {
        return routeViewModel.getRoutes().size();
    }

    public class RouteViewHolder extends RecyclerView.ViewHolder{

        public ViewHolderRouteBinding binding;

        public RouteViewHolder(@NonNull ViewHolderRouteBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.routeButtonDescription.setOnClickListener(view -> {
                int routeIndex = getAdapterPosition();
                routeViewModel.setSelectedRoute(routeViewModel.getRoutes().get(routeIndex));

//                Intent intent = new Intent();
//                intent.setClass(mainActivity, RouteDetailsActivity.class);
//                // prosledjujemo index rute koja je selektovana
//                intent.putExtra(RouteDetailsActivity.SELECTED_ROUTE_INDEX, routeIndex);
//
//                mainActivity.startActivity(intent);
            });

            binding.routeButtonLocation.setOnClickListener(view -> {
                int routeIndex = getAdapterPosition();
                String locationString = routeViewModel.getRoutes().get(routeIndex).getLocation();
                locationString = locationString.replace(" ", "20%");
                locationString = locationString.replace(",", "%2C");
                Uri locationUri = Uri.parse("geo:0,0?q=" + locationString);

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(locationUri);

                mainActivity.startActivity(intent);
            });
        }
    }
}
