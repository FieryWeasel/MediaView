package com.mediaview.mediaview.DAO.accessor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.mediaview.mediaview.model.Media;
import com.mediaview.mediaview.tools.DBManager;
import com.mediaview.mediaview.tools.Manager;

import java.util.ArrayList;

/**
 * Created by iem on 10/12/14.
 */
public class MediaDataAccessor {

    public static final String MEDIA_VERSION = "mediaVersion";
    public static final String MEDIA_NOM = "mediaName";
    public static final String MEDIA_URL = "mediaURL";
    public static final String MEDIA_TYPE = "mediaType";
    public static final String MEDIA_ID = "mediaId";
    public static final String MEDIA_TABLE = "medias";

    private DBManager mDBManager;
    SQLiteDatabase db;

    public MediaDataAccessor(Context context){
        mDBManager = new DBManager(context);
    }

    public void createMedia(Media media){
        db = mDBManager.getReadableDatabase();
        ContentValues values = new ContentValues(4);
        values.put(MEDIA_VERSION, media.getVersion());
        values.put(MEDIA_NOM, media.getName());
        values.put(MEDIA_URL, media.getUrl());
        values.put(MEDIA_TYPE, media.getType().toString());
        long returnCode = db.insert(MEDIA_TABLE,MEDIA_ID,values);
        if(returnCode == -1){
            Log.e("Create_Media", "Error");
        }
    }

    public void createMedia(int version, String name, String url, String type){
        db = mDBManager.getReadableDatabase();
        ContentValues values = new ContentValues(4);
        values.put(MEDIA_VERSION, version);
        values.put(MEDIA_NOM, name);
        values.put(MEDIA_URL, url);
        values.put(MEDIA_TYPE, type.toString());
        long returnCode = db.insert(MEDIA_TABLE,MEDIA_ID,values);
        if(returnCode == -1){
            Log.e("Create_Media", "Error");
        }
    }

    public Media getMediaById(int id){
        Media media = new Media();
        SQLiteDatabase db = mDBManager.getReadableDatabase();
        Cursor c;
        try {
            c = db.rawQuery("SELECT * FROM "+MEDIA_TABLE+" WHERE "+MEDIA_ID+" = ?",new String[] {String.valueOf(id)} );
            media.setVersion(c.getInt(1));
            media.setName(c.getString(2));
            media.setUrl(c.getString(3));
            media.setType(Manager.getInstance().getEnumFromString(c.getString(4)));
            c.close();
        }
        catch (SQLiteException e) {
            Log.d("getMediaById", "error getting Media : " + e.getMessage());
            media = null;
        }
        db.close();
        return media;
    }

    public ArrayList<Media> getAllMedia(){
        ArrayList<Media> medias = new ArrayList<Media>();
        db = mDBManager.getReadableDatabase();
        Media media;
        Cursor c;
        try {
            c = db.rawQuery("SELECT * FROM "+MEDIA_TABLE, null);
            boolean eof = c.moveToFirst();
            while(eof){
                media = new Media();
                media.setId(c.getInt(0));
                media.setVersion(c.getInt(1));
                media.setName(c.getString(2));
                media.setUrl(c.getString(3));
                media.setType(Manager.getInstance().getEnumFromString(c.getString(4)));
                eof = c.moveToNext();
                medias.add(media);
            }

            c.close();
        }
        catch (SQLiteException e) {
            Log.d("getMediaById", "error getting Medias : " + e.getMessage());
            medias = null;
        }
        db.close();
        return medias;
    }

    public ArrayList<Media> getMediaByType(String mediaType){
        ArrayList<Media> medias = new ArrayList<Media>();
        db = mDBManager.getReadableDatabase();
        Media media;
        Cursor c;
        try {
            c = db.rawQuery("SELECT * FROM "+MEDIA_TABLE+" WHERE "+MEDIA_TYPE+" = ?", new String[] {mediaType});
            boolean eof = c.moveToFirst();
            while(eof){
                media = new Media();
                media.setVersion(c.getInt(1));
                media.setName(c.getString(2));
                media.setUrl(c.getString(3));
                media.setType(Manager.getInstance().getEnumFromString(c.getString(4)));
                eof = c.moveToNext();
                medias.add(media);
            }

            c.close();
        }
        catch (SQLiteException e) {
            Log.d("getMediaById", "error getting Media : " + e.getMessage());
            medias = null;
        }
        db.close();
        return medias;
    }
}