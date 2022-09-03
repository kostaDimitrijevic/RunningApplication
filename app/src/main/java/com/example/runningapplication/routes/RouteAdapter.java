package com.example.runningapplication.routes;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.runningapplication.databinding.ViewHolderRouteBinding;

import java.util.List;

public class RouteAdapter extends RecyclerView.Adapter<RouteAdapter.RouteViewHolder> {

    private final RouteBrowseActivity routeBrowseActivity;
    private final List<Route> routes;

    public RouteAdapter(RouteBrowseActivity routeBrowseActivity, List<Route> routes){
        this.routeBrowseActivity = routeBrowseActivity;
        this.routes = routes;
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
        Route route = routes.get(position);
        ViewHolderRouteBinding binding = holder.binding;

        binding.routeImage.setImageDrawable(route.getImage());
        binding.routeLabel.setText(route.getLabel());
        binding.routeName.setText(route.getName());
        binding.routeLength.setText(route.getLength() + "km");
        binding.routeDifficulty.setText(route.getDifficulty());

    }

    @Override
    public int getItemCount() {
        return routes.size();
    }

    public class RouteViewHolder extends RecyclerView.ViewHolder{

        public ViewHolderRouteBinding binding;

        public RouteViewHolder(@NonNull ViewHolderRouteBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.routeButtonDescription.setOnClickListener(view -> {
                int routeIndex = getAdapterPosition();

                Intent intent = new Intent();
                intent.setClass(routeBrowseActivity, RouteDetailsActivity.class);
                // prosledjujemo index rute koja je selektovana
                intent.putExtra(RouteDetailsActivity.SELECTED_ROUTE_INDEX, routeIndex);

                routeBrowseActivity.startActivity(intent);
            });

            binding.routeButtonLocation.setOnClickListener(view -> {
                int routeIndex = getAdapterPosition();
                String locationString = routes.get(routeIndex).getLocation();
                locationString = locationString.replace(" ", "20%");
                locationString = locationString.replace(",", "%2C");
                Uri locationUri = Uri.parse("geo:0,0?q=" + locationString);

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(locationUri);

                routeBrowseActivity.startActivity(intent);
            });
        }
    }
}
