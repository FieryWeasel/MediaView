package com.mediaview.mediaview.Tools.Tasks;


import android.os.AsyncTask;


import com.mediaview.mediaview.Model.Media;
import com.mediaview.mediaview.Tools.Constants;
import com.mediaview.mediaview.Tools.DownloadHelper;
import com.mediaview.mediaview.Tools.Manager;
import com.mediaview.mediaview.Tools.XmlParser;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


public class DataInitializationTask {

    private List<Media> medias;


    public DataInitializationTask() {
        new Task().execute();
    }

    private class Task extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            InputStream stream = DownloadHelper.loadFile(Constants.FILE_URL);

                if(stream !=null)
                    medias = XmlParser.parse(stream);

            for(Media media : medias)
                Manager.getInstance().getDbManager().createMedia(media);

            Manager.getInstance().setAllMedias(medias);

            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

    }

}