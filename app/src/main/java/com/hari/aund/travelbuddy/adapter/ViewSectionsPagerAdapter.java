package com.hari.aund.travelbuddy.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

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
    }

    @Override
    public Fragment getItem(int position) {
        return PlacesSubTypeFragment.newInstance(
                mPlacesCategoryActivity.getPlacesCategory().getCategoryActivityId(),
                position + 1,
                mSubTypeList.get(position),
                mPlacesCategoryActivity.getPlaceTypeName()
        );
    }

    @Override
    public int getCount() {
        return mSectionCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position <= mSectionCount) {
            return mSubTypeList.get(position);
        }
        return "SECTION Unknown";
    }
}
