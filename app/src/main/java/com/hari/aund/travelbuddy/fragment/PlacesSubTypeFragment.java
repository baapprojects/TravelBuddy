package com.hari.aund.travelbuddy.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hari.aund.travelbuddy.R;
import com.hari.aund.travelbuddy.adapter.PlacesListAdapter;
import com.hari.aund.travelbuddy.data.PlacesCategory;
import com.hari.aund.travelbuddy.data.PlacesListInfo;
import com.hari.aund.travelbuddy.parser.PlacesApiParser;
import com.hari.aund.travelbuddy.utils.DefaultValues;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.ArrayList;

/**
 * Created by Hari Nivas Kumar R P on 8/16/2016.
 */
public class PlacesSubTypeFragment extends Fragment
        implements DefaultValues {

    private static final String LOG_TAG = PlacesSubTypeFragment.class.getSimpleName();

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String ARG_PLACES_CATEGORY_PARCEL = "places_category_parcel";

    private int mCategoryActivityId;
    private int mSectionNumber;
    private String mSectionName;
    private String mCategoryName;
    private Double mLatitude, mLongitude;
    private PlacesCategory mPlacesCategory;
    private PlacesListAdapter mPlacesListAdapter;
    private ArrayList<PlacesListInfo> mPlacesListInfoArray = new ArrayList<>();
    private ProgressWheel mProgressWheel;

    public PlacesSubTypeFragment() {
    }

    public static PlacesSubTypeFragment newInstance(int sectionNumber,
                                                    PlacesCategory placesCategory) {
        PlacesSubTypeFragment fragment = new PlacesSubTypeFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        args.putParcelable(ARG_PLACES_CATEGORY_PARCEL, placesCategory);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_places_sub_type, container, false);

        setSectionNumber(getArguments());
        mPlacesCategory = getArguments().getParcelable(ARG_PLACES_CATEGORY_PARCEL);
        readValues();

        new PlacesApiParser(this).getPlaceListDetails();

        setProgressWheel((ProgressWheel) rootView.findViewById(R.id.progress_wheel));
        getProgressWheel().spin();

        /* TODO: Set Category Activity name for Type 'C'
        if (mCategoryActivityId == PlacesCategoryValues.PLACES_CATEGORY_C_ACTIVITY) {
            PlacesCategoryActivity placesCategoryActivity =
                    (PlacesCategoryActivity) getActivity();
            ActionBar actionBar = placesCategoryActivity.getSupportActionBar();
            if (actionBar != null) {
                String activityNewTitle = mCategoryName +
                        " - " + mSectionName;
                actionBar.setTitle(activityNewTitle);
            }
        }
        */

        int columnCount = 1;
        StaggeredGridLayoutManager sGridLayoutManager =
                new StaggeredGridLayoutManager(columnCount, StaggeredGridLayoutManager.VERTICAL);

        mPlacesListAdapter = new PlacesListAdapter(getActivity(),
                mPlacesListInfoArray, mCategoryActivityId);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(sGridLayoutManager);
        recyclerView.setAdapter(mPlacesListAdapter);

        return rootView;
    }

    private void readValues() {
        //Set Default Values if parcel is null; Def Place Kerala
        if (mPlacesCategory != null) {
            setCategoryActivityId();
            setSectionName();
            setCategoryActivityId();
            setCategoryName();
            setLatitude();
            setLongitude();
        } else {
            setSectionName(DEFAULT_CATEGORY_NAME);
            setLatitude(DEFAULT_LATITUDE);
            setLongitude(DEFAULT_LONGITUDE);
        }
    }

    public String getCategoryName() {
        return mCategoryName;
    }

    private void setCategoryName() {
        this.mCategoryName = mPlacesCategory.getCategoryName();
    }

    public int getCategoryActivityId() {
        return mCategoryActivityId;
    }

    private void setCategoryActivityId() {
        this.mCategoryActivityId = mPlacesCategory.getCategoryActivityId();
    }

    public int getSectionNumber() {
        return mSectionNumber;
    }

    private void setSectionNumber(Bundle bundle) {
        this.mSectionNumber = bundle.getInt(ARG_SECTION_NUMBER);
    }

    public String getSectionName() {
        return mSectionName;
    }

    private void setSectionName() {
        this.mSectionName = mPlacesCategory.getSubTypeList().get(mSectionNumber - 1);
    }

    private void setSectionName(String sectionName) {
        this.mSectionName = sectionName;
    }

    public Double getLatitude() {
        return mLatitude;
    }

    private void setLatitude() {
        mLatitude = mPlacesCategory.getLatitude();
    }

    private void setLatitude(Double latitude) {
        mLatitude = latitude;
    }

    public Double getLongitude() {
        return mLongitude;
    }

    private void setLongitude() {
        mLongitude = mPlacesCategory.getLongitude();
    }

    private void setLongitude(Double longitude) {
        mLongitude = longitude;
    }

    public PlacesListAdapter getPlacesListAdapter() {
        return mPlacesListAdapter;
    }

    private void setPlacesListAdapter(PlacesListAdapter mPlacesListAdapter) {
        this.mPlacesListAdapter = mPlacesListAdapter;
    }

    public ArrayList<PlacesListInfo> getPlacesListInfoArray() {
        return mPlacesListInfoArray;
    }

    private void setPlacesListInfoArray(ArrayList<PlacesListInfo> mPlacesListInfoArray) {
        this.mPlacesListInfoArray = mPlacesListInfoArray;
    }

    public ProgressWheel getProgressWheel() {
        return mProgressWheel;
    }

    private void setProgressWheel(ProgressWheel mProgressWheel) {
        this.mProgressWheel = mProgressWheel;
    }
}
