package com.hari.aund.travelbuddy.parser;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.hari.aund.travelbuddy.activity.PlacesCategoryActivity;
import com.hari.aund.travelbuddy.app.TBConfig;
import com.hari.aund.travelbuddy.app.TravelBuddyApp;
import com.hari.aund.travelbuddy.data.PlacesListInfo;
import com.hari.aund.travelbuddy.fragment.PlacesSubTypeFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Hari Nivas Kumar R P on 8/17/2016.
 */
public class PlacesApiParser implements PlacesApiUrlValues {

    private static final String LOG_TAG = PlacesApiParser.class.getSimpleName();

    private PlacesCategoryActivity mPlacesCategoryActivity;
    private PlacesSubTypeFragment mPlacesSubTypeFragment;

    public PlacesApiParser(){

    }

    public PlacesApiParser(PlacesCategoryActivity placesCategoryActivity) {
        mPlacesCategoryActivity = placesCategoryActivity;
    }

    public PlacesApiParser(PlacesSubTypeFragment placesSubTypeFragment) {
        mPlacesSubTypeFragment = placesSubTypeFragment;
    }

    //Type: details
    public static String getExplorePlacesDetailsUrl(String placeId) {
        return PLACES_API_BASE_URL +
                PLACES_API_REQ_TYPE_DETAIL +
                PLACES_API_RES_TYPE +
                PLACES_URL_KEY_BASE_PLACE_ID + placeId +
                PLACES_URL_PREFIX_KEY + TBConfig.getPlacesApiKey();
    }

    private void parseExplorePlaceDetails(JSONObject jsonObjectIn) {
        try {
            Log.d(LOG_TAG, "parseExplorePlaceDetails : jsonObjectIn - " + jsonObjectIn);

            JSONObject resultsJsonObject = jsonObjectIn.getJSONObject(TAG_RESULT);
            JSONObject geometryJsonObject = resultsJsonObject.getJSONObject(TAG_GEOMETRY);
            JSONObject locationJsonObject = geometryJsonObject.getJSONObject(TAG_LOCATION);

            mPlacesCategoryActivity.getPlacesCategoryAdapter().setLatitude(
                    locationJsonObject.getString(TAG_LAT));
            mPlacesCategoryActivity.getPlacesCategoryAdapter().setLongitude(
                    locationJsonObject.getString(TAG_LNG));
            mPlacesCategoryActivity.getPlacesCategoryAdapter().notifyDataSetChanged();

            Log.d(LOG_TAG, "PlaceId - " + mPlacesCategoryActivity.getPlaceId());
            Log.d(LOG_TAG, "Place Name - " + mPlacesCategoryActivity.getPlaceName());
            Log.d(LOG_TAG, "Latitude - " +
                    mPlacesCategoryActivity.getPlacesCategoryAdapter().getLatitude());
            Log.d(LOG_TAG, "Longitude - " +
                    mPlacesCategoryActivity.getPlacesCategoryAdapter().getLongitude());
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }
    }

