package com.hari.aund.travelbuddy.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.hari.aund.travelbuddy.R;
import com.hari.aund.travelbuddy.adapter.ViewSectionsPagerAdapter;
import com.hari.aund.travelbuddy.adapter.ViewSpinnerAdapter;
import com.hari.aund.travelbuddy.data.PlacesCategories;
import com.hari.aund.travelbuddy.data.PlacesCategory;
import com.hari.aund.travelbuddy.fragment.PlacesSubTypeFragment;
import com.hari.aund.travelbuddy.utils.Utility;

public class PlacesCategoryActivity extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener {

    private static final String LOG_TAG = PlacesCategoryActivity.class.getSimpleName();

    private PlacesCategory mPlacesCategory = null;
    private String mPlaceTypeName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places_category);

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
        }

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        Spinner spinner = (Spinner)findViewById(R.id.spinner);

        if ((mPlacesCategory.getCategoryActivityId() == PlacesCategories.PLACES_CATEGORY_A_ACTIVITY) ||
                (mPlacesCategory.getCategoryActivityId() == PlacesCategories.PLACES_CATEGORY_C_ACTIVITY)) {
            ViewSectionsPagerAdapter sectionsPagerAdapter =
                    new ViewSectionsPagerAdapter(this,
                            getSupportFragmentManager());

            viewPager.setAdapter(sectionsPagerAdapter);

            if (mPlacesCategory.getCategoryActivityId() == PlacesCategories.PLACES_CATEGORY_A_ACTIVITY) {
                tabLayout.setupWithViewPager(viewPager);
            } else {
                tabLayout.setVisibility(View.INVISIBLE);
            }

            spinner.setVisibility(View.INVISIBLE);
        } else if (mPlacesCategory.getCategoryActivityId() == PlacesCategories.PLACES_CATEGORY_B_ACTIVITY){
            viewPager.setVisibility(View.INVISIBLE);
            tabLayout.setVisibility(View.INVISIBLE);

            ViewSpinnerAdapter viewSpinnerAdapter = new ViewSpinnerAdapter(this,
                    toolbar.getContext(),
                    mPlacesCategory.getSubTypeList().toArray(
                            new String[mPlacesCategory.getSubTypeListSize()]
                    )
            );

            spinner.setAdapter(viewSpinnerAdapter);
            spinner.setOnItemSelectedListener(this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_places_category, menu);
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
                .replace(R.id.nested_scroll_view,
                        PlacesSubTypeFragment.newInstance(position + 1,
                                mPlacesCategory.getSubTypeList().get(position))
                ).commit();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public PlacesCategory getPlacesCategory() {
        return mPlacesCategory;
    }

    private void setPlacesCategory(PlacesCategory mPlacesCategory) {
        this.mPlacesCategory = mPlacesCategory;
    }
}
