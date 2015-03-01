package com.mediaview.mediaview.tools.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ProgressBar;

import com.mediaview.mediaview.tools.Constants;
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

    private ProgressBar mProgressBar;
    private DownloadEventListener mListener;

    public DownloadTask(Context mContext, DownloadEventListener listener) {
        this.mContext = mContext;
        mListener = listener;
    }

    @Override
    protected Void doInBackground(Object... voids) {
        String path = (String) voids[0];
        InputStream stream = DownloadHelper.loadFile(path);
        String fileName = path.substring(path.lastIndexOf("/"));
        //mProgressBar = (ProgressBar)voids[1];

        File root = android.os.Environment.getExternalStorageDirectory();
        File dir = new File(root.getAbsolutePath() + Constants.DIRECTORY_SAVE);
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

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        mListener.hasFinishSuccessfully();
    }

    public interface DownloadEventListener{
        public void hasFinishSuccessfully();
        public void hasFinishWithError();
    }
}
