package com.hari.aund.travelbuddy.parser;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.hari.aund.travelbuddy.activity.FlightDetailActivity;
import com.hari.aund.travelbuddy.app.TBConfig;
import com.hari.aund.travelbuddy.app.TravelBuddyApp;
import com.hari.aund.travelbuddy.data.FlightDetail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Hari Nivas Kumar R P on 8/17/2016.
 */
public class FlightApiParser implements FlightApiUrlValues {

    private static final String LOG_TAG = FlightApiParser.class.getSimpleName();

    private FlightDetailActivity mFlightDetailActivity;

    public FlightApiParser(FlightDetailActivity flightDetailActivity) {
        mFlightDetailActivity = flightDetailActivity;
    }

    private String getFlightDetailUrl(String fromSourcePlace, String toDestinationPlace) {
        return FLIGHT_API_BASE_URL + FLIGHT_API_RES_TYPE +
                FLIGHT_API_REQ_TYPE_SEARCH +
                FLIGHT_URL_PREFIX_KEY + TBConfig.getRome2RioApiKey()+
                FLIGHT_URL_PREFIX_ORIGIN + fromSourcePlace +
                FLIGHT_URL_PREFIX_DEST + toDestinationPlace +
                FLIGHT_URL_PREFIX_FLAGS + FLIGHT_URL_SUFFIX_FLAGS_DEF;
    }

    private void parseFlightDetails(JSONObject jsonObjectIn) {
        try {
            FlightDetail flightDetail = new FlightDetail();

            JSONArray routesJSONArray =
                    jsonObjectIn.getJSONArray(TAG_ROUTES);

            for (int routeIndex = 0;
                 routeIndex < routesJSONArray.length();
                 routeIndex++) {

                JSONObject routeJSONObject =
                        routesJSONArray.getJSONObject(routeIndex);
                JSONArray segmentsJSONArray =
                        routeJSONObject.getJSONArray(TAG_SEGMENTS);

                for (int segmentIndex = 0;
                     segmentIndex < segmentsJSONArray.length();
                     segmentIndex++) {

                    JSONObject segmentJSONObject =
                            segmentsJSONArray.getJSONObject(segmentIndex);
                    JSONArray itinerariesJSONArray =
                            segmentJSONObject.getJSONArray(TAG_ITINERARIES);

                    for (int itinerariesIndex = 0;
                         itinerariesIndex < itinerariesJSONArray.length();
                         itinerariesIndex++) {

                        JSONObject itinerariesJSONObject =
                                itinerariesJSONArray.getJSONObject(itinerariesIndex);
                        JSONArray legsJSONArray =
                                itinerariesJSONObject.getJSONArray(TAG_LEGS);

                        for (int legsIndex = 0;
                             legsIndex < legsJSONArray.length();
                             legsIndex++) {

                            JSONObject legsJSONObject =
                                    legsJSONArray.getJSONObject(legsIndex);
                            JSONObject indicativePricesJSONObject =
                                    legsJSONObject.getJSONObject(TAG_INDICATIVE_PRICE);

                            flightDetail.setPrice(indicativePricesJSONObject.getDouble(TAG_PRICE));

                            JSONArray hopsJSONArray = legsJSONObject.getJSONArray(TAG_HOPS);
                            JSONObject hopsJSONObject = hopsJSONArray.getJSONObject(0);

                            flightDetail.setsTime(hopsJSONObject.getString(TAG_S_TIME));
                            flightDetail.settTime(hopsJSONObject.getString(TAG_T_TIME));
                            flightDetail.setFlight(hopsJSONObject.getString(TAG_FLIGHT));
                            flightDetail.setAirline(hopsJSONObject.getString(TAG_AIRLINE));
                            if (hopsJSONObject.has(TAG_S_TERMINAL)) {
                                flightDetail.setTerminal(hopsJSONObject.getString(TAG_S_TERMINAL));
                            } else if (hopsJSONObject.has(TAG_S_TERMINAL)) {
                                flightDetail.setTerminal(hopsJSONObject.getString(TAG_T_TERMINAL));
                            }
                            flightDetail.setDuration(hopsJSONObject.getDouble(TAG_DURATION));

                            mFlightDetailActivity.getFlightDetailList().add(flightDetail);
                        }
                    }
                }
            }
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }

        mFlightDetailActivity.getFlightListAdapter().notifyDataSetChanged();
    }

    public void getFlightDetails() {
        String flightDetailReqUrl = getFlightDetailUrl(
                mFlightDetailActivity.getFromSourceCity(),
                mFlightDetailActivity.getToDestinationCity());
        Log.d(LOG_TAG, "getFlightDetails : URL - " + flightDetailReqUrl);

        JsonObjectRequest placesJsonObjReq =
                new JsonObjectRequest(
                        flightDetailReqUrl,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject jsonObjectIn) {
                                mFlightDetailActivity.getProgressWheel().stopSpinning();
                                //Log.d(LOG_TAG, "getFlightDetails : jsonObjectIn - " + jsonObjectIn);
                                parseFlightDetails(jsonObjectIn);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                Log.e(LOG_TAG, "Volley : onErrorResponse - " +
                                        volleyError.getMessage());
                            }
                        }
                );

        TravelBuddyApp.getInstance()
                .addToRequestQueue(placesJsonObjReq);
    }
}
