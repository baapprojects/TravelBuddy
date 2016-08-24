package com.hari.aund.travelbuddy.data;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Hari Nivas Kumar R P on 8/24/2016.
 */
public class FlightValues {

    public static final String flightPlaces[] = {
            "Bangkok", "Bangalore", "Chennai",
            "Dubai", "Delhi", "Goa",
            "Hyderabad", "Kathmandu", "Kolkata",
            "Mumbai", "Pune", "Singapore",
    };

    public static ArrayList<String> getFlightsAsArrayList() {
        ArrayList<String> arrayList = new ArrayList<>();
        Collections.addAll(arrayList, flightPlaces);
        return arrayList;
    }
}
