package com.example.runningapplication.workouts;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationChannelCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleService;

import com.example.runningapplication.MainActivity;
import com.example.runningapplication.R;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicReference;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class WorkoutService extends LifecycleService {

    private boolean serviceStarted = false;

    private static final String NOTIFICATION_CHANNEL_ID = "workout-notification-channel";
    private static final int NOTIFICATION_ID = 1;


    public static final String INTENT_ACTION_START = "com.example.runningapplication.workouts.START";
    public static final String INTENT_ACTION_POWER = "com.example.runningapplication.workouts.POWER";

    @Inject
    public LifecycleAwareMotivator motivator;

    @Inject
    public LifecycleAwarePlayer player;

    @Inject
    public LifecycleAwareMeasurer measurer;

    @Inject
    public LifecycleAwareLocator locator;

    @Override
    public void onCreate() {
        super.onCreate();

        getLifecycle().addObserver(motivator);
        getLifecycle().addObserver(player);
        getLifecycle().addObserver(measurer);
        getLifecycle().addObserver(locator);
    }

    // poziva se svaki put kada se pokrene servis
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        super.onStartCommand(intent, flags, startId);
        createNotificationChannel();
        // id notifikacije i sama notifikacija koju vidi korisnik
        startForeground(NOTIFICATION_ID, getNotification());

        switch (intent.getAction()) {
            case INTENT_ACTION_START:
                if (!serviceStarted) {
                    serviceStarted = true;
                    motivator.start(this);
                    player.start(this);
                    measurer.start(this);
                    locator.getLocation(this);
                }
                break;
            case INTENT_ACTION_POWER:
                if (serviceStarted) {
                    motivator.changeMessage(this);
                }
                break;
        }

        return START_STICKY;
    }

    // nema veze sa medju procesnom komunikacijom
    public class PrimitiveBinder extends Binder{
        public WorkoutService getService(){
            return WorkoutService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        super.onBind(intent);
        return new PrimitiveBinder();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void createNotificationChannel() {

        NotificationChannelCompat notificationChannel = new NotificationChannelCompat
                .Builder(NOTIFICATION_CHANNEL_ID, NotificationManagerCompat.IMPORTANCE_LOW)
                .setName(getString(R.string.workout_notification_channel_name))
                .build();

        NotificationManagerCompat.from(this).createNotificationChannel(notificationChannel);
    }

    private Notification getNotification(){

        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        intent.setAction(MainActivity.INTENT_ACTION_NOTIFICATION);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        return new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.baseline_directions_run_black_24)
                .setContentText(getString(R.string.workout_notification_content_title))
                .setContentText(getString(R.string.workout_notification_content_text))
                .setContentIntent(pendingIntent)
                .setColorized(true)
                .setColor(ContextCompat.getColor(this, R.color.teal_200))
                .build();
    }
}
