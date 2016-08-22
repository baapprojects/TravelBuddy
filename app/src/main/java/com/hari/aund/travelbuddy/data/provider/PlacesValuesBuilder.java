package com.hari.aund.travelbuddy.data.provider.generated.values;

import android.content.ContentValues;
import com.hari.aund.travelbuddy.data.provider.PlaceColumns;
import java.lang.String;

public class PlacesValuesBuilder {
  ContentValues values = new ContentValues();

  public PlacesValuesBuilder Id(int value) {
    values.put(PlaceColumns._ID, value);
    return this;
  }

  public PlacesValuesBuilder Id(long value) {
    values.put(PlaceColumns._ID, value);
    return this;
  }

  public PlacesValuesBuilder placeId(String value) {
    values.put(PlaceColumns.PLACE_ID, value);
    return this;
  }

  public PlacesValuesBuilder name(float value) {
    values.put(PlaceColumns.NAME, value);
    return this;
  }

  public PlacesValuesBuilder address(String value) {
    values.put(PlaceColumns.ADDRESS, value);
    return this;
  }

  public PlacesValuesBuilder vicinity(String value) {
    values.put(PlaceColumns.VICINITY, value);
    return this;
  }

  public PlacesValuesBuilder latitude(float value) {
    values.put(PlaceColumns.LATITUDE, value);
    return this;
  }

  public PlacesValuesBuilder longitude(float value) {
    values.put(PlaceColumns.LONGITUDE, value);
    return this;
  }

  public PlacesValuesBuilder photoCoverRef(int value) {
    values.put(PlaceColumns.PHOTO_COVER_REF, value);
    return this;
  }

  public PlacesValuesBuilder photoCoverRef(long value) {
    values.put(PlaceColumns.PHOTO_COVER_REF, value);
    return this;
  }

  public PlacesValuesBuilder phoneNumber(String value) {
    values.put(PlaceColumns.PHONE_NUMBER, value);
    return this;
  }

  public PlacesValuesBuilder website(String value) {
    values.put(PlaceColumns.WEBSITE, value);
    return this;
  }

  public PlacesValuesBuilder rating(float value) {
    values.put(PlaceColumns.RATING, value);
    return this;
  }

  public PlacesValuesBuilder categoryId(int value) {
    values.put(PlaceColumns.CATEGORY_ID, value);
    return this;
  }

  public PlacesValuesBuilder categoryId(long value) {
    values.put(PlaceColumns.CATEGORY_ID, value);
    return this;
  }

  public PlacesValuesBuilder subTypeName(String value) {
    values.put(PlaceColumns.SUB_TYPE_NAME, value);
    return this;
  }

  public ContentValues values() {
    return values;
  }
}
