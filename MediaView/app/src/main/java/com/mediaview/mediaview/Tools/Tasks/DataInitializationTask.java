package com.mediaview.mediaview.tools.tasks;


import android.os.AsyncTask;

import com.mediaview.mediaview.model.Media;
import com.mediaview.mediaview.tools.Constants;
import com.mediaview.mediaview.tools.DownloadHelper;
import com.mediaview.mediaview.tools.Manager;
import com.mediaview.mediaview.tools.XmlParser;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


public class DataInitializationTask {

    private List<Media> medias;
    private InitializationCallback mCallback;


    public DataInitializationTask(InitializationCallback callback) {
        mCallback = callback;
        new Task().execute();
    }

    private class Task extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mCallback.preExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            InputStream stream = DownloadHelper.loadFile(Constants.FILE_URL);

                if(stream !=null)
                    medias = XmlParser.parse(stream);

            for(Media media : medias)
                Manager.getInstance().getDbManager().createMedia(media);

            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mCallback.postExecute();
        }
    }

    public interface InitializationCallback{

        public void postExecute();
        public void preExecute();

    }
}