package com.mediaview.mediaview.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import com.mediaview.mediaview.DAO.accessor.MediaDataAccessor;
import com.mediaview.mediaview.model.Media;
import com.mediaview.mediaview.tools.tasks.DownloadTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by iem on 10/02/15.
 */
public class DownloadMedia implements DownloadTask.DownloadEventListener{

    private Media mMedia;
    private Context mContext;
    private DownloadMediaEventListener mListener;

    public interface DownloadMediaEventListener{
        public void hasFinishSuccessfully();
        public void hasFinishWithError();
    }

    public DownloadMedia(Media mMedia, Context mContext, DownloadMediaEventListener mListener) {
        this.mMedia = mMedia;
        this.mContext = mContext;
        this.mListener = mListener;
    }

    private void downloadMedia() {
        if(mMedia.getType() == Media.EType.Image){

            String filename = mMedia.getUrl().substring(mMedia.getUrl().lastIndexOf("/"));
            Bitmap bmp = drawableToBitmap(UIiv.getDrawable());

            FileOutputStream out = null;

            File root = Environment.getExternalStorageDirectory();
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

        }else if(mMedia.getType() == Media.EType.Texte) {
            try {
                String fileName = mMedia.getUrl().substring(mMedia.getUrl().lastIndexOf("/"));
                File root = Environment.getExternalStorageDirectory();
                File dir = new File(root.getAbsolutePath() + Constants.DIRECTORY_SAVE);
                if(!dir.exists()){
                    dir.mkdirs();
                }

                File myFile = new File(dir, fileName);
                myFile.createNewFile();
                FileOutputStream fOut = new FileOutputStream(myFile);
                OutputStreamWriter myOutWriter =
                        new OutputStreamWriter(fOut);
                myOutWriter.append(UIiv.toString());
                myOutWriter.close();
                fOut.close();

            } catch (Exception e) {
                e.printStackTrace();
                hasFinishWithError();
            }
            hasFinishSuccessfully();
        }else{
            DownloadTask task = new DownloadTask(mContext, this);
            task.execute(Constants.MEDIA_URL + mMedia.getUrl());

        }

    }

    @Override
    public void hasFinishSuccessfully() {
        Toast.makeText(mContext, "Download complete", Toast.LENGTH_LONG).show();
        MediaDataAccessor dataAccessor = new MediaDataAccessor(mContext);
        dataAccessor.addMediaLocal(mMedia);

        mListener.hasFinishSuccessfully();
    }

    @Override
    public void hasFinishWithError() {
        mListener.hasFinishWithError();
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
}
