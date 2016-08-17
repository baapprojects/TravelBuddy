package com.hari.aund.travelbuddy.utils;

import com.hari.aund.travelbuddy.data.PlacesCategoryValues;

/**
 * Created by Hari Nivas Kumar R P on 8/17/2016.
 */
public interface DefaultValues {

    String DEFAULT_PLACE_ID = "ChIJd7gN4_CECDsRZ7QW-3bwXco";
    String DEFAULT_PLACE_NAME = "Alappuzha, Kerala, India";

    int DEFAULT_CATEGORY_ID = PlacesCategoryValues.PLACES_CATEGORY_TEMPLE_ID;
    String DEFAULT_CATEGORY_NAME = PlacesCategoryValues.placesCategories[DEFAULT_CATEGORY_ID];

    int DEFAULT_SUB_TYPE_ID = 1;
    String DEFAULT_SUB_TYPE_NAME = PlacesCategoryValues.templeSubTypes[DEFAULT_SUB_TYPE_ID];

    Double DEFAULT_LATITUDE = 9.4126239;
    Double DEFAULT_LONGITUDE = 76.4100267;

}
