package gr.museum.app.museumapp.objects;

import java.io.Serializable;

/**
 * Created by barbarosa on 26/4/2016.
 */
public class ExchibitContentObj implements Serializable {
    private int id;
    private String type;
    private String path;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}