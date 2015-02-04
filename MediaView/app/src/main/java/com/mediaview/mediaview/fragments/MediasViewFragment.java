package com.mediaview.mediaview.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.mediaview.mediaview.model.Media;
import com.mediaview.mediaview.R;
import com.mediaview.mediaview.tools.LoadMedia;

/**
 * Created by IEM on 14/11/2014.
 */
public class MediasViewFragment extends Fragment {

    private static Media media;

    private ImageView UIImageView = null;
    private TextView UITextView = null;
    private VideoView UIVideoView = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.media_fragment_view, container, false);

        initComponent(rootView);
        LoadMedia loadMedia = new LoadMedia(getActivity());

        switch (media.getType()){
            case Audio:
                setVisibility(View.GONE, View.GONE, View.VISIBLE);
                loadMedia.loadAudio(media, UIVideoView);
                break;
            case Video:
                setVisibility(View.GONE, View.GONE, View.VISIBLE);
                loadMedia.loadVideo(media, UIVideoView);
                break;
            case Texte:
                setVisibility(View.GONE, View.VISIBLE, View.GONE);
                loadMedia.loadText(media, UITextView);
                break;
            case Image:
                setVisibility(View.VISIBLE, View.GONE, View.GONE);
                loadMedia.loadImage(media, UIImageView);
                break;
        }

        return rootView;
    }

    private void initComponent(View rootView) {
        UIImageView = (ImageView) rootView.findViewById(R.id.media_view_image);
        UITextView = (TextView) rootView.findViewById(R.id.media_view_text);
        UIVideoView = (VideoView) rootView.findViewById(R.id.media_view_video);
    }

    private void setVisibility(int image, int text, int video) {
        UIImageView.setVisibility(image);
        UITextView.setVisibility(text);
        UIVideoView.setVisibility(video);
    }

    public void setMedia(Media media){
        this.media = media;
    }
}