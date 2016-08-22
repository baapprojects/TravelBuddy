package com.hari.aund.travelbuddy.data.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PlaceDatabase extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    public static final String PLACES = "CREATE TABLE places ("
            + PlaceColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + PlaceColumns.PLACE_ID + " TEXT UNIQUE,"
            + PlaceColumns.NAME + " REAL,"
            + PlaceColumns.ADDRESS + " TEXT,"
            + PlaceColumns.VICINITY + " TEXT,"
            + PlaceColumns.LATITUDE + " REAL,"
            + PlaceColumns.LONGITUDE + " REAL,"
            + PlaceColumns.PHOTO_COVER_REF + " INTEGER,"
            + PlaceColumns.PHONE_NUMBER + " TEXT,"
            + PlaceColumns.WEBSITE + " TEXT,"
            + PlaceColumns.RATING + " REAL,"
            + PlaceColumns.CATEGORY_ID + " INTEGER,"
            + PlaceColumns.SUB_TYPE_NAME + " TEXT)";

    private static volatile PlaceDatabase instance;

    private Context context;

    private PlaceDatabase(Context context) {
        super(context, "placeDatabase.db", null, DATABASE_VERSION);
        this.context = context;
    }

    public static PlaceDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (PlaceDatabase.class) {
                if (instance == null) {
                    instance = new PlaceDatabase(context);
                }
            }
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(PLACES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
