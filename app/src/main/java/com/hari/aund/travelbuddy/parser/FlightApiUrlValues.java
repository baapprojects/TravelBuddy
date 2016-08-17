package com.hari.aund.travelbuddy.parser;

/**
 * Created by Hari Nivas Kumar R P on 8/17/2016.
 */
public interface FlightApiUrlValues {

    String FLIGHT_QUERY_START = "?";

    String FLIGHT_API_BASE_URL = "http://free.rome2rio.com/api/1.2/";

    String FLIGHT_API_RES_TYPE = "json/";
    String FLIGHT_API_REQ_TYPE_SEARCH = "Search" + FLIGHT_QUERY_START;

    String FLIGHT_URL_PREFIX_KEY = "key=";
    String FLIGHT_URL_PREFIX_ORIGIN = "&oName=";
    String FLIGHT_URL_PREFIX_DEST = "&dName=";
    String FLIGHT_URL_PREFIX_FLAGS = "&flags=";

    String FLIGHT_URL_SUFFIX_FLAGS_DEF = "0x000FFFF0";

    String TAG_ROUTES = "routes";
    String TAG_SEGMENTS = "segments";
    String TAG_ITINERARIES = "itineraries";
    String TAG_LEGS = "legs";
    String TAG_INDICATIVE_PRICE = "indicativePrice";
    String TAG_PRICE = "price";
    String TAG_HOPS = "hops";
    String TAG_S_TIME = "sTime";
    String TAG_T_TIME = "tTIme";
    String TAG_FLIGHT = "flight";
    String TAG_AIRLINE = "airline";
    String TAG_S_TERMINAL = "sTerminal";
    String TAG_DURATION = "duration";

}
