package com.example.runningapplication.workouts;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.DefaultLifecycleObserver;

import com.example.runningapplication.MainActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.gms.tasks.Task;

import javax.inject.Inject;

public class LifecycleAwareLocator implements DefaultLifecycleObserver {

    @Inject
    public LifecycleAwareLocator() {
    }

    public void getLocation(Context context) {

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d(MainActivity.LOG_TAG, "permission not granted");
            return;
        }

        FusedLocationProviderClient locationProviderClient = LocationServices.getFusedLocationProviderClient(context);

        Task<Location> task = locationProviderClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, new CancellationTokenSource().getToken());

        task.addOnSuccessListener(location -> {
            if(location != null){
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();

                Log.d(MainActivity.LOG_TAG, "lat: " + latitude + " lon: " + longitude);
            }
        });
    }
}
