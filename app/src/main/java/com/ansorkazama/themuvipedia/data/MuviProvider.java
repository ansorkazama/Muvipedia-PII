/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ansorkazama.themuvipedia.data;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

/**
 *
 * This For Project-II
 * Load all favorite movies
 *
 * This class supplies Movie information from an encapsulated database.   It works in conjunction
 * with FetchMovieTask to provide data to the display fragments and the adapters that
 * fill out the UX.
 *
 * For more info, see
 * http://developer.android.com/guide/topics/providers/content-providers.html
 */
public class MuviProvider extends ContentProvider {

    // The URI Matcher used by this content provider.
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    // private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private MuviDbHelper mOpenHelper;

    static final int LS_POPULAR_MOVIE = 100;
    static final int LS_TOP_RATED_MOVIE = 101;
    static final int LS_FAVORITES_MOVIE = 102;

    static final int DT_POPULAR_MOVIE = 103;
    static final int DT_TOP_RATED_MOVIE = 104;
    static final int DT_FAVORITE_MOVIE = 105;

    private static final SQLiteQueryBuilder sMoviePopularListQueryBuilder;
    private static final SQLiteQueryBuilder sMovieTopRatedListQueryBuilder;
    private static final SQLiteQueryBuilder sMovieFavoritesListQueryBuilder;

    static {
        sMoviePopularListQueryBuilder = new SQLiteQueryBuilder();
        sMoviePopularListQueryBuilder.setTables(
                MuviContract.MoviePopular.TABLE_NAME);
    }

    static {
        sMovieTopRatedListQueryBuilder = new SQLiteQueryBuilder();
        sMovieTopRatedListQueryBuilder.setTables(
                MuviContract.MovieTopRated.TABLE_NAME);
    }

    static {
        sMovieFavoritesListQueryBuilder = new SQLiteQueryBuilder();
        sMovieFavoritesListQueryBuilder.setTables(
                MuviContract.MovieFavorites.TABLE_NAME);
    }

    private static final String sMoviePopularIdSelection =
            MuviContract.MoviePopular.TABLE_NAME +
                    "." + MuviContract.MoviePopular.CL_MOVIE_ID + " = ? ";

    private static final String sMovieTopRatedIdSelection =
            MuviContract.MovieTopRated.TABLE_NAME +
                    "." + MuviContract.MovieTopRated.CL_MOVIE_ID + " = ? ";

    private static final String sMovieFavoritesIdSelection =
            MuviContract.MovieFavorites.TABLE_NAME +
                    "." + MuviContract.MovieFavorites.CL_MOVIE_ID + " = ? ";

