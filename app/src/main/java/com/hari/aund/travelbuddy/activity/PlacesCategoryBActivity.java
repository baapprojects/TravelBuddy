package com.hari.aund.travelbuddy.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;

import com.hari.aund.travelbuddy.R;
import com.hari.aund.travelbuddy.adapter.ViewSpinnerAdapter;
import com.hari.aund.travelbuddy.data.PlacesCategory;
import com.hari.aund.travelbuddy.fragment.PlacesSubTypeFragment;
import com.hari.aund.travelbuddy.utils.Utility;

import java.util.ArrayList;

public class PlacesCategoryBActivity extends AppCompatActivity
        implements OnItemSelectedListener {

    private static final String LOG_TAG = PlacesCategoryBActivity.class.getSimpleName();

    private PlacesCategory mPlacesCategory = null;
    private String mPlaceTypeName = null;
    private ArrayList<String> mSubTypeList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places_category_b);

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
            //actionBar.setDisplayShowTitleEnabled(false);
        }

        ViewSpinnerAdapter viewSpinnerAdapter = new ViewSpinnerAdapter(this,
                toolbar.getContext(),
                mPlacesCategory.getSubTypeList().toArray(
                        new String[mPlacesCategory.getSubTypeListSize()]
                )
        );

        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        spinner.setAdapter(viewSpinnerAdapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_places_category_b, menu);
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long longValue) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container,
                        PlacesSubTypeFragment.newInstance(position+1,
                                mPlacesCategory.getSubTypeList().get(position))
                ).commit();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}
