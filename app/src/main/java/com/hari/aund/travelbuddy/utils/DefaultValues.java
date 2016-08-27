package com.hari.aund.travelbuddy.utils;

import com.hari.aund.travelbuddy.data.PlacesCategoryValues;

/**
 * Created by Hari Nivas Kumar R P on 8/17/2016.
 */
public interface DefaultValues {

    String DEFAULT_PLACE_ID = "ChIJd7gN4_CECDsRZ7QW-3bwXco";
    String DEFAULT_PLACE_NAME = "Alappuzha, Kerala, India";

    int DEFAULT_INVALID_CATEGORY_ID = -1;
    int DEFAULT_CATEGORY_ID = PlacesCategoryValues.PLACES_CATEGORY_TEMPLE_ID;
    String DEFAULT_CATEGORY_NAME = PlacesCategoryValues.placesCategories[DEFAULT_CATEGORY_ID];

    int DEFAULT_INVALID_SUB_TYPE_ID = -1;
    int DEFAULT_SUB_TYPE_ID = 1;
    String DEFAULT_SUB_TYPE_NAME = PlacesCategoryValues.templeSubTypes[DEFAULT_SUB_TYPE_ID];

    Double DEFAULT_LATITUDE = 9.4126239;
    Double DEFAULT_LONGITUDE = 76.4100267;

    String DEFAULT_CATEGORY_SECTION_NAME = "temple";
    String DEFAULT_PLACE_ID_TEMPLE = "ChIJS9LI1WGUpzsRnnBIX4GNem8";
    String DEFAULT_PLACE_NAME_TEMPLE = "Guruvayur Temple";

    int DEFAULT_COLUMN_COUNT_1 = 1;
    int DEFAULT_COLUMN_COUNT_3 = 3;

    String REST_KEY_VALUE = "";
    int DEFAULT_INVALID_SPINNER_ID = -1;

    String DEFAULT_SOURCE_CITY = "Chennai";
    String DEFAULT_DESTINATION_CITY = "Delhi";
}
