package com.hari.aund.travelbuddy.fragment;

import android.content.DialogInterface;
import android.content.Intent;
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
import com.hari.aund.travelbuddy.utils.Utility;

/**
 * A placeholder fragment containing a simple view.
 */
public class FlightSearchFragment extends Fragment
        implements View.OnClickListener {

    private static final String LOG_TAG = FlightSearchFragment.class.getSimpleName();

    private static final int INDEX_CITIES_FROM = 1;
    private static final int INDEX_CITIES_TO = 2;

    private int mNavSectionId;
    private String mNavSectionName;
    private String mFromSourceCity;
    private String mToDestinationCity;
    private ActionBar mActionBar;

    RelativeLayout fromLayout, toLayout;
    TextView fromCityTextView, toCityTextView;
    Button search;

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

        readAndSetNavSectionId();
        setFragmentTitle();

        View rootView = inflater.inflate(R.layout.fragment_flight_search, container, false);

        fromLayout = (RelativeLayout) rootView.findViewById(R.id.from_layout);
        toLayout = (RelativeLayout) rootView.findViewById(R.id.to_layout);
        fromCityTextView = (TextView) rootView.findViewById(R.id.from_name);
        toCityTextView = (TextView) rootView.findViewById(R.id.to_name);
        search = (Button) rootView.findViewById(R.id.search);

        fromLayout.setOnClickListener(this);
        toLayout.setOnClickListener(this);
        search.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.from_layout:
                displayCities(INDEX_CITIES_FROM);
                break;
            case R.id.to_layout:
                displayCities(INDEX_CITIES_TO);
                break;
            case R.id.search:
                if (getFromSourceCity().equals(getToDestinationCity())){
                    Snackbar.make(view, "Source & Destination are Same.!", Snackbar.LENGTH_LONG)
                            .show();
                    return;
                }

                Intent intent = new Intent(getActivity(), FlightDetailActivity.class);
                intent.putExtra(Utility.KEY_CITY_SOURCE, getFromSourceCity());
                intent.putExtra(Utility.KEY_CITY_DESTINATION, getToDestinationCity());
                startActivity(intent);

                break;
        }
    }

    private void displayCities(final int index) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        final ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<String>(getContext(),
                        android.R.layout.select_dialog_singlechoice);

        arrayAdapter.add("Bangkok");
        arrayAdapter.add("Bangalore");
        arrayAdapter.add("Chennai");
        arrayAdapter.add("Dubai");
        arrayAdapter.add("Delhi");
        arrayAdapter.add("Goa");
        arrayAdapter.add("Hyderabad");
        arrayAdapter.add("Kathmandu");
        arrayAdapter.add("Kolkata");
        arrayAdapter.add("Mumbai");
        arrayAdapter.add("Pune");
        arrayAdapter.add("Singapore");

        builder.setNegativeButton("cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }
        );

        builder.setAdapter(arrayAdapter,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String strName = arrayAdapter.getItem(which);
                        if (INDEX_CITIES_FROM == index) {
                            setFromSourceCity(strName);
                        } else {
                            setToDestinationCity(strName);
                        }
                    }
                }
        );
        builder.show();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            setFromSourceCity(savedInstanceState.getString(Utility.KEY_CITY_SOURCE));
            setToDestinationCity(savedInstanceState.getString(Utility.KEY_CITY_DESTINATION));
        } else {
            setFromSourceCity(getContext().getResources().getString(R.string.chennai));
            setToDestinationCity(getContext().getResources().getString(R.string.delhi));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Utility.KEY_CITY_SOURCE, getFromSourceCity());
        outState.putString(Utility.KEY_CITY_DESTINATION, getToDestinationCity());
    }

    private void readAndSetNavSectionId() {
        setNavSectionId(getArguments().getInt(Utility.KEY_NAVIGATION_SECTION_ID));
        setNavSectionName(Utility.getNavSectionName(getContext(), getNavSectionId()));
        Log.d(LOG_TAG, "NAV SECTION ID - " + getNavSectionId() + " & Name : " + getNavSectionName());
    }

    private void setNavSectionId(int navSectionId) {
        this.mNavSectionId = navSectionId;
    }

    private int getNavSectionId() {
        return mNavSectionId;
    }

    private void setNavSectionName(String navSectionName) {
        this.mNavSectionName = navSectionName;
    }

    private String getNavSectionName() {
        return mNavSectionName;
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
