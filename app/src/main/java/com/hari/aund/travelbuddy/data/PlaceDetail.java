package com.hari.aund.travelbuddy.data;

import java.util.ArrayList;

/**
 * Created by Hari Nivas Kumar R P on 8/17/2016.
 */
public class PlaceDetail{

    private String mId;
    private String mName;
    private String mVicinity;
    private String mWebsite;
    private String mPhoneNumber;
    private String mAddress;
    private Double mRating;
    private double mLatitude;
    private double mLongitude;
    private ArrayList<String> mPhotoReference;
    private ArrayList<String> mTimetable;
    private ArrayList<ReviewDetail> mReviewDetails;

    public PlaceDetail(String id) {
        mId = id;
        mPhotoReference = new ArrayList<>();
        mTimetable = new ArrayList<>();
        mReviewDetails = new ArrayList<>();
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

    public double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(double latitude) {
        mLatitude = latitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(double longitude) {
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

    public ArrayList<ReviewDetail> getReviewDetails() {
        return mReviewDetails;
    }

    public void setReviewDetails(ArrayList<ReviewDetail> reviewDetails) {
        mReviewDetails = reviewDetails;
    }

    public ReviewDetail getReviewEntry(int position){
        return getReviewDetails().get(position);
    }

    public void addReviewEntry(ReviewDetail reviewDetail){
        getReviewDetails().add(reviewDetail);
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
        return mRating != null;
    }
    
    public boolean hasPhotoReference(){
        return !getPhotoReference().isEmpty();
    }
    
    public boolean hasTimeTable(){
        return !getTimetable().isEmpty();
    }

    public boolean hasReviews(){
        return !getReviewDetails().isEmpty();
    }
}
