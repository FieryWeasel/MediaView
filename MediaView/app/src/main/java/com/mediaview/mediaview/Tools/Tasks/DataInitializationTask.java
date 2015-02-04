package com.mediaview.mediaview.tools.tasks;


import android.content.Context;
import android.os.AsyncTask;


import com.mediaview.mediaview.DAO.accessor.MediaDataAccessor;
import com.mediaview.mediaview.model.Media;
import com.mediaview.mediaview.tools.Constants;
import com.mediaview.mediaview.tools.DownloadHelper;
import com.mediaview.mediaview.tools.Manager;
import com.mediaview.mediaview.tools.XmlParser;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


public class DataInitializationTask {

    private List<Media> medias;
    private Context context;


    public DataInitializationTask(Context context) {
        new Task().execute();
        this.context = context;
    }

    private class Task extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            InputStream stream = DownloadHelper.loadFile(Constants.FILE_URL);

            MediaDataAccessor mediaAccessor = new MediaDataAccessor(context);

            XmlParser parser = new XmlParser(context);
                if(stream !=null)
                    medias = parser.parse(stream);

            for(Media media : medias)
                mediaAccessor.createMedia(media);

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