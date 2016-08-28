package com.hari.aund.travelbuddy.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.hari.aund.travelbuddy.R;
import com.hari.aund.travelbuddy.adapter.ViewSectionsPagerAdapter;
import com.hari.aund.travelbuddy.adapter.ViewSpinnerAdapter;
import com.hari.aund.travelbuddy.data.PlacesCategory;
import com.hari.aund.travelbuddy.data.PlacesCategoryValues;
import com.hari.aund.travelbuddy.fragment.PlacesSubTypeFragment;
import com.hari.aund.travelbuddy.utils.DefaultValues;
import com.hari.aund.travelbuddy.utils.Utility;

public class PlacesSubTypeActivity extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener, DefaultValues {

    private static final String LOG_TAG = PlacesSubTypeActivity.class.getSimpleName();

    private static final int PREFERENCE_MODE_PRIVATE = 0;

    private boolean mSaveToPreference = true;
    private PlacesCategory mPlacesCategory = null;
    private SharedPreferences mSharedPreferences;
    private Toolbar mToolbar;
    private Spinner mSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places_sub_type);

        mToolbar = (Toolbar) findViewById(R.id.toolbar_places_sub_type);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSaveToPreference = false;
                finish();
                overridePendingTransition(
                        R.animator.activity_open_translate, R.animator.activity_close_translate);
            }
        });

        mSharedPreferences = getPreferences(PREFERENCE_MODE_PRIVATE);

        readValues(getIntent(), savedInstanceState);

        if (mPlacesCategory != null)
            initViewObjects();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "inside onStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor mPreferenceEditor = mSharedPreferences.edit();
        if (mSaveToPreference) {
            mPreferenceEditor.putInt(Utility.KEY_CATEGORY_ID,
                    mPlacesCategory.getCategoryId());
            if (mPlacesCategory.getCategoryActivityId() ==
                    PlacesCategoryValues.PLACES_CATEGORY_B_ACTIVITY) {
                mPreferenceEditor.putInt(Utility.KEY_SPINNER_ID,
                        mSpinner.getSelectedItemPosition());
            }
        } else {
            mPreferenceEditor.putInt(Utility.KEY_CATEGORY_ID, -1);
            if (mPlacesCategory.getCategoryActivityId() ==
                    PlacesCategoryValues.PLACES_CATEGORY_B_ACTIVITY) {
                mPreferenceEditor.putInt(Utility.KEY_SPINNER_ID, -1);
            }
        }
        mPreferenceEditor.apply();
        Log.d(LOG_TAG, "inside onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPlacesCategory == null) {
            Log.d(LOG_TAG, "mPlacesCategory is null");
            int categoryId = mSharedPreferences.getInt(
                    Utility.KEY_CATEGORY_ID, DEFAULT_CATEGORY_ID);
            mPlacesCategory = new PlacesCategory(categoryId);
            initViewObjects();
        }
        Log.d(LOG_TAG, "inside onResume");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mSaveToPreference = false;
        overridePendingTransition(
                R.animator.activity_open_translate, R.animator.activity_close_translate);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long longValue) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.nested_scroll_view,
                        PlacesSubTypeFragment.newInstance(
                                position + 1,//sectionNumber not categoryId
                                mPlacesCategory)
                ).commit();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void readValues(Intent intent, Bundle savedInstanceState){
        if (intent.getExtras() != null)
            mPlacesCategory = intent.getParcelableExtra(
                    Utility.KEY_PLACE_CATEGORY_INFO);
    }

    private void initViewObjects(){
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(mPlacesCategory.getCategoryName());
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        AdView adView = (AdView) findViewById(R.id.ad_places_sub_type);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        adView.loadAd(adRequest);

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.places_category_appbar);
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        mSpinner = (Spinner) findViewById(R.id.spinner);

        if ((mPlacesCategory.getCategoryActivityId() == PlacesCategoryValues.PLACES_CATEGORY_A_ACTIVITY) ||
                (mPlacesCategory.getCategoryActivityId() == PlacesCategoryValues.PLACES_CATEGORY_C_ACTIVITY)) {
            ViewSectionsPagerAdapter sectionsPagerAdapter =
                    new ViewSectionsPagerAdapter(this,
                            getSupportFragmentManager());

            viewPager.setAdapter(sectionsPagerAdapter);

            if (mPlacesCategory.getCategoryActivityId() == PlacesCategoryValues.PLACES_CATEGORY_A_ACTIVITY) {
                tabLayout.setupWithViewPager(viewPager);
            } else {
                tabLayout.setVisibility(View.INVISIBLE);
                appBarLayout.removeView(tabLayout);
            }

            mSpinner.setVisibility(View.INVISIBLE);
        } else if (mPlacesCategory.getCategoryActivityId() == PlacesCategoryValues.PLACES_CATEGORY_B_ACTIVITY) {
            viewPager.setVisibility(View.INVISIBLE);
            tabLayout.setVisibility(View.INVISIBLE);
            appBarLayout.removeView(tabLayout);

            ViewSpinnerAdapter viewSpinnerAdapter = new ViewSpinnerAdapter(this,
                    mToolbar.getContext(),
                    PlacesCategoryValues.displayNameSubTypes[mPlacesCategory.getCategoryId()]
            );

            mSpinner.setAdapter(viewSpinnerAdapter);
            mSpinner.setOnItemSelectedListener(this);

            int spinnerSelection = mSharedPreferences.getInt(
                    Utility.KEY_SPINNER_ID, DEFAULT_INVALID_SPINNER_ID);
            if (spinnerSelection != DEFAULT_INVALID_SPINNER_ID)
                mSpinner.setSelection(spinnerSelection);
        }
    }

    public PlacesCategory getPlacesCategory() {
        return mPlacesCategory;
    }
}
