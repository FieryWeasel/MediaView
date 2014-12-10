package com.mediaview.mediaview.Tools.Services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.mediaview.mediaview.Tools.Constants;

public class UpDateReceiver extends BroadcastReceiver {
    public UpDateReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(Constants.WAKE_TO_UPDATE)) {
            Intent intentDownload = new Intent(context, DownloadService.class);
            context.startService(intentDownload);   
        }

    }
}




