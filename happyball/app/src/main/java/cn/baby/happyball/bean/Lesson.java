package cn.baby.happyball.bean;

import java.io.Serializable;

/**
 * @author DRH
 */
public class Lesson implements Serializable {
    private int id;
    private String name;
    private String image;
    private String description;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }
}
