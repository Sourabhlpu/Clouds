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

    private static final Object lock = new Object();
    private static AppExecutors sInstance;
    private final Executor diskIO;
    private final Executor mainThread;
    private final Executor networkIO;


    private AppExecutors(Executor diskIO, Executor networkIO, Executor mainThead)
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

    private static class MainThreadExecutor implements Executor {

        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable runnable) {
            mainThreadHandler.post(runnable);
        }
    }

}
