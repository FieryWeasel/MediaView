package com.mediaview.mediaview.activities;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;

import com.mediaview.mediaview.R;
import com.mediaview.mediaview.tools.services.DownloadService;


public class Loading extends Activity {

    private Receiver receiver;
    private Activity activity = this;
    private LoadingSign loadingSign;
    private TextView UITextViewLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_loading);

        receiver = new Receiver();
        registerReceiver(receiver, new IntentFilter(DownloadService.LOADING_KEY));

        UITextViewLoading = (TextView) findViewById(R.id.loading_text);

        loadingSign = new LoadingSign(UITextViewLoading, "Loading");
        loadingSign.start();

        loadDB();
    }

    private void loadDB() {
        Intent intent = new Intent(this, DownloadService.class);
        startService(intent);
    }

    private class LoadingSign extends Thread{
        private static final double TIME_BEFORE_MAJ_SIGN = 0.5 * 1000;
        TextView textViewToEdit;
        String stringToEdit;
        int nbPointAfterString = 0;
        String stringDisplayed;
        public boolean needToStop = false;
        public LoadingSign(TextView textViewToEdit, String stringToEdit) {
            super();
            this.textViewToEdit = textViewToEdit;
            this.stringToEdit = stringToEdit;
            this.nbPointAfterString = 0;
        }
        @Override
        public void run() {
            while(!needToStop){
                stringDisplayed = stringToEdit;
                nbPointAfterString = (nbPointAfterString + 1) % 4;
                for(int i = 0; i < nbPointAfterString; i++)
                    stringDisplayed += ".";
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textViewToEdit.setText(stringDisplayed);
                    }
                });
                try {
                    Thread.sleep((long) TIME_BEFORE_MAJ_SIGN);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class Receiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(DownloadService.LOADING_KEY)){
                loadingSign.needToStop = true;
                Intent intent2Launch = new Intent(activity, MainActivity.class);
                startActivity(intent2Launch);
                activity.finish();
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
