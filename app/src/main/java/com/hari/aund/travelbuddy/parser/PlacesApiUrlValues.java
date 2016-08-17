package com.hari.aund.travelbuddy.parser;

/**
 * Created by Hari Nivas Kumar R P on 8/17/2016.
 */
public interface PlacesApiUrlValues {

    String PLACES_API_BASE_URL =
            "https://maps.googleapis.com/maps/api/place/";

    String PLACES_QUERY_START = "?";

    String PLACES_API_REQ_TYPE_DETAIL = "details";
    String PLACES_API_REQ_TYPE_NEARBY_SEARCH = "nearbysearch";
    String PLACES_API_REQ_TYPE_PHOTO = "photo" + PLACES_QUERY_START;

    String PLACES_API_RES_TYPE = "/json" + PLACES_QUERY_START;

    String PLACES_URL_KEY_BASE_PLACE_ID = "placeid=";
    String PLACES_URL_KEY_BASE_LOCATION = "location=";
    String PLACES_URL_KEY_BASE_PHOTO_REFERENCE = "photoreference=";

    String PLACES_URL_PREFIX_TYPES = "&types=";
    String PLACES_URL_PREFIX_RADIUS = "&radius=";
    String PLACES_URL_PREFIX_RANK_BY = "&rankby=";
    String PLACES_URL_PREFIX_MAX_HEIGHT = "&maxheight=";
    String PLACES_URL_PREFIX_MAX_WIDTH = "&maxwidth=";
    String PLACES_URL_PREFIX_KEY = "&key=";

    String PLACES_URL_SUFFIX_RADIUS_DEF = "30000";
    String PLACES_URL_SUFFIX_RANK_BY_DEF = "prominence";
    String PLACES_URL_SUFFIX_MAX_HEIGHT_DEF = "220";
    String PLACES_URL_SUFFIX_MAX_WIDTH_DEF = "220";

    String TAG_RESULT = "result";
    String TAG_RESULTS = "results";
    String TAG_GEOMETRY = "geometry";
    String TAG_LOCATION = "location";
    String TAG_LATITUDE = "lat";
    String TAG_LONGITUDE = "lng";
    String TAG_ICON = "icon";
    String TAG_NAME = "name";
    String TAG_PLACE_ID = "place_id";
    String TAG_RATING = "rating";
    String TAG_ADDRESS = "vicinity";
    String TAG_PHOTOS = "photos";
    String TAG_PHOTOS_REFERENCE = "photo_reference";

}
