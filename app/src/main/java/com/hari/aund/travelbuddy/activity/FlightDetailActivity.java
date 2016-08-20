package com.hari.aund.travelbuddy.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.hari.aund.travelbuddy.R;
import com.hari.aund.travelbuddy.adapter.FlightListAdapter;
import com.hari.aund.travelbuddy.data.FlightDetail;
import com.hari.aund.travelbuddy.parser.FlightApiParser;
import com.hari.aund.travelbuddy.utils.DefaultValues;
import com.hari.aund.travelbuddy.utils.Utility;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.ArrayList;

public class FlightDetailActivity extends AppCompatActivity
        implements DefaultValues{

    private static final String LOG_TAG = FlightDetailActivity.class.getSimpleName();

    private String mFromSourceCity, mToDestinationCity;
    private FlightListAdapter mFlightListAdapter;
    private ArrayList<FlightDetail> mFlightDetailList = new ArrayList<>();
    private ProgressWheel mProgressWheel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_flight_detail);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (getIntent().getExtras() != null) {
            mFromSourceCity = getIntent().getStringExtra(Utility.KEY_CITY_SOURCE);
            mToDestinationCity = getIntent().getStringExtra(Utility.KEY_CITY_DESTINATION);

            //TODO: Toast to be removed
            String toastStr = "Please, wait! searching flights between \n" +
                    "'" + mFromSourceCity.toUpperCase() + "' & '"
                    + mToDestinationCity.toUpperCase() + "'";
            Toast.makeText(this, toastStr, Toast.LENGTH_LONG).show();
        }

        mProgressWheel = (ProgressWheel) findViewById(R.id.progress_wheel);
        mProgressWheel.spin();

        StaggeredGridLayoutManager sGridLayoutManager = new StaggeredGridLayoutManager(
                DEFAULT_COLUMN_COUNT_1, StaggeredGridLayoutManager.VERTICAL);

        mFlightListAdapter = new FlightListAdapter(this, mFlightDetailList);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(sGridLayoutManager);
        recyclerView.setAdapter(mFlightListAdapter);

        new FlightApiParser(this).getFlightDetails();
    }

    public String getFromSourceCity() {
        return mFromSourceCity;
    }

    public void setFromSourceCity(String fromSourceCity) {
        mFromSourceCity = fromSourceCity;
    }

    public String getToDestinationCity() {
        return mToDestinationCity;
    }

    public void setToDestinationCity(String toDestinationCity) {
        mToDestinationCity = toDestinationCity;
    }

    public FlightListAdapter getFlightListAdapter() {
        return mFlightListAdapter;
    }

    public void setFlightListAdapter(FlightListAdapter flightListAdapter) {
        mFlightListAdapter = flightListAdapter;
    }

    public ArrayList<FlightDetail> getFlightDetailList() {
        return mFlightDetailList;
    }

    public void setFlightDetailList(ArrayList<FlightDetail> flightDetailList) {
        mFlightDetailList = flightDetailList;
    }

    public ProgressWheel getProgressWheel() {
        return mProgressWheel;
    }

    public void setProgressWheel(ProgressWheel progressWheel) {
        mProgressWheel = progressWheel;
    }
}
