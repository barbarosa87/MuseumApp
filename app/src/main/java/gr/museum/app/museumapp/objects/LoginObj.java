package gr.museum.app.museumapp.objects;

import java.io.Serializable;

/**
 * Created by barbarosa on 19/4/2016.
 */
public class LoginObj implements Serializable {

    String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
