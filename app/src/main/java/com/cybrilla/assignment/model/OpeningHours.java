package com.cybrilla.assignment.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Anirudh on 10-12-2017.
 */

public class OpeningHours {

    @SerializedName("open_now")
    private Boolean openNow;

    public Boolean getOpenNow() {
        return openNow;
    }

    public void setOpenNow(Boolean openNow) {
        this.openNow = openNow;
    }

}
