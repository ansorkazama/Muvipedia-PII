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

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 *
 * This For Project-II
 * Load all favorite movies
 *
 * Manages a local database for movie data.
 */
public class MuviDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "popularmovies.db";

    public MuviDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " + MuviContract.MoviePopular.TABLE_NAME + " (" +
                MuviContract.MoviePopular._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +  // REQUIRED!!!
                MuviContract.MoviePopular.CL_MOVIE_ID + " INTEGER NOT NULL UNIQUE ON CONFLICT REPLACE, " +
                MuviContract.MoviePopular.CL_POPULAR_INDEX + " INTEGER, " +
                MuviContract.MoviePopular.CL_TOP_RATED_INDEX + " INTEGER, " +
                MuviContract.MoviePopular.CL_OVERVIEW + " TEXT NOT NULL, " +
                MuviContract.MoviePopular.CL_POSTER_PATH + " TEXT NOT NULL, " +
                MuviContract.MoviePopular.CL_RELEASE_DATE + " TEXT, " +
                MuviContract.MoviePopular.CL_RUNTIME + " TEXT, " +
                MuviContract.MoviePopular.CL_TITLE + " TEXT NOT NULL, " +
                MuviContract.MoviePopular.CL_VOTE_AVERAGE + " TEXT NOT NULL, " +
                MuviContract.MoviePopular.CL_VOTE_COUNT + " TEXT NOT NULL, " +
                MuviContract.MoviePopular.CL_TAGLINE + " TEXT, " +
                MuviContract.MoviePopular.CL_TRAILER + " TEXT, " +
                MuviContract.MoviePopular.CL_TRAILER2 + " TEXT, " +
                MuviContract.MoviePopular.CL_TRAILER3 + " TEXT, "  +
                MuviContract.MoviePopular.CL_TRAILER_NAME + " TEXT, " +
                MuviContract.MoviePopular.CL_TRAILER2_NAME + " TEXT, " +
                MuviContract.MoviePopular.CL_TRAILER3_NAME + " TEXT, "  +
                MuviContract.MoviePopular.CL_FAVORITE + " INTEGER DEFAULT 0, " +
                MuviContract.MoviePopular.CL_FAVORITE_TIMESTAMP + " INTEGER, " +
                MuviContract.MoviePopular.CL_DETAILS_LOADED + " INTEGER DEFAULT 0, " +
                MuviContract.MoviePopular.CL_REVIEW + " TEXT, " +
                MuviContract.MoviePopular.CL_REVIEW2 + " TEXT, " +
                MuviContract.MoviePopular.CL_REVIEW3 + " TEXT, "  +
                MuviContract.MoviePopular.CL_REVIEW_NAME + " TEXT, " +
                MuviContract.MoviePopular.CL_REVIEW2_NAME + " TEXT, " +
                MuviContract.MoviePopular.CL_REVIEW3_NAME + " TEXT, "  +
                MuviContract.MoviePopular.CL_SPARE_INTEGER + " INTEGER DEFAULT 0, " +
                MuviContract.MoviePopular.CL_SPARE_STRING1 + " TEXT, " +
                MuviContract.MoviePopular.CL_SPARE_STRING2 + " TEXT, " +
                MuviContract.MoviePopular.CL_SPARE_STRING3 + " TEXT, "  +
                MuviContract.MoviePopular.CL_MYNAME + " TEXT NOT NULL" +

                     " );";

        final String SQL_CREATE_MOVIE_TABLE2 = "CREATE TABLE " + MuviContract.MovieTopRated.TABLE_NAME + " (" +
                MuviContract.MovieTopRated._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +  // REQUIRED!!!
                MuviContract.MovieTopRated.CL_MOVIE_ID + " INTEGER NOT NULL UNIQUE ON CONFLICT REPLACE, " +
                MuviContract.MovieTopRated.CL_POPULAR_INDEX + " INTEGER, " +
                MuviContract.MovieTopRated.CL_TOP_RATED_INDEX + " INTEGER, " +
                MuviContract.MovieTopRated.CL_OVERVIEW + " TEXT NOT NULL, " +
                MuviContract.MovieTopRated.CL_POSTER_PATH + " TEXT NOT NULL, " +
                MuviContract.MovieTopRated.CL_RELEASE_DATE + " TEXT, " +
                MuviContract.MovieTopRated.CL_RUNTIME + " TEXT, " +
                MuviContract.MovieTopRated.CL_TITLE + " TEXT NOT NULL, " +
                MuviContract.MovieTopRated.CL_VOTE_AVERAGE + " TEXT NOT NULL, " +
                MuviContract.MovieTopRated.CL_VOTE_COUNT + " TEXT NOT NULL, " +
                MuviContract.MovieTopRated.CL_TAGLINE + " TEXT, " +
                MuviContract.MovieTopRated.CL_TRAILER + " TEXT, " +
                MuviContract.MovieTopRated.CL_TRAILER2 + " TEXT, " +
                MuviContract.MovieTopRated.CL_TRAILER3 + " TEXT, "  +
                MuviContract.MovieTopRated.CL_TRAILER_NAME + " TEXT, " +
                MuviContract.MovieTopRated.CL_TRAILER2_NAME + " TEXT, " +
                MuviContract.MovieTopRated.CL_TRAILER3_NAME + " TEXT, "  +
                MuviContract.MovieTopRated.CL_FAVORITE + " INTEGER DEFAULT 0, " +
                MuviContract.MovieTopRated.CL_FAVORITE_TIMESTAMP + " INTEGER, " +
                MuviContract.MovieTopRated.CL_DETAILS_LOADED + " INTEGER DEFAULT 0, " +
                MuviContract.MovieTopRated.CL_REVIEW + " TEXT, " +
                MuviContract.MovieTopRated.CL_REVIEW2 + " TEXT, " +
                MuviContract.MovieTopRated.CL_REVIEW3 + " TEXT, "  +
                MuviContract.MovieTopRated.CL_REVIEW_NAME + " TEXT, " +
                MuviContract.MovieTopRated.CL_REVIEW2_NAME + " TEXT, " +
                MuviContract.MovieTopRated.CL_REVIEW3_NAME + " TEXT, "  +
                MuviContract.MovieTopRated.CL_SPARE_INTEGER + " INTEGER DEFAULT 0, " +
                MuviContract.MovieTopRated.CL_SPARE_STRING1 + " TEXT, " +
                MuviContract.MovieTopRated.CL_SPARE_STRING2 + " TEXT, " +
                MuviContract.MovieTopRated.CL_SPARE_STRING3 + " TEXT, "  +
                MuviContract.MovieTopRated.CL_MYNAME + " TEXT NOT NULL" +

                " );";

        final String SQL_CREATE_MOVIE_TABLE3 = "CREATE TABLE " + MuviContract.MovieFavorites.TABLE_NAME + " (" +
                MuviContract.MovieFavorites._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +  // REQUIRED!!!

                MuviContract.MovieFavorites.CL_MOVIE_ID + " INTEGER NOT NULL UNIQUE ON CONFLICT REPLACE, " +
                MuviContract.MovieFavorites.CL_POPULAR_INDEX + " INTEGER, " +
                MuviContract.MovieFavorites.CL_TOP_RATED_INDEX + " INTEGER, " +
                MuviContract.MovieFavorites.CL_OVERVIEW + " TEXT NOT NULL, " +
                MuviContract.MovieFavorites.CL_POSTER_PATH + " TEXT NOT NULL, " +
                MuviContract.MovieFavorites.CL_RELEASE_DATE + " TEXT, " +
                MuviContract.MovieFavorites.CL_RUNTIME + " TEXT, " +
                MuviContract.MovieFavorites.CL_TITLE + " TEXT NOT NULL, " +
                MuviContract.MovieFavorites.CL_VOTE_AVERAGE + " TEXT NOT NULL, " +
                MuviContract.MovieFavorites.CL_VOTE_COUNT + " TEXT NOT NULL, " +
                MuviContract.MovieFavorites.CL_TAGLINE + " TEXT, " +
                MuviContract.MovieFavorites.CL_TRAILER + " TEXT, " +
                MuviContract.MovieFavorites.CL_TRAILER2 + " TEXT, " +
                MuviContract.MovieFavorites.CL_TRAILER3 + " TEXT, "  +
                MuviContract.MovieFavorites.CL_TRAILER_NAME + " TEXT, " +
                MuviContract.MovieFavorites.CL_TRAILER2_NAME + " TEXT, " +
                MuviContract.MovieFavorites.CL_TRAILER3_NAME + " TEXT, "  +
                MuviContract.MovieFavorites.CL_FAVORITE + " INTEGER DEFAULT 0, " +
                MuviContract.MovieFavorites.CL_FAVORITE_TIMESTAMP + " INTEGER ," +
                MuviContract.MovieFavorites.CL_DETAILS_LOADED + " INTEGER DEFAULT 0, " +
                MuviContract.MovieFavorites.CL_REVIEW + " TEXT, " +
                MuviContract.MovieFavorites.CL_REVIEW2 + " TEXT, " +
                MuviContract.MovieFavorites.CL_REVIEW3 + " TEXT, "  +
                MuviContract.MovieFavorites.CL_REVIEW_NAME + " TEXT, " +
                MuviContract.MovieFavorites.CL_REVIEW2_NAME + " TEXT, " +
                MuviContract.MovieFavorites.CL_REVIEW3_NAME + " TEXT, "  +
                MuviContract.MovieFavorites.CL_SPARE_INTEGER + " INTEGER DEFAULT 0, " +
                MuviContract.MovieFavorites.CL_SPARE_STRING1 + " TEXT, " +
                MuviContract.MovieFavorites.CL_SPARE_STRING2 + " TEXT, " +
                MuviContract.MovieFavorites.CL_SPARE_STRING3 + " TEXT, "  +
                MuviContract.MovieFavorites.CL_MYNAME + " TEXT NOT NULL" +

                " );";

        sqLiteDatabase.execSQL(SQL_CREATE_MOVIE_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_MOVIE_TABLE2);
        sqLiteDatabase.execSQL(SQL_CREATE_MOVIE_TABLE3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MuviContract.MoviePopular.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MuviContract.MovieTopRated.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MuviContract.MovieFavorites.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
