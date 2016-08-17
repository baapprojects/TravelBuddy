package com.hari.aund.travelbuddy.data;

import java.util.ArrayList;

/**
 * Created by Hari Nivas Kumar R P on 8/17/2016.
 */
public class PlaceDetail {

    private String mId;
    private String mName;
    private String mVicinity;
    private String mWebsite;
    private String mPhoneNumber;
    private String mAddress;
    private Double mRating;
    private Double mLatitude;
    private Double mLongitude;
    private ArrayList<String> mPhotoReference;
    private ArrayList<String> mTimetable;

    public PlaceDetail(String id) {
        mId = id;
        mPhotoReference = new ArrayList<>();
        mTimetable = new ArrayList<>();
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getVicinity() {
        return mVicinity;
    }

    public void setVicinity(String vicinity) {
        mVicinity = vicinity;
    }

    public String getWebsite() {
        return mWebsite;
    }

    public void setWebsite(String website) {
        mWebsite = website;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        mPhoneNumber = phoneNumber;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public Double getRating() {
        return mRating;
    }

    public void setRating(Double rating) {
        mRating = rating;
    }

    public Double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(Double latitude) {
        mLatitude = latitude;
    }

    public Double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(Double longitude) {
        mLongitude = longitude;
    }

    public ArrayList<String> getPhotoReference() {
        return mPhotoReference;
    }

    public void setPhotoReference(ArrayList<String> photoReference) {
        mPhotoReference = photoReference;
    }

    public String getPhotoRef(int position){
        return getPhotoReference().get(position);
    }

    public void addPhotoRef(String photoRef){
        getPhotoReference().add(photoRef);
    }

    public ArrayList<String> getTimetable() {
        return mTimetable;
    }

    public void setTimetable(ArrayList<String> timetable) {
        mTimetable = timetable;
    }

    public String getTimeEntry(int position){
        return getTimetable().get(position);
    }

    public void addTimeEntry(String timeEntry){
        getTimetable().add(timeEntry);
    }

    public boolean hasVicinity(){
        return getVicinity() != null;
    }

    public boolean hasWebsite(){
        return getWebsite() != null;
    }

    public boolean hasPhoneNumber(){
        return getPhoneNumber() != null;
    }

    public boolean hasAddress(){
        return getAddress() != null;
    }

    public boolean hasRating(){
        return getRating() != 0;
    }
    
    public boolean hasPhotoReference(){
        return !getPhotoReference().isEmpty();
    }
    
    public boolean hasTimeTable(){
        return !getTimetable().isEmpty();
    }
}