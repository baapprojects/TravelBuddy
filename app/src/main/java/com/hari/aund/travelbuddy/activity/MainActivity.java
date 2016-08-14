package com.hari.aund.travelbuddy.activity;

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
import com.hari.aund.travelbuddy.utils.Utility;
import com.hari.aund.travelbuddy.fragment.ExplorePlacesFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final int DEFAULT_SECTION_ID =
            Utility.NAV_SECTION_EXPLORE_PLACES;
    private DrawerLayout mDrawerLayout;

    private int mNavSectionId = DEFAULT_SECTION_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            changeFragment(ExplorePlacesFragment
                    .getNewInstance(getNavSectionId()));
        }

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                mDrawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close){

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

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
                break;
            case R.id.nav_search_flight:
                setNavSectionId(Utility.NAV_SECTION_SEARCH_FLIGHTS);
                break;
            case R.id.nav_favourite:
                setNavSectionId(Utility.NAV_SECTION_FAVOURITES);
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

        changeFragment(ExplorePlacesFragment
                .getNewInstance(getNavSectionId()));

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null){
            setNavSectionId(savedInstanceState
                    .getInt(Utility.KEY_NAVIGATION_SECTION_ID));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Utility.KEY_NAVIGATION_SECTION_ID,
                getNavSectionId());
    }

    private void changeFragment(Fragment fragment) {
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