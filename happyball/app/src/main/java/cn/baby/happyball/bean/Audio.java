package cn.baby.happyball.bean;

import java.io.Serializable;

public class Audio implements Serializable {

    private int id;
    private String title;
    private String audiofile;
    private String timelong;
    private String singer;
    private String album;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setAudiofile(String audiofile) {
        this.audiofile = audiofile;
    }

    public String getAudiofile() {
        return audiofile;
    }

    public void setTimelong(String timelong) {
        this.timelong = timelong;
    }

    public String getTimelong() {
        return timelong;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getSinger() {
        return singer;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getAlbum() {
        return album;
    }
}
