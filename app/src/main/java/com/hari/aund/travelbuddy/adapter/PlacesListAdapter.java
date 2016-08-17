package com.hari.aund.travelbuddy.adapter;

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
import com.hari.aund.travelbuddy.activity.MainActivity;
import com.hari.aund.travelbuddy.data.PlacesListInfo;
import com.hari.aund.travelbuddy.parser.PlacesApiParser;
import com.hari.aund.travelbuddy.utils.Utility;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Hari Nivas Kumar R P on 8/16/2016.
 */
public class PlacesListAdapter extends RecyclerView.Adapter<PlacesListAdapter.ViewHolder> {

    private static final String LOG_TAG = PlacesListAdapter.class.getSimpleName();

    private int mCategoryId;
    private String mCategoryName;
    private Context mContext;
    private ArrayList<PlacesListInfo> mPlacesListInfoArrayList;

    public PlacesListAdapter(Context context,
                             ArrayList<PlacesListInfo> placesListInfoArrayList,
                             int categoryId,
                             String categoryName) {
        mContext = context;
        mPlacesListInfoArrayList = placesListInfoArrayList;
        mCategoryId = categoryId;
        mCategoryName = categoryName;
    }

    @Override
    public PlacesListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.recycler_view_places_sub_type, viewGroup, false);
        final ViewHolder viewHolder = new ViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View onClickView) {
                String placeId = mPlacesListInfoArrayList
                        .get(viewHolder.getAdapterPosition())
                        .getPlaceId();

                // TODO: Change appropriate class
                //Intent intent = new Intent(mContext, PlaceDetailActivity.class);
                Intent intent = new Intent(mContext, MainActivity.class);
                intent.putExtra(Utility.KEY_PLACE_ID, placeId);
                intent.putExtra(Utility.KEY_CATEGORY_ID, mCategoryId);
                intent.putExtra(Utility.KEY_CATEGORY_NAME, mCategoryName);
                Log.d(LOG_TAG, "onCreateViewHolder : Place Id - " + placeId);
                mContext.startActivity(intent);
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final PlacesListAdapter.ViewHolder viewHolder, int position) {
        PlacesListInfo placesListInfo = mPlacesListInfoArrayList.get(position);

        if (placesListInfo.isPhotoReferenceAvailable()) {
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

        Typeface typefaceName = Typeface.createFromAsset(mContext.getAssets(), "Rosario-Bold.ttf");
        Typeface typefaceAddress = Typeface.createFromAsset(mContext.getAssets(), "Rosario-Regular.ttf");

        viewHolder.place_id.setText(placesListInfo.getPlaceId());
        viewHolder.place_name.setText(placesListInfo.getPlaceName());
        viewHolder.place_name.setTypeface(typefaceName);
        viewHolder.place_address.setText(placesListInfo.getPlaceAddress());
        viewHolder.place_address.setTypeface(typefaceAddress);

        if (placesListInfo.getPlaceRating() != null) {
            viewHolder.rating.setRating(Float.parseFloat(String.valueOf(placesListInfo.getPlaceRating())));
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
}