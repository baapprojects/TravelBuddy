package com.hari.aund.travelbuddy.data.provider;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.util.ArrayList;

public class PlaceProvider extends ContentProvider {
    public static final String AUTHORITY = "com.hari.aund.data.provider.PlaceProvider";

    private static final int PLACES_CONTENT_URI_PLACES = 0;

    private static final int PLACES_PLACE_ID = 1;

    private static final int PLACES_CONTENT_URI_CATEGORY = 2;

    private static final int PLACES_CATEGORY_ID = 3;

    private static final int PLACES_CONTENT_URI_SUB_TYPE = 4;

    private static final int PLACES_SUB_TYPE_ID = 5;

    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        MATCHER.addURI(AUTHORITY, "place", PLACES_CONTENT_URI_PLACES);
        MATCHER.addURI(AUTHORITY, "place/*", PLACES_PLACE_ID);
        MATCHER.addURI(AUTHORITY, "category", PLACES_CONTENT_URI_CATEGORY);
        MATCHER.addURI(AUTHORITY, "category/#", PLACES_CATEGORY_ID);
        MATCHER.addURI(AUTHORITY, "subtype", PLACES_CONTENT_URI_SUB_TYPE);
        MATCHER.addURI(AUTHORITY, "subtype/#", PLACES_SUB_TYPE_ID);
    }

    private SQLiteOpenHelper database;

    @Override
    public boolean onCreate() {
        database = com.hari.aund.travelbuddy.data.provider.PlaceDatabase.getInstance(getContext());
        return true;
    }

    private SelectionBuilder getBuilder(String table) {
        SelectionBuilder builder = new SelectionBuilder();
        return builder;
    }

    private long[] insertValues(SQLiteDatabase db, String table, ContentValues[] values) {
        long[] ids = new long[values.length];
        for (int i = 0; i < values.length; i++) {
            ContentValues cv = values[i];
            db.insertOrThrow(table, null, cv);
        }
        return ids;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = database.getWritableDatabase();
        db.beginTransaction();
        try {
            switch (MATCHER.match(uri)) {
                case PLACES_CONTENT_URI_PLACES: {
                    long[] ids = insertValues(db, "places", values);
                    getContext().getContentResolver().notifyChange(uri, null);
                    break;
                }
                case PLACES_PLACE_ID: {
                    long[] ids = insertValues(db, "places", values);
                    getContext().getContentResolver().notifyChange(uri, null);
                    break;
                }
                case PLACES_CONTENT_URI_CATEGORY: {
                    long[] ids = insertValues(db, "places", values);
                    getContext().getContentResolver().notifyChange(uri, null);
                    break;
                }
                case PLACES_CATEGORY_ID: {
                    long[] ids = insertValues(db, "places", values);
                    getContext().getContentResolver().notifyChange(uri, null);
                    break;
                }
                case PLACES_CONTENT_URI_SUB_TYPE: {
                    long[] ids = insertValues(db, "places", values);
                    getContext().getContentResolver().notifyChange(uri, null);
                    break;
                }
                case PLACES_SUB_TYPE_ID: {
                    long[] ids = insertValues(db, "places", values);
                    getContext().getContentResolver().notifyChange(uri, null);
                    break;
                }
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return values.length;
    }

    @Override
    public ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> ops) throws OperationApplicationException {
        ContentProviderResult[] results;
        final SQLiteDatabase db = database.getWritableDatabase();
        db.beginTransaction();
        try {
            results = super.applyBatch(ops);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return results;
    }

    @Override
    public String getType(Uri uri) {
        switch (MATCHER.match(uri)) {
            case PLACES_CONTENT_URI_PLACES: {
                return "vnd.android.cursor.dir/place";
            }
            case PLACES_PLACE_ID: {
                return "vnd.android.cursor.item/place";
            }
            case PLACES_CONTENT_URI_CATEGORY: {
                return "vnd.android.cursor.dir/category";
            }
            case PLACES_CATEGORY_ID: {
                return "vnd.android.cursor.item/category";
            }
            case PLACES_CONTENT_URI_SUB_TYPE: {
                return "vnd.android.cursor.dir/subtype";
            }
            case PLACES_SUB_TYPE_ID: {
                return "vnd.android.cursor.item/subtype";
            }
            default: {
                throw new IllegalArgumentException("Unknown URI " + uri);
            }
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase db = database.getReadableDatabase();
        switch (MATCHER.match(uri)) {
            case PLACES_CONTENT_URI_PLACES: {
                SelectionBuilder builder = getBuilder("Places");
                if (sortOrder == null) {
                    sortOrder = "_id ASC";
                }
                final String groupBy = null;
                final String having = null;
                final String limit = null;
                Cursor cursor = builder.table("places")
                        .where(selection, selectionArgs)
                        .query(db, projection, groupBy, having, sortOrder, limit);
                cursor.setNotificationUri(getContext().getContentResolver(), uri);
                return cursor;
            }
            case PLACES_PLACE_ID: {
                SelectionBuilder builder = getBuilder("Places");
                final String groupBy = null;
                final String having = null;
                final String limit = null;
                Cursor cursor = builder.table("places")
                        .where("place_id=?", uri.getPathSegments().get(1))
                        .where(selection, selectionArgs)
                        .query(db, projection, groupBy, having, sortOrder, limit);
                cursor.setNotificationUri(getContext().getContentResolver(), uri);
                return cursor;
            }
            case PLACES_CONTENT_URI_CATEGORY: {
                SelectionBuilder builder = getBuilder("Places");
                if (sortOrder == null) {
                    sortOrder = "category_id ASC";
                }
                final String groupBy = null;
                final String having = null;
                final String limit = null;
                Cursor cursor = builder.table("places")
                        .where(selection, selectionArgs)
                        .query(db, projection, groupBy, having, sortOrder, limit);
                cursor.setNotificationUri(getContext().getContentResolver(), uri);
                return cursor;
            }
            case PLACES_CATEGORY_ID: {
                SelectionBuilder builder = getBuilder("Places");
                final String groupBy = null;
                final String having = null;
                final String limit = null;
                Cursor cursor = builder.table("places")
                        .where("category_id=?", uri.getPathSegments().get(1))
                        .where(selection, selectionArgs)
                        .query(db, projection, groupBy, having, sortOrder, limit);
                cursor.setNotificationUri(getContext().getContentResolver(), uri);
                return cursor;
            }
            case PLACES_CONTENT_URI_SUB_TYPE: {
                SelectionBuilder builder = getBuilder("Places");
                if (sortOrder == null) {
                    sortOrder = "sub_type_id ASC";
                }
                final String groupBy = null;
                final String having = null;
                final String limit = null;
                Cursor cursor = builder.table("places")
                        .where(selection, selectionArgs)
                        .query(db, projection, groupBy, having, sortOrder, limit);
                cursor.setNotificationUri(getContext().getContentResolver(), uri);
                return cursor;
            }
            case PLACES_SUB_TYPE_ID: {
                SelectionBuilder builder = getBuilder("Places");
                final String groupBy = null;
                final String having = null;
                final String limit = null;
                Cursor cursor = builder.table("places")
                        .where("sub_type_id=?", uri.getPathSegments().get(1))
                        .where(selection, selectionArgs)
                        .query(db, projection, groupBy, having, sortOrder, limit);
                cursor.setNotificationUri(getContext().getContentResolver(), uri);
                return cursor;
            }
            default: {
                throw new IllegalArgumentException("Unknown URI " + uri);
            }
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = database.getWritableDatabase();
        switch (MATCHER.match(uri)) {
            case PLACES_CONTENT_URI_PLACES: {
                final long id = db.insertOrThrow("places", null, values);
                getContext().getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri, id);
            }
            case PLACES_PLACE_ID: {
                final long id = db.insertOrThrow("places", null, values);
                getContext().getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri, id);
            }
            case PLACES_CONTENT_URI_CATEGORY: {
                final long id = db.insertOrThrow("places", null, values);
                getContext().getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri, id);
            }
            case PLACES_CATEGORY_ID: {
                final long id = db.insertOrThrow("places", null, values);
                getContext().getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri, id);
            }
            case PLACES_CONTENT_URI_SUB_TYPE: {
                final long id = db.insertOrThrow("places", null, values);
                getContext().getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri, id);
            }
            case PLACES_SUB_TYPE_ID: {
                final long id = db.insertOrThrow("places", null, values);
                getContext().getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri, id);
            }
            default: {
                throw new IllegalArgumentException("Unknown URI " + uri);
            }
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String where, String[] whereArgs) {
        final SQLiteDatabase db = database.getWritableDatabase();
        switch (MATCHER.match(uri)) {
            case PLACES_CONTENT_URI_PLACES: {
                SelectionBuilder builder = getBuilder("Places");
                builder.where(where, whereArgs);
                final int count = builder.table("places")
                        .update(db, values);
                if (count > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return count;
            }
            case PLACES_PLACE_ID: {
                SelectionBuilder builder = getBuilder("Places");
                builder.where("place_id=?", uri.getPathSegments().get(1));
                builder.where(where, whereArgs);
                final int count = builder.table("places")
                        .update(db, values);
                if (count > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return count;
            }
            case PLACES_CONTENT_URI_CATEGORY: {
                SelectionBuilder builder = getBuilder("Places");
                builder.where(where, whereArgs);
                final int count = builder.table("places")
                        .update(db, values);
                if (count > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return count;
            }
            case PLACES_CATEGORY_ID: {
                SelectionBuilder builder = getBuilder("Places");
                builder.where("category_id=?", uri.getPathSegments().get(1));
                builder.where(where, whereArgs);
                final int count = builder.table("places")
                        .update(db, values);
                if (count > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return count;
            }
            case PLACES_CONTENT_URI_SUB_TYPE: {
                SelectionBuilder builder = getBuilder("Places");
                builder.where(where, whereArgs);
                final int count = builder.table("places")
                        .update(db, values);
                if (count > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return count;
            }
            case PLACES_SUB_TYPE_ID: {
                SelectionBuilder builder = getBuilder("Places");
                builder.where("sub_type_id=?", uri.getPathSegments().get(1));
                builder.where(where, whereArgs);
                final int count = builder.table("places")
                        .update(db, values);
                if (count > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return count;
            }
            default: {
                throw new IllegalArgumentException("Unknown URI " + uri);
            }
        }
    }

    @Override
    public int delete(Uri uri, String where, String[] whereArgs) {
        final SQLiteDatabase db = database.getWritableDatabase();
        switch (MATCHER.match(uri)) {
            case PLACES_CONTENT_URI_PLACES: {
                SelectionBuilder builder = getBuilder("Places");
                builder.where(where, whereArgs);
                final int count = builder
                        .table("places")
                        .delete(db);
                getContext().getContentResolver().notifyChange(uri, null);
                return count;
            }
            case PLACES_PLACE_ID: {
                SelectionBuilder builder = getBuilder("Places");
                builder.where("place_id=?", uri.getPathSegments().get(1));
                builder.where(where, whereArgs);
                final int count = builder
                        .table("places")
                        .delete(db);
                getContext().getContentResolver().notifyChange(uri, null);
                return count;
            }
            case PLACES_CONTENT_URI_CATEGORY: {
                SelectionBuilder builder = getBuilder("Places");
                builder.where(where, whereArgs);
                final int count = builder
                        .table("places")
                        .delete(db);
                getContext().getContentResolver().notifyChange(uri, null);
                return count;
            }
            case PLACES_CATEGORY_ID: {
                SelectionBuilder builder = getBuilder("Places");
                builder.where("category_id=?", uri.getPathSegments().get(1));
                builder.where(where, whereArgs);
                final int count = builder
                        .table("places")
                        .delete(db);
                getContext().getContentResolver().notifyChange(uri, null);
                return count;
            }
            case PLACES_CONTENT_URI_SUB_TYPE: {
                SelectionBuilder builder = getBuilder("Places");
                builder.where(where, whereArgs);
                final int count = builder
                        .table("places")
                        .delete(db);
                getContext().getContentResolver().notifyChange(uri, null);
                return count;
            }
            case PLACES_SUB_TYPE_ID: {
                SelectionBuilder builder = getBuilder("Places");
                builder.where("sub_type_id=?", uri.getPathSegments().get(1));
                builder.where(where, whereArgs);
                final int count = builder
                        .table("places")
                        .delete(db);
                getContext().getContentResolver().notifyChange(uri, null);
                return count;
            }
            default: {
                throw new IllegalArgumentException("Unknown URI " + uri);
            }
        }
    }

}
