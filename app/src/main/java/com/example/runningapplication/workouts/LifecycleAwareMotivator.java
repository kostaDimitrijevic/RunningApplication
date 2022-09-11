package com.example.runningapplication.workouts;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.example.runningapplication.R;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicReference;

import javax.inject.Inject;

public class LifecycleAwareMotivator implements DefaultLifecycleObserver {

    private final Timer timer = new Timer();

    private final AtomicReference<String> message = new AtomicReference<>(null);
    private int messageIndex = 1;

    @Inject
    public LifecycleAwareMotivator() {
    }

    public void start(Context context){
        if(message.get() == null){
            String[] motivationMessages = context.getResources().getStringArray(R.array.workout_toast_motivation);
            message.set(motivationMessages[0]);
        }

        Handler handler = new Handler(Looper.getMainLooper());
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(() -> {
                    Toast.makeText(context, message.get(), Toast.LENGTH_SHORT).show();
                });
            }
        }, 0, 7000);
    }

    public void changeMessage(Context context){
        String[] motivationMessages = context.getResources().getStringArray(R.array.workout_toast_motivation);
        message.set(motivationMessages[messageIndex]);
        messageIndex = (messageIndex + 1) % motivationMessages.length;

        Toast.makeText(
                context,
                "changeMotivationMessage()",
                Toast.LENGTH_SHORT
        ).show();
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        timer.cancel();
    }
}
