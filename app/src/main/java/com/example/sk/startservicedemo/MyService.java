package com.example.sk.startservicedemo;

import android.app.Service;
import android.content.Intent;
import android.nfc.Tag;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by SK on 3/23/2018.
 */
/*
-Insert service tag in AndroidManifest.xml
            <service
            android:name=".MyService"
            android:exported="false" />

*/
public class MyService extends Service {
    public static final String TAG = "StartedServiceDemo";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG,"onBind: ");
        return null;
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: ");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG,"onStartCommand: ");
        Runnable r = new Runnable() {
            @Override
            public void run() {
                long endTime = System.currentTimeMillis() + 10 * 1000;
                for (int i = 0; i < 3; i++) {
                    while (System.currentTimeMillis() < endTime) {
                        synchronized (this) {
                            try {
                                wait(endTime - System.currentTimeMillis());
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    Log.d(TAG, "onStartCommand: running");

                }
                stopSelf();
            }
        };
        Thread t = new Thread(r);
        t.start();


        return START_STICKY;

        //return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy: ");
    }
}
