package com.hari.aund.travelbuddy.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hari.aund.travelbuddy.R;
import com.hari.aund.travelbuddy.activity.MainActivity;
import com.hari.aund.travelbuddy.utils.DefaultValues;
import com.hari.aund.travelbuddy.utils.Utility;

/**
 * A placeholder fragment containing a simple view.
 */
public class FavouritesFragment extends Fragment
        implements View.OnClickListener, DefaultValues {

    private static final String LOG_TAG = FavouritesFragment.class.getSimpleName();

    private static final int PREFERENCE_MODE_PRIVATE = 0;

    private int mNavSectionId;
    private String mNavSectionName;
    private ActionBar mActionBar = null;
    private SharedPreferences mSharedPreferences;

    public FavouritesFragment() {
    }

    public static FavouritesFragment getNewInstance(int navSectionId) {
        FavouritesFragment FavouritesFragment = new FavouritesFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(Utility.KEY_NAVIGATION_SECTION_ID, navSectionId);
        FavouritesFragment.setArguments(bundle);

        return FavouritesFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mActionBar = ((MainActivity) getActivity()).getSupportActionBar();

        mSharedPreferences = getActivity().getPreferences(PREFERENCE_MODE_PRIVATE);
        readValues();
        setFragmentTitle();

        View rootView = inflater.inflate(R.layout.fragment_favourites, container, false);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab_favourites);
        fab.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences.Editor mPreferenceEditor = mSharedPreferences.edit();
        mPreferenceEditor.putInt(Utility.KEY_NAVIGATION_SECTION_ID,
                getNavSectionId());
        mPreferenceEditor.apply();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getNavSectionId() == 0) {
            setNavSectionId(mSharedPreferences.getInt(
                    Utility.KEY_NAVIGATION_SECTION_ID, Utility.NAV_SECTION_EXPLORE_PLACES));
            setNavSectionName(Utility.getNavSectionName(getContext(), getNavSectionId()));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_favourites:
                Snackbar.make(view, "Use this to filter your Favourites!", Snackbar.LENGTH_LONG)
                        .show();
                break;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void readValues() {
        if (getArguments() != null) {
            setNavSectionId(getArguments().getInt(
                    Utility.KEY_NAVIGATION_SECTION_ID));
            setNavSectionName(Utility.getNavSectionName(
                    getContext(), getNavSectionId()));
        }
    }

    private void setNavSectionId(int navSectionId) {
        this.mNavSectionId = navSectionId;
    }

    private int getNavSectionId() {
        return mNavSectionId;
    }

    private void setNavSectionName(String navSectionName) {
        this.mNavSectionName = navSectionName;
    }

    private String getNavSectionName() {
        return mNavSectionName;
    }

    private void setFragmentTitle() {
        if (mActionBar != null) {
            mActionBar.setTitle(getNavSectionName());
        }
    }
}
