package com.example.runningapplication.workouts;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationChannelCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.runningapplication.R;

import java.util.Timer;
import java.util.TimerTask;

public class WorkoutService extends Service {

    private final Timer timer = new Timer();
    private boolean serviceStarted = false;

    private static final String NOTIFICATION_CHANNEL_ID = "workout-notification-channel";
    private static final int NOTIFICATION_ID = 1;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    // poziva se svaki put kada se pokrene servis
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        createNotificationChannel();
        // id notifikacije i sama notifikacija koju vidi korisnik
        startForeground(NOTIFICATION_ID, getNotification());

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

    private void createNotificationChannel() {

        NotificationChannelCompat notificationChannel = new NotificationChannelCompat
                .Builder(NOTIFICATION_CHANNEL_ID, NotificationManagerCompat.IMPORTANCE_DEFAULT)
                .setName(getString(R.string.workout_notification_channel_name))
                .build();

        NotificationManagerCompat.from(this).createNotificationChannel(notificationChannel);
    }

    private Notification getNotification(){

        return new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.baseline_directions_run_black_24)
                .setContentText(getString(R.string.workout_notification_content_title))
                .setContentText(getString(R.string.workout_notification_content_text))
                .build();
    }
}
