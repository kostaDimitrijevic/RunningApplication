package com.example.runningapplication.workouts;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.runningapplication.R;

import java.util.Timer;
import java.util.TimerTask;

public class WorkoutService extends Service {

    private final Timer timer = new Timer();
    private boolean serviceStarted = false;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    // poziva se svaki put kada se pokrene servis
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(!serviceStarted){
            scheduleTimer();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    private void scheduleTimer(){
        Handler handler = new Handler(Looper.getMainLooper());
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(() -> {
                    Toast.makeText(WorkoutService.this, getResources().getStringArray(R.array.workout_toast_motivation)[0], Toast.LENGTH_SHORT).show();
                });
            }
        }, 0, 7000);

        serviceStarted = true;
    }
}
