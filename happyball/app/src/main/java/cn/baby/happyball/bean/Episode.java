package cn.baby.happyball.bean;

import java.io.Serializable;

/**
 * @author DRH
 */
public class Episode implements Serializable {

    private String episode;
    private String title;
    private String videofile;
    private String guide_melody_file;
    private String accompaniment_file;
    private String lyric_file;

    public void setEpisode(String episode) {
        this.episode = episode;
    }

    public String getEpisode() {
        return episode;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setVideofile(String videofile) {
        this.videofile = videofile;
    }

    public String getVideofile() {
        return videofile;
    }

    public void setGuide_melody_file(String guide_melody_file) {
        this.guide_melody_file = guide_melody_file;
    }

    public String getGuide_melody_file() {
        return guide_melody_file;
    }

    public void setAccompaniment_file(String accompaniment_file) {
        this.accompaniment_file = accompaniment_file;
    }

    public String getAccompaniment_file() {
        return accompaniment_file;
    }

    public void setLyric_file(String lyric_file) {
        this.lyric_file = lyric_file;
    }

    public String getLyric_file() {
        return lyric_file;
    }
}