    public void getExplorePlaceDetails() {
        String placesReqUrl = getExplorePlacesDetailsUrl(mPlacesCategoryActivity.getPlaceId());
        Log.d(LOG_TAG, "URL - " + placesReqUrl);

        JsonObjectRequest placesJsonObjReq =
                new JsonObjectRequest(
                        placesReqUrl,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject jsonObjectIn) {
                                //Log.d(LOG_TAG, "getPlaceListDetails : jsonObjectIn - " + jsonObjectIn);
                                parseExplorePlaceDetails(jsonObjectIn);
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

    //Type: nearbysearch
    public static String getPlacesListUrl(String latitudeAndLongitude, String sectionName) {
        return PLACES_API_BASE_URL +
                PLACES_API_REQ_TYPE_NEARBY_SEARCH +
                PLACES_API_RES_TYPE +
                PLACES_URL_KEY_BASE_LOCATION + latitudeAndLongitude +
                PLACES_URL_PREFIX_TYPES + sectionName +
                PLACES_URL_PREFIX_RADIUS + PLACES_URL_SUFFIX_RADIUS_DEF +
                PLACES_URL_PREFIX_RANK_BY + PLACES_URL_SUFFIX_RANK_BY_DEF +
                PLACES_URL_PREFIX_KEY + TBConfig.getPlacesApiKey();
    }

    private void parsePlaceListDetails(JSONObject jsonObjectIn) {
        try {
            //Log.d(LOG_TAG, "parsePlaceListDetails : jsonObjectIn - " + jsonObjectIn);

            JSONArray jsonArray = jsonObjectIn.getJSONArray(TAG_RESULTS);
            for (int index = 0; index < jsonArray.length(); index++) {

                JSONObject jsonObject = jsonArray.getJSONObject(index);
                PlacesListInfo placesListInfo = new PlacesListInfo();

                placesListInfo.setPlaceId(jsonObject.getString(TAG_PLACE_ID));
                placesListInfo.setPlaceName(jsonObject.getString(TAG_NAME));
                placesListInfo.setPlaceAddress(jsonObject.getString(TAG_VICINITY));
                placesListInfo.setIconUrl(jsonObject.getString(TAG_ICON));

                if (jsonObject.has(TAG_RATING)) {
                    placesListInfo.setPlaceRating(jsonObject.getDouble(TAG_RATING));
                }

                if (jsonObject.has(TAG_PHOTOS)) {
                    JSONArray jsonArrayPhotos = jsonObject.getJSONArray(TAG_PHOTOS);
                    Log.d(LOG_TAG, jsonArrayPhotos.toString());

                    JSONObject jsonObjectPhotos = jsonArrayPhotos.getJSONObject(0);
                    placesListInfo.setPhotoReference(
                            jsonObjectPhotos.getString(TAG_PHOTOS_REFERENCE));
                }

                mPlacesSubTypeFragment.getPlacesListInfoArray().add(placesListInfo);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        mPlacesSubTypeFragment.getPlacesListAdapter().notifyDataSetChanged();
    }

    public void getPlaceListDetails() {
        String latitudeAndLongitude =
                mPlacesSubTypeFragment.getLatitude() + "," +
                        mPlacesSubTypeFragment.getLongitude();

        String placesReqUrl = getPlacesListUrl(latitudeAndLongitude,
                mPlacesSubTypeFragment.getSectionName());
        Log.d(LOG_TAG, "Section : " + mPlacesSubTypeFragment.getSectionName());
        Log.d(LOG_TAG, "URL - " + placesReqUrl);

        JsonObjectRequest placeReq =
                new JsonObjectRequest(
                        placesReqUrl,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject jsonObjectIn) {
                                mPlacesSubTypeFragment.getProgressWheel().stopSpinning();
                                //Log.d(LOG_TAG, "getPlaceListDetails : jsonObjectIn - " + jsonObjectIn);
                                parsePlaceListDetails(jsonObjectIn);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                Log.e(LOG_TAG, "VolleyError : " + volleyError.getMessage());
                            }
                        }
                );

        TravelBuddyApp.getInstance().addToRequestQueue(placeReq);
    }

    //Type:photoreference
    public String getPhotoUrl(String photoReference) {
        return PLACES_API_BASE_URL +
                PLACES_API_REQ_TYPE_PHOTO +
                PLACES_URL_KEY_BASE_PHOTO_REFERENCE + photoReference +
                PLACES_URL_PREFIX_MAX_HEIGHT + PLACES_URL_SUFFIX_MAX_HEIGHT_DEF +
                PLACES_URL_PREFIX_MAX_WIDTH + PLACES_URL_SUFFIX_MAX_WIDTH_DEF +
                PLACES_URL_PREFIX_KEY + TBConfig.getPlacesApiKey();
    }
}
