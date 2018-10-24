package cn.baby.happyball.bean;

import java.io.Serializable;

/**
 * @author DRH
 * @data 2018/10/23
 */
public class SingleDance implements Serializable {

    private String title;
    private String video;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getVideo() {
        return video;
    }
}
