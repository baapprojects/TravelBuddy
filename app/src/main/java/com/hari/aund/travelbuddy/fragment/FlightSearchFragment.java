package com.hari.aund.travelbuddy.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hari.aund.travelbuddy.R;
import com.hari.aund.travelbuddy.activity.FlightDetailActivity;
import com.hari.aund.travelbuddy.activity.MainActivity;
import com.hari.aund.travelbuddy.data.FlightValues;
import com.hari.aund.travelbuddy.utils.DefaultValues;
import com.hari.aund.travelbuddy.utils.Utility;

/**
 * A placeholder fragment containing a simple view.
 */
public class FlightSearchFragment extends Fragment
        implements View.OnClickListener, DefaultValues {

    private static final String LOG_TAG = FlightSearchFragment.class.getSimpleName();

    private static final int INDEX_CITIES_SOURCE = 1;
    private static final int INDEX_CITIES_DESTINATION = 2;
    private static final int PREFERENCE_MODE_PRIVATE = 0;

    private int mNavSectionId;
    private String mNavSectionName;
    private String mFromSourceCity;
    private String mToDestinationCity;
    private ActionBar mActionBar;
    private RelativeLayout sourceCityLayout, destinationCityLayout;
    private TextView fromCityTextView, toCityTextView;
    private Button searchFlightButton;
    private SharedPreferences mSharedPreferences;

    public FlightSearchFragment() {
    }

    public static FlightSearchFragment getNewInstance(int navSectionId) {
        FlightSearchFragment flightSearchFragment = new FlightSearchFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(Utility.KEY_NAVIGATION_SECTION_ID, navSectionId);
        flightSearchFragment.setArguments(bundle);

        return flightSearchFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mActionBar = ((MainActivity) getActivity()).getSupportActionBar();
        mSharedPreferences = getActivity().getPreferences(PREFERENCE_MODE_PRIVATE);

        readAndSetNavSectionId();
        setFragmentTitle();

        View rootView = inflater.inflate(R.layout.fragment_flight_search, container, false);

        sourceCityLayout = (RelativeLayout) rootView.findViewById(R.id.source_layout);
        destinationCityLayout = (RelativeLayout) rootView.findViewById(R.id.destination_layout);
        fromCityTextView = (TextView) rootView.findViewById(R.id.source_place_id);
        toCityTextView = (TextView) rootView.findViewById(R.id.destination_place_id);
        searchFlightButton = (Button) rootView.findViewById(R.id.flight_search_btn_id);

        sourceCityLayout.setOnClickListener(this);
        destinationCityLayout.setOnClickListener(this);
        searchFlightButton.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences.Editor mPreferenceEditor = mSharedPreferences.edit();
        mPreferenceEditor.putInt(Utility.KEY_NAVIGATION_SECTION_ID,
                getNavSectionId());
        mPreferenceEditor.putString(Utility.KEY_CITY_SOURCE,
                getFromSourceCity());
        mPreferenceEditor.putString(Utility.KEY_CITY_DESTINATION,
                getToDestinationCity());
        mPreferenceEditor.apply();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getNavSectionId() == 0 || getFromSourceCity() == null ||
                getToDestinationCity() == null) {

            if (getNavSectionId() == 0) {
                setNavSectionId(mSharedPreferences.getInt(
                        Utility.KEY_NAVIGATION_SECTION_ID, Utility.NAV_SECTION_EXPLORE_PLACES));
                setNavSectionName(Utility.getNavSectionName(getContext(), getNavSectionId()));
            }

            setFromSourceCity(mSharedPreferences.getString(
                    Utility.KEY_CITY_SOURCE, DEFAULT_SOURCE_CITY));
            setToDestinationCity(mSharedPreferences.getString(
                    Utility.KEY_CITY_DESTINATION, DEFAULT_DESTINATION_CITY));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.source_layout:
                displayFlightCities(INDEX_CITIES_SOURCE);
                break;
            case R.id.destination_layout:
                displayFlightCities(INDEX_CITIES_DESTINATION);
                break;
            case R.id.flight_search_btn_id:
                if (getFromSourceCity().equals(getToDestinationCity())) {
                    Snackbar.make(view, "Source & Destination are Same.!", Snackbar.LENGTH_LONG)
                            .show();
                    return;
                }
                startFlightDetailActivity();
                break;
        }
    }

    private void displayFlightCities(final int index) {

        final ArrayAdapter<String> flightsArrayAdapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.select_dialog_singlechoice,
                FlightValues.getFlightsAsArrayList()
        );

        AlertDialog.Builder cityChooseBuilder = new AlertDialog.Builder(getContext());
        cityChooseBuilder.setNegativeButton("cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }
        );
        cityChooseBuilder.setAdapter(flightsArrayAdapter,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String cityName = flightsArrayAdapter.getItem(which);
                        if (INDEX_CITIES_SOURCE == index)
                            setFromSourceCity(cityName);
                        else
                            setToDestinationCity(cityName);
                    }
                }
        );
        cityChooseBuilder.show();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void readAndSetNavSectionId() {
        setNavSectionId(getArguments().getInt(Utility.KEY_NAVIGATION_SECTION_ID));
        setNavSectionName(Utility.getNavSectionName(getContext(), getNavSectionId()));
        Log.d(LOG_TAG, "NAV SECTION ID - " + getNavSectionId() + " & Name : " + getNavSectionName());
    }

    private void startFlightDetailActivity(){
        if (!Utility.isNetworkAvailable(getContext())){
            Log.d(LOG_TAG, "You are Offline! : startFlightDetailActivity!");
            return;
        }

        Intent intent = new Intent(getActivity(), FlightDetailActivity.class);
        intent.putExtra(Utility.KEY_CITY_SOURCE, getFromSourceCity());
        intent.putExtra(Utility.KEY_CITY_DESTINATION, getToDestinationCity());
        startActivity(intent);
        getActivity().overridePendingTransition(
                R.animator.activity_open_translate, R.animator.activity_close_scale);
    }

    private int getNavSectionId() {
        return mNavSectionId;
    }

    private void setNavSectionId(int navSectionId) {
        this.mNavSectionId = navSectionId;
    }

    private String getNavSectionName() {
        return mNavSectionName;
    }

    private void setNavSectionName(String navSectionName) {
        this.mNavSectionName = navSectionName;
    }

    private void setFragmentTitle() {
        if (mActionBar != null) {
            mActionBar.setTitle(Utility.getNavSectionName(getContext(), mNavSectionId));
        }
    }

    public String getFromSourceCity() {
        return mFromSourceCity;
    }

    public void setFromSourceCity(String fromSourceCity) {
        mFromSourceCity = fromSourceCity;
        fromCityTextView.setText(getFromSourceCity());
    }

    public String getToDestinationCity() {
        return mToDestinationCity;
    }

    public void setToDestinationCity(String toDestinationCity) {
        mToDestinationCity = toDestinationCity;
        toCityTextView.setText(getToDestinationCity());
    }
}
