package com.hari.aund.travelbuddy;

/**
 * Created by Hari Nivas Kumar R P on 8/14/2016.
 */
public class PlaceAutoComplete {
    public CharSequence mPlaceId;
    public CharSequence mDescription;

    PlaceAutoComplete(CharSequence placeId, CharSequence description) {
        this.mPlaceId = placeId;
        this.mDescription = description;
    }

    @Override
    public String toString() {
        return getPlaceDescription();
    }

    private CharSequence getPlaceIdSeq(){
        return mPlaceId;
    }

    public String getPlaceId(){
        return mPlaceId.toString();
    }

    private CharSequence getPlaceDescriptionSeq(){
        return mDescription;
    }

    public String getPlaceDescription(){
        return mDescription.toString();
    }
}