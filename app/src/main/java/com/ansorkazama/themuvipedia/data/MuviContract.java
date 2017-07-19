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

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Defines table and column names
 */
public class MuviContract {

    public static final String CT_AUTHORITY = "com.ansorkazama.themuvipedia";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CT_AUTHORITY);

    public static final String PATH_TOP_RATED = "top_rated";
    public static final String PATH_FAVORITES = "favorites";
    public static final String PATH_POPULAR = "popular";
    public static final String PATH_MOVIE = "movie";

    // COLUMNS is for:  id, overview, poster_path, release date, title, vote_average, vote_count (detail:  trailers)

    public static final class MoviePopular implements BaseColumns {

        public static final String TABLE_NAME = "movie";

        //CL_XXX Stand For Column
        public static final String CL_MOVIE_ID = "movie_id";
        public static final String CL_POPULAR_INDEX = "popular_index";
        public static final String CL_TOP_RATED_INDEX = "top_rated_index";
        public static final String CL_OVERVIEW = "overview";
        public static final String CL_POSTER_PATH = "poster_path";
        public static final String CL_RELEASE_DATE = "release_date";
        public static final String CL_RUNTIME = "runtime";
        public static final String CL_TITLE = "title";
        public static final String CL_VOTE_AVERAGE = "vote_average";
        public static final String CL_VOTE_COUNT = "vote_count";
        public static final String CL_TAGLINE = "tagline";
        public static final String CL_TRAILER = "trailer";
        public static final String CL_TRAILER2 = "trailer2";
        public static final String CL_TRAILER3 = "trailer3";
        public static final String CL_TRAILER_NAME = "trailer_name";
        public static final String CL_TRAILER2_NAME = "trailer2_name";
        public static final String CL_TRAILER3_NAME = "trailer3_name";
        public static final String CL_FAVORITE = "favorite_flag";
        public static final String CL_FAVORITE_TIMESTAMP = "favorite_timestamp";
        public static final String CL_DETAILS_LOADED = "details_loaded";
        public static final String CL_REVIEW = "review";
        public static final String CL_REVIEW2 = "review2";
        public static final String CL_REVIEW3 = "review3";
        public static final String CL_REVIEW_NAME = "review_name";
        public static final String CL_REVIEW2_NAME = "review2_name";
        public static final String CL_REVIEW3_NAME = "review3_name";
        public static final String CL_SPARE_INTEGER = "spare_integer";
        public static final String CL_SPARE_STRING1 = "spare_string1";
        public static final String CL_SPARE_STRING2 = "spare_string2";
        public static final String CL_SPARE_STRING3 = "spare_string3";
        public static final String CL_MYNAME = "myname";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_POPULAR).build();

        public static final String CT_POPULAR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CT_AUTHORITY + "/" + PATH_POPULAR;

        public static final String CT_POPULAR_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CT_AUTHORITY
                        + "/" + PATH_POPULAR + "/" + PATH_MOVIE;

        public static Uri buildMovieDetailsUri(long id) {
            return ContentUris.withAppendedId(buildMovieDetails(), id);
        }

        public static Uri buildPopularMoviesUri() {
            return BASE_CONTENT_URI.buildUpon()
                    .appendPath(PATH_POPULAR)
                    .build();
        }

        public static Uri buildMovieDetails() {
            return BASE_CONTENT_URI.buildUpon()
                    .appendPath(PATH_POPULAR)
                    .appendPath(PATH_MOVIE)
                    .build();
        }

        public static String getMovieModeFromUri(Uri uri) {
            return uri.getPathSegments().get(0);
        }
        public static String getMovieIDFromUri(Uri uri) {
            return uri.getPathSegments().get(2);
        }

    }
    public static final class MovieTopRated implements BaseColumns {

        public static final String TABLE_NAME = "top_rated";

        //CL_XXX stand for COLUMN
        public static final String CL_MOVIE_ID = "movie_id";
        public static final String CL_POPULAR_INDEX = "popular_index";
        public static final String CL_TOP_RATED_INDEX = "top_rated_index";
        public static final String CL_OVERVIEW = "overview";
        public static final String CL_POSTER_PATH = "poster_path";
        public static final String CL_RELEASE_DATE = "release_date";
        public static final String CL_RUNTIME = "runtime";
        public static final String CL_TITLE = "title";
        public static final String CL_VOTE_AVERAGE = "vote_average";
        public static final String CL_VOTE_COUNT = "vote_count";
        public static final String CL_TAGLINE = "tagline";
        public static final String CL_TRAILER = "trailer";
        public static final String CL_TRAILER2 = "trailer2";
        public static final String CL_TRAILER3 = "trailer3";
        public static final String CL_TRAILER_NAME = "trailer_name";
        public static final String CL_TRAILER2_NAME = "trailer2_name";
        public static final String CL_TRAILER3_NAME = "trailer3_name";
        public static final String CL_FAVORITE = "favorite_flag";
        public static final String CL_FAVORITE_TIMESTAMP = "favorite_timestamp";
        public static final String CL_DETAILS_LOADED = "details_loaded";
        public static final String CL_REVIEW = "review";
        public static final String CL_REVIEW2 = "review2";
        public static final String CL_REVIEW3 = "review3";
        public static final String CL_REVIEW_NAME = "review_name";
        public static final String CL_REVIEW2_NAME = "review2_name";
        public static final String CL_REVIEW3_NAME = "review3_name";
        public static final String CL_SPARE_INTEGER = "spare_integer";
        public static final String CL_SPARE_STRING1 = "spare_string1";
        public static final String CL_SPARE_STRING2 = "spare_string2";
        public static final String CL_SPARE_STRING3 = "spare_string3";
        public static final String CL_MYNAME = "myname";


        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TOP_RATED).build();

        public static final String CT_TOP_RATED_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CT_AUTHORITY + "/" + PATH_TOP_RATED;

        public static final String CT_TOP_RATED_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CT_AUTHORITY
                        + "/" + PATH_TOP_RATED + "/" + PATH_MOVIE;

        public static Uri buildMovieDetailsUri(long id) {
            return ContentUris.withAppendedId(buildMovieDetails(), id);
        }

        public static Uri buildTopRatedMoviesUri() {
            return BASE_CONTENT_URI.buildUpon()
                    .appendPath(PATH_TOP_RATED)
                    .build();
        }

        public static Uri buildMovieDetails(String movie_id) {
            return BASE_CONTENT_URI.buildUpon()
                    .appendPath(PATH_MOVIE)
                    .appendPath(movie_id)
                    .build();
        }

        public static Uri buildMovieDetails() {
            return BASE_CONTENT_URI.buildUpon()
                    .appendPath(PATH_TOP_RATED)
                    .appendPath(PATH_MOVIE)
                    .build();
        }

        public static String getMovieIDFromUri(Uri uri) {
            return uri.getPathSegments().get(2);
        }
    }

    public static final class MovieFavorites implements BaseColumns {

        public static final String TABLE_NAME = "favorites";

        //CL_XXX Stand for COLUMN
        public static final String CL_MOVIE_ID = "movie_id";
        public static final String CL_POPULAR_INDEX = "popular_index";
        public static final String CL_TOP_RATED_INDEX = "top_rated_index";
        public static final String CL_OVERVIEW = "overview";
        public static final String CL_POSTER_PATH = "poster_path";
        public static final String CL_RELEASE_DATE = "release_date";
        public static final String CL_RUNTIME = "runtime";
        public static final String CL_TITLE = "title";
        public static final String CL_VOTE_AVERAGE = "vote_average";
        public static final String CL_VOTE_COUNT = "vote_count";
        public static final String CL_TAGLINE = "tagline";
        public static final String CL_TRAILER = "trailer";
        public static final String CL_TRAILER2 = "trailer2";
        public static final String CL_TRAILER3 = "trailer3";
        public static final String CL_TRAILER_NAME = "trailer_name";
        public static final String CL_TRAILER2_NAME = "trailer2_name";
        public static final String CL_TRAILER3_NAME = "trailer3_name";
        public static final String CL_FAVORITE = "favorite_flag";
        public static final String CL_FAVORITE_TIMESTAMP = "favorite_timestamp";
        public static final String CL_DETAILS_LOADED = "details_loaded";
        public static final String CL_REVIEW = "review";
        public static final String CL_REVIEW2 = "review2";
        public static final String CL_REVIEW3 = "review3";
        public static final String CL_REVIEW_NAME = "review_name";
        public static final String CL_REVIEW2_NAME = "review2_name";
        public static final String CL_REVIEW3_NAME = "review3_name";
        public static final String CL_SPARE_INTEGER = "spare_integer";
        public static final String CL_SPARE_STRING1 = "spare_string1";
        public static final String CL_SPARE_STRING2 = "spare_string2";
        public static final String CL_SPARE_STRING3 = "spare_string3";
        public static final String CL_MYNAME = "myname";


        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVORITES).build();

        public static final String CT_FAVORITES_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CT_AUTHORITY + "/" + PATH_FAVORITES;

        public static final String CT_FAVORITES_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CT_AUTHORITY
                        + "/" + PATH_FAVORITES;

        public static Uri buildMovieDetailsUri(long id) {
            return ContentUris.withAppendedId(buildMovieDetails(), id);
        }

        public static Uri buildFavoriteMoviesUri() {
            return BASE_CONTENT_URI.buildUpon()
                    .appendPath(PATH_FAVORITES)
                    .build();
        }

        public static Uri buildMovieFavoritesWithID(String movie_id) {
            return BASE_CONTENT_URI.buildUpon()
                    .appendPath(PATH_FAVORITES)
                    .appendPath(movie_id)
                    .build();
        }

        public static Uri buildMovieDetails() {
            return BASE_CONTENT_URI.buildUpon()
                    .appendPath(PATH_FAVORITES)
                    .appendPath(PATH_MOVIE)
                    .build();
        }

        public static String getMovieIDFromUri(Uri uri) {
            return uri.getPathSegments().get(2);  // see comment earlier
        }
    }
}
