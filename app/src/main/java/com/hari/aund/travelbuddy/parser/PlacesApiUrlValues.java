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

    String TAG_RESULTS = "results";
    String TAG_STATUS = "status";

    String TAG_RESULT = "result";
    String TAG_GEOMETRY = "geometry";
    String TAG_LOCATION = "location";
    String TAG_LAT = "lat";
    String TAG_LNG = "lng";
    String TAG_ICON = "icon";
    String TAG_NAME = "name";
    String TAG_PLACE_ID = "place_id";
    String TAG_RATING = "rating";
    String TAG_VICINITY = "vicinity";
    String TAG_PHOTOS = "photos";
    String TAG_PHOTOS_REFERENCE = "photo_reference";
    String TAG_OPENING_HOURS = "opening_hours";
    String TAG_FORMATTED_ADDRESS = "formatted_address";
    String TAG_PHONE_NUMBER = "formatted_phone_number";
    String TAG_TIMETABLE = "weekday_text";
    String TAG_USER_RATINGS_TOTAL = "user_ratings_total";
    String TAG_WEBSITE = "website";
    String TAG_REVIEWS = "reviews";
    String TAG_REVIEWER_NAME = "author_name";
    String TAG_REVIEW_CONTENT = "text";
    String TAG_REVIEW_RATING = "rating";


    String STATUS_OK = "OK";
    String STATUS_ZERO_RESULTS = "ZERO_RESULTS";

    int ERROR_CODE_ZERO_RESULTS = 1;
    int ERROR_CODE_NETWORK_FAILURE = 2;
}
