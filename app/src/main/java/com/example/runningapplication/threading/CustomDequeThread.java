package com.example.runningapplication.threading;

import java.util.concurrent.LinkedBlockingDeque;

public class CustomDequeThread extends Thread{

    private LinkedBlockingDeque<Runnable> runnableDeque = new LinkedBlockingDeque<>();

    public LinkedBlockingDeque<Runnable> getRunnableDeque() {
        return runnableDeque;
    }

    @Override
    public void run() {
        while (true){
            try {
                // blokira se dok ne postoji niko u redu
                Runnable runnable = runnableDeque.takeFirst();
                runnable.run();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
