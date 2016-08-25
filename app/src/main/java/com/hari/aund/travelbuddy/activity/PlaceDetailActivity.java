package com.hari.aund.travelbuddy.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hari.aund.travelbuddy.R;
import com.hari.aund.travelbuddy.adapter.ReviewListAdapter;
import com.hari.aund.travelbuddy.data.PlaceDetail;
import com.hari.aund.travelbuddy.data.provider.PlaceColumns;
import com.hari.aund.travelbuddy.data.provider.Places;
import com.hari.aund.travelbuddy.parser.PlacesApiParser;
import com.hari.aund.travelbuddy.utils.DefaultValues;
import com.hari.aund.travelbuddy.utils.Utility;

public class PlaceDetailActivity extends AppCompatActivity
        implements OnMapReadyCallback, DefaultValues,
        View.OnClickListener, LoaderManager.LoaderCallbacks<Cursor> {

    private static final String LOG_TAG = PlaceDetailActivity.class.getSimpleName();
    private static final int PLACE_DETAIL_CURSOR_LOADER_ID = 0;

    private int mCategoryId;
    private boolean mMarkAsFavourite = false;
    private String mPlaceId, mCategoryName, mSectionName;
    private PlaceDetail mPlaceDetail;
    private TextView placeName, placeVicinity, placeAddress, placeRating;
    private ImageView coverImage;
    private CardView photosCard, timetableCard;
    private LinearLayout photosLayout, timetableLayout;
    private RecyclerView reviewsRecyclerView;
    private FloatingActionButton callFab, webFab, favFab, shareFab;
    private GoogleMap mGoogleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_place_detail);
        setSupportActionBar(toolbar);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setTitle("");
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }

        initViewsAndLayout();

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            mPlaceId = intent.getStringExtra(Utility.KEY_PLACE_ID);
            mCategoryId = intent.getIntExtra(Utility.KEY_CATEGORY_ID, DEFAULT_CATEGORY_ID);
            mCategoryName = intent.getStringExtra(Utility.KEY_CATEGORY_NAME);
            mSectionName = intent.getStringExtra(Utility.KEY_PLACE_SECTION_NAME);
        } else {
            mPlaceId = DEFAULT_PLACE_ID_TEMPLE;
            mCategoryId = DEFAULT_CATEGORY_ID;
            mCategoryName = DEFAULT_CATEGORY_NAME;
            mSectionName = DEFAULT_CATEGORY_SECTION_NAME;
        }
        mPlaceDetail = new PlaceDetail(mPlaceId);

        getSupportLoaderManager().initLoader(
                PLACE_DETAIL_CURSOR_LOADER_ID, null, this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        getSupportLoaderManager().destroyLoader(PLACE_DETAIL_CURSOR_LOADER_ID);
        if (mMarkAsFavourite)
            addPlaceToFavourites();
        else
            removePlaceFromFavourites();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_place_detail_share:
                triggerShareIntent();
                break;
            case R.id.fab_place_detail_fav:
                saveToDataBase(view);
                break;
            case R.id.fab_place_detail_website:
                triggerWebViewIntent();
                break;
            case R.id.fab_place_detail_call:
                triggerCallIntent();
                break;
        }
    }

    public void animateToPlaceInMap() {
        LatLng placeLatLng = new LatLng(mPlaceDetail.getLatitude(),
                mPlaceDetail.getLongitude());

        mGoogleMap.addMarker(
                new MarkerOptions()
                        .position(placeLatLng)
                        .title("Here")
        );

        mGoogleMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                        new LatLng(
                                mPlaceDetail.getLatitude(),
                                mPlaceDetail.getLongitude()
                        ),
                        12.0f
                )
        );
    }

    private void initViewsAndLayout() {
        placeName = (TextView) findViewById(R.id.place_name);
        placeVicinity = (TextView) findViewById(R.id.place_vicinity);
        placeAddress = (TextView) findViewById(R.id.place_address);
        placeRating = (TextView) findViewById(R.id.place_rating);
        coverImage = (ImageView) findViewById(R.id.cover_image);

        photosCard = (CardView) findViewById(R.id.card2);
        timetableCard = (CardView) findViewById(R.id.card3);

        photosLayout = (LinearLayout) findViewById(R.id.photo_layout);
        timetableLayout = (LinearLayout) findViewById(R.id.timetable_layout);
        reviewsRecyclerView = (RecyclerView) findViewById(R.id.reviews_recycler_view);

        callFab = (FloatingActionButton) findViewById(R.id.fab_place_detail_call);
        webFab = (FloatingActionButton) findViewById(R.id.fab_place_detail_website);
        favFab = (FloatingActionButton) findViewById(R.id.fab_place_detail_fav);
        shareFab = (FloatingActionButton) findViewById(R.id.fab_place_detail_share);

        setOnClickListenerToViews();
    }

    private void setOnClickListenerToViews() {
        callFab.setOnClickListener(this);
        webFab.setOnClickListener(this);
        favFab.setOnClickListener(this);
        shareFab.setOnClickListener(this);
    }

    private void triggerShareIntent() {
        String shareText = "Download Travel Buddy to help find places with ease.";
        String shareIntentTitle = "Share using";

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareText);

        startActivity(Intent.createChooser(shareIntent, shareIntentTitle));
    }

    private void triggerCallIntent() {
        if (mPlaceDetail.hasPhoneNumber()) {
            Uri callUri = Uri.parse("tel:" + mPlaceDetail.getPhoneNumber());

            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(callUri);
            try {
                startActivity(callIntent);
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
    }

    private void triggerWebViewIntent() {
        if (mPlaceDetail.hasWebsite()) {
            Uri websiteUri = Uri.parse(mPlaceDetail.getWebsite());

            Intent webIntent = new Intent(Intent.ACTION_VIEW);
            webIntent.setData(websiteUri);
            startActivity(webIntent);
        }
    }

    private void saveToDataBase(View view) {
        if (mPlaceDetail.getName() != null && !mPlaceDetail.getName().isEmpty()) {
            if (!mMarkAsFavourite) {
                favFab.setImageResource(R.drawable.ic_favorite_black_24dp);
            } else {
                favFab.setImageResource(R.drawable.ic_favorite_white_24dp);
            }
            mMarkAsFavourite = !mMarkAsFavourite;
        }
    }

    public void populateLayoutsWithData() {
        setFabViewVisibility();
        populateHeaderLayout();
        createAddressLayout();
        createPhotosLayout();
        createTimeTableLayout();
        createReviewsRecyclerView();
    }

    private void setFabViewVisibility() {
        if (!mPlaceDetail.hasPhoneNumber()) {
            callFab.setVisibility(View.GONE);
            Log.d(LOG_TAG, "No Phone Number. So, call Fab is hidden!");
        }
        if (!mPlaceDetail.hasWebsite()) {
            webFab.setVisibility(View.GONE);
            Log.d(LOG_TAG, "No Website Link. So, website Fab is hidden!");
        }
    }

    private void populateHeaderLayout() {
        placeName.setText(mPlaceDetail.getName());
        placeVicinity.setText(mPlaceDetail.getVicinity());

        if (mPlaceDetail.hasPhotoReference()) {
            String heightAndWidthStr = 500 + "";
            //TODO: to be removed later
/*            Picasso.with(this)
                    .load(new PlacesApiParser(this).getPhotoUrl(
                            mPlaceDetail.getPhotoReference().get(0),
                            heightAndWidthStr, heightAndWidthStr)
                    )
                    .fit()
                    .into(coverImage);
                    */
            coverImage.setAlpha(0.6f);
        }

        if (mPlaceDetail.hasRating()) {
            String ratingStr = mPlaceDetail.getRating() + " user ratings";
            placeRating.setText(ratingStr);
        } else {
            placeRating.setVisibility(View.GONE);
        }
    }

    private void createAddressLayout() {
        placeAddress.setText(mPlaceDetail.getAddress());
        animateToPlaceInMap();
    }

    private void createPhotosLayout() {
        if (mPlaceDetail.hasPhotoReference()) {
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );

            ImageView imageView;
            PlacesApiParser placesApiParser = new PlacesApiParser(this);

            for (int index = 0; index < mPlaceDetail.getPhotoReference().size(); index++) {
                imageView = new ImageView(this);

                imageView.setPadding(5, 0, 5, 0);
                imageView.setLayoutParams(params);
                String heightAndWidthStr = 500 + "";
                //TODO: to be removed later
/*                Picasso.with(this)
                        .load(placesApiParser.getPhotoUrl(
                                mPlaceDetail.getPhotoReference().get(index),
                                heightAndWidthStr, heightAndWidthStr))
                        .resize(300, 300)
                        .into(imageView);
                        */

                photosLayout.addView(imageView);
                Log.d(LOG_TAG, "Photo Reference : " + mPlaceDetail.getPhotoReference().get(index));
            }
        } else {
            photosCard.setVisibility(View.GONE);
            Log.d(LOG_TAG, "No Entries. So, Photos Layout is ignored!");
        }
    }

    private void createTimeTableLayout() {
        if (mPlaceDetail.hasTimeTable()) {
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            TextView textView;

            for (int index = 0; index < mPlaceDetail.getTimetable().size(); index++) {
                textView = new TextView(this);
                textView.setLayoutParams(params);
                textView.setPadding(3, 8, 3, 8);
                textView.setText(mPlaceDetail.getTimeEntry(index));
                textView.setTextSize(17);
                textView.setTextColor(Color.parseColor("#2f2f2f"));

                timetableLayout.addView(textView);
            }
        } else {
            timetableCard.setVisibility(View.GONE);
            Log.d(LOG_TAG, "No Entries. So, TimeTable Layout is ignored!");
        }
    }

    private void createReviewsRecyclerView() {
        if (mPlaceDetail.hasReviews()) {
            StaggeredGridLayoutManager sGridLayoutManager = new StaggeredGridLayoutManager(
                    DEFAULT_COLUMN_COUNT_1, StaggeredGridLayoutManager.VERTICAL);
            ReviewListAdapter reviewListAdapter = new ReviewListAdapter(this, mPlaceDetail.getReviewDetails());

            reviewsRecyclerView.setLayoutManager(sGridLayoutManager);
            reviewsRecyclerView.setAdapter(reviewListAdapter);
        } else {
            reviewsRecyclerView.setVisibility(View.GONE);
            Log.d(LOG_TAG, "No Reviews. So, Reviews Recycler View is ignored!");
        }
    }

    private void addPlaceToFavourites() {
        Cursor placeCursor = null;
        Log.d(LOG_TAG, mPlaceDetail.getId() + " - " + mPlaceDetail.getName());
        try {
            placeCursor = getContentResolver().query(
                    Places.CONTENT_URI_PLACES,
                    new String[]{PlaceColumns.PLACE_ID},
                    PlaceColumns.PLACE_ID + " = ?",
                    new String[]{mPlaceDetail.getId()},
                    null);
            if (placeCursor != null && placeCursor.moveToFirst()) {
                Log.d(LOG_TAG, "addToFavourites : Entry[" + mPlaceDetail.getName() + "] already present in DB!");
            } else {
                ContentValues placeContentValues =
                        mPlaceDetail.getPlaceDetailsAsContentValues();

                placeContentValues.put(PlaceColumns.CATEGORY_ID, mCategoryId);
                placeContentValues.put(PlaceColumns.SUB_TYPE_NAME, mSectionName);

                getContentResolver()
                        .insert(Places.CONTENT_URI_PLACES, placeContentValues);
                Log.d(LOG_TAG, "addToFavourites : New Entry[" + mPlaceDetail.getName() + "] added to DB!");
            }
        } catch (NullPointerException e) {
            Log.e(LOG_TAG, "addToFavourites : NullPointerException@try for Cursor!");
            e.printStackTrace();
        } finally {
            try {
                if (placeCursor != null)
                    placeCursor.close();
            } catch (NullPointerException e) {
                Log.e(LOG_TAG, "addToFavourites : NullPointerException@finally for Cursor!");
                e.printStackTrace();
            }
        }
    }

    private void removePlaceFromFavourites() {
        Cursor placeCursor = null;
        Log.d(LOG_TAG, mPlaceDetail.getId() + " - " + mPlaceDetail.getName());
        try {
            placeCursor = getContentResolver().query(
                    Places.CONTENT_URI_PLACES,
                    new String[]{PlaceColumns.PLACE_ID},
                    PlaceColumns.PLACE_ID + " = ?",
                    new String[]{mPlaceDetail.getId()},
                    null);
            if (placeCursor != null && placeCursor.moveToFirst()) {
                getContentResolver()
                        .delete(Places.withPlaceId(mPlaceDetail.getId()), null, null);
                Log.d(LOG_TAG, "removeFromFavourites : Entry[" + mPlaceDetail.getName() + "] removed from DB!");
            } else {
                Log.d(LOG_TAG, "removeFromFavourites : No Entry[" + mPlaceDetail.getName() + "] present in DB!");
            }
        } catch (NullPointerException e) {
            Log.e(LOG_TAG, "removeFromFavourites : NullPointerException@try for Cursor!");
            e.printStackTrace();
        } finally {
            try {
                if (placeCursor != null)
                    placeCursor.close();
            } catch (NullPointerException e) {
                Log.e(LOG_TAG, "removeFromFavourites : NullPointerException@finally for Cursor!");
                e.printStackTrace();
            }
        }
    }

    public PlaceDetail getPlaceDetail() {
        return mPlaceDetail;
    }

    private void setPlaceDetail(PlaceDetail placeDetail) {
        mPlaceDetail = placeDetail;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, Bundle args) {
        Uri uri = null;
        if (loaderId == PLACE_DETAIL_CURSOR_LOADER_ID) {
            uri = Places.withPlaceId(mPlaceDetail.getId());

            Log.d(LOG_TAG, "onCreateLoader : PlaceId  - " +
                    mPlaceDetail.getId() + "\n Uri - " + uri);
        }
        return new CursorLoader(this, uri, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> placeDetailLoader, Cursor placeCursor) {
        if (placeDetailLoader.getId() == PLACE_DETAIL_CURSOR_LOADER_ID) {
            if (placeCursor != null && placeCursor.moveToFirst()) {
                mMarkAsFavourite = true;
                favFab.setImageResource(R.drawable.ic_favorite_black_24dp);

                mPlaceDetail.updatePlaceDetailsFromCursor(placeCursor);
                populateLayoutsWithData();

                Log.d(LOG_TAG, "onLoadFinished : Entry[" + mPlaceDetail.getName() +
                        "] present in DB! So, no network call");
            } else {
                mMarkAsFavourite = false;
                favFab.setImageResource(R.drawable.ic_favorite_white_24dp);

                new PlacesApiParser(this).getPlaceDetails();

                Log.d(LOG_TAG, "onLoadFinished : Entry for Id[" + mPlaceDetail.getId() +
                        "] not present in DB! So, do make a network call");

            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> placeDetailLoader) {
        placeDetailLoader.reset();
    }
}
