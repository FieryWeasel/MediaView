package com.mediaview.mediaview.fragments;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
import com.mediaview.mediaview.tools.LoadMedia;
import com.mediaview.mediaview.tools.tasks.DownloadTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by IEM on 14/11/2014.
 */
public class MediasViewFragment extends Fragment implements DownloadTask.DownloadEventListener {

    private static Media media;

    private ImageView UIImageView = null;
    private TextView UITextView = null;
    private VideoView UIVideoView = null;
    private Button UIButtonDownload;
    private Button UIButtonDelete;

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

        if(media.isLocal()){
            buttonsVisibility(View.GONE, View.VISIBLE);
        }else
            buttonsVisibility(View.VISIBLE, View.GONE);

        return rootView;
    }

    private void initComponent(View rootView) {
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
                    deleteSuccessfull();
                }else{
                    deleteWithError();
                }
            }
        });
    }



    private void downloadMedia() {
        if(media.getType() == Media.EType.Image){

            String filename = media.getUrl().substring(media.getUrl().lastIndexOf("/"));
            Bitmap bmp = drawableToBitmap(UIImageView.getDrawable());

            FileOutputStream out = null;

            File root = android.os.Environment.getExternalStorageDirectory();
            File dir = new File(root.getAbsolutePath() + Constants.DIRECTORY_SAVE);
            if(!dir.exists()){
                dir.mkdirs();
            }

            try {
                out = new FileOutputStream(root.getAbsolutePath() + Constants.DIRECTORY_SAVE + "/" + filename);
                bmp.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
                hasFinishSuccessfully();
                // PNG is a lossless format, the compression factor (100) is ignored
            } catch (Exception e) {
                e.printStackTrace();
                hasFinishWithError();
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }else if(media.getType() == Media.EType.Texte) {
            try {
                String fileName = media.getUrl().substring(media.getUrl().lastIndexOf("/"));
                File root = android.os.Environment.getExternalStorageDirectory();
                File dir = new File(root.getAbsolutePath() + Constants.DIRECTORY_SAVE);
                if(!dir.exists()){
                    dir.mkdirs();
                }

                File myFile = new File(dir, fileName);
                myFile.createNewFile();
                FileOutputStream fOut = new FileOutputStream(myFile);
                OutputStreamWriter myOutWriter =
                        new OutputStreamWriter(fOut);
                myOutWriter.append(UITextView.getText());
                myOutWriter.close();
                fOut.close();

            } catch (Exception e) {
                e.printStackTrace();
                hasFinishWithError();
            }
            hasFinishSuccessfully();
        }else{
                DownloadTask task = new DownloadTask(getActivity(), this);
                task.execute(Constants.MEDIA_URL + media.getUrl());

        }

    }

    public Bitmap drawableToBitmap (Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable)drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
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

    @Override
    public void hasFinishSuccessfully() {
        Toast.makeText(getActivity(), "Download complete", Toast.LENGTH_LONG).show();
        MediaDataAccessor dataAccessor = new MediaDataAccessor(getActivity());
        dataAccessor.addMediaLocal(media);

       buttonsVisibility(View.GONE, View.VISIBLE);
    }

    @Override
    public void hasFinishWithError() {
        Toast.makeText(getActivity(), "Error while downloading", Toast.LENGTH_LONG).show();
    }

    private void deleteWithError() {
        Toast.makeText(getActivity(), "Error while deleting", Toast.LENGTH_LONG).show();
    }

    private void deleteSuccessfull() {
        Toast.makeText(getActivity(), "Delete complete", Toast.LENGTH_LONG).show();
        MediaDataAccessor dataAccessor = new MediaDataAccessor(getActivity());
        dataAccessor.deleteMediaLocal(media);

        buttonsVisibility(View.VISIBLE, View.GONE);
    }
}