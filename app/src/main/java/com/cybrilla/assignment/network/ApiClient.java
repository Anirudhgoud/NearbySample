package com.cybrilla.assignment.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Anirudh on 11/29/2016.
 */

public class ApiClient {
    public static final String GOOGLE_API_BASE = "https://maps.googleapis.com/maps/api/";
    public static final String DEV_SERVER_BASE_URL = "http://dev";

    public static final String BASE_URL = GOOGLE_API_BASE;
    private static Retrofit googleRetrofit = null;


    public static Retrofit getGoogleClient() {
        if (googleRetrofit == null) {
            googleRetrofit = new Retrofit.Builder()
                    .baseUrl(GOOGLE_API_BASE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return googleRetrofit;
    }
}
