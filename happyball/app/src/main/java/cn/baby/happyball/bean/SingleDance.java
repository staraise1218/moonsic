package cn.baby.happyball.bean;

import java.io.Serializable;

/**
 * @author DRH
 * @data 2018/10/23
 */
public class SingleDance implements Serializable {

    private String title;
    private String video;
    private String image;

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

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }
}
