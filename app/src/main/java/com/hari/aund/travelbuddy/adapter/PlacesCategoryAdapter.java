package com.hari.aund.travelbuddy.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hari.aund.travelbuddy.R;
import com.hari.aund.travelbuddy.activity.PlacesSubTypeActivity;
import com.hari.aund.travelbuddy.data.PlacesCategory;
import com.hari.aund.travelbuddy.data.PlacesCategoryValues;
import com.hari.aund.travelbuddy.utils.Utility;

import java.util.ArrayList;

/**
 * Created by Hari Nivas Kumar R P on 8/15/2016.
 */
public class PlacesCategoryAdapter extends RecyclerView.Adapter<PlacesCategoryAdapter.ViewHolder> {

    private static final String LOG_TAG = PlacesCategoryAdapter.class.getSimpleName();

    private Context mContext;
    private ArrayList<PlacesCategory> mPlacesCategoryList;
    private Double mLatitude;
    private Double mLongitude;

    public PlacesCategoryAdapter(Context context) {
        mContext = context;
        setPlacesCategoryList();
    }

    public PlacesCategoryAdapter(Context context, Double latitude, Double longitude) {
        mContext = context;
        mLatitude = latitude;
        mLongitude = longitude;
        setPlacesCategoryList();
    }

    @Override
    public PlacesCategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parentViewGroup, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View newView = layoutInflater.inflate(R.layout.recycler_view_places, parentViewGroup, false);
        final ViewHolder viewHolder = new ViewHolder(newView);

        newView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View onClickView) {
                PlacesCategory placesCategory = getPlacesCategoryList().get(viewHolder.getAdapterPosition());

                placesCategory.setLatitude(getLatitude());
                placesCategory.setLongitude(getLongitude());

                Intent placesCategoryIntent = new Intent(mContext, PlacesSubTypeActivity.class);
                placesCategoryIntent.putExtra(Utility.KEY_PLACE_CATEGORY_INFO, placesCategory);
                mContext.startActivity(placesCategoryIntent);
                ((Activity)mContext).overridePendingTransition(
                        R.animator.activity_open_translate, R.animator.activity_close_translate);
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PlacesCategoryAdapter.ViewHolder viewHolder, int position) {
        if (mPlacesCategoryList.isEmpty()) {
            return;
        }

        PlacesCategory placesCategory = mPlacesCategoryList.get(position);

        //TODO: Consider Using it for Tablets
        //viewHolder.categoryName.setText(placesCategory.getCategoryName());
        viewHolder.categoryImage.setImageResource(placesCategory.getImageResourceId());
    }

    @Override
    public int getItemCount() {
        return mPlacesCategoryList.size();
    }

    public ArrayList<PlacesCategory> getPlacesCategoryList() {
        return mPlacesCategoryList;
    }

    private void setPlacesCategoryList() {
        mPlacesCategoryList = new ArrayList<>(PlacesCategoryValues.MAX_PLACES_CATEGORIES);
        for (int index = 0; index < PlacesCategoryValues.MAX_PLACES_CATEGORIES; index++) {
            mPlacesCategoryList.add(new PlacesCategory(index));
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        //TextView categoryName; /* TODO: Consider Using it for Tablets */
        ImageView categoryImage;

        public ViewHolder(View itemView) {
            super(itemView);
            //TODO: Consider Using it for Tablets
            //categoryName = (TextView) itemView.findViewById(R.id.category_name);
            categoryImage = (ImageView) itemView.findViewById(R.id.category_image);
        }
    }

    public Double getLatitude() {
        return mLatitude;
    }

    private void setLatitude(Double latitude) {
        mLatitude = latitude;
    }

    public void setLatitude(String latitude) {
        setLatitude(Double.parseDouble(latitude));
    }

    public Double getLongitude() {
        return mLongitude;
    }

    private void setLongitude(Double longitude) {
        mLongitude = longitude;
    }

    public void setLongitude(String longitude) {
        setLongitude(Double.parseDouble(longitude));
    }
}
