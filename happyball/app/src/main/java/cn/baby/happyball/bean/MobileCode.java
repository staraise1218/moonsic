package cn.baby.happyball.bean;

import java.io.Serializable;

/**
 * @author DRH
 */
public class MobileCode implements Serializable {

    private String episode;
    private String title;
    private String videofile;

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
}