    private Cursor getMoviesByPopularity(Uri uri, String[] projection, String sortOrder) {

        String[] selectionArgs = null;
        String selection = null;

        return sMoviePopularListQueryBuilder.query(mOpenHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getMoviesByTopRating(Uri uri, String[] projection, String sortOrder) {

        String[] selectionArgs = null;
        String selection = null;

        return sMovieTopRatedListQueryBuilder.query(mOpenHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getMoviesByFavorites(Uri uri, String[] projection, String sortOrder) {

        String[] selectionArgs = null;
        String selection = null;

        return sMovieFavoritesListQueryBuilder.query(mOpenHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getPopularMovieByID(Uri uri, String[] projection, String sortOrder) {

        String movie_id = MuviContract.MoviePopular.getMovieIDFromUri(uri);

        return sMoviePopularListQueryBuilder.query(mOpenHelper.getReadableDatabase(),
                projection,
                sMoviePopularIdSelection,
                new String[] { movie_id },
                null,
                null,
                sortOrder
        );
    }

    private Cursor getTopRatedMovieByID(Uri uri, String[] projection, String sortOrder) {

        String movie_id = MuviContract.MovieTopRated.getMovieIDFromUri(uri);

        return sMovieTopRatedListQueryBuilder.query(mOpenHelper.getReadableDatabase(),
                projection,
                sMovieTopRatedIdSelection,
                new String[] { movie_id },
                null,
                null,
                sortOrder
        );
    }

    private Cursor getFavoritesMovieByID(Uri uri, String[] projection, String sortOrder) {

        String movie_id = MuviContract.MovieFavorites.getMovieIDFromUri(uri);

        return sMovieFavoritesListQueryBuilder.query(mOpenHelper.getReadableDatabase(),
                projection,
                sMovieFavoritesIdSelection,
                new String[] { movie_id },
                null,
                null,
                sortOrder
        );
    }

    /*
        This essential element in the ContentProvider architecture constructs
        a regular expression framework to parse the incoming URIs
     */
    static UriMatcher buildUriMatcher() {

        // 1) The code passed into the constructor represents the code to return for the root
        // URI.  It's common to use NO_MATCH as the code for this case. Add the constructor below.

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MuviContract.CT_AUTHORITY;

        // 2) Use the addURI function to match each of the types.  Use the constants from
        // WeatherContract to help define the types to the UriMatcher.

        matcher.addURI(authority, MuviContract.PATH_POPULAR, LS_POPULAR_MOVIE);
        matcher.addURI(authority, MuviContract.PATH_TOP_RATED, LS_TOP_RATED_MOVIE);
        matcher.addURI(authority, MuviContract.PATH_FAVORITES, LS_FAVORITES_MOVIE);
        matcher.addURI(authority,
                MuviContract.PATH_POPULAR + "/" +
                MuviContract.PATH_MOVIE + "/#", DT_POPULAR_MOVIE);
        matcher.addURI(authority,
                MuviContract.PATH_TOP_RATED + "/" +
                MuviContract.PATH_MOVIE + "/#", DT_TOP_RATED_MOVIE);
        matcher.addURI(authority,
                MuviContract.PATH_FAVORITES + "/" +
                MuviContract.PATH_MOVIE + "/#", DT_FAVORITE_MOVIE);

        // 3) Return the new matcher!

        return matcher;
    }


    // create the database if necessary

    @Override
    public boolean onCreate() {
        mOpenHelper = new MuviDbHelper(getContext());
        return true;
    }

    @Override
    public String getType(Uri uri) {

        // Use the Uri Matcher to determine what kind of URI this is.
        final int match = sUriMatcher.match(uri);

        switch (match) {

            case LS_POPULAR_MOVIE:
                return MuviContract.MoviePopular.CT_POPULAR_TYPE;
            case LS_TOP_RATED_MOVIE:
                return MuviContract.MovieTopRated.CT_TOP_RATED_TYPE;
            case LS_FAVORITES_MOVIE:
                return MuviContract.MovieFavorites.CT_FAVORITES_TYPE;
            case DT_POPULAR_MOVIE:
                return MuviContract.MoviePopular.CT_POPULAR_ITEM_TYPE;
            case DT_TOP_RATED_MOVIE:
                return MuviContract.MovieTopRated.CT_TOP_RATED_ITEM_TYPE;
            case DT_FAVORITE_MOVIE:
                return MuviContract.MovieFavorites.CT_FAVORITES_ITEM_TYPE;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        // Here's the switch statement that, given a URI, will determine what kind of request it is,
        // and query the database accordingly.
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {

            case LS_POPULAR_MOVIE:
            {
                retCursor = getMoviesByPopularity(uri, projection, sortOrder);
                break;
            }
            case DT_POPULAR_MOVIE:
            {
                retCursor = getPopularMovieByID(uri, projection, sortOrder);
                break;
            }
            case LS_TOP_RATED_MOVIE:
            {
                retCursor = getMoviesByTopRating(uri, projection, sortOrder);
                break;
            }
            case DT_TOP_RATED_MOVIE:
            {
                retCursor = getTopRatedMovieByID(uri, projection, sortOrder);
                break;
            }
            case LS_FAVORITES_MOVIE:
            {
                retCursor = getMoviesByFavorites(uri, projection, sortOrder);
                break;
            }
            case DT_FAVORITE_MOVIE:
            {
                retCursor = getFavoritesMovieByID(uri, projection, sortOrder);
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    /*

     */
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case LS_POPULAR_MOVIE: {

                long _id = db.insert(MuviContract.MoviePopular.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = MuviContract.MoviePopular.buildPopularMoviesUri();
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case LS_TOP_RATED_MOVIE: {

                long _id = db.insert(MuviContract.MovieTopRated.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = MuviContract.MovieTopRated.buildTopRatedMoviesUri();
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case LS_FAVORITES_MOVIE: {

                long _id = db.insert(MuviContract.MovieFavorites.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = MuviContract.MovieFavorites.buildFavoriteMoviesUri();
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        // Student: A null value deletes all rows.  In my implementation of this, I only notified
        // the uri listeners (using the content resolver) if the rowsDeleted != 0 or the selection
        // is null.
        // Oh, and you should notify the listeners here.

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        final int match = sUriMatcher.match(uri);
        int numDeleted;

        switch (match) {

            case LS_POPULAR_MOVIE: {
                numDeleted =  db.delete(MuviContract.MoviePopular.TABLE_NAME,
                        selection,
                        selectionArgs
                        );
                break;
            }
            case LS_TOP_RATED_MOVIE: {
                numDeleted =  db.delete(MuviContract.MovieTopRated.TABLE_NAME,
                        selection,
                        selectionArgs
                );
                break;
            }
            case LS_FAVORITES_MOVIE: {
                numDeleted =  db.delete(MuviContract.MovieFavorites.TABLE_NAME,
                        selection,
                        selectionArgs
                );
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return numDeleted;
    }


    @Override
    public int update(
            Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        final int match = sUriMatcher.match(uri);
        int numUpdated;

        switch (match) {

            case LS_POPULAR_MOVIE: {
                numUpdated =  db.update(MuviContract.MoviePopular.TABLE_NAME,
                        values, selection,
                        selectionArgs
                );
                break;
            }
            case DT_POPULAR_MOVIE: {
                numUpdated =  db.update(MuviContract.MoviePopular.TABLE_NAME,
                        values, selection,
                        selectionArgs
                );
                break;
            }
            case LS_TOP_RATED_MOVIE: {
                numUpdated =  db.update(MuviContract.MovieTopRated.TABLE_NAME,
                        values, selection,
                        selectionArgs
                );
                break;
            }
            case DT_TOP_RATED_MOVIE: {
                numUpdated =  db.update(MuviContract.MovieTopRated.TABLE_NAME,
                        values, selection,
                        selectionArgs
                );
                break;
            }
            case LS_FAVORITES_MOVIE: {
                numUpdated =  db.update(MuviContract.MovieFavorites.TABLE_NAME,
                        values, selection,
                        selectionArgs
                );
                break;
            }
            case DT_FAVORITE_MOVIE: {
                numUpdated =  db.update(MuviContract.MovieFavorites.TABLE_NAME,
                        values, selection,
                        selectionArgs
                );
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return numUpdated;
    }

    // movie favorites won't ever get downloaded from TheMovieDb - so
    // punt on the bulkInsert method...
    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int returnCount = 0;
        switch (match) {
            case LS_POPULAR_MOVIE:
                db.beginTransaction();
                try {
                    for (ContentValues value : values) {

                        long _id = db.insert(MuviContract.MoviePopular.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;

            case LS_TOP_RATED_MOVIE:
                db.beginTransaction();
                try {
                    for (ContentValues value : values) {

                        long _id = db.insert(MuviContract.MovieTopRated.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }
    }

    // http://developer.android.com/reference/android/content/ContentProvider.html#shutdown()
    @Override
    @TargetApi(11)
    public void shutdown() {
        mOpenHelper.close();
        super.shutdown();
    }
}