package com.mediaview.mediaview.tools;

import com.mediaview.mediaview.model.Media;

/**
 * Created by Kazuya on 14/11/2014.
 * MediaView
 */
public class Manager {

    private static Manager instance = null;
    private DBManager dbManager;

    public static Manager getInstance(){
        if(instance == null)
            instance = new Manager();
        return instance;
    }

    public String getStringFromEnum(Media.EType enumType){
        if(enumType == null)
            return "unknown";
        else
            return enumType.toString();
    }

    public Media.EType getEnumFromString(String enumType){
        if(enumType.equalsIgnoreCase("image")) {
            return Media.EType.Image;
        }else if(enumType.equalsIgnoreCase("texte")) {
            return Media.EType.Texte;
        }else if(enumType.equalsIgnoreCase("video")) {
            return Media.EType.Video;
        }else if(enumType.equalsIgnoreCase("audio")) {
            return Media.EType.Audio;
        }else {
            return null;
        }
    }

    public DBManager getDbManager() {
        return dbManager;
    }

    public void setDbManager(DBManager dbManager) {
        this.dbManager = dbManager;
    }
}
