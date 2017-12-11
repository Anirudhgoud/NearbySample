package com.cybrilla.assignment.util;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;

import com.cybrilla.assignment.R;
import com.cybrilla.assignment.activities.HomeScreenActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.LatLng;


/**
 * Created by Anirudh on 10-12-2017.
 */

public class GeneralUtil {

    public static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    public static LatLng getLatLng(double latitiude, double longitude) {
        return new LatLng(latitiude, longitude);
    }

    public static Location getLocation(Double latitiude, Double longitude) {
        Location location = new Location("");
        if (latitiude != null && longitude != null) {
            location.setLatitude(latitiude);
            location.setLongitude(longitude);
        }
        return location;
    }

    public static void showGMSCheckDialog(final Context context) {
        final HomeScreenActivity activity = (HomeScreenActivity) context;

        if (GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context) != ConnectionResult.SUCCESS) {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setMessage(activity.getString(R.string.app_name) + activity.getString(R.string.won_t_run_unless_you_update_google_play_services));
            builder.setCancelable(false);
            builder.setPositiveButton(R.string.update, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(activity.getString(R.string.market_uri)
                                + GoogleApiAvailability.GOOGLE_PLAY_SERVICES_PACKAGE)));
                    } catch (android.content.ActivityNotFoundException anfe) {
                        activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(activity.getString(R.string.google_play_url)
                                + GoogleApiAvailability.GOOGLE_PLAY_SERVICES_PACKAGE)));
                    }
                    activity.finish();
                }
            });
            builder.setNegativeButton(R.string.exit, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    activity.finish();
                }
            });
            builder.create().show();
        }
    }

    public static boolean checkPermissions(Context context) {
        int permissionState = ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_COARSE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }


    public static void startLocationPermissionRequest(HomeScreenActivity homeScreenActivity) {
        ActivityCompat.requestPermissions(homeScreenActivity,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                REQUEST_PERMISSIONS_REQUEST_CODE);
    }

    public static int getZoomLevel(Circle circle) {
        int zoomLevel = 0;
        if (circle != null) {
            double radius = circle.getRadius();
            double scale = radius / 500;
            zoomLevel = (int) (16 - Math.log(scale) / Math.log(2));
        }
        return zoomLevel;
    }
}
