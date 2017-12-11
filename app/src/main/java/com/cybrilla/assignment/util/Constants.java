package com.cybrilla.assignment.util;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Anirudh on 10-12-2017.
 */

public class Constants {

    public final static LatLng latLng = new LatLng(12.871321, 74.842577);
    public static final String RESTAURANT = "restaurant";

    public class MapSettings {
        public final static int UPDATE_INTERVAL_IN_MILLISECONDS = 5000;
        public final static int FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 2000;
        public static final float ANCHOR_VALUE = 0.5f;
        public static final float INITIAL_VALUE = 0f;
        public static final float POLYLINE_WIDTH = 5;
        public static final float SMALLEST_DISPLACEMENT = 10F;
        public static final int PROXIMITY_RADIUS = 5000;
    }
}
