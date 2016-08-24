package com.hari.aund.travelbuddy.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.hari.aund.travelbuddy.R;
import com.hari.aund.travelbuddy.fragment.ExplorePlacesFragment;
import com.hari.aund.travelbuddy.fragment.FavouritesFragment;
import com.hari.aund.travelbuddy.fragment.FlightSearchFragment;
import com.hari.aund.travelbuddy.utils.Utility;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final int DEFAULT_SECTION_ID =
            Utility.NAV_SECTION_EXPLORE_PLACES;
    private static final int PREFERENCE_MODE_PRIVATE = 0;

    private int mNavSectionId = DEFAULT_SECTION_ID;
    private SharedPreferences mSharedPreferences;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_nav_bar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mSharedPreferences = getPreferences(PREFERENCE_MODE_PRIVATE);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                mDrawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close) {

            @Override
            public void onDrawerOpened(View drawerView) {
                // TODO Auto-generated method stub
                Utility.hideSoftKeyboard(MainActivity.this);
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                // TODO Auto-generated method stub
                Utility.hideSoftKeyboard(MainActivity.this);
                super.onDrawerClosed(drawerView);
            }
        };
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            changeFragment(getNavItemResId());
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        Utility.hideSoftKeyboard(this);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_explore_places:
                setNavSectionId(Utility.NAV_SECTION_EXPLORE_PLACES);
                changeFragment();
                break;
            case R.id.nav_search_flight:
                setNavSectionId(Utility.NAV_SECTION_SEARCH_FLIGHTS);
                changeFragment();
                break;
            case R.id.nav_favourite:
                setNavSectionId(Utility.NAV_SECTION_FAVOURITES);
                changeFragment();
                break;
            case R.id.nav_share:
                setNavSectionId(DEFAULT_SECTION_ID);
                break;
            case R.id.nav_contact_us:
                setNavSectionId(DEFAULT_SECTION_ID);
                break;
            default:
                Log.e(LOG_TAG, "Unknown id - " + item.getItemId());
                break;
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor mPreferenceEditor = mSharedPreferences.edit();
        mPreferenceEditor.putInt(Utility.KEY_NAVIGATION_SECTION_ID,
                getNavSectionId());
        mPreferenceEditor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setNavSectionId(mSharedPreferences
                .getInt(Utility.KEY_NAVIGATION_SECTION_ID,
                        DEFAULT_SECTION_ID));
        changeFragment(getNavItemResId());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void changeFragment(){
        Fragment fragment = null;

        switch (getNavSectionId()){
            case Utility.NAV_SECTION_EXPLORE_PLACES:
                fragment = ExplorePlacesFragment
                        .getNewInstance(getNavSectionId());
                break;
            case Utility.NAV_SECTION_SEARCH_FLIGHTS:
                fragment = FlightSearchFragment
                        .getNewInstance(getNavSectionId());
                break;
            case Utility.NAV_SECTION_FAVOURITES:
                fragment = FavouritesFragment
                        .getNewInstance(getNavSectionId());
                break;
        }

        if (fragment != null) fragmentTransaction(fragment);
    }

    private void changeFragment(int navItemResId){
        changeFragment();
        mNavigationView.setCheckedItem(navItemResId);
    }

    private int getNavItemResId(){
        switch (getNavSectionId()){
            case Utility.NAV_SECTION_EXPLORE_PLACES:
                return R.id.nav_explore_places;
            case Utility.NAV_SECTION_SEARCH_FLIGHTS:
                return R.id.nav_search_flight;
            case Utility.NAV_SECTION_FAVOURITES:
                return R.id.nav_favourite;
        }
        return R.id.nav_explore_places;
    }

    private void fragmentTransaction(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    public int getNavSectionId() {
        return mNavSectionId;
    }

    public void setNavSectionId(int navSectionId) {
        this.mNavSectionId = navSectionId;
    }
}