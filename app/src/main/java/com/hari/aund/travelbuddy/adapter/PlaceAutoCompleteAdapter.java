package com.hari.aund.travelbuddy.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.maps.model.LatLngBounds;
import com.hari.aund.travelbuddy.utils.gplaces.MyPlacesFilter;
import com.hari.aund.travelbuddy.utils.gplaces.PlaceAutoComplete;

import java.util.ArrayList;

/**
 * Created by Hari Nivas Kumar R P on 8/13/2016.
 */
public class PlaceAutoCompleteAdapter extends ArrayAdapter<PlaceAutoComplete>
        implements Filterable {

    private static final String LOG_TAG = PlaceAutoCompleteAdapter.class.getSimpleName();

    private ArrayList<PlaceAutoComplete> mNewPlacesList;
    private LatLngBounds mBounds;
    private AutocompleteFilter mPlaceFilter;
    private GoogleApiClient mGoogleApiClient;

    public PlaceAutoCompleteAdapter(Context context,
                                    int resource,
                                    GoogleApiClient googleApiClient,
                                    LatLngBounds bounds,
                                    AutocompleteFilter filter) {
        super(context, resource);
        mGoogleApiClient = googleApiClient;
        mBounds = bounds;
        mPlaceFilter = filter;
    }

    @Override
    public int getCount() {
        return getNewPlacesList().size();
    }

    @Override
    public PlaceAutoComplete getItem(int position) {
        return getNewPlacesList().get(position);
    }

    @Override
    public Filter getFilter() {
        return new MyPlacesFilter(this);
    }

    public ArrayList<PlaceAutoComplete> getNewPlacesList(){
        return mNewPlacesList;
    }

    public void setNewPlacesList(ArrayList<PlaceAutoComplete> newPlacesList){
        this.mNewPlacesList = newPlacesList;
    }

    public LatLngBounds getBounds() {
        return mBounds;
    }

    public AutocompleteFilter getPlaceFilter() {
        return mPlaceFilter;
    }

    public GoogleApiClient getGoogleApiClient() {
        return mGoogleApiClient;
    }
}
