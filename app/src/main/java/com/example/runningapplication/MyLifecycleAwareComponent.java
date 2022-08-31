package com.example.runningapplication;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

public class MyLifecycleAwareComponent implements DefaultLifecycleObserver {

    public static final String LOG_TAG = "activity-lifecycle";

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        DefaultLifecycleObserver.super.onCreate(owner);

        Log.d(LOG_TAG, "myOnCreate() called");
    }
}
