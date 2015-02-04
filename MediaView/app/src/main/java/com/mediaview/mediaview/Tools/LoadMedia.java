package com.mediaview.mediaview.tools;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.mediaview.mediaview.model.Media;

import com.squareup.picasso.Picasso;
import com.github.kevinsawicki.http.HttpRequest;

/**
 * Created by Simon on 03/02/2015.
 */
public class LoadMedia {
    private Context context;

    public LoadMedia(Context context){
        this.context = context;
    }

    public boolean loadImage(Media media, ImageView container){
        if(media.getType() != Media.EType.Image)
            return false;

        Picasso.with(context).load(media.getUrl()).into(container);

        return true;
    }

    public boolean loadAudio(Media media, VideoView container){
        if(media.getType() != Media.EType.Audio)
            return false;

        MediaController mc = new MediaController(context);
        mc.setAnchorView(container);
        mc.setMediaPlayer(container);

        Uri video = Uri.parse(media.getUrl());

        container.setMediaController(mc);
        container.setVideoURI(video);
        container.start();

        return true;
    }

    public boolean loadVideo(Media media, VideoView container){
        if(media.getType() != Media.EType.Video)
            return false;

        MediaController mc = new MediaController(context);
        mc.setAnchorView(container);
        mc.setMediaPlayer(container);

        Uri video = Uri.parse(media.getUrl());

        container.setMediaController(mc);
        container.setVideoURI(video);
        container.start();

        return true;
    }

    public boolean loadText(Media media, TextView container){
        if(media.getType() != Media.EType.Texte)
            return false;

        new DownloadTask().execute(media.getUrl(), container);

        return true;
    }

    private class DownloadTask extends AsyncTask<Object, Long, String> {
        EditText container;

        protected String doInBackground(Object... params) {
            try {
                container = (EditText) params[1];
                return HttpRequest.get((String)params[0]).body();
            } catch (com.github.kevinsawicki.http.HttpRequest.HttpRequestException exception) {
                return null;
            }
        }

        protected void onProgressUpdate(Long... progress) {
            Log.d(this.getClass().getName(), "Downloaded bytes: " + progress[0]);
        }

        protected void onPostExecute(String response) {
            container.setText(response);
        }
    }
}
