package com.cybrilla.assignment.network;

import com.cybrilla.assignment.model.NearbyApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Anirudh on 11/29/2016.
 */

public interface ApiInterface {

    @GET("place/nearbysearch/json?sensor=true&key= AIzaSyB9SnxcVlBcyDyE0xGIj9Pg1YaLEK7q3qI")
    Call<NearbyApiResponse> getNearbyPlaces(@Query("type") String type, @Query("location") String location, @Query("radius") int radius);

}