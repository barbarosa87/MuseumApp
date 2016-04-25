package gr.museum.app.museumapp.objects;

import java.io.Serializable;

/**
 * Created by barbarosa on 26/4/2016.
 */
public class ExchibitContentObj implements Serializable {
    private int id;
    private String video;
    private String audio;
    private String picture;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

}