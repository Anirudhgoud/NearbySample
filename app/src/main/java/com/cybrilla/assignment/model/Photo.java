package com.cybrilla.assignment.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Anirudh on 10-12-2017.
 */

public class Photo {

    @SerializedName("height")
    private Integer height;

    @SerializedName("html_attributions")
    private List<Object> htmlAttributions = null;

    @SerializedName("photo_reference")
    private String photoReference;

    @SerializedName("width")
    private Integer width;


    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public List<Object> getHtmlAttributions() {
        return htmlAttributions;
    }

    public void setHtmlAttributions(List<Object> htmlAttributions) {
        this.htmlAttributions = htmlAttributions;
    }

    public String getPhotoReference() {
        return photoReference;
    }

    public void setPhotoReference(String photoReference) {
        this.photoReference = photoReference;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }
}
