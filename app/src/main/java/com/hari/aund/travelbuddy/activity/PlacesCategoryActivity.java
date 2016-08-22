package com.hari.aund.travelbuddy.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.hari.aund.travelbuddy.R;
import com.hari.aund.travelbuddy.adapter.PlacesCategoryAdapter;
import com.hari.aund.travelbuddy.parser.PlacesApiParser;
import com.hari.aund.travelbuddy.utils.DefaultValues;
import com.hari.aund.travelbuddy.utils.Utility;

public class PlacesCategoryActivity extends AppCompatActivity
        implements View.OnClickListener, DefaultValues {

    private static final String LOG_TAG = PlacesCategoryActivity.class.getSimpleName();

    private String mPlaceId;
    private String mPlaceName;
    private String mLatitude;
    private String mLongitude;
    private PlacesCategoryAdapter mPlacesCategoryAdapter = null;

    private static final int PREFERENCE_MODE_PRIVATE = 0;
    private SharedPreferences mSharedPreferences;
    private boolean mActivityTriggerFromMainActivity = false;
    private RecyclerView mRecyclerView;
    private boolean mSaveToPreference = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places_cateogory);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_places_category);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSaveToPreference = false;
                Log.d(LOG_TAG, "just before finish()");
                finish();
            }
        });

        Log.d(LOG_TAG, "inside onCreate");

        mSharedPreferences = getPreferences(PREFERENCE_MODE_PRIVATE);

        readValues(getIntent(), savedInstanceState);

        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setTitle(getPlaceName());
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }

        /* TODO: Consider Using it for Tablets
        StaggeredGridLayoutManager sGridLayoutManager =
                new StaggeredGridLayoutManager(
                        Utility.PLACES_ACTIVITY_COLUMN_COUNT_PORTRAIT,
                        StaggeredGridLayoutManager.VERTICAL
                );
        */

        initViewObjects();
        if (mActivityTriggerFromMainActivity)
            createAndAddAdapterToView();
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
            mPreferenceEditor.putString(Utility.KEY_PLACE_ID, getPlaceId());
            mPreferenceEditor.putString(Utility.KEY_PLACE_NAME, getPlaceName());
            mPreferenceEditor.putString(Utility.KEY_PLACE_LATITUDE, getLatitude());
            mPreferenceEditor.putString(Utility.KEY_PLACE_LONGITUDE, getLongitude());
        } else {
            mPreferenceEditor.putString(Utility.KEY_PLACE_ID, REST_KEY_VALUE);
            mPreferenceEditor.putString(Utility.KEY_PLACE_NAME, REST_KEY_VALUE);
            mPreferenceEditor.putString(Utility.KEY_PLACE_LATITUDE, REST_KEY_VALUE);
            mPreferenceEditor.putString(Utility.KEY_PLACE_LONGITUDE, REST_KEY_VALUE);
        }
        mPreferenceEditor.apply();
        Log.d(LOG_TAG, "inside onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if ((getLatitude() == null || getLongitude() == null || getPlaceId() == null) &&
                (!mActivityTriggerFromMainActivity)){
            Log.d(LOG_TAG, "latitude | longitude | placeId is null");
            setPlaceId(mSharedPreferences.getString(
                    Utility.KEY_PLACE_ID, DEFAULT_PLACE_ID));
            setPlaceName(mSharedPreferences.getString(
                    Utility.KEY_PLACE_NAME, DEFAULT_PLACE_NAME));
            setLatitude(mSharedPreferences.getString(
                    Utility.KEY_PLACE_LATITUDE, DEFAULT_LATITUDE.toString()));
            setLongitude(mSharedPreferences.getString(
                    Utility.KEY_PLACE_LONGITUDE, DEFAULT_LONGITUDE.toString()));

            initViewObjects();
            createAndAddAdapterToView();
        }
        Log.d(LOG_TAG, "inside onResume");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        /*
        if (savedInstanceState != null) {
            setPlaceId(savedInstanceState.getString(Utility.KEY_PLACE_ID));
            setPlaceName(savedInstanceState.getString(Utility.KEY_PLACE_NAME));
            setLatitude(savedInstanceState.getString(Utility.KEY_PLACE_LATITUDE));
            setLongitude(savedInstanceState.getString(Utility.KEY_PLACE_LONGITUDE));
            Log.d(LOG_TAG, "onRestoreInstanceState - savedInstanceState is Restored!");
        } else {
            Log.d(LOG_TAG, "onRestoreInstanceState - savedInstanceState is Empty!");
        }
        Log.d(LOG_TAG, "inside onRestoreInstanceState");
        */
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        /*
        outState.putString(Utility.KEY_PLACE_ID, getPlaceId());
        outState.putString(Utility.KEY_PLACE_NAME, getPlaceName());
        outState.putString(Utility.KEY_PLACE_LATITUDE, getLatitude());
        outState.putString(Utility.KEY_PLACE_LONGITUDE, getLongitude());
        Log.d(LOG_TAG, "onSaveInstanceState - savedInstanceState is Filled!");
        Log.d(LOG_TAG, "inside onSaveInstanceState");
        */
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fab_places_favourite) {
            Snackbar.make(view, mPlaceName, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    private void readValues(Intent intent, Bundle savedInstanceState) {
        //Set Default Values if extras/bundle is null; Def Place Alappuzha
        /*
        if (savedInstanceState != null) {
            setPlaceId(savedInstanceState.getString(Utility.KEY_PLACE_ID));
            setPlaceName(savedInstanceState.getString(Utility.KEY_PLACE_NAME));
            setLatitude(savedInstanceState.getString(Utility.KEY_PLACE_LATITUDE));
            setLongitude(savedInstanceState.getString(Utility.KEY_PLACE_LONGITUDE));
            Log.d(LOG_TAG, "readValues - savedInstanceState is Restored!");
        } else */ if (intent.getExtras() != null) {
            setPlaceId(getIntent());
            setPlaceName(getIntent());
            if (getPlaceId().equals(mSharedPreferences.getString(
                    Utility.KEY_PLACE_ID, DEFAULT_PLACE_ID))){
                setLatitude(mSharedPreferences.getString(
                        Utility.KEY_PLACE_LATITUDE, DEFAULT_LATITUDE.toString()));
                setLongitude(mSharedPreferences.getString(
                        Utility.KEY_PLACE_LONGITUDE, DEFAULT_LONGITUDE.toString()));
                mActivityTriggerFromMainActivity = false;
            } else {
                mActivityTriggerFromMainActivity = true;
            }
            Log.d(LOG_TAG, "readValues - savedInstanceState : intent.Extras is Restored!");
            /*
        } else {
            setPlaceId(DEFAULT_PLACE_ID);
            setPlaceName(DEFAULT_PLACE_NAME);
            setLatitude(DEFAULT_LATITUDE.toString());
            setLongitude(DEFAULT_LONGITUDE.toString());
            Log.d(LOG_TAG, "readValues - savedInstanceState : Default Values is Restored!");
            */
        }
        Log.d(LOG_TAG, "inside readValues");
    }

    private void initViewObjects(){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,
                DEFAULT_COLUMN_COUNT_3, LinearLayoutManager.VERTICAL, false);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        FloatingActionButton favouriteFab =
                (FloatingActionButton) findViewById(R.id.fab_places_favourite);
        favouriteFab.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mSaveToPreference = false;
        Log.d(LOG_TAG, "inside onBackPressed");
    }

    private void createAndAddAdapterToView(){
        if ((getLatitude() == null || getLatitude().isEmpty()) ||
                (getLongitude() == null || getLongitude().isEmpty())) {
            Log.d(LOG_TAG, "Latitude || Longitude is null");
            new PlacesApiParser(this).getExplorePlaceDetails();

            mPlacesCategoryAdapter = new PlacesCategoryAdapter(this);
        } else {
            Log.d(LOG_TAG, "Latitude - " + getLatitude() + " Longitude - " + getLongitude());
            mPlacesCategoryAdapter = new PlacesCategoryAdapter(this,
                    Double.parseDouble(getLatitude()),
                    Double.parseDouble(getLongitude())
            );
        }
        mRecyclerView.setAdapter(mPlacesCategoryAdapter);
    }

    public String getPlaceId() {
        return mPlaceId;
    }

    private void setPlaceId(String mPlaceId) {
        this.mPlaceId = mPlaceId;
    }

    private void setPlaceId(Intent intent) {
        setPlaceId(intent.getStringExtra(Utility.KEY_PLACE_ID));
    }

    public String getPlaceName() {
        return mPlaceName;
    }

    private void setPlaceName(String mPlaceName) {
        this.mPlaceName = mPlaceName;
    }

    private void setPlaceName(Intent intent) {
        setPlaceName(intent.getStringExtra(Utility.KEY_PLACE_NAME));
    }

    public PlacesCategoryAdapter getPlacesCategoryAdapter() {
        return mPlacesCategoryAdapter;
    }

    private void setPlacesCategoryAdapter(PlacesCategoryAdapter mPlacesCategoryAdapter) {
        this.mPlacesCategoryAdapter = mPlacesCategoryAdapter;
    }

    public String getLongitude() {
        return mLongitude;
    }

    public void setLongitude(String longitude) {
        mLongitude = longitude;
    }

    public String getLatitude() {
        return mLatitude;
    }

    public void setLatitude(String latitude) {
        mLatitude = latitude;
    }
}
