package com.hari.aund.travelbuddy.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.hari.aund.travelbuddy.R;
import com.hari.aund.travelbuddy.adapter.PlacesListAdapter;
import com.hari.aund.travelbuddy.app.TravelBuddyApp;
import com.hari.aund.travelbuddy.data.PlacesCategory;
import com.hari.aund.travelbuddy.data.PlacesListInfo;
import com.hari.aund.travelbuddy.utils.Utility;
import com.pnikosis.materialishprogress.ProgressWheel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Hari Nivas Kumar R P on 8/16/2016.
 */
public class PlacesSubTypeFragment extends Fragment {

    private static final String LOG_TAG = PlacesSubTypeFragment.class.getSimpleName();

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String ARG_PLACES_CATEGORY_PARCEL = "places_category_parcel";

    // TODO : Move this to Parser
    private static final String TAG_RESULT = "results";
    private static final String TAG_ICON = "icon";
    private static final String TAG_NAME = "name";
    private static final String TAG_PLACE_ID = "place_id";
    private static final String TAG_RATING = "rating";
    private static final String TAG_ADDRESS = "vicinity";
    private static final String TAG_PHOTOS = "photos";
    private static final String TAG_PHOTOS_REFERENCE = "photo_reference";

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

        mSectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
        mPlacesCategory = getArguments().getParcelable(ARG_PLACES_CATEGORY_PARCEL);

        if (mPlacesCategory != null) {
            mCategoryActivityId = mPlacesCategory.getCategoryActivityId();
            mSectionName = mPlacesCategory.getSubTypeList().get(mSectionNumber - 1);
            mCategoryName = mPlacesCategory.getCategoryName();
            mLatitude = mPlacesCategory.getLatitude();
            mLongitude = mPlacesCategory.getLongitude();
        } else{
            //Default if parcel is null - Place Kerala
            mSectionName = "hindu_temple";
            mLatitude = 9.4126239;
            mLongitude = 76.4100267;
        }

        getPlaceListDetail();

        mProgressWheel = (ProgressWheel) rootView.findViewById(R.id.progress_wheel);
        mProgressWheel.spin();

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

    // TODO : Move this to Parser
    public void getPlaceListDetail() {
        String placesReqUrl = Utility.getPlacesListUrl(mLatitude + "," + mLongitude, mSectionName);
        Log.d(LOG_TAG, "Section : " + mSectionName);
        Log.d(LOG_TAG, "URL - " + placesReqUrl);

        JsonObjectRequest placeReq =
                new JsonObjectRequest(
                        placesReqUrl,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject jsonObjectIn) {
                                mProgressWheel.stopSpinning();

                                try {
                                    JSONArray jsonArray = jsonObjectIn.getJSONArray(TAG_RESULT);
                                    for (int index = 0; index < jsonArray.length(); index++) {

                                        JSONObject jsonObject = jsonArray.getJSONObject(index);
                                        PlacesListInfo placesListInfo = new PlacesListInfo();

                                        placesListInfo.setPlaceId(jsonObject.getString(TAG_PLACE_ID));
                                        placesListInfo.setPlaceName(jsonObject.getString(TAG_NAME));
                                        placesListInfo.setPlaceAddress(jsonObject.getString(TAG_ADDRESS));
                                        placesListInfo.setIconUrl(jsonObject.getString(TAG_ICON));

                                        if (jsonObject.has(TAG_RATING)) {
                                            placesListInfo.setPlaceRating(jsonObject.getDouble(TAG_RATING));
                                        }

                                        if (jsonObject.has(TAG_PHOTOS)) {
                                            JSONArray jsonArrayPhotos = jsonObject.getJSONArray(TAG_PHOTOS);
                                            Log.d(LOG_TAG, jsonArrayPhotos.toString());

                                            JSONObject jsonObjectPhotos = jsonArrayPhotos.getJSONObject(0);
                                            placesListInfo.setPhotoReference(jsonObjectPhotos.getString(TAG_PHOTOS_REFERENCE));
                                        }

                                        mPlacesListInfoArray.add(placesListInfo);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                mPlacesListAdapter.notifyDataSetChanged();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                Log.e(LOG_TAG, "VolleyError : " + volleyError.getMessage());
                            }
                        }
                );

        TravelBuddyApp.getInstance().addToRequestQueue(placeReq);
    }
}
