package cn.baby.happyball.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author DRH
 * @data 2018/11/12
 */
public class Knowledge implements Serializable {

    private String title;
    private List<String> images = new ArrayList<>(4);
    private List<String> answer = new ArrayList<>(4);

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<String> getImages() {
        return images;
    }

    public void setAnswer(List<String> answer) {
        this.answer = answer;
    }

    public List<String> getAnswer() {
        return answer;
    }
}
