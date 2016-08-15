package com.hari.aund.travelbuddy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.hari.aund.travelbuddy.R;
import com.hari.aund.travelbuddy.app.TravelBuddyApp;
import com.hari.aund.travelbuddy.utils.Utility;

import org.json.JSONException;
import org.json.JSONObject;

public class PlacesActivity extends AppCompatActivity
        implements View.OnClickListener {

    private static final String LOG_TAG = PlacesActivity.class.getSimpleName();

    private static final int MAX_PLACES_TYPE = 6;
    private static final String TAG_RESULT = "result";
    private static final String TAG_GEOMETRY = "geometry";
    private static final String TAG_LOCATION = "location";

    private String mPlaceId = null;
    private String mPlaceName = null;
    private Double latitude = null;
    private Double longitude = null;

    private LinearLayout linearLayout[] = new LinearLayout[MAX_PLACES_TYPE];
    private int linearLayoutIds[] = new int[]{
            R.id.tourist, R.id.hotel,
            R.id.restaurants, R.id.shopping,
            R.id.movie, R.id.food_court
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getIntent().getExtras() != null) {
            mPlaceId = getIntent().getStringExtra(Utility.KEY_PLACE_ID);
            mPlaceName = getIntent().getStringExtra(Utility.KEY_PLACE_NAME);
        }

        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setTitle(mPlaceName);
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }

        for (int index = 0; index < MAX_PLACES_TYPE; index++) {
            linearLayout[index] =
                    (LinearLayout) findViewById(linearLayoutIds[index]);
            linearLayout[index].setOnClickListener(this);
        }

        getPlaceDetail();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, mPlaceName, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        Class classType = PlacesCategoryAActivity.class;
        String placeTypeName = "Default";
        switch (view.getId()){
            case R.id.tourist:
                Log.d(LOG_TAG, "Tourists Selected!");
                placeTypeName = "Tourists";
                classType = PlacesCategoryAActivity.class;
                break;
            case R.id.hotel:
                Log.d(LOG_TAG, "Hotel Selected!");
                placeTypeName = "Hotel";
                classType = PlacesCategoryBActivity.class;
                break;
            case R.id.restaurants:
                Log.d(LOG_TAG, "Restaurants Selected!");
                placeTypeName = "Restaurants";
                classType = PlacesCategoryCActivity.class;
                break;
            case R.id.shopping:
                Log.d(LOG_TAG, "Shopping Selected!");
                placeTypeName = "Shopping";
                classType = PlacesCategoryAActivity.class;
                break;
            case R.id.movie:
                Log.d(LOG_TAG, "Movie Selected!");
                placeTypeName = "Movie";
                classType = PlacesCategoryBActivity.class;
                break;
            case R.id.food_court:
                Log.d(LOG_TAG, "Food Court Selected!");
                placeTypeName = "Food Court";
                classType = PlacesCategoryCActivity.class;
                break;
        }
        Intent placesTypeIntent = new Intent(this, classType);
        placesTypeIntent.putExtra(Utility.KEY_PLACE_TYPE_NAME, placeTypeName);
        startActivity(placesTypeIntent);
    }

    public void getPlaceDetail() {
        String placesReqUrl = Utility.getPlacesUrl(mPlaceId);
        Log.d(LOG_TAG, "URL - " + placesReqUrl);
        JsonObjectRequest placesJsonObjReq =
                new JsonObjectRequest(
                        placesReqUrl,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {
                                try {
                                    JSONObject results = jsonObject.getJSONObject(TAG_RESULT);
                                    JSONObject geometry = results.getJSONObject(TAG_GEOMETRY);
                                    JSONObject location = geometry.getJSONObject(TAG_LOCATION);

                                    latitude = Double.parseDouble(location.getString("lat"));
                                    longitude = Double.parseDouble(location.getString("lng"));

                                    Log.d(LOG_TAG, "PlaceId - " + mPlaceId);
                                    Log.d(LOG_TAG, "Place Name - " + mPlaceName);
                                    Log.d(LOG_TAG, "Latitude - " + latitude + " Longitude - " + longitude);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                Log.e(LOG_TAG, "Volley : onErrorResponse - " + volleyError.getMessage());
                            }
                        }
                );

        TravelBuddyApp.getInstance()
                .addToRequestQueue(placesJsonObjReq);
    }
}
