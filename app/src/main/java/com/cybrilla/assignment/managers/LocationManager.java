package com.cybrilla.assignment.managers;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;

import com.cybrilla.assignment.activities.HomeScreenActivity;
import com.cybrilla.assignment.model.NearbyApiResponse;
import com.cybrilla.assignment.network.ApiClient;
import com.cybrilla.assignment.network.ApiInterface;
import com.cybrilla.assignment.util.Constants;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cybrilla.assignment.util.GeneralUtil.REQUEST_PERMISSIONS_REQUEST_CODE;

/**
 * Created by Anirudh on 10-12-2017.
 */

public class LocationManager {

    private static LocationManager mLocationInstance;
    private Context mContext;
    private Location mLocation = null;
    private LocationRequest locationRequest;
    private LocationCallback mLocationCallback;
    private LocationUpdateListener locationListener = null;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationSettingsRequest mLocationSettingsRequest;

    private LocationManager(Context context) {
        mContext = context.getApplicationContext();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(mContext);
        initLocationRequest();
    }

    public static LocationManager getInstance(Context context) {
        if (mLocationInstance == null) {
            mLocationInstance = new LocationManager(context);
        }
        return mLocationInstance;
    }

    private void initLocationRequest() {
        if (locationRequest == null) {
            locationRequest = LocationRequest.create();
            locationRequest.setInterval(Constants.MapSettings.UPDATE_INTERVAL_IN_MILLISECONDS);
            locationRequest.setFastestInterval(Constants.MapSettings.
                    FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        }
        createLocationCallback();
        buildLocationSettingsRequest();
    }

    private void buildLocationSettingsRequest() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);
        mLocationSettingsRequest = builder.build();
    }


    public void startLocationUpdates(HomeScreenActivity homeScreenActivity) {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            startLocationPermissionRequest(homeScreenActivity);
            return;
        }
        mFusedLocationClient.requestLocationUpdates(locationRequest,
                mLocationCallback, Looper.myLooper());
    }

    public void setLocationListener(LocationUpdateListener locationListener) {
        this.locationListener = locationListener;
    }

    public void fetchNearbyPlaces(String placeType, Location location) {
        if (location != null) {
            Call<NearbyApiResponse> nearbyApiResponseCall = ApiClient.getGoogleClient().create(ApiInterface.class)
                    .getNearbyPlaces(placeType, location.getLatitude() + "," + location.getLongitude(),
                            Constants.MapSettings.PROXIMITY_RADIUS);
            nearbyApiResponseCall.enqueue(new Callback<NearbyApiResponse>() {
                @Override
                public void onResponse(Call<NearbyApiResponse> call, Response<NearbyApiResponse> response) {
                    if (locationListener != null) {
                        locationListener.plotMarkers(response.body());
                    }
                }

                @Override
                public void onFailure(Call<NearbyApiResponse> call, Throwable t) {

                }
            });
        }
    }

    private void createLocationCallback() {
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                mLocation = locationResult.getLastLocation();
                if (locationListener != null) {
                    locationListener.onLocationChanged(mLocation);
                }
            }
        };
    }

    public void startLocationPermissionRequest(HomeScreenActivity homeScreenActivity) {
        ActivityCompat.requestPermissions(homeScreenActivity,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                REQUEST_PERMISSIONS_REQUEST_CODE);
    }

    public void stopLocationUpdates() {
        if (mFusedLocationClient != null) {
            try {
                mFusedLocationClient.removeLocationUpdates(mLocationCallback);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public interface LocationUpdateListener {
        void onLocationChanged(Location location);

        void plotMarkers(NearbyApiResponse nearbyApiResponse);
    }
}
