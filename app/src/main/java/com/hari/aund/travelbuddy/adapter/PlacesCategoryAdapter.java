package com.hari.aund.travelbuddy.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hari.aund.travelbuddy.R;
import com.hari.aund.travelbuddy.activity.PlacesCategoryActivity;
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
    private LayoutInflater mLayoutInflater;
    private ArrayList<PlacesCategory> mPlacesCategory;

    public PlacesCategoryAdapter(Context context) {
        mContext = context;
        mPlacesCategory = new ArrayList<>(PlacesCategoryValues.MAX_PLACES_CATEGORIES);
        for (int index = 0; index < PlacesCategoryValues.MAX_PLACES_CATEGORIES; index++) {
            mPlacesCategory.add(new PlacesCategory(index));
        }
    }

    @Override
    public PlacesCategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parentViewGroup, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View newView = layoutInflater.inflate(R.layout.recycler_view_places, parentViewGroup, false);
        final ViewHolder viewHolder = new ViewHolder(newView);

        newView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View onClickView) {
                PlacesCategory placesCategory = mPlacesCategory.get(viewHolder.getAdapterPosition());

                Intent placesCategoryIntent = new Intent(mContext, PlacesCategoryActivity.class);
                placesCategoryIntent.putExtra(Utility.KEY_PLACE_CATEGORY_INFO, placesCategory);
                placesCategoryIntent.putExtra(Utility.KEY_PLACE_TYPE_NAME, placesCategory.getCategoryName());
                mContext.startActivity(placesCategoryIntent);
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PlacesCategoryAdapter.ViewHolder viewHolder, int position) {
        PlacesCategory placesCategory = mPlacesCategory.get(position);

        viewHolder.categoryName.setText(placesCategory.getCategoryName());
        viewHolder.categoryImage.setImageResource(placesCategory.getImageResourceId());
    }

    @Override
    public int getItemCount() {
        return mPlacesCategory.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;
        ImageView categoryImage;
        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            categoryName = (TextView) itemView.findViewById(R.id.category_name);
            categoryImage = (ImageView) itemView.findViewById(R.id.category_image);
        }
    }
}
