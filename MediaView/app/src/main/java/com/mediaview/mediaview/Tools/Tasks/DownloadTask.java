package com.mediaview.mediaview.tools.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.ProgressBar;

import com.mediaview.mediaview.tools.DownloadHelper;

import org.apache.http.util.ByteArrayBuffer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by iem on 04/02/15.
 */
public class DownloadTask extends AsyncTask<Object, Void, Void>{

    private Context mContext;
    private String dirName = "MediaView";

    private ProgressBar progressBar;

    public DownloadTask(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    protected Void doInBackground(Object... voids) {
        String path = (String) voids[0];
        InputStream stream = DownloadHelper.loadFile(path);
        String fileName = path.substring(path.lastIndexOf("/"));

        File root = android.os.Environment.getExternalStorageDirectory();
        File dir = new File(root.getAbsolutePath() + "/mnt/sdcard/" + dirName);
        if(!dir.exists()){
            dir.mkdirs();
        }

        File file = new File(dir,fileName);

        BufferedInputStream bufferinstream = new BufferedInputStream(stream);

        ByteArrayBuffer baf = new ByteArrayBuffer(5000);
        int current = 0;
        try {
            while((current = bufferinstream.read()) != -1){
                baf.append((byte) current);
            }

            FileOutputStream fos = new FileOutputStream(file);
            fos.write(baf.toByteArray());
            fos.flush();
            fos.close();
            int dotindex = fileName.lastIndexOf('.');

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
