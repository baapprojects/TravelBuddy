package com.hari.aund.travelbuddy.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
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
import com.hari.aund.travelbuddy.data.provider.PlaceColumns;
import com.hari.aund.travelbuddy.data.provider.Places;
import com.hari.aund.travelbuddy.parser.PlacesApiParser;
import com.hari.aund.travelbuddy.utils.DefaultValues;
import com.hari.aund.travelbuddy.utils.Utility;

public class PlacesCategoryActivity extends AppCompatActivity
        implements View.OnClickListener, DefaultValues {

    private static final String LOG_TAG = PlacesCategoryActivity.class.getSimpleName();

    private static final int PREFERENCE_MODE_PRIVATE = 0;

    private boolean mActivityTriggerFromMainActivity = false;
    private boolean mSaveToPreference = true;
    private boolean mFetchingLatLngInProgress = false;
    private boolean mMarkAsFavourite = false;
    private String mPlaceId;
    private String mPlaceName;
    private String mLatitude;
    private String mLongitude;
    private PlacesCategoryAdapter mPlacesCategoryAdapter = null;
    private SharedPreferences mSharedPreferences;
    private RecyclerView mRecyclerView;
    private ActionBar mActionBar;
    private FloatingActionButton mFavouriteFab;

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
                finish();
            }
        });

        mSharedPreferences = getPreferences(PREFERENCE_MODE_PRIVATE);
        readValues(getIntent());

        initViewObjects();
        if (mActivityTriggerFromMainActivity)
            createAndAddAdapterToView();
    }

    @Override
    protected void onStart() {
        super.onStart();
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

        if (mMarkAsFavourite)
            addPlaceToFavourites();
        else
            removePlaceFromFavourites();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if ((getLatitude() == null ||
                getLongitude() == null || getPlaceId() == null) &&
                !mActivityTriggerFromMainActivity) {
            setPlaceId(mSharedPreferences.getString(
                    Utility.KEY_PLACE_ID, DEFAULT_PLACE_ID));
            setPlaceName(mSharedPreferences.getString(
                    Utility.KEY_PLACE_NAME, DEFAULT_PLACE_NAME));
            setLatitude(mSharedPreferences.getString(
                    Utility.KEY_PLACE_LATITUDE, DEFAULT_LATITUDE.toString()));
            setLongitude(mSharedPreferences.getString(
                    Utility.KEY_PLACE_LONGITUDE, DEFAULT_LONGITUDE.toString()));
        }

        if (!mFetchingLatLngInProgress)
            createAndAddAdapterToView();
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
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fab_places_favourite) {
            String favSnackBarMessage;
            if (!mMarkAsFavourite) {
                mFavouriteFab.setImageResource(R.drawable.ic_favorite_black_24dp);
                favSnackBarMessage = mPlaceName + " added to Favourites!";
            } else {
                mFavouriteFab.setImageResource(R.drawable.ic_favorite_white_24dp);
                favSnackBarMessage = mPlaceName + " removed from Favourites!";
            }
            mMarkAsFavourite = !mMarkAsFavourite;
            Snackbar.make(view, favSnackBarMessage, Snackbar.LENGTH_LONG)
                    .show();
        }
    }

    private void readValues(Intent intent) {
        //Set Default Values if extras/bundle is null; Def Place Alappuzha
        if (intent.getExtras() != null) {
            setPlaceId(getIntent());
            setPlaceName(getIntent());
            if (getPlaceId().equals(mSharedPreferences.getString(
                    Utility.KEY_PLACE_ID, DEFAULT_PLACE_ID))){
                setLatitude(mSharedPreferences.getString(
                        Utility.KEY_PLACE_LATITUDE,
                        DEFAULT_LATITUDE.toString()));
                setLongitude(mSharedPreferences.getString(
                        Utility.KEY_PLACE_LONGITUDE,
                        DEFAULT_LONGITUDE.toString()));
                mActivityTriggerFromMainActivity = false;
            } else {
                mActivityTriggerFromMainActivity = true;
            }
        }
    }

    private void initViewObjects(){
        mActionBar = getSupportActionBar();
        if (mActionBar != null)
            mActionBar.setDisplayHomeAsUpEnabled(true);

        /* TODO: Consider Using it for Tablets
        StaggeredGridLayoutManager sGridLayoutManager =
                new StaggeredGridLayoutManager(
                        Utility.PLACES_ACTIVITY_COLUMN_COUNT_PORTRAIT,
                        StaggeredGridLayoutManager.VERTICAL
                );
        */
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,
                DEFAULT_COLUMN_COUNT_3, LinearLayoutManager.VERTICAL, false);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        mFavouriteFab = (FloatingActionButton) findViewById(R.id.fab_places_favourite);
        mFavouriteFab.setOnClickListener(this);
    }

    private void createAndAddAdapterToView(){
        if (mActionBar != null)
            mActionBar.setTitle(getPlaceName());

        if ((getLatitude() == null || getLatitude().isEmpty()) ||
                (getLongitude() == null || getLongitude().isEmpty())) {
            new PlacesApiParser(this).getExplorePlaceDetails();
            mFetchingLatLngInProgress = true;

            mPlacesCategoryAdapter = new PlacesCategoryAdapter(this);
        } else {
            mPlacesCategoryAdapter = new PlacesCategoryAdapter(this,
                    Double.parseDouble(getLatitude()),
                    Double.parseDouble(getLongitude())
            );
        }
        mRecyclerView.setAdapter(mPlacesCategoryAdapter);
    }

    private void addPlaceToFavourites(){
        Cursor placeCursor = null;
        Log.d(LOG_TAG, getPlaceId() + "" + getPlaceName());
        try {
            placeCursor = getContentResolver().query(
                    Places.CONTENT_URI_PLACES,
                    null,
                    PlaceColumns.PLACE_ID + " = ?",
                    new String[]{getPlaceId()},
                    null);
            if (placeCursor != null && placeCursor.moveToFirst()) {
                Log.d(LOG_TAG, "addToFavourites : Entry[" + getPlaceName() + "] already present in DB!");
            } else {
                ContentValues placeContentValues = new ContentValues();

                placeContentValues.put(PlaceColumns.PLACE_ID, getPlaceId());
                placeContentValues.put(PlaceColumns.NAME, getPlaceName());
                placeContentValues.put(PlaceColumns.LATITUDE, getLatitude());
                placeContentValues.put(PlaceColumns.LONGITUDE, getLongitude());

                getContentResolver()
                        .insert(Places.CONTENT_URI_PLACES, placeContentValues);
                Log.d(LOG_TAG, "addToFavourites : New Entry[" + getPlaceName() + "] added to DB!");
            }
        }catch (NullPointerException e){
            Log.e(LOG_TAG, "addToFavourites : NullPointerException@try for Cursor!");
            e.printStackTrace();
        }finally {
            try {
                if (placeCursor != null)
                    placeCursor.close();
            }catch (NullPointerException e){
                Log.e(LOG_TAG, "addToFavourites : NullPointerException@finally for Cursor!");
                e.printStackTrace();
            }
        }
    }

    private void removePlaceFromFavourites(){
        Cursor placeCursor  = null;
        Log.d(LOG_TAG, getPlaceId() + "" + getPlaceName());
        try {
            placeCursor = getContentResolver().query(
                    Places.CONTENT_URI_PLACES,
                    null,
                    PlaceColumns.PLACE_ID + " = ?",
                    new String[]{getPlaceId()},
                    null);
            if (placeCursor != null && placeCursor.moveToFirst()) {
                getContentResolver()
                        .delete(Places.withPlaceId(getPlaceId()), null, null);
                Log.d(LOG_TAG, "removeFromFavourites : Entry[" + getPlaceId() + "] removed from DB!");
            } else {
                Log.d(LOG_TAG, "removeFromFavourites : No Entry[" + getPlaceId() + "] present in DB!");
            }
        }catch (NullPointerException e){
            Log.e(LOG_TAG, "removeFromFavourites : NullPointerException@try for Cursor!");
            e.printStackTrace();
        }finally {
            try {
                if (placeCursor != null)
                    placeCursor.close();
            }catch (NullPointerException e){
                Log.e(LOG_TAG, "removeFromFavourites : NullPointerException@finally for Cursor!");
                e.printStackTrace();
            }
        }
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
