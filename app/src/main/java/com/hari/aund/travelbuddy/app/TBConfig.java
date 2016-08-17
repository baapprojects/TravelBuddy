package com.hari.aund.travelbuddy.app;

/**
 * Created by Hari Nivas Kumar R P on 8/15/2016.
 */
public class TBConfig {
    private static final String placesApiKey = "GOOGLE_PLACES-API-KEY";

    public static String getPlacesApiKey(){
        return placesApiKey;
    }

    private static final String rome2rioApiKey = "ROME2RIO_FLIGHT-API_KEY";

    public static String getRome2RioApiKey(){
        return rome2rioApiKey;
    }
}