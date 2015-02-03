package com.mediaview.mediaview.tools;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.mediaview.mediaview.model.Media;

import java.util.ArrayList;

/**
 * Created by Kazuya on 14/11/2014.
 * MediaView
 */
public class DBManager extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MV_DB";
    private static final String FILE_CHARSET = "UTF-8";
    private Context context;

    public DBManager(Context context) {
        super(context, DATABASE_NAME, null, Constants.DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        if (db == null) {
            db = this.getWritableDatabase();
        }
        if (db == null) {
            Log.e(Constants.CREATION_TAG,"CAN'T OPEN OR CREATE DATA BASE");
        }

        String statement = "CREATE TABLE IF NOT EXISTS medias (" +
                "mediaId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "mediaVersion INTEGER, " +
                "mediaName VARCHAR(32), " +
                "mediaURL VARCHAR(32)," +
                "mediaType VARCHAR(32))";

        // Creation tables
        try {
            db.execSQL(statement);
        }catch  (Exception e){
            Log.d(Constants.CREATION_TAG, "Erreur Sql : " + e.getMessage());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

        String statement = "DROP TABLE IF EXISTS medias";

        // Creation tables
        try {
            sqLiteDatabase.execSQL(statement);
        }catch  (Exception e){
            Log.d(Constants.CREATION_TAG, "Erreur Sql : " + e.getMessage());
        }

        statement = "CREATE TABLE IF NOT EXISTS medias (" +
                "mediaId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "mediaVersion INTEGER, " +
                "mediaName VARCHAR(32), " +
                "mediaURL VARCHAR(32)," +
                "mediaType VARCHAR(32))";

        // Creation tables
        try {
            sqLiteDatabase.execSQL(statement);
        }catch  (Exception e){
            Log.d(Constants.CREATION_TAG, "Erreur Sql : " + e.getMessage());
        }
    }
}
