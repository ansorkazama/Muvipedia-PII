package com.ansorkazama.themuvipedia.data;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import com.ansorkazama.themuvipedia.TrafficManager;

import java.util.Iterator;

/**
 *  connection checker, if connect to network, then load the details of popular and top rated
 *  in databases
 */

public class MuviLoadDetails extends AsyncTask<String, Void, Void> {

    private final Context mContext;
    private TrafficManager mTM;
    private Iterator mIterator;

    public MuviLoadDetails(Context context) {
        mContext = context;
        mTM = TrafficManager.getInstance(mContext);
    }



    @Override
    protected Void doInBackground(String... params) {

        Uri uri = MuviContract.MoviePopular.buildPopularMoviesUri();
        Cursor cursor_needs_details = mContext.getContentResolver().query(
                uri,
                new String[]{
                        MuviContract.MoviePopular.CL_MOVIE_ID
                },
                MuviContract.MoviePopular.CL_DETAILS_LOADED + " = 1",
                null,
                null,
                null);

        if (cursor_needs_details == null || !cursor_needs_details.moveToFirst()) {
            cursor_needs_details.close();
            return null;
        }


        int movie_id;
        do {
            movie_id = cursor_needs_details.getInt(
                    cursor_needs_details.getColumnIndex(MuviContract.MoviePopular.CL_MOVIE_ID));
            mTM.addPopularMovieToUpdate(movie_id);
        } while (cursor_needs_details.moveToNext());
        cursor_needs_details.close();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        mTM.iteratePopularMoviesList();
    }
}


