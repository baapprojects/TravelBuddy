package com.hari.aund.travelbuddy.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hari.aund.travelbuddy.R;

/**
 * Created by Hari Nivas Kumar R P on 8/16/2016.
 */
public class PlacesSubTypeFragment extends Fragment {

    private static final String LOG_TAG = PlacesSubTypeFragment.class.getSimpleName();

    private static final String ARG_CATEGORY_ACTIVITY_ID = "category_activity_id";
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String ARG_SECTION_NAME = "section_name";
    private static final String ARG_CATEGORY_NAME = "category_name";

    private int mCategoryActivityId;
    private int mSectionNumber;
    private String mSectionName;
    private String mCategoryName;

    public PlacesSubTypeFragment() {
    }

    public static PlacesSubTypeFragment newInstance(int categoryActivityId,
                                                    int sectionNumber,
                                                    String sectionName,
                                                    String categoryName) {
        PlacesSubTypeFragment fragment = new PlacesSubTypeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_CATEGORY_ACTIVITY_ID, categoryActivityId);
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        args.putString(ARG_SECTION_NAME, sectionName);
        args.putString(ARG_CATEGORY_NAME, categoryName);
        Log.d(LOG_TAG, "SectionNumber - " + sectionNumber);
        Log.d(LOG_TAG, "SectionName - " + sectionName);
        //args.putParcelable(Utility.KEY_PLACE_CATEGORY_INFO, );
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_places_category, container, false);

        mCategoryActivityId = getArguments().getInt(ARG_CATEGORY_ACTIVITY_ID);
        mSectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
        mSectionName = getArguments().getString(ARG_SECTION_NAME);
        mCategoryName = getArguments().getString(ARG_CATEGORY_NAME);

        /* TODO: Set Category Activity name for Type 'C'
        if (mCategoryActivityId == PlacesCategories.PLACES_CATEGORY_C_ACTIVITY) {
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

        CharSequence charSequence = "Section id - " + mSectionNumber + "\n" +
                "Section Name - " + mSectionName;

        Log.d(LOG_TAG, "From Char Sequence - " + charSequence);

        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
        textView.setText(charSequence);

        return rootView;
    }
}
