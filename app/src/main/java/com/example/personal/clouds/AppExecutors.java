package com.example.personal.clouds;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by personal on 12/24/2017.
 */

public class AppExecutors {

    public static final Object lock = new Object();
    public static AppExecutors sInstance;
    public final Executor diskIO;
    public final Executor mainThread;
    public final Executor networkIO;


    public AppExecutors(Executor diskIO, Executor networkIO, Executor mainThead)
    {
        this.diskIO = diskIO;
        this.mainThread = mainThead;
        this.networkIO = networkIO;
    }


    public static AppExecutors getInstance()
    {
        if(sInstance == null)
        {
            synchronized (lock)
            {
                sInstance = new AppExecutors(Executors.newSingleThreadExecutor(),
                        Executors.newFixedThreadPool(3),
                        new MainThreadExecutor());
            }
        }

        return sInstance;
    }

    public Executor diskIO() {
        return diskIO;
    }

    public Executor mainThread() {
        return mainThread;
    }

    public Executor networkIO() {
        return networkIO;
    }

    public static class MainThreadExecutor implements Executor {

        public Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable runnable) {
            mainThreadHandler.post(runnable);
        }
    }

}
