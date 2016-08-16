package com.hari.aund.travelbuddy.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.hari.aund.travelbuddy.activity.PlacesCategoryActivity;
import com.hari.aund.travelbuddy.fragment.PlacesSubTypeFragment;

/**
 * Created by Hari Nivas Kumar R P on 8/16/2016.
 */
public class ViewSectionsPagerAdapter extends FragmentPagerAdapter {

    private final String LOG_TAG = ViewSectionsPagerAdapter.class.getSimpleName();

    private PlacesCategoryActivity mPlacesCategoryActivity;

    public ViewSectionsPagerAdapter(PlacesCategoryActivity placesCategoryActivity,
                                    FragmentManager fragmentManager) {
        super(fragmentManager);
        mPlacesCategoryActivity = placesCategoryActivity;
    }

    @Override
    public Fragment getItem(int position) {
        return PlacesSubTypeFragment.newInstance(
                position + 1,//sectionNumber not categoryId
                mPlacesCategoryActivity.getPlacesCategory()
        );
    }

    @Override
    public int getCount() {
        return mPlacesCategoryActivity
                .getPlacesCategory()
                .getSubTypeListSize();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position <= getCount()) {
            return mPlacesCategoryActivity
                    .getPlacesCategory()
                    .getSubTypeList()
                    .get(position);
        }
        return "SECTION Unknown";
    }
}
