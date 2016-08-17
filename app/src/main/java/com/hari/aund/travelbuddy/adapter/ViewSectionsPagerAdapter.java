package com.hari.aund.travelbuddy.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.hari.aund.travelbuddy.activity.PlacesSubTypeActivity;
import com.hari.aund.travelbuddy.fragment.PlacesSubTypeFragment;

/**
 * Created by Hari Nivas Kumar R P on 8/16/2016.
 */
public class ViewSectionsPagerAdapter extends FragmentPagerAdapter {

    private final String LOG_TAG = ViewSectionsPagerAdapter.class.getSimpleName();

    private PlacesSubTypeActivity mPlacesSubTypeActivity;

    public ViewSectionsPagerAdapter(PlacesSubTypeActivity placesSubTypeActivity,
                                    FragmentManager fragmentManager) {
        super(fragmentManager);
        mPlacesSubTypeActivity = placesSubTypeActivity;
    }

    @Override
    public Fragment getItem(int position) {
        return PlacesSubTypeFragment.newInstance(
                position + 1,//sectionNumber not categoryId
                mPlacesSubTypeActivity.getPlacesCategory()
        );
    }

    @Override
    public int getCount() {
        return mPlacesSubTypeActivity
                .getPlacesCategory()
                .getSubTypeListSize();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position <= getCount()) {
            return mPlacesSubTypeActivity
                    .getPlacesCategory()
                    .getSubTypeList()
                    .get(position);
        }
        return "SECTION Unknown";
    }
}
