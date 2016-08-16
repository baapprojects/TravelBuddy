package com.hari.aund.travelbuddy.utils;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.hari.aund.travelbuddy.activity.MainActivity;
import com.hari.aund.travelbuddy.app.TBConfig;

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

    public static final String KEY_PLACE_TYPE_NAME = "place_type_name";
    public static final String KEY_PLACE_CATEGORY_INFO = "places_category_info";

    public static final int PLACES_ACTIVITY_COLUMN_COUNT_PORTRAIT = 1;
    public static final int PLACES_ACTIVITY_COLUMN_COUNT_LANDSCAPE = 1;

    private static final String PLACES_URL_BASE =
            "https://maps.googleapis.com/maps/api/place/details/json?placeid=";
    private static final String PLACES_URL_KEY_PREFIX = "&key=";

    public static String getPlacesUrl(String placeId) {
        return PLACES_URL_BASE + placeId +
                PLACES_URL_KEY_PREFIX + TBConfig.getPlacesApiKey();
    }

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
}
