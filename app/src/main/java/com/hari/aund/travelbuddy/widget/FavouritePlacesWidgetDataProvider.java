package com.hari.aund.travelbuddy.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.os.Build;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.hari.aund.travelbuddy.R;
import com.hari.aund.travelbuddy.activity.PlaceDetailActivity;
import com.hari.aund.travelbuddy.data.PlacesCategoryValues;
import com.hari.aund.travelbuddy.data.PlacesListInfo;
import com.hari.aund.travelbuddy.data.provider.PlaceColumns;
import com.hari.aund.travelbuddy.data.provider.Places;
import com.hari.aund.travelbuddy.utils.Utility;

/**
 * Created by Hari Nivas Kumar R P on 8/27/2016.
 */
public class FavouritePlacesWidgetDataProvider
        implements RemoteViewsService.RemoteViewsFactory {

    private static final String LOG_TAG = FavouritePlacesWidgetDataProvider.class.getSimpleName();

    private Cursor mFavPlacesCursor;
    private Context mContext;
    private Intent mIntent;

    public FavouritePlacesWidgetDataProvider(Context context, Intent intent) {
        mContext = context;
        mIntent = intent;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
        if (mFavPlacesCursor != null) {
            mFavPlacesCursor.close();
        }

        final long identityToken = Binder.clearCallingIdentity();

        //"content://com.hari.aund.data.provider.PlaceProvider/place"
        mFavPlacesCursor = mContext.getContentResolver().query(
                Places.CONTENT_URI_PLACES, null, null, null, null);

        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {
        if (mFavPlacesCursor != null) {
            mFavPlacesCursor.close();
            mFavPlacesCursor = null;
        }
    }

    @Override
    public int getCount() {
        return (mFavPlacesCursor == null ? 0 : mFavPlacesCursor.getCount());
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (position == AdapterView.INVALID_POSITION ||
                mFavPlacesCursor == null ||
                !mFavPlacesCursor.moveToPosition(position)) {
            return null;
        }

        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(),
                R.layout.list_item_favourites);

        int categoryId = getCategoryId(mFavPlacesCursor);
        PlacesListInfo placesListInfo = getPlacesListInfo(mFavPlacesCursor);

        if (placesListInfo.isPhotoReferenceAvailable()) {
            /*
            Uri imageUri = Uri.parse(new PlacesApiParser()
                            .getPhotoUrl(placesListInfo.getPhotoReference()));
            remoteViews.setImageViewUri(R.id.place_pic, imageUri);
             */

            // Content Description for Non-text elements
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
                remoteViews.setContentDescription(R.id.place_pic,
                        "Image of - " + placesListInfo.getPlaceName());
            }
        }

        remoteViews.setTextViewText(R.id.place_id, placesListInfo.getPlaceId());
        remoteViews.setTextViewText(R.id.place_name, placesListInfo.getPlaceName());
        remoteViews.setTextViewText(R.id.place_address, placesListInfo.getPlaceAddress());

        final Intent fillInIntent = new Intent(mContext, PlaceDetailActivity.class);
        fillInIntent.putExtra(Utility.KEY_PLACE_ID, placesListInfo.getPlaceId());
        fillInIntent.putExtra(Utility.KEY_CATEGORY_ID, categoryId);
        fillInIntent.putExtra(Utility.KEY_CATEGORY_NAME, getCategoryName(categoryId));
        fillInIntent.putExtra(Utility.KEY_PLACE_SECTION_NAME, getSectionName(mFavPlacesCursor));
        remoteViews.setOnClickFillInIntent(R.id.place_pic, fillInIntent);

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return new RemoteViews(mContext.getPackageName(), R.layout.list_item_favourites);
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        if (mFavPlacesCursor.moveToPosition(position))
            return mFavPlacesCursor.getLong(0);
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    private PlacesListInfo getPlacesListInfo(Cursor cursor) {
        PlacesListInfo placesListInfo = new PlacesListInfo();

        placesListInfo.setPlaceId(cursor.getString(
                cursor.getColumnIndex(PlaceColumns.PLACE_ID)));
        placesListInfo.setPlaceName(cursor.getString(
                cursor.getColumnIndex(PlaceColumns.NAME)));
        placesListInfo.setPlaceAddress(cursor.getString(
                cursor.getColumnIndex(PlaceColumns.ADDRESS)));
        placesListInfo.setPhotoReference(cursor.getString(
                cursor.getColumnIndex(PlaceColumns.PHOTO_COVER_REF)));

        return placesListInfo;
    }

    private String getCategoryName(int categoryId) {
        return PlacesCategoryValues.placesCategories[categoryId];
    }

    private int getCategoryId(Cursor cursor) {
        return cursor.getInt(cursor.getColumnIndex(PlaceColumns.CATEGORY_ID));
    }

    private String getSectionName(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndex(PlaceColumns.SUB_TYPE_NAME));
    }
}
