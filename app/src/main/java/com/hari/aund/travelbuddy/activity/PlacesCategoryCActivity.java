package com.hari.aund.travelbuddy.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.hari.aund.travelbuddy.R;
import com.hari.aund.travelbuddy.data.PlacesCategory;
import com.hari.aund.travelbuddy.utils.Utility;

public class PlacesCategoryCActivity extends AppCompatActivity {

    private static final String LOG_TAG = PlacesCategoryCActivity.class.getSimpleName();

    private PlacesCategory mPlacesCategory = null;
    private String mPlaceTypeName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places_category_c);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getIntent().getExtras() != null) {
            mPlacesCategory = getIntent().getParcelableExtra(Utility.KEY_PLACE_CATEGORY_INFO);
            mPlaceTypeName = getIntent().getStringExtra(Utility.KEY_PLACE_TYPE_NAME);
        }

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(mPlaceTypeName);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_places_category_c, menu);
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

    public PlacesCategory getPlacesCategory() {
        return mPlacesCategory;
    }

    private void setPlacesCategory(PlacesCategory mPlacesCategory) {
        this.mPlacesCategory = mPlacesCategory;
    }
}
