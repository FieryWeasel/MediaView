package com.mediaview.mediaview.model;

/**
 * Created by Kazuya on 14/11/2014.
 * MediaView
 */
public class Media {
    private int id;
    private int version;
    private String name;
    private String url;

    public enum EType{Image, Texte, Video, Audio}
    private EType type;
    private boolean isLocal;

    public Media() {
        this.setId(-1);
        this.setVersion(-1);
        this.setName("");
        this.setUrl("");
        this.setType(null);
        this.setLocal(false);
    }

    public Media(int version, String name, String url, EType type, boolean isLocal) {
        this.setVersion(version);
        this.setName(name);
        this.setUrl(url);
        this.setType(type);
        this.setLocal(isLocal);
    }

    public Media(int id, int version, String name, String url, EType type, boolean isLocal) {
        this.setId(id);
        this.setVersion(version);
        this.setName(name);
        this.setUrl(url);
        this.setType(type);
        this.setLocal(isLocal);
    }

    @Override
    public String toString() {
        return "Media{" +
                "id=" + id +
                ", version=" + version +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", type=" + type.toString() +
                ", isLocal=" + isLocal() +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public EType getType() {
        return type;
    }

    public void setType(EType type) {
        this.type = type;
    }

    public boolean isLocal() {
        return isLocal;
    }

    public void setLocal(boolean isLocal) {
        this.isLocal = isLocal;
    }

}
