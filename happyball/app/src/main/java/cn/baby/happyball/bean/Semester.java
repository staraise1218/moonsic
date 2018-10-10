package cn.baby.happyball.bean;

import java.io.Serializable;

/**
 * 班级
 *
 * @author DRH
 */
public class Semester implements Serializable {
    private int id;
    private String name;
    private String image;

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
