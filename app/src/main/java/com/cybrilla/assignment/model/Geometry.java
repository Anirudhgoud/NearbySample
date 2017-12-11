package com.cybrilla.assignment.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Anirudh on 10-12-2017.
 */

public class Geometry {

    @SerializedName("location")
    private Location location;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

}
