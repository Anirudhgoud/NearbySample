package com.cybrilla.assignment.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import com.cybrilla.assignment.R;
import com.cybrilla.assignment.managers.LocationManager;
import com.cybrilla.assignment.model.NearbyApiResponse;
import com.cybrilla.assignment.model.Result;
import com.cybrilla.assignment.util.Constants;
import com.cybrilla.assignment.util.GeneralUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import static com.cybrilla.assignment.util.GeneralUtil.REQUEST_PERMISSIONS_REQUEST_CODE;

public class HomeScreenActivity extends BaseActivity implements OnMapReadyCallback, LocationManager.LocationUpdateListener {

    private GoogleMap mMap = null;
    private Marker mSourceMarker = null;
    private Location mCurrentLocation = null;

    private boolean isLocationFetched;

    public HomeScreenActivity() {
        super(R.layout.activity_home, R.string.nearby_places);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUi();
        LocationManager.getInstance(this).setLocationListener(this);
    }

    private void initUi() {
        if (!GeneralUtil.checkPermissions(this)) {
            startLocationPermissionRequest();
        }
        GeneralUtil.showGMSCheckDialog(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        LocationManager.getInstance(this).startLocationUpdates(HomeScreenActivity.this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }


    @Override
    public void onLocationChanged(Location location) {
        if (mMap != null && location != null) {
            mCurrentLocation = location;
        }
        if (location != null && !isLocationFetched) {
            LocationManager.getInstance(this).fetchNearbyPlaces(Constants.RESTAURANT, location);
            isLocationFetched = true;
        }
    }

    /**
     * plot all the nearby place markers
     *
     * @param nearbyApiResponse
     */
    @Override
    public void plotMarkers(NearbyApiResponse nearbyApiResponse) {
        if (nearbyApiResponse != null) {
            Double lat = null;
            Double lng = null;
            if (mMap != null) {
                mMap.clear();
            }
            List<Result> nearbyPlaces = nearbyApiResponse.getResults();
            for (int i = 0; i < nearbyPlaces.size(); i++) {
                if (nearbyPlaces.get(i).getGeometry() != null &&
                        nearbyPlaces.get(i).getGeometry().getLocation() != null) {
                    lat = nearbyPlaces.get(i).getGeometry().getLocation().getLat();
                    lng = nearbyPlaces.get(i).getGeometry().getLocation().getLng();
                }
                String placeName = nearbyPlaces.get(i).getName();
                addSourceMarker(GeneralUtil.getLocation(lat, lng), placeName);
            }
        }
    }

    /**
     * Add source marker based on the current location
     *
     * @param mCurrentLocation
     */
    private void addSourceMarker(Location mCurrentLocation, String placeName) {
        if (mCurrentLocation != null) {
            mSourceMarker = mMap.addMarker(new MarkerOptions()
                    .position(GeneralUtil.getLatLng(mCurrentLocation.getLatitude(),
                            mCurrentLocation.getLongitude())).title(placeName));
            mSourceMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.location_map_pin));
            mSourceMarker.setAnchor(Constants.MapSettings.ANCHOR_VALUE,
                    Constants.MapSettings.ANCHOR_VALUE);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mSourceMarker.getPosition(), 14));
        }
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSIONS_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    LocationManager.getInstance(this).startLocationUpdates(HomeScreenActivity.this);
                } else {
                    //Nothing to do
                }
                return;
            }
        }
    }

    /**
     * Request for location permissions
     */
    public void startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                REQUEST_PERMISSIONS_REQUEST_CODE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isLocationFetched = false;
//        LocationManager.getInstance(this).startLocationUpdates(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocationManager.getInstance(this).stopLocationUpdates();
    }
}
