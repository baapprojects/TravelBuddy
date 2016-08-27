package com.hari.aund.travelbuddy.utils;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.hari.aund.travelbuddy.R;
import com.hari.aund.travelbuddy.activity.MainActivity;

/**
 * Created by Hari Nivas Kumar R P on 8/13/2016.
 */
public class Utility {
    private static final String LOG_TAG = Utility.class.getSimpleName();

    public static final int NAV_SECTION_EXPLORE_PLACES = 1;
    public static final int NAV_SECTION_SEARCH_FLIGHTS = 2;
    public static final int NAV_SECTION_FAVOURITES = 3;

    public static final int NAV_SECTION_SHARE = 4;
    public static final int NAV_SECTION_CONTACT_US = 5;

    public static final String KEY_NAVIGATION_SECTION_ID = "nav_section_id";

    public static final String KEY_PLACE_ID = "place_id";
    public static final String KEY_PLACE_NAME = "place_name";

    public static final String KEY_PLACE_LATITUDE = "latitude";
    public static final String KEY_PLACE_LONGITUDE = "longitude";

    public static final String KEY_CATEGORY_ID = "category_id";
    public static final String KEY_CATEGORY_NAME = "category_name";

    public static final String KEY_PLACE_SECTION_NAME = "places_section_name";
    public static final String KEY_PLACE_SECTION_NUMBER = "places_section_number";
    public static final String KEY_PLACE_CATEGORY_INFO = "places_category_info";

    public static final String KEY_SPINNER_ID = "spinner_id";

    public static final String KEY_FILTER_ID = "filter_id";
    public static final String KEY_FILTER_NAME = "filter_name";

    public static final int PLACES_ACTIVITY_COLUMN_COUNT_PORTRAIT = 1;
    public static final int PLACES_ACTIVITY_COLUMN_COUNT_LANDSCAPE = 1;

    public static final String KEY_CITY_SOURCE = "from_source_city";
    public static final String KEY_CITY_DESTINATION = "to_destination_city";

    public static final String ACTION_DATA_UPDATE = "com.hari.aund.travelbuddy.app.ACTION_DATA_UPDATED";

    /**
     * Hides the soft keyboard
     */
    public static void hideSoftKeyboard(Fragment fragment) {
        hideSoftKeyboard((MainActivity) fragment.getActivity());
    }

    public static void hideSoftKeyboard(Activity mainActivity) {
        View view = mainActivity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager =
                    (InputMethodManager) mainActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static boolean isPlacePickerSettingEnabled() {
        return true;
    }

    public static String getNavSectionName(Context context, int navSectionId) {
        switch (navSectionId) {
            case Utility.NAV_SECTION_EXPLORE_PLACES:
                //return "Explore Places";
                return context.getResources().getString(R.string.explore_places);
            case Utility.NAV_SECTION_SEARCH_FLIGHTS:
                //return "Search Flights";
                return context.getResources().getString(R.string.search_flights);
            case Utility.NAV_SECTION_FAVOURITES:
                //return "Favourites";
                return context.getResources().getString(R.string.favourites);
            default:
                Log.e(LOG_TAG, "Unknown Navigation Section id");
                return context.getResources().getString(R.string.unknown);
        }
    }

}
