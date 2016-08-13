package com.hari.aund.travelbuddy.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.hari.aund.travelbuddy.activity.MainActivity;
import com.hari.aund.travelbuddy.utils.gplaces.PlaceAutoComplete;
import com.hari.aund.travelbuddy.adapter.PlaceAutoCompleteAdapter;
import com.hari.aund.travelbuddy.R;
import com.hari.aund.travelbuddy.utils.Utility;

import java.util.ArrayList;

/**
 * Created by Hari Nivas Kumar R P on 8/13/2016.
 */
public class ExplorePlacesFragment extends Fragment
        implements GoogleApiClient.OnConnectionFailedListener,
        AdapterView.OnItemClickListener {

    private static final String LOG_TAG = ExplorePlacesFragment.class.getSimpleName();
    private static final String KEY_NAVIGATION_SECTION_ID = "nav_section_id";

    private int mNavSectionId = 0;
    private static LatLngBounds mLatLngBounds = null;
    private static ArrayList<String> mPlacesIdAL = null, mPlacesNameAL = null;
    private static ArrayAdapter<String> mDefaultPlacesArrayAdapter = null;
    private static PlaceAutoCompleteAdapter mPlaceAutoCompleteAdapter = null;

    private ActionBar mActionBar = null;
    private GoogleApiClient mGoogleApiClient = null;

    public ExplorePlacesFragment() {
    }

    public static ExplorePlacesFragment getNewInstance(int navSectionId) {
        ExplorePlacesFragment explorePlacesFragment = new ExplorePlacesFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(KEY_NAVIGATION_SECTION_ID, navSectionId);
        explorePlacesFragment.setArguments(bundle);

        return explorePlacesFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        mActionBar = ((MainActivity) getActivity()).getSupportActionBar();

        readAndSetNavSectionId();
        setFragmentTitle();

        mGoogleApiClient = new GoogleApiClient
                .Builder(getActivity())
                .addApi(Places.GEO_DATA_API)
                .build();

        initValues();

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        setPlaceAutoCompleteAdapter();
        AutoCompleteTextView searchPlace =
                (AutoCompleteTextView) rootView.findViewById(R.id.enterplace);
        searchPlace.setAdapter(getPlaceAutoCompleteAdapter());
        searchPlace.setOnItemClickListener(this);

        setDefaultPlacesArrayAdapter();
        ListView placesListView =
                (ListView) rootView.findViewById(R.id.places);
        placesListView.setAdapter(getDefaultPlacesArrayAdapter());
        placesListView.setOnItemClickListener(this);

        return rootView;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onStop() {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        String placeId = null, placeName = null;

        if (adapterView.getAdapter() instanceof PlaceAutoCompleteAdapter) {
            Log.d(LOG_TAG, "adapterView.getAdapter() instanceof PlaceAutoCompleteAdapter");

            PlaceAutoComplete place =
                    getPlaceAutoCompleteAdapter().getItem(position);

            placeId = place.getPlaceId();
            placeName = place.getPlaceDescription();
        } else if (adapterView.getAdapter() instanceof ArrayAdapter<?>) {
            Log.d(LOG_TAG, "adapterView.getAdapter() instanceof ArrayAdapter<String>");

            placeId = getDefaultPlaceId(position);
            placeName = getDefaultPlaceName(position);
        }

        if ((placeId != null) && (placeName != null)) {
            Log.d(LOG_TAG, "PlaceId - " + placeId + " Name - " + placeName);
        }
    }

    private void readAndSetNavSectionId() {
        this.mNavSectionId = getArguments().getInt(KEY_NAVIGATION_SECTION_ID);
        Log.d(LOG_TAG, "NAV SECTION ID - " +
                getNavSectionId() + " & Name : " + getNavSectionName());
    }

    private int getNavSectionId() {
        return mNavSectionId;
    }

    private String getNavSectionName(){
        switch (mNavSectionId){
            case Utility.NAV_SECTION_EXPLORE_PLACES:
                //return "Explore Places";
                return getResources().getString(R.string.explore_places);
            case Utility.NAV_SECTION_SEARCH_FLIGHTS:
                //return "Search Flights";
                return getResources().getString(R.string.search_flights);
            case Utility.NAV_SECTION_FAVOURITES:
                //return "Favourites";
                return getResources().getString(R.string.favourites);
            default:
                Log.e(LOG_TAG, "Unknown Navigation Section id");
                return "Unknown";
        }
    }

    private void setFragmentTitle(){
        if (mActionBar != null) {
            mActionBar.setTitle(getNavSectionName());
        }
    }

    private void initValues(){
        setDefaultPlacesIdAL();
        setUpDefaultPlacesNameAL();
        setBoundsGreaterSydney();
    }

    private void setBoundsGreaterSydney() {
        mLatLngBounds =  new LatLngBounds(
                new LatLng(37.398160, -122.180831),
                new LatLng(37.430610, -121.972090)
        );
    }

    private LatLngBounds getBoundsGreaterSydney() {
        return mLatLngBounds;
    }

    private ArrayList<String> getDefaultPlacesIdAL() {
        return mPlacesIdAL;
    }

    private void setDefaultPlacesIdAL() {
        mPlacesIdAL = new ArrayList<>();

        mPlacesIdAL.add("ChIJLbZ-NFv9DDkRzk0gTkm3wlI");
        mPlacesIdAL.add("ChIJbU60yXAWrjsR4E9-UejD3_g");
        mPlacesIdAL.add("ChIJlfcOXx8FWTkRLlJU7YfYG4Y");
        mPlacesIdAL.add("ChIJ9wH5Z8NTBDkRJXdLVsUE_nw");
        mPlacesIdAL.add("ChIJYTN9T-plUjoRM9RjaAunYW4");
        mPlacesIdAL.add("ChIJW_Wc1P8SCDsRmXw47fuQvWQ");
        mPlacesIdAL.add("ChIJwe1EZjDG5zsRaYxkjY_tpF0");
        mPlacesIdAL.add("ChIJQbc2YxC6vzsRkkDzYv-H-Oo");
        mPlacesIdAL.add("ChIJnaj_mSQJ4TgR8eeXRm16VgY");
    }

    private String getDefaultPlaceId(int position){
        return getDefaultPlacesIdAL().get(position);
    }

    private ArrayList<String> getDefaultPlacesNameAL() {
        return mPlacesNameAL;
    }

    private void setUpDefaultPlacesNameAL() {
        mPlacesNameAL = new ArrayList<>();

        mPlacesNameAL.add("New Delhi, India");
        mPlacesNameAL.add("Bangalore, Karnataka, India");
        mPlacesNameAL.add("Gujarat, India");
        mPlacesNameAL.add("Himachal Pradesh, India");
        mPlacesNameAL.add("Chennai, Tamil Nadu, India");
        mPlacesNameAL.add("Kerala, India");
        mPlacesNameAL.add("Mumbai, Maharashtra, India");
        mPlacesNameAL.add("Goa, India");
        mPlacesNameAL.add("Jammu & Kashmir");
    }

    private String getDefaultPlaceName(int position){
        return getDefaultPlacesNameAL().get(position);
    }

    public ArrayAdapter<String> getDefaultPlacesArrayAdapter() {
        return mDefaultPlacesArrayAdapter;
    }

    private void setDefaultPlacesArrayAdapter() {
        mDefaultPlacesArrayAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                mPlacesNameAL);
    }

    private PlaceAutoCompleteAdapter getPlaceAutoCompleteAdapter() {
        return mPlaceAutoCompleteAdapter;
    }

    private void setPlaceAutoCompleteAdapter() {
        mPlaceAutoCompleteAdapter = new PlaceAutoCompleteAdapter(
                getActivity(),
                android.R.layout.simple_list_item_1,
                mGoogleApiClient,
                getBoundsGreaterSydney(),
                null);
    }
}
