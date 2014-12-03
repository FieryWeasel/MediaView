package com.mediaview.mediaview.tools.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.mediaview.mediaview.tools.Constants;

public class UpDateReceiver extends BroadcastReceiver {
    public UpDateReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("LOL", "Recurring alarm; requesting download service.");
        // start the download
        Intent downloader = new Intent(context, DownloadService.class);
        context.startService(downloader);
    }
}




