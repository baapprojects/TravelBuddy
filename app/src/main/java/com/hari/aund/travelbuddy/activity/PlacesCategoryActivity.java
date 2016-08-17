package com.hari.aund.travelbuddy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
    private PlacesCategoryAdapter mPlacesCategoryAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places_cateogory);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        readValues(getIntent());

        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setTitle(getPlaceName());
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }

        new PlacesApiParser(this).getExplorePlaceDetails();

        /* TODO: Consider Using it for Tablets
        StaggeredGridLayoutManager sGridLayoutManager =
                new StaggeredGridLayoutManager(
                        Utility.PLACES_ACTIVITY_COLUMN_COUNT_PORTRAIT,
                        StaggeredGridLayoutManager.VERTICAL
                );
        */

        GridLayoutManager gridLayoutManager =
                new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false);

        mPlacesCategoryAdapter = new PlacesCategoryAdapter(this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(mPlacesCategoryAdapter);

        FloatingActionButton favouriteFab =
                (FloatingActionButton) findViewById(R.id.fab_places_favourite);
        favouriteFab.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fab_places_favourite) {
            Snackbar.make(view, mPlaceName, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    private void readValues(Intent intent) {
        //Set Default Values if extras is null; Def Place Alappuzha
        if (intent.getExtras() != null) {
            setPlaceId(getIntent());
            setPlaceName(getIntent());
        } else {
            setPlaceId(DEFAULT_PLACE_ID);
            setPlaceName(DEFAULT_PLACE_NAME);
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
}
