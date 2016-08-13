package com.hari.aund.travelbuddy;

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

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final int DEFAULT_SECTION_ID =
            Utility.NAV_SECTION_EXPLORE_PLACES;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        changeFragment(ExplorePlacesFragment
                .getNewInstance(DEFAULT_SECTION_ID));

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                mDrawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
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
                changeFragment(ExplorePlacesFragment
                        .getNewInstance(Utility.NAV_SECTION_EXPLORE_PLACES));
                break;
            case R.id.nav_search_flight:
                changeFragment(ExplorePlacesFragment
                        .getNewInstance(Utility.NAV_SECTION_SEARCH_FLIGHTS));
                break;
            case R.id.nav_favourite:
                changeFragment(ExplorePlacesFragment
                        .getNewInstance(Utility.NAV_SECTION_FAVOURITES));
                break;
            case R.id.nav_share:
                changeFragment(ExplorePlacesFragment
                        .getNewInstance(DEFAULT_SECTION_ID));
                break;
            case R.id.nav_contact_us:
                changeFragment(ExplorePlacesFragment
                        .getNewInstance(DEFAULT_SECTION_ID));
                break;
            default:
                Log.e(LOG_TAG, "Unknown id - " + item.getItemId());
                break;
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void changeFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}