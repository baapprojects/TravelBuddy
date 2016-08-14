package com.hari.aund.travelbuddy.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.hari.aund.travelbuddy.R;
import com.hari.aund.travelbuddy.activity.MainActivity;
import com.hari.aund.travelbuddy.activity.PlacesActivity;
import com.hari.aund.travelbuddy.adapter.PlaceAutoCompleteAdapter;
import com.hari.aund.travelbuddy.utils.Utility;
import com.hari.aund.travelbuddy.utils.gplaces.PlaceAutoComplete;

import java.util.ArrayList;

/**
 * Created by Hari Nivas Kumar R P on 8/13/2016.
 */
public class ExplorePlacesFragment extends Fragment
        implements GoogleApiClient.OnConnectionFailedListener,
        AdapterView.OnItemClickListener,
        View.OnClickListener {

    private static final String LOG_TAG = ExplorePlacesFragment.class.getSimpleName();
    private static final String KEY_NEW_PLACE_NAME = "new-place-name";
    private static final int PLACE_PICKER_REQUEST = 1;
    private static final int RESULTS_OK = -1;

    private int mNavSectionId = 0;
    private static LatLngBounds mLatLngBounds = null;
    private static ArrayList<String> mPlacesIdAL = null, mPlacesNameAL = null;
    private static ArrayAdapter<String> mDefaultPlacesArrayAdapter = null;
    private static PlaceAutoCompleteAdapter mPlaceAutoCompleteAdapter = null;

    private ActionBar mActionBar = null;
    private GoogleApiClient mGoogleApiClient = null;

    private TextView mNewPlace;

    public ExplorePlacesFragment() {
    }

    public static ExplorePlacesFragment getNewInstance(int navSectionId) {
        ExplorePlacesFragment explorePlacesFragment = new ExplorePlacesFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(Utility.KEY_NAVIGATION_SECTION_ID, navSectionId);
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

        setExplorePlacesVisibility(rootView);

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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            setNavSectionId(savedInstanceState.getInt(Utility.KEY_NAVIGATION_SECTION_ID));
            setNewPlace(savedInstanceState.getString(KEY_NEW_PLACE_NAME));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Utility.KEY_NAVIGATION_SECTION_ID, getNavSectionId());
        outState.putString(KEY_NEW_PLACE_NAME, getNewPlace());
    }

    @Override
    public void onStop() {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.find_place_on_map) {
            try {
                setNewPlace(getString(R.string.def_no_place_selected));

                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQUEST);
            } catch (Exception e) {
                Log.e(LOG_TAG, "Message - " + e.getMessage());
                e.printStackTrace();
            }
        } else if (view.getId() == R.id.new_place_found_on_map) {
            Toast.makeText(getContext(), getNewPlace(), Toast.LENGTH_LONG)
                    .show();

            Intent placesIntent = new Intent(getActivity(), PlacesActivity.class);
            placesIntent.putExtra(Utility.KEY_PLACE_ID, "placeId");
            placesIntent.putExtra(Utility.KEY_PLACE_NAME, getNewPlace());
            startActivity(placesIntent);
        }
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

            Intent placesIntent = new Intent(getActivity(), PlacesActivity.class);
            placesIntent.putExtra(Utility.KEY_PLACE_ID, placeId);
            placesIntent.putExtra(Utility.KEY_PLACE_NAME, placeName);
            startActivity(placesIntent);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULTS_OK) {
                Place place = PlacePicker.getPlace(data, getContext());

                //mPlacesIdAL.add(place.getId());
                //mPlacesNameAL.add(place.getName().toString());
                //mPlacesNameAL.add(place.getAddress().toString());
                //mDefaultPlacesArrayAdapter.notifyDataSetChanged();
                //setNewPlace(place.getAddress().toString());
                setNewPlace(place.getName().toString());

                Log.d(LOG_TAG, "PlaceId - " + place.getId());
                Log.d(LOG_TAG, "PlaceName - " + place.getName().toString());
                Log.d(LOG_TAG, "PlaceAddress - " + place.getAddress().toString());

                if (getView() != null) {
                    Snackbar.make(getView(), place.getId() + "\n" + place.getAddress().toString(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null)
                            .show();
                } else {
                    Toast.makeText(getContext(), "getView() is null", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        }
    }

    private void readAndSetNavSectionId() {
        setNavSectionId(getArguments().getInt(Utility.KEY_NAVIGATION_SECTION_ID));
    }

    private void setNavSectionId(int navSectionId) {
        this.mNavSectionId = navSectionId;
        Log.d(LOG_TAG, "NAV SECTION ID - " +
                getNavSectionId() + " & Name : " + getNavSectionName());
    }

    private int getNavSectionId() {
        return mNavSectionId;
    }

    private String getNavSectionName() {
        switch (mNavSectionId) {
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

    private void setFragmentTitle() {
        if (mActionBar != null) {
            mActionBar.setTitle(getNavSectionName());
        }
    }

    private void initValues() {
        setDefaultPlacesIdAL();
        setUpDefaultPlacesNameAL();
        setBoundsGreaterSydney();
    }

    private void setBoundsGreaterSydney() {
        mLatLngBounds = new LatLngBounds(
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

        mPlacesIdAL.add("ChIJSdRbuoqEXjkRFmVPYRHdzk8");
        mPlacesIdAL.add("ChIJd7gN4_CECDsRZ7QW-3bwXco");
        mPlacesIdAL.add("ChIJbU60yXAWrjsR4E9-UejD3_g");
        mPlacesIdAL.add("ChIJpQoX1dIJGToRqD-zaCsOWPw");
        mPlacesIdAL.add("ChIJYTN9T-plUjoRM9RjaAunYW4");
        mPlacesIdAL.add("ChIJQbc2YxC6vzsRkkDzYv-H-Oo");
        mPlacesIdAL.add("ChIJlfcOXx8FWTkRLlJU7YfYG4Y");
        mPlacesIdAL.add("ChIJ9wH5Z8NTBDkRJXdLVsUE_nw");
        mPlacesIdAL.add("ChIJnaj_mSQJ4TgR8eeXRm16VgY");
        mPlacesIdAL.add("ChIJv8a-SlENCDsRkkGEpcqC1Qs");
        mPlacesIdAL.add("ChIJZ_YISduC-DkRvCxsj-Yw40M");
        mPlacesIdAL.add("ChIJW_Wc1P8SCDsRmXw47fuQvWQ");
        mPlacesIdAL.add("ChIJ5XOPmBBwrzsRCe4TG2b7kns");
        mPlacesIdAL.add("ChIJwe1EZjDG5zsRaYxkjY_tpF0");
        mPlacesIdAL.add("ChIJLbZ-NFv9DDkRzk0gTkm3wlI");
        mPlacesIdAL.add("ChIJARFGZy6_wjsRQ-Oenb9DjYI");
    }

    private String getDefaultPlaceId(int position) {
        return getDefaultPlacesIdAL().get(position);
    }

    private ArrayList<String> getDefaultPlacesNameAL() {
        return mPlacesNameAL;
    }

    private void setUpDefaultPlacesNameAL() {
        mPlacesNameAL = new ArrayList<>();

        mPlacesNameAL.add("Ahmedabad, Gujarat, India");
        mPlacesNameAL.add("Alappuzha, Kerala, India");
        mPlacesNameAL.add("Bangalore, Karnataka, India");
        mPlacesNameAL.add("Bhubaneswar, Odisha, India");
        mPlacesNameAL.add("Chennai, Tamil Nadu, India");
        mPlacesNameAL.add("Goa, India");
        mPlacesNameAL.add("Gujarat, India");
        mPlacesNameAL.add("Himachal Pradesh, India");
        mPlacesNameAL.add("Jammu and Kashmir");
        mPlacesNameAL.add("Kochi, Kerala, India");
        mPlacesNameAL.add("Kolkata, West Bengal, India");
        mPlacesNameAL.add("Kerala, India");
        mPlacesNameAL.add("Mysuru, Karnataka, India");
        mPlacesNameAL.add("Mumbai, Maharashtra, India");
        mPlacesNameAL.add("New Delhi, India");
        mPlacesNameAL.add("Pune, Maharashtra, India");
    }

    private String getDefaultPlaceName(int position) {
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

    private void setExplorePlacesVisibility(View rootView) {
        RelativeLayout relativeLayout =
                (RelativeLayout) rootView.findViewById(R.id.explore_places_layout);
        ImageView findPlace =
                (ImageView) rootView.findViewById(R.id.find_place_on_map);
        mNewPlace =
                (TextView) rootView.findViewById(R.id.new_place_found_on_map);
        AutoCompleteTextView searchPlace =
                (AutoCompleteTextView) rootView.findViewById(R.id.explore_place);

        //Read from Settings
        if (Utility.isPlacePickerSettingEnabled() &&
                getNavSectionId() == Utility.NAV_SECTION_EXPLORE_PLACES) {
            findPlace.setVisibility(View.VISIBLE);
            findPlace.setOnClickListener(this);

            mNewPlace.setVisibility(View.VISIBLE);
            mNewPlace.setOnClickListener(this);

            searchPlace.setVisibility(View.INVISIBLE);
        } else {
            findPlace.setVisibility(View.INVISIBLE);
            mNewPlace.setVisibility(View.INVISIBLE);

            setPlaceAutoCompleteAdapter();
            searchPlace.setVisibility(View.VISIBLE);
            searchPlace.setAdapter(getPlaceAutoCompleteAdapter());
            searchPlace.setOnItemClickListener(this);
        }
    }

    private void setNewPlace(String newPlace) {
        mNewPlace.setText(newPlace);
    }

    private String getNewPlace() {
        return mNewPlace.getText().toString();
    }
}
