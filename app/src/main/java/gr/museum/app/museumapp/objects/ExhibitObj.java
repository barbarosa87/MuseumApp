package gr.museum.app.museumapp.objects;

import java.io.Serializable;
import java.util.ArrayList;

import gr.museum.app.museumapp.enumerators.ExhibitContentTypesEnum;

/**
 * Created by barbarosa on 26/4/2016.
 */
public class ExhibitObj implements Serializable {
    private int id;
    private String name = "";
    private String information = "";
    private String beacon_id = "";
    private String beacon_minor = "";
    private String beacon_major = "";

    ArrayList<ExchibitContentObj> exhibitContent = new ArrayList<ExchibitContentObj>();

    public ArrayList<ExchibitContentObj> getExhibitContent() {
        return exhibitContent;
    }

    public void setExhibitContent(ArrayList<ExchibitContentObj> exhibitContent) {
        this.exhibitContent = exhibitContent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getBeacon_id() {
        return beacon_id;
    }

    public void setBeacon_id(String beacon_id) {
        this.beacon_id = beacon_id;
    }

    public String getBeacon_minor() {
        return beacon_minor;
    }

    public void setBeacon_minor(String beacon_minor) {
        this.beacon_minor = beacon_minor;
    }

    public String getBeacon_major() {
        return beacon_major;
    }

    public void setBeacon_major(String beacon_major) {
        this.beacon_major = beacon_major;
    }


    public ArrayList<ExchibitContentObj> getImageExchibitContents() {
        ArrayList<ExchibitContentObj> exchibitContentObjArrayList = new ArrayList<>();
        for (ExchibitContentObj exchibitContentObj : getExhibitContent()) {
            if (exchibitContentObj.getType().equalsIgnoreCase(ExhibitContentTypesEnum.IMAGE.toString())) {
                exchibitContentObjArrayList.add(exchibitContentObj);
            }
        }
        return exchibitContentObjArrayList;
    }

    public ArrayList<ExchibitContentObj> getVideoExhibitContentObj() {
        ArrayList<ExchibitContentObj> exchibitContentObjArrayList = new ArrayList<>();
        for (ExchibitContentObj exchibitContentObj : getExhibitContent()) {
            if (exchibitContentObj.getType().equalsIgnoreCase(ExhibitContentTypesEnum.VIDEO.toString())) {
                exchibitContentObjArrayList.add(exchibitContentObj);
            }
        }
        return exchibitContentObjArrayList;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExhibitObj that = (ExhibitObj) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return id;
    }
}