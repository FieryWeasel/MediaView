package com.mediaview.mediaview.fragments;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.mediaview.mediaview.DAO.accessor.MediaDataAccessor;
import com.mediaview.mediaview.model.Media;
import com.mediaview.mediaview.R;
import com.mediaview.mediaview.tools.Constants;
import com.mediaview.mediaview.tools.DownloadMedia;
import com.mediaview.mediaview.tools.LoadMedia;

import java.io.File;

/**
 * Created by IEM on 14/11/2014.
 */
public class MediasViewFragment extends Fragment implements DownloadMedia.DownloadMediaEventListener {

    private static Media media;

    private ImageView UIImageView = null;
    private TextView UITextView = null;
    private VideoView UIVideoView = null;
    private Button UIButtonDownload;
    private Button UIButtonDelete;
    private ImageView UIiv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.media_fragment_view, container, false);

        initComponent(rootView);

        Drawable d = Drawable.createFromPath(Uri.parse(Constants.MEDIA_URL + media.getUrl()).getPath());
        UIiv = (ImageView)rootView.findViewById(R.id.ivMediaDisplay);
        UIiv.setImageDrawable(d);

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

        if(media.isLocal()){
            buttonsVisibility(View.GONE, View.VISIBLE);
        }else
            buttonsVisibility(View.VISIBLE, View.GONE);

        return rootView;
    }

    private void initComponent(View rootView) {

        DownloadMedia dlMedia = new DownloadMedia(media, getActivity(), this);
        UIImageView = (ImageView) rootView.findViewById(R.id.media_view_image);
        UITextView = (TextView) rootView.findViewById(R.id.media_view_text);
        UIVideoView = (VideoView) rootView.findViewById(R.id.media_view_video);
        UIButtonDownload = (Button) rootView.findViewById(R.id.btn_download);
        UIButtonDelete = (Button) rootView.findViewById(R.id.btn_delete);
        UIButtonDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadMedia();
            }
        });
        UIButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String filename = media.getUrl().substring(media.getUrl().lastIndexOf("/"));
                File root = android.os.Environment.getExternalStorageDirectory();
                File file = new File(root.getAbsolutePath() + Constants.DIRECTORY_SAVE + "/" + filename);
                boolean deleted = file.delete();
                if(deleted){
                    deleteSuccessful();
                }else{
                    deleteWithError();
                }
            }
        });
    }

    private void setVisibility(int image, int text, int video) {
        UIImageView.setVisibility(image);
        UITextView.setVisibility(text);
        UIVideoView.setVisibility(video);
    }





    private void buttonsVisibility(int down, int delete){
        UIButtonDownload.setVisibility(down);
        UIButtonDelete.setVisibility(delete);
    }

    public void setMedia(Media media){
        this.media = media;
    }



    private void deleteWithError() {
        Toast.makeText(getActivity(), "Error while deleting", Toast.LENGTH_LONG).show();
    }

    private void deleteSuccessful() {
        Toast.makeText(getActivity(), "Delete complete", Toast.LENGTH_LONG).show();
        MediaDataAccessor dataAccessor = new MediaDataAccessor(getActivity());
        dataAccessor.deleteMediaLocal(media);

        buttonsVisibility(View.VISIBLE, View.GONE);
    }

    @Override
    public void hasFinishSuccessfully() {
        buttonsVisibility(View.GONE, View.VISIBLE);
    }

    @Override
    public void hasFinishWithError() {
        Toast.makeText(getActivity(), "Error while downloading", Toast.LENGTH_LONG).show();
    }
}