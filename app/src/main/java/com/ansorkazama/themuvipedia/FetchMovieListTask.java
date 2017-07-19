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
package com.ansorkazama.themuvipedia;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import com.ansorkazama.themuvipedia.data.MuviContract;
import com.ansorkazama.themuvipedia.data.MuviContract.MoviePopular;
import com.ansorkazama.themuvipedia.data.MuviContract.MovieTopRated;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Vector;

import timber.log.Timber;

public class FetchMovieListTask extends AsyncTask<String, Void, Void> {

    private final Boolean EXTRA_VERBOSE = true;
    private final String LOG_TAG = FetchMovieListTask.class.getSimpleName();

    private final TrafficManager mTM;
    private final Context mContext;

    public FetchMovieListTask(Context context) {
        mContext = context;
        mTM = TrafficManager.getInstance(mContext);
    }

    private boolean DEBUG = true;

    public final String FE_POPULAR = "popular";
    public final String FE_TOP_RATED = "toprated";
    public final String FE_POPULAR_MOVIE_DETAILS = "pop_details";
    public final String FE_TOP_RATED_MOVIE_DETAILS = "tr_details";

    private void getMovieListFromJson(String movieListJsonStr, String whatToDo
                    ) throws JSONException {


        //  COLUMNS:  id, overview, poster_path, release date, title, vote_average, vote_count

        final String MVDB_RESULTS = "results";

        final String MVDB_ID = "id";
        final String MVDB_OVERVIEW = "overview";
        final String MVDB_POSTER_PATH = "poster_path";
        final String MVDB_RELEASE_DATE = "release_date";
        final String MVDB_POPULARITY = "popularity";
        final String MVDB_TITLE = "title";
        final String MVDB_VOTE_AVERAGE = "vote_average";
        final String MVDB_VOTE_COUNT = "vote_count";



        try {
            JSONObject movieListJson = new JSONObject(movieListJsonStr);
            JSONArray movieListArray = movieListJson.getJSONArray(MVDB_RESULTS);
            Vector<ContentValues> cVVector = new Vector<ContentValues>(movieListArray.length());
            Uri uri = null;

            for(int i = 0; i < movieListArray.length(); i++) {
                // These are the values that will be collected.

                int id;
                String overview;
                String poster_path;
                String release_date;
                String popularity;
                String title;
                String vote_average;
                String vote_count;

                JSONObject movieInfo = movieListArray.getJSONObject(i);

                id = movieInfo.getInt(MVDB_ID);
                overview = movieInfo.getString(MVDB_OVERVIEW);
                poster_path = movieInfo.getString(MVDB_POSTER_PATH);
                release_date = movieInfo.getString(MVDB_RELEASE_DATE);
                popularity = movieInfo.getString(MVDB_POPULARITY);
                title = movieInfo.getString(MVDB_TITLE);
                vote_average = movieInfo.getString(MVDB_VOTE_AVERAGE);
                vote_count = movieInfo.getString(MVDB_VOTE_COUNT);


                ContentValues movieValues = new ContentValues();

                if (whatToDo.contains(FE_TOP_RATED)) {
                    
                    uri = MovieTopRated.CONTENT_URI;
                    movieValues.put(MovieTopRated.CL_MOVIE_ID, id);
                    movieValues.put(MovieTopRated.CL_POPULAR_INDEX, popularity);

                    movieValues.put(MovieTopRated.CL_OVERVIEW, overview);
                    movieValues.put(MovieTopRated.CL_POSTER_PATH, poster_path);
                    movieValues.put(MovieTopRated.CL_RELEASE_DATE, release_date);
                    movieValues.put(MovieTopRated.CL_TITLE, title);
                    movieValues.put(MovieTopRated.CL_VOTE_AVERAGE, vote_average);
                    movieValues.put(MovieTopRated.CL_VOTE_COUNT, vote_count);
                    movieValues.put(MovieTopRated.CL_MYNAME, "toprated");


                } else {
                    uri = MoviePopular.CONTENT_URI;
                    movieValues.put(MoviePopular.CL_MOVIE_ID, id);
                    movieValues.put(MoviePopular.CL_POPULAR_INDEX, popularity);

                    movieValues.put(MoviePopular.CL_OVERVIEW, overview);
                    movieValues.put(MoviePopular.CL_POSTER_PATH, poster_path);
                    movieValues.put(MoviePopular.CL_RELEASE_DATE, release_date);
                    movieValues.put(MoviePopular.CL_TITLE, title);
                    movieValues.put(MoviePopular.CL_VOTE_AVERAGE, vote_average);
                    movieValues.put(MoviePopular.CL_VOTE_COUNT, vote_count);
                    movieValues.put(MoviePopular.CL_MYNAME, "popular");

                }

                cVVector.add(movieValues);
            }

            if (whatToDo.contains(FE_POPULAR)) {
                mTM.buildPopularMoviesListToUpdate();
            }

            int inserted = 0;
            // add to database
            if ( cVVector.size() > 0 ) {
                ContentValues[] cvArray = new ContentValues[cVVector.size()];
                cVVector.toArray(cvArray);
                inserted = mContext.getContentResolver().bulkInsert(uri, cvArray);
            }

            if (EXTRA_VERBOSE) Timber.i("download complete. " + inserted + " Inserted");

        } catch (JSONException e) {
            Timber.i(e.getMessage(), e);
            e.printStackTrace();
        }
    }


