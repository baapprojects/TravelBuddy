package com.hari.aund.travelbuddy.data;

/**
 * Created by Hari Nivas Kumar R P on 8/16/2016.
 */
public class PlacesListInfo {

    private static final String LOG_TAG = PlacesListInfo.class.getSimpleName();

    private String mPlaceName;
    private String mPlaceId;
    private String mPlaceAddress;
    private String mIconUrl;
    private String mPhotoReference;
    private Double mPlaceRating;

    public PlacesListInfo() {
    }

    public PlacesListInfo(String placeId, String placeName, String placeAddress, String iconUrl) {
        this.mPlaceId = placeId;
        this.mPlaceAddress = placeAddress;
        this.mPlaceName = placeName;
        this.mIconUrl = iconUrl;
    }

    public void setPlaceId(String mPlaceId) {
        this.mPlaceId = mPlaceId;
    }

    public String getPlaceId() {
        return mPlaceId;
    }

    public String getPlaceAddress() {
        return mPlaceAddress;
    }

    public void setPlaceAddress(String mPlaceAddress) {
        this.mPlaceAddress = mPlaceAddress;
    }

    public String getPlaceName() {
        return mPlaceName;
    }

    public void setPlaceName(String mPlaceName) {
        this.mPlaceName = mPlaceName;
    }

    public String getIconUrl() {
        return mIconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.mIconUrl = iconUrl;
    }

    public void setPlaceRating(Double mPlaceRating) {
        this.mPlaceRating = mPlaceRating;
    }

    public Double getPlaceRating() {
        return mPlaceRating;
    }

    public void setPhotoReference(String mPhotoReference) {
        this.mPhotoReference = mPhotoReference;
    }

    public String getPhotoReference() {
        return mPhotoReference;
    }
}
