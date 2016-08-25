package com.hari.aund.travelbuddy.data.provider;

import android.net.Uri;

/**
 * Created by Hari Nivas Kumar R P on 8/23/2016.
 */
public class Places {

    public static final String AUTHORITY =
            "com.hari.aund.data.provider.PlaceProvider";
    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    interface Path {
        String PLACES = "place";
        String CATEGORY = "category";
        String SUB_TYPE = "subtype";
    }

    public static Uri buildUri(String... paths) {
        Uri.Builder builder = BASE_CONTENT_URI.buildUpon();
        for (String path : paths) {
            builder.appendPath(path);
        }
        return builder.build();
    }

    public static final Uri CONTENT_URI_PLACES = buildUri(Path.PLACES);

    public static Uri withPlaceId(String placeId) {
        return CONTENT_URI_PLACES.buildUpon().appendPath(placeId).build();
    }

    public static final Uri CONTENT_URI_CATEGORY = buildUri(Path.CATEGORY);

    public static Uri withCategoryId(String categoryId) {
        return CONTENT_URI_CATEGORY.buildUpon().appendPath(categoryId).build();
    }

    public static final Uri CONTENT_URI_SUB_TYPE = buildUri(Path.SUB_TYPE);

    public static Uri withSubTypeName(String subTypeName) {
        String queryStr = subTypeName.replaceAll(" ","_").toLowerCase();
        return CONTENT_URI_SUB_TYPE.buildUpon().appendPath(queryStr).build();
    }
}