    private void getMovieDetailsFromJson(String movieInfoJsonStr, String whatToDo
    ) throws JSONException {

        // These are the names of the JSON objects that need to be extracted.

        //  COLUMNS:  id, runtime, youtube trailer array if it exists

        final String MVDB_ID = "id";
        final String MVDB_RUNTIME = "runtime";
        final String MVDB_TRAILERS = "trailers";
        final String MVDB_YOUTUBE_TRAILERS = "youtube";
        final String TRAILER_NAME = "name";
        final String TRAILER_SOURCE = "source";
        final String MVDB_REVIEWS = "reviews";
        final String MVDB_REVIEWS_RESULTS = "results";
        final String REVIEW_NAME = "author";
        final String REVIEW_SOURCE = "url";


        int inserted;

        try {
            JSONObject movieInfoJson = new JSONObject(movieInfoJsonStr);
            JSONObject movieInfoTrailersJson = movieInfoJson.getJSONObject(MVDB_TRAILERS);
            JSONArray movieYoutubeTrailerArray = movieInfoTrailersJson.getJSONArray(MVDB_YOUTUBE_TRAILERS);
            JSONObject movieInfoReviewsJson = movieInfoJson.getJSONObject(MVDB_REVIEWS);
            JSONArray movieInfoReviewsArray = movieInfoReviewsJson.getJSONArray(MVDB_REVIEWS_RESULTS);

            // Vector<ContentValues> cVVector = new Vector<ContentValues>(movieYoutubeTrailerArray.length());

            int id;
            String runtime;
            String trailer1_source = "", trailer2_source = "", trailer3_source = "";
            String trailer1_name = "", trailer2_name = "", trailer3_name = "";
            String review1_source = "", review2_source = "", review3_source = "";
            String review1_name = "", review2_name = "", review3_name = "";

            id = movieInfoJson.getInt(MVDB_ID);  // TODO : error check the movie id
            runtime = movieInfoJson.getString(MVDB_RUNTIME);

            ContentValues movieValues = new ContentValues();

            for (int i = 0; i < movieInfoReviewsArray.length(); i++) {
                // Get the JSON object for this review in the list
                JSONObject review = movieInfoReviewsArray.getJSONObject(i);
                String name = review.getString(REVIEW_NAME);
                String source = review.getString(REVIEW_SOURCE);

                if (i == 0) {
                    review1_source = source;
                    review1_name = name;
                }
                else if (i == 1) {
                    review2_source = source;
                    review2_name = name;
                }
                else if (i == 2) {
                    review3_source = source;
                    review3_name = name;
                }
            }


            movieValues.put(MoviePopular.CL_REVIEW, review1_source);
            movieValues.put(MoviePopular.CL_REVIEW2, review2_source);
            movieValues.put(MoviePopular.CL_REVIEW3, review3_source);

            movieValues.put(MoviePopular.CL_REVIEW_NAME, review1_name);
            movieValues.put(MoviePopular.CL_REVIEW2_NAME, review2_name);
            movieValues.put(MoviePopular.CL_REVIEW3_NAME, review3_name);
            // flag that all data is now loaded...
            movieValues.put(MoviePopular.CL_DETAILS_LOADED, 1);


            for (int i = 0; i < movieYoutubeTrailerArray.length(); i++) {
                // Get the JSON object for this trailer in the list
                JSONObject trailer = movieYoutubeTrailerArray.getJSONObject(i);
                String name = trailer.getString(TRAILER_NAME);
                String source = trailer.getString(TRAILER_SOURCE);

                if (i == 0) {
                    trailer1_source = source;
                    trailer1_name = name;
                }
                else if (i == 1) {
                    trailer2_source = source;
                    trailer2_name = name;
                }
                else if (i == 2) {
                    trailer3_source = source;
                    trailer3_name = name;
                }

            }

            movieValues.put(MoviePopular.CL_RUNTIME, runtime);
            movieValues.put(MoviePopular.CL_TRAILER, trailer1_source);
            movieValues.put(MoviePopular.CL_TRAILER2, trailer2_source);
            movieValues.put(MoviePopular.CL_TRAILER3, trailer3_source);

            movieValues.put(MoviePopular.CL_TRAILER_NAME, trailer1_name);
            movieValues.put(MoviePopular.CL_TRAILER2_NAME, trailer2_name);
            movieValues.put(MoviePopular.CL_TRAILER3_NAME, trailer3_name);

            inserted = mContext.getContentResolver().update(
                    MoviePopular.buildMovieDetailsUri(id),
                    movieValues,
                    MoviePopular.CL_MOVIE_ID + " = " + String.valueOf(id),
                    null);

            inserted = mContext.getContentResolver().update(
                    MovieTopRated.buildMovieDetailsUri(id),
                    movieValues,
                    MoviePopular.CL_MOVIE_ID + " = " + String.valueOf(id),
                    null);

            inserted = mContext.getContentResolver().update(
                    MuviContract.MovieFavorites.buildMovieDetailsUri(id),
                    movieValues,
                    MoviePopular.CL_MOVIE_ID + " = " + String.valueOf(id),
                    null);

        } catch (JSONException e) {
            Timber.i(e.getMessage(), e);
            e.printStackTrace();
        }
    }

