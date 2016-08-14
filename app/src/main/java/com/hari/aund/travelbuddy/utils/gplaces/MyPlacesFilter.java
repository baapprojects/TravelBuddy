package com.hari.aund.travelbuddy.utils.gplaces;

import android.util.Log;
import android.widget.Filter;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.Places;
import com.hari.aund.travelbuddy.adapter.PlaceAutoCompleteAdapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Hari Nivas Kumar R P on 8/14/2016.
 */
public class MyPlacesFilter extends Filter {

    private static final String LOG_TAG = MyPlacesFilter.class.getSimpleName();

    private static final int DEFAULT_WAITING_TIME = 60;//seconds

    private PlaceAutoCompleteAdapter mPlaceAutoCompleteAdapter;

    public MyPlacesFilter(PlaceAutoCompleteAdapter placeAutoCompleteAdapter){
        mPlaceAutoCompleteAdapter = placeAutoCompleteAdapter;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {

        FilterResults results = new FilterResults();
        // Skip the autocomplete query if no constraints are given.
        if (constraint != null) {

            // Query the autocomplete API for the (constraint) search string.
            mPlaceAutoCompleteAdapter.setNewPlacesList(getAutocomplete(constraint));

            if (mPlaceAutoCompleteAdapter.getNewPlacesList() != null) {
                // The API successfully returned results.
                results.values = mPlaceAutoCompleteAdapter.getNewPlacesList();
                results.count = mPlaceAutoCompleteAdapter.getNewPlacesList().size();
            }
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint,
                                  FilterResults filterResults) {

        if (filterResults != null && filterResults.count > 0) {
            // The API returned at least one result, update the data.
            mPlaceAutoCompleteAdapter.notifyDataSetChanged();
        } else {
            // The API did not return any filterResults, invalidate the data set.
            mPlaceAutoCompleteAdapter.notifyDataSetInvalidated();
        }
    }

    private ArrayList<PlaceAutoComplete> getAutocomplete(CharSequence constraint) {

        if (mPlaceAutoCompleteAdapter.getGoogleApiClient() != null &&
                mPlaceAutoCompleteAdapter.getGoogleApiClient().isConnected()) {

            Log.d(LOG_TAG, "AutoComplete Query - Start");
            Log.d(LOG_TAG, "CharSequence - Query for: " + constraint);

            // Submit the query to the autocomplete API and retrieve a PendingResult that will
            // contain the results when the query completes.
            PendingResult<AutocompletePredictionBuffer> results =
                    Places.GeoDataApi.getAutocompletePredictions(
                            mPlaceAutoCompleteAdapter.getGoogleApiClient(),
                            constraint.toString(),
                            mPlaceAutoCompleteAdapter.getBounds(),
                            mPlaceAutoCompleteAdapter.getPlaceFilter());

            // This method should have been called off the main UI thread. Block and wait for at most 60s
            // for a result from the API.
            AutocompletePredictionBuffer buffer =
                    results.await(DEFAULT_WAITING_TIME, TimeUnit.SECONDS);

            // Confirm that the query completed successfully, otherwise return null
            Status status = buffer.getStatus();
            if (!status.isSuccess()) {
                Log.e(LOG_TAG, "Status : " + status.toString());
                Log.e(LOG_TAG, "AutoComplete Query Failed - End");

                // Release the buffer now that all data has been copied.
                buffer.release();
                return null;
            }

            Log.d(LOG_TAG, "AutoComplete Query Completed - End");
            return readResults(buffer);
        }
        else {
            Log.e(LOG_TAG, "AutoComplete Query - Start : Failed.");
            Log.e(LOG_TAG, "Google API client is not connected!");
            return null;
        }
    }

    private ArrayList<PlaceAutoComplete> readResults(AutocompletePredictionBuffer buffer){

        Log.d(LOG_TAG, "Received " + buffer.getCount() + " predictions.!");

        // Copy the results into our own data structure, because we can't hold onto the buffer.
        // AutocompletePrediction objects encapsulate the API response (place ID and mDescription).

        Iterator<AutocompletePrediction> iterator = buffer.iterator();
        ArrayList<PlaceAutoComplete> resultList = new ArrayList<>(buffer.getCount());
        while (iterator.hasNext()) {
            AutocompletePrediction prediction = iterator.next();

            // Get the details of this prediction and copy it into a new PlaceAutoComplete object.
            resultList.add(new PlaceAutoComplete(
                    prediction.getPlaceId(),
                    prediction.getFullText(null)));

            Log.d(LOG_TAG, "PlaceId - " + prediction.getPlaceId());
            Log.d(LOG_TAG, "PlaceFullText - " + prediction.getFullText(null));
            List<Integer> placeTypeIntegerList = prediction.getPlaceTypes();
            if (placeTypeIntegerList != null && !placeTypeIntegerList.isEmpty()) {
                for (int index = 0; index < placeTypeIntegerList.size(); index++){
                    Log.d(LOG_TAG, "PlaceType[" + index +"] - " + placeTypeIntegerList.get(index));
                }
            }
            Log.d(LOG_TAG, "PlacePrimaryText - " + prediction.getPrimaryText(null));
            Log.d(LOG_TAG, "PlaceSecondaryText - " + prediction.getSecondaryText(null));
            Log.d(LOG_TAG, "DataValid - " + prediction.isDataValid());
        }

        // Release the buffer now that all data has been copied.
        buffer.release();

        return resultList;
    }
}
