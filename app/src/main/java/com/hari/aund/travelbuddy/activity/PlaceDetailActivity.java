package com.hari.aund.travelbuddy.activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
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

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hari.aund.travelbuddy.R;
import com.hari.aund.travelbuddy.adapter.ReviewListAdapter;
import com.hari.aund.travelbuddy.data.PlaceDetail;
import com.hari.aund.travelbuddy.parser.PlacesApiParser;
import com.hari.aund.travelbuddy.utils.DefaultValues;
import com.hari.aund.travelbuddy.utils.Utility;

public class PlaceDetailActivity extends AppCompatActivity
        implements OnMapReadyCallback, DefaultValues, View.OnClickListener {

    private static final String LOG_TAG = PlaceDetailActivity.class.getSimpleName();

    private int mCategoryId;
    private String mPlaceId, mCategoryName;
    private PlaceDetail mPlaceDetail;
    private TextView placeName, placeVicinity, placeAddress, placeRating, favouriteText;
    private ImageView coverImage, shareIcon, favouriteIcon;
    private CardView addressCard, photosCard, timetableCard;
    private LinearLayout callNowLayout, websiteLayout, reviewsLayout, favouriteLayout;
    private LinearLayout photosLayout, timetableLayout, reviewsRecycleViewLayout;
    private RecyclerView reviewsRecyclerView;
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
        } else {
            mPlaceId = DEFAULT_PLACE_ID_TEMPLE;
            mCategoryId = DEFAULT_CATEGORY_ID;
            mCategoryName = DEFAULT_CATEGORY_NAME;
        }
        mPlaceDetail = new PlaceDetail(mPlaceId);

        new PlacesApiParser(this).getPlaceDetails();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.share_icon:
                triggerShareIntent();
                break;
            case R.id.call_now_layout:
                triggerCallIntent();
                break;
            case R.id.website_layout:
                triggerWebViewIntent();
                break;
            case R.id.reviews_layout:
                triggerReviewActivity();
                break;
            case R.id.favourite_layout:
                saveToDataBase(view);
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
        favouriteText = (TextView) findViewById(R.id.favourite_text);

        coverImage = (ImageView) findViewById(R.id.cover_image);
        favouriteIcon = (ImageView) findViewById(R.id.favourite_icon);
        shareIcon = (ImageView) findViewById(R.id.share_icon);

        addressCard = (CardView) findViewById(R.id.card1);
        photosCard = (CardView) findViewById(R.id.card2);
        timetableCard = (CardView) findViewById(R.id.card3);

        callNowLayout = (LinearLayout) findViewById(R.id.call_now_layout);
        websiteLayout = (LinearLayout) findViewById(R.id.website_layout);
        reviewsLayout = (LinearLayout) findViewById(R.id.reviews_layout);
        favouriteLayout = (LinearLayout) findViewById(R.id.favourite_layout);
        photosLayout = (LinearLayout) findViewById(R.id.photo_layout);
        timetableLayout = (LinearLayout) findViewById(R.id.timetable_layout);
        reviewsRecycleViewLayout = (LinearLayout) findViewById(R.id.reviews_content_Layout);

        reviewsRecyclerView = (RecyclerView) findViewById(R.id.reviews_recycler_view);

        setOnClickListenerToViews();
    }

    private void setOnClickListenerToViews() {
        shareIcon.setOnClickListener(this);

        callNowLayout.setOnClickListener(this);
        websiteLayout.setOnClickListener(this);
        reviewsLayout.setOnClickListener(this);
        favouriteLayout.setOnClickListener(this);
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
            //TODO : Add proper Permission and Start the intent
            //startActivity(callIntent);
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

    private void triggerReviewActivity() {
        //Intent reviewIntent = new Intent(PlaceDetailActivity.this, ReviewActivity.class);
        Intent reviewIntent = new Intent(PlaceDetailActivity.this, MainActivity.class);
        reviewIntent.putExtra(Utility.KEY_PLACE_ID, mPlaceId);
        startActivity(reviewIntent);
    }

    private void saveToDataBase(View view) {
        /* TODO: Create Database and add to correct table based on mCategoryId
        if (!db.getPlaces(mPlaceId)) {
            favouriteIcon.setImageResource(R.drawable.favourite_icon_red);
            favouriteText.setText("SAVED");
            favouriteText.setTextColor(Color.RED);
            db.addPlaces(place_id);
        } else {
            Snackbar.make(, "Already Added", Snackbar.LENGTH_SHORT).show();
        }
        */
    }

    public void populateLayoutsWithData(){
        populateHeaderLayout();
        createAddressLayout();
        createPhotosLayout();
        createTimeTableLayout();
        createReviewsRecyclerView();
    }

    private void populateHeaderLayout(){
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

    private void createAddressLayout(){
        placeAddress.setText(mPlaceDetail.getAddress());
        animateToPlaceInMap();
    }

    private void createPhotosLayout(){
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

    private void createTimeTableLayout(){
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

    private void createReviewsRecyclerView(){
        StaggeredGridLayoutManager sGridLayoutManager = new StaggeredGridLayoutManager(
                DEFAULT_COLUMN_COUNT_1, StaggeredGridLayoutManager.VERTICAL);
        ReviewListAdapter reviewListAdapter = new ReviewListAdapter(this, mPlaceDetail.getReviewDetails());

        reviewsRecyclerView.setLayoutManager(sGridLayoutManager);
        reviewsRecyclerView.setAdapter(reviewListAdapter);
    }

    public PlaceDetail getPlaceDetail() {
        return mPlaceDetail;
    }

    private void setPlaceDetail(PlaceDetail placeDetail) {
        mPlaceDetail = placeDetail;
    }
}
