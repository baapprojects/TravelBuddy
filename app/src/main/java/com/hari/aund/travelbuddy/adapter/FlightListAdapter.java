package com.hari.aund.travelbuddy.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
        View view = layoutInflater.inflate(R.layout.recycler_view_flight_detail, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final FlightListAdapter.ViewHolder viewHolder, int position) {
        FlightDetail flightDetail = mFlightDetailsArrayList.get(position);

        Drawable flightIcon = mContext.getResources().getDrawable(flightDetail.getFlightIcon());
        viewHolder.flightIcon.setImageDrawable(flightIcon);
        viewHolder.flightName.setText(flightDetail.getFlight());
        viewHolder.airlineName.setText(flightDetail.getAirline());
        viewHolder.ticketPrice.setText(flightDetail.getPriceValue());
        viewHolder.startTime.setText(flightDetail.getsTime());
        viewHolder.endTime.setText(flightDetail.gettTime());
        viewHolder.terminalNumber.setText(flightDetail.getTerminalFullStr());
    }

    @Override
    public int getItemCount() {
        return mFlightDetailsArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView airlineName, flightName, ticketPrice, startTime, endTime, terminalNumber;
        ImageView flightIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            flightIcon = (ImageView) itemView.findViewById(R.id.flight_icon);
            airlineName = (TextView) itemView.findViewById(R.id.airline);
            flightName = (TextView) itemView.findViewById(R.id.flight);
            ticketPrice = (TextView) itemView.findViewById(R.id.price);
            startTime = (TextView) itemView.findViewById(R.id.sTime);
            endTime = (TextView) itemView.findViewById(R.id.tTime);
            terminalNumber = (TextView) itemView.findViewById(R.id.terminal);
        }
    }
}
