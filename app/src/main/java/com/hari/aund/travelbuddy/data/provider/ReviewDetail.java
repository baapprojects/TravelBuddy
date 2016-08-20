package com.hari.aund.travelbuddy.data.provider;

/**
 * Created by Hari Nivas Kumar R P on 8/20/2016.
 */
public class ReviewDetail {
    
    private String mReviewerName;
    private String mRating;
    private String mContent;

    public ReviewDetail() {
    }

    public String getReviewerName() {
        return mReviewerName;
    }

    public void setReviewerName(String reviewerName) {
        mReviewerName = reviewerName;
    }

    public String getRating() {
        return mRating;
    }

    public void setRating(String rating) {
        mRating = rating;
    }

    public float getRatingValue(){
        return Float.parseFloat(getRating());
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }
}
