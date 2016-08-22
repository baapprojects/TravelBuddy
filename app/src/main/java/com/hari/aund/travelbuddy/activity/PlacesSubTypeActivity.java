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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

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

    private PlacesCategory mPlacesCategory = null;

    private static final int PREFERENCE_MODE_PRIVATE = 0;
    private SharedPreferences mSharedPreferences;
    private Toolbar mToolbar;
    private Spinner mSpinner;
    private boolean mSaveToPreference = true;

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
        /*
        if (savedInstanceState != null) {
            mPlacesCategory = savedInstanceState.getParcelable(Utility.KEY_PLACE_CATEGORY_INFO);
            Log.d(LOG_TAG, "onRestoreInstanceState - savedInstanceState is Restored!");
        } else {
            Log.d(LOG_TAG, "onRestoreInstanceState - savedInstanceState is Empty!");
        }
        Log.d(LOG_TAG, "inside onRestoreInstanceState ");
        */
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        /*
        outState.putParcelable(Utility.KEY_PLACE_CATEGORY_INFO, getPlacesCategory());
        Log.d(LOG_TAG, "onSaveInstanceState - savedInstanceState is Filled!");
        Log.d(LOG_TAG, "inside onSaveInstanceState ");
        */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_places_category, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
        /*if (savedInstanceState != null) {
            mPlacesCategory = savedInstanceState.getParcelable(Utility.KEY_PLACE_CATEGORY_INFO);
            Log.d(LOG_TAG, "readValues - savedInstanceState is Restored!");
        } else */ if (intent.getExtras() != null) {
            mPlacesCategory = intent.getParcelableExtra(Utility.KEY_PLACE_CATEGORY_INFO);
            Log.d(LOG_TAG, "readValues - savedInstanceState : intent.Extras is Restored!");
            /*
        } else {
            mPlacesCategory = new PlacesCategory(DEFAULT_CATEGORY_ID);
            Log.d(LOG_TAG, "readValues - savedInstanceState : Default Values is Restored!");
            */
        }
    }

    private void initViewObjects(){
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(mPlacesCategory.getCategoryName());
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

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
                    mPlacesCategory.getSubTypeList().toArray(
                            new String[mPlacesCategory.getSubTypeListSize()]
                    )
            );

            mSpinner.setAdapter(viewSpinnerAdapter);
            mSpinner.setOnItemSelectedListener(this);

            int spinnerSelection = mSharedPreferences.getInt(
                    Utility.KEY_SPINNER_ID, DEFAULT_INVALID_SPINNER_ID);
            if (spinnerSelection != DEFAULT_INVALID_SPINNER_ID)
                mSpinner.setSelection(spinnerSelection);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mSaveToPreference = false;
    }

    public PlacesCategory getPlacesCategory() {
        return mPlacesCategory;
    }
}
