package com.example.runningapplication.threading;

import android.os.Looper;

public class CustomLooperThread extends Thread{

    private Looper looper = null;

    @Override
    public void run() {
        Looper.prepare();
        // mora nakon prepare
        looper = Looper.myLooper();
        Looper.loop();
    }

    public Looper getLooper() {
        return looper;
    }
}
