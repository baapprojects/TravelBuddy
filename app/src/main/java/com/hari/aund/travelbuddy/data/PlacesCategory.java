package com.hari.aund.travelbuddy.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Hari Nivas Kumar R P on 8/15/2016.
 */
public class PlacesCategory implements Parcelable{

    private static final byte SUB_TYPE_LIST_INVALID_REF = 0x00;
    private static final byte SUB_TYPE_LIST_VALID_REF = 0x01;

    private int mCategoryId;
    private String mCategoryName;
    private ArrayList<String> mSubTypeList = null;
    private int mCategoryActivityId = 0;
    private Class mCategoryActivityClass = null;
    private int mImageResourceId = 0;

    public PlacesCategory(int categoryId) {
        setCategoryId(categoryId);
        setCategoryName(PlacesCategoryValues.placesCategories[categoryId]);

        setSubTypeList(PlacesCategoryValues.categoriesSubTypeCount[categoryId]);

        for (int index = 0; index < PlacesCategoryValues.categoriesSubTypeCount[categoryId]; index++){
            getSubTypeList().add(PlacesCategoryValues.getSubType(categoryId, index));
        }

        setCategoryActivityId(PlacesCategoryValues
                .getCategoryActivityId(PlacesCategoryValues
                        .categoriesSubTypeCount[categoryId]));

        setCategoryActivityClass(PlacesCategoryValues
                .getCategoryActivityClass(getCategoryActivityId()));

        setImageResourceId(PlacesCategoryValues
                .getCategoryImageResourceId(categoryId));
    }

    protected PlacesCategory(Parcel in) {
        setCategoryId(in.readInt());
        setCategoryName(in.readString());
        if (in.readByte() == SUB_TYPE_LIST_VALID_REF) {
            mSubTypeList = new ArrayList<String>();
            in.readList(mSubTypeList, String.class.getClassLoader());
        } else {
            mSubTypeList = null;
        }
        setCategoryActivityId(in.readInt());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getCategoryId());
        dest.writeString(getCategoryName());
        if (mSubTypeList == null) {
            dest.writeByte((byte) (SUB_TYPE_LIST_INVALID_REF));
        } else {
            dest.writeByte((byte) (SUB_TYPE_LIST_VALID_REF));
            dest.writeList(getSubTypeList());
        }
        dest.writeInt(getCategoryActivityId());
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<PlacesCategory> CREATOR =
            new Parcelable.Creator<PlacesCategory>() {
        @Override
        public PlacesCategory createFromParcel(Parcel in) {
            return new PlacesCategory(in);
        }

        @Override
        public PlacesCategory[] newArray(int size) {
            return new PlacesCategory[size];
        }
    };

    public int getCategoryId() {
        return mCategoryId;
    }

    private void setCategoryId(int mCategoryId) {
        this.mCategoryId = mCategoryId;
    }

    public String getCategoryName() {
        return mCategoryName;
    }

    private void setCategoryName(String mCategoryName) {
        this.mCategoryName = mCategoryName;
    }

    public ArrayList<String> getSubTypeList() {
        return mSubTypeList;
    }

    private void setSubTypeList(int size){
        mSubTypeList = new ArrayList<>(size);
    }

    public void setSubTypeList(ArrayList<String> mSubTypeList) {
        this.mSubTypeList = mSubTypeList;
    }

    public int getSubTypeListSize(){
        return mSubTypeList.size();
    }

    public int getCategoryActivityId() {
        return mCategoryActivityId;
    }

    private void setCategoryActivityId(int categoryActivityId) {
        this.mCategoryActivityId = categoryActivityId;
    }

    public Class getCategoryActivityClass() {
        return mCategoryActivityClass;
    }

    private void setCategoryActivityClass(Class categoryActivityClass) {
        this.mCategoryActivityClass = categoryActivityClass;
    }

    public int getImageResourceId() {
        return mImageResourceId;
    }

    private void setImageResourceId(int mImageResourceId) {
        this.mImageResourceId = mImageResourceId;
    }
}
