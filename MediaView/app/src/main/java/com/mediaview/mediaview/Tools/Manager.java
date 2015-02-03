package com.mediaview.mediaview.tools;

import com.mediaview.mediaview.model.Media;

import java.util.List;

/**
 * Created by Kazuya on 14/11/2014.
 * MediaView
 */
public class Manager {

    private static Manager instance = null;
    private DBManager dbManager;
    private List<Media> allMedias;

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

    public List<Media> getAllMedias() {
        return allMedias;
    }

    public void setAllMedias(List<Media> allMedias) {
        this.allMedias = allMedias;
    }
}
