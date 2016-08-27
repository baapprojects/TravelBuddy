package com.hari.aund.travelbuddy.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.hari.aund.travelbuddy.R;
import com.hari.aund.travelbuddy.activity.PlaceDetailActivity;
import com.hari.aund.travelbuddy.data.PlacesListInfo;
import com.hari.aund.travelbuddy.utils.Utility;

import java.util.ArrayList;

/**
 * Created by Hari Nivas Kumar R P on 8/16/2016.
 */
public class PlacesListAdapter extends RecyclerView.Adapter<PlacesListAdapter.ViewHolder> {

    private static final String LOG_TAG = PlacesListAdapter.class.getSimpleName();
    private static final int SINGLE_CATEGORY_SECTION_INDEX = 0;

    private Context mContext;
    private ArrayList<Integer> mCategoryIdAL;
    private ArrayList<String> mCategoryNameAL;
    private ArrayList<String> mSectionNameAL;
    private ArrayList<PlacesListInfo> mPlacesListInfoArrayList;
    private boolean mSingleCategoryAndSection;

    public PlacesListAdapter(Context context,
                             ArrayList<PlacesListInfo> placesListInfoAL,
                             ArrayList<Integer> categoryIdAL,
                             ArrayList<String> categoryNameAL,
                             ArrayList<String> sectionNameAL) {
        mContext = context;
        mPlacesListInfoArrayList = placesListInfoAL;
        mCategoryIdAL = categoryIdAL;
        mCategoryNameAL = categoryNameAL;
        mSectionNameAL = sectionNameAL;
        mSingleCategoryAndSection = false;
    }

    public PlacesListAdapter(Context context,
                             ArrayList<PlacesListInfo> placesListInfoAL,
                             int categoryId,
                             String categoryName,
                             String sectionName) {
        mContext = context;
        mPlacesListInfoArrayList = placesListInfoAL;

        mCategoryIdAL = new ArrayList<>();
        mCategoryIdAL.add(categoryId);
        mCategoryNameAL = new ArrayList<>();
        mCategoryNameAL.add(categoryName);
        mSectionNameAL = new ArrayList<>();
        mSectionNameAL.add(sectionName);

        mSingleCategoryAndSection = true;
    }

    @Override
    public PlacesListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.recycler_view_places_sub_type, viewGroup, false);
        final ViewHolder viewHolder = new ViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View onClickView) {
                int othersPosition = mSingleCategoryAndSection ?
                        SINGLE_CATEGORY_SECTION_INDEX : viewHolder.getAdapterPosition();
                startPlaceDetailActivity(viewHolder.getAdapterPosition(), othersPosition);
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final PlacesListAdapter.ViewHolder viewHolder, int position) {
        PlacesListInfo placesListInfo = mPlacesListInfoArrayList.get(position);

        //TODO : to be removed later
        /*
        if (placesListInfo.isPhotoReferenceAvailable() &&
                !Utility.isNetworkAvailable(mContext)) {
            //Log.d(LOG_TAG, "ImageUrl - " + new PlacesApiParser().getPhotoUrl(placesListInfo.getPhotoReference()));
            Picasso.with(mContext)
                    .load(new PlacesApiParser().getPhotoUrl(placesListInfo.getPhotoReference()))
                    .placeholder(R.drawable.loading_icon)
                    .error(R.drawable.no_image)
                    .into(viewHolder.place_pic,
                            new Callback() {
                                @Override
                                public void onSuccess() {
                                    //Log.d(LOG_TAG, "onSuccess - image loading success!");
                                }
                                @Override
                                public void onError() {
                                    Log.e(LOG_TAG, "onError - image loading failure!");
                                }
                            }
                    );
        }
        */

        // Content Description for Non-text elements
        viewHolder.place_pic.setContentDescription(
                mContext.getResources().getString(R.string.image_for_place));

        Typeface typefaceName = Typeface.createFromAsset(mContext.getAssets(), "Rosario-Bold.ttf");
        Typeface typefaceAddress = Typeface.createFromAsset(mContext.getAssets(), "Rosario-Regular.ttf");

        viewHolder.place_id.setText(placesListInfo.getPlaceId());
        viewHolder.place_name.setText(placesListInfo.getPlaceName());
        viewHolder.place_name.setTypeface(typefaceName);
        viewHolder.place_address.setText(placesListInfo.getPlaceAddress());
        viewHolder.place_address.setTypeface(typefaceAddress);

        if (placesListInfo.getPlaceRating() != null) {
            viewHolder.rating.setRating(Float.parseFloat(String.valueOf(placesListInfo.getPlaceRating())));
            // Content Description for Non-text elements
            viewHolder.rating.setContentDescription(mContext.getString(
                    R.string.place_detail_place_rating_with_value, placesListInfo.getPlaceRating()));
        }
    }

    @Override
    public int getItemCount() {
        return mPlacesListInfoArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView place_id, place_name, place_address;
        ImageView place_pic;
        RatingBar rating;

        public ViewHolder(View itemView) {
            super(itemView);

            place_id = (TextView) itemView.findViewById(R.id.place_id);
            place_name = (TextView) itemView.findViewById(R.id.place_name);
            place_address = (TextView) itemView.findViewById(R.id.place_Address);
            place_pic = (ImageView) itemView.findViewById(R.id.place_pic);
            rating = (RatingBar) itemView.findViewById(R.id.rating);
        }
    }

    public void clearAdapterData() {
        mPlacesListInfoArrayList.clear();
        mCategoryIdAL.clear();
        mCategoryNameAL.clear();
        mSectionNameAL.clear();
    }

    public void updateAdapterData(ArrayList<PlacesListInfo> placesListInfoAL,
                                  ArrayList<Integer> categoryIdAL,
                                  ArrayList<String> categoryNameAL,
                                  ArrayList<String> sectionNameAL) {
        clearAdapterData();
        mPlacesListInfoArrayList = placesListInfoAL;
        mCategoryIdAL = categoryIdAL;
        mCategoryNameAL = categoryNameAL;
        mSectionNameAL = sectionNameAL;
    }

    private void startPlaceDetailActivity(int placesPosition, int othersPosition) {
        if (!Utility.isNetworkAvailable(mContext)){
            Log.d(LOG_TAG, "You are Offline! : startPlaceDetailActivity!");

            //When invoking from Favourites, we should start PlaceDetailActivity.
            if (mSingleCategoryAndSection) return;
        }

        Intent intent = new Intent(mContext, PlaceDetailActivity.class);
        intent.putExtra(Utility.KEY_PLACE_ID,
                mPlacesListInfoArrayList.get(placesPosition).getPlaceId());
        intent.putExtra(Utility.KEY_CATEGORY_ID, mCategoryIdAL.get(othersPosition));
        intent.putExtra(Utility.KEY_CATEGORY_NAME, mCategoryNameAL.get(othersPosition));
        intent.putExtra(Utility.KEY_PLACE_SECTION_NAME, mSectionNameAL.get(othersPosition));
        mContext.startActivity(intent);

        ((Activity) mContext).overridePendingTransition(
                R.animator.activity_open_translate, R.animator.activity_close_translate);
    }
}