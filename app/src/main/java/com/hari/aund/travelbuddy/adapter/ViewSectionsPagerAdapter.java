package com.hari.aund.travelbuddy.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.hari.aund.travelbuddy.activity.PlacesCategoryAActivity;
import com.hari.aund.travelbuddy.activity.PlacesCategoryCActivity;
import com.hari.aund.travelbuddy.fragment.PlacesSubTypeFragment;
import com.hari.aund.travelbuddy.data.PlacesCategories;

import java.util.ArrayList;

/**
 * Created by Hari Nivas Kumar R P on 8/16/2016.
 */
public class ViewSectionsPagerAdapter extends FragmentPagerAdapter {

    private final String LOG_TAG = ViewSectionsPagerAdapter.class.getSimpleName();

    private PlacesCategoryAActivity mPlacesCategoryAActivity;
    private PlacesCategoryCActivity mPlacesCategoryCActivity;

    private int mActivityCategoryType = 0;
    private int mSectionCount = 0;
    private ArrayList<String> mSubTypeList = null;

    public ViewSectionsPagerAdapter(Activity placesCategoryActivity,
                                    FragmentManager fragmentManager,
                                    int activityCategoryType) {
        super(fragmentManager);
        if (activityCategoryType == PlacesCategories.PLACES_CATEGORY_A_ACTIVITY) {
            mPlacesCategoryAActivity = (PlacesCategoryAActivity) placesCategoryActivity;
            mActivityCategoryType = mPlacesCategoryAActivity.getPlacesCategory().getCategoryId();
            mSectionCount = mPlacesCategoryAActivity.getPlacesCategory().getSubTypeListSize();
            mSubTypeList = mPlacesCategoryAActivity.getPlacesCategory().getSubTypeList();
        } else if (activityCategoryType == PlacesCategories.PLACES_CATEGORY_C_ACTIVITY) {
            mPlacesCategoryCActivity = (PlacesCategoryCActivity) placesCategoryActivity;
            mActivityCategoryType = mPlacesCategoryCActivity.getPlacesCategory().getCategoryId();
            mSectionCount = mPlacesCategoryCActivity.getPlacesCategory().getSubTypeListSize();
            mSubTypeList = mPlacesCategoryCActivity.getPlacesCategory().getSubTypeList();
        }
        Log.d(LOG_TAG, "mActivityCategoryType - " + mActivityCategoryType);
        Log.d(LOG_TAG, "mSectionCount - " + mSectionCount);
        if (mSubTypeList != null) {
            for (int index = 0; index < mSectionCount; index++) {
                Log.d(LOG_TAG, "mSubTypeList[" + index + "] - " + mSubTypeList.get(index));
            }
        }
    }

    @Override
    public Fragment getItem(int position) {
        Log.d(LOG_TAG, "Position - " + position);
        Log.d(LOG_TAG, "Name - " + mSubTypeList.get(position));
        return PlacesSubTypeFragment.newInstance(position + 1, mSubTypeList.get(position));
    }

    @Override
    public int getCount() {
        Log.d(LOG_TAG, "getCount : Count - " + mSectionCount);
        return mSectionCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Log.d(LOG_TAG, "getPageTitle - Entered");

        if (position <= mSectionCount){
            Log.d(LOG_TAG, "getPageTitle : Title - " + mSubTypeList.get(position));
            return mSubTypeList.get(position);
        }
        Log.d(LOG_TAG, "getPageTitle - Exited - Unknown");
        return "SECTION Unknown";
    }
}
