package com.hari.aund.travelbuddy.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hari.aund.travelbuddy.R;
import com.hari.aund.travelbuddy.data.FlightDetail;

import java.util.ArrayList;

/**
 * Created by Hari Nivas Kumar R P on 8/17/2016.
 */
public class FlightListAdapter extends RecyclerView.Adapter<FlightListAdapter.ViewHolder> {

    private static final String LOG_TAG = FlightListAdapter.class.getSimpleName();

    private Context mContext;
    private ArrayList<FlightDetail> mFlightDetailsArrayList;

    public FlightListAdapter(Context context,
                             ArrayList<FlightDetail> flightDetailsArrayList) {
        mContext = context;
        mFlightDetailsArrayList = flightDetailsArrayList;
    }

    @Override
    public FlightListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.recycler_view_places_sub_type, viewGroup, false);
        final ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final FlightListAdapter.ViewHolder viewHolder, int position) {
        FlightDetail flightDetail = mFlightDetailsArrayList.get(position);

        Typeface typefaceName = Typeface.createFromAsset(mContext.getAssets(), "Rosario-Bold.ttf");
        Typeface typefaceAddress = Typeface.createFromAsset(mContext.getAssets(), "Rosario-Regular.ttf");
    }

    @Override
    public int getItemCount() {
        return mFlightDetailsArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
