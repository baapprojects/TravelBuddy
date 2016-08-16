package com.hari.aund.travelbuddy.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.hari.aund.travelbuddy.activity.PlacesCategoryActivity;
import com.hari.aund.travelbuddy.fragment.PlacesSubTypeFragment;

import java.util.ArrayList;

/**
 * Created by Hari Nivas Kumar R P on 8/16/2016.
 */
public class ViewSectionsPagerAdapter extends FragmentPagerAdapter {

    private final String LOG_TAG = ViewSectionsPagerAdapter.class.getSimpleName();

    private PlacesCategoryActivity mPlacesCategoryActivity;

    private int mSectionCount = 0;
    private ArrayList<String> mSubTypeList = null;

    public ViewSectionsPagerAdapter(PlacesCategoryActivity placesCategoryActivity,
                                    FragmentManager fragmentManager) {
        super(fragmentManager);
        mPlacesCategoryActivity = placesCategoryActivity;
        mSectionCount = mPlacesCategoryActivity.getPlacesCategory().getSubTypeListSize();
        mSubTypeList = mPlacesCategoryActivity.getPlacesCategory().getSubTypeList();

        Log.d(LOG_TAG, "mActivityCategoryType - " + mPlacesCategoryActivity.getPlacesCategory().getCategoryId());
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
        return PlacesSubTypeFragment.newInstance(
                mPlacesCategoryActivity.getPlacesCategory().getCategoryActivityId(),
                position + 1,
                mSubTypeList.get(position),
                mPlacesCategoryActivity.getPlaceTypeName()
        );
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