    @Override
    protected Void doInBackground(String... params) {

        if (params.length == 0) {
            return null;
        }

        String whatToDo = params[0];
        String arg = params[1];
        String fetch_string;

        if (whatToDo.contains(FE_POPULAR)) {
            fetch_string = "http://api.themoviedb.org/3/movie/popular?page=" + arg;
        } else if (whatToDo.contains(FE_TOP_RATED)) {
            fetch_string = "http://api.themoviedb.org/3/movie/top_rated?page=" + arg;
        } else if (whatToDo.contains(FE_POPULAR_MOVIE_DETAILS)
                || whatToDo.contains(FE_TOP_RATED_MOVIE_DETAILS)) {
            fetch_string =
                    "http://api.themoviedb.org/3/movie/" + arg + "?&append_to_response=trailers,reviews";
        } else {
            throw new RuntimeException(LOG_TAG + "Impossible task here");
        }

        final String APPID = "api_key";
        final String THE_MOVIE_DATABASE_API_KEY = BuildConfig.THE_MOVIE_DATABASE_API_KEY;

        Uri builtUri = Uri.parse(fetch_string).buildUpon()
                .appendQueryParameter(APPID, THE_MOVIE_DATABASE_API_KEY)
                .build();

        Timber.i("fetching URL: " + fetch_string);


        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;


        String movieJsonStr = null;
        try {
            URL url = new URL(builtUri.toString());

            // Create the request to TheMovieDb, and open the connection

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {

                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {

                return null;
            }
            movieJsonStr = buffer.toString();

            if (whatToDo.contains(FE_POPULAR_MOVIE_DETAILS)
                    || whatToDo.contains(FE_TOP_RATED_MOVIE_DETAILS)) {
                getMovieDetailsFromJson(movieJsonStr, whatToDo);
            } else {
                getMovieListFromJson(movieJsonStr, whatToDo);
            }

        } catch (IOException e) {
            Timber.i("Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attempting
            // to parse it.
        } catch (JSONException e) {
            Timber.i(e.getMessage(), e);
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Timber.i("Error closing stream", e);
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        mTM.nextPopularMovie();

    }
}