package com.hari.aund.travelbuddy.utils;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.hari.aund.travelbuddy.activity.MainActivity;

/**
 * Created by Hari Nivas Kumar R P on 8/13/2016.
 */
public class Utility {
    public static final int NAV_SECTION_EXPLORE_PLACES = 1;
    public static final int NAV_SECTION_SEARCH_FLIGHTS = 2;
    public static final int NAV_SECTION_FAVOURITES = 3;

    public static final int NAV_SECTION_SHARE = 4;
    public static final int NAV_SECTION_CONTACT_US = 5;

    public static final String KEY_NAVIGATION_SECTION_ID = "nav_section_id";

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

    public static boolean isPlacePickerSettingEnabled(){
        return true;
    }
}
