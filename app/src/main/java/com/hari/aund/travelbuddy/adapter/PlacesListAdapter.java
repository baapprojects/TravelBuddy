package com.hari.aund.travelbuddy.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.hari.aund.travelbuddy.R;
import com.hari.aund.travelbuddy.activity.MainActivity;
import com.hari.aund.travelbuddy.data.PlacesListInfo;

import java.util.ArrayList;

/**
 * Created by Hari Nivas Kumar R P on 8/16/2016.
 */
public class PlacesListAdapter extends RecyclerView.Adapter<PlacesListAdapter.ViewHolder> {

    private int mCategoryId;
    private Context mContext;
    private ArrayList<PlacesListInfo> mPlacesListInfoArrayList;

    public PlacesListAdapter(Context context,
                             ArrayList<PlacesListInfo> placesListInfoArrayList,
                             int categoryId) {
        mContext = context;
        mPlacesListInfoArrayList = placesListInfoArrayList;
        mCategoryId = categoryId;
    }

    @Override
    public PlacesListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.recycler_view_places_sub_type, viewGroup, false);
        final ViewHolder viewHolder = new ViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View onClickView) {
                String placeId = mPlacesListInfoArrayList.get(
                        viewHolder.getAdapterPosition()).getPlaceId();

                // TODO: Change appropriate class
                //Intent intent = new Intent(mContext, PlaceDetailActivity.class);
                Intent intent = new Intent(mContext, MainActivity.class);
                intent.putExtra("mPlaceId", placeId);
                intent.putExtra("mCategoryId", mCategoryId);
                mContext.startActivity(intent);
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PlacesListAdapter.ViewHolder viewHolder, int position) {
        PlacesListInfo PlacesListInfo = mPlacesListInfoArrayList.get(position);

        Typeface typefaceName = Typeface.createFromAsset(mContext.getAssets(),"Rosario-Bold.ttf");
        Typeface typefaceAddress = Typeface.createFromAsset(mContext.getAssets(),"Rosario-Regular.ttf");

        viewHolder.place_id.setText(PlacesListInfo.getPlaceId());
        viewHolder.place_name.setText(PlacesListInfo.getPlaceName());
        viewHolder.place_name.setTypeface(typefaceName);
        viewHolder.place_address.setText(PlacesListInfo.getPlaceAddress());
        viewHolder.place_address.setTypeface(typefaceAddress);

        if (PlacesListInfo.getPlaceRating() != null) {
            viewHolder.rating.setRating(Float.parseFloat(String.valueOf(PlacesListInfo.getPlaceRating())));
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