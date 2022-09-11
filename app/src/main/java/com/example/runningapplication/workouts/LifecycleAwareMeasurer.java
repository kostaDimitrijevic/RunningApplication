package com.example.runningapplication.workouts;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.example.runningapplication.MainActivity;

import javax.inject.Inject;

public class LifecycleAwareMeasurer implements DefaultLifecycleObserver {

    private SensorManager sensorManager = null;

    private SensorEventListener listener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            Log.d(MainActivity.LOG_TAG, "temp-sensor: " + sensorEvent.values[0]);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    @Inject
    public LifecycleAwareMeasurer() {
    }

    public void start(Context context){
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);

        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        if(sensorManager != null){
            sensorManager.unregisterListener(listener);
        }
    }
}
