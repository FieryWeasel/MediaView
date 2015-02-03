package com.mediaview.mediaview.tools.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;


import com.mediaview.mediaview.tools.tasks.DataInitializationTask;

public class DownloadService extends Service {
    public DownloadService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        new DataInitializationTask(getApplicationContext());

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
