package com.mediaview.mediaview.activities;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.mediaview.mediaview.R;
import com.mediaview.mediaview.tools.services.DownloadService;


public class Loading extends Activity {

    private Receiver receiver;
    private Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_loading);

        receiver = new Receiver();
        registerReceiver(receiver, new IntentFilter(DownloadService.LOADING_KEY));

        loadDB();
    }

    private void loadDB() {
        Intent intent = new Intent(this, DownloadService.class);
        startService(intent);
    }

    private class Receiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(DownloadService.LOADING_KEY)){
                Intent intent2Launch = new Intent(activity, MainActivity.class);
                startActivity(intent2Launch);
            }

            unregisterReceiver(receiver);
            receiver = null;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(receiver!=null) {

        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

    }
}
