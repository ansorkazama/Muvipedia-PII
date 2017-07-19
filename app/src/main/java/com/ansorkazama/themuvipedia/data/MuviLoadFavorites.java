package com.ansorkazama.themuvipedia.data;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import com.ansorkazama.themuvipedia.TrafficManager;

import com.ansorkazama.themuvipedia.data.MuviContract.MovieFavorites;

/**
 * This For Project-II
 * Load all favorite movies
 */
public class MuviLoadFavorites extends AsyncTask<String, Void, Void> {

    private TrafficManager mTM;
    private final Context mContext;

    public MuviLoadFavorites(Context context) {
        mContext = context;
        mTM = TrafficManager.getInstance(mContext);
    }

    @Override
    protected Void doInBackground(String... params) {

        Uri uri = MuviContract.MovieFavorites.buildFavoriteMoviesUri();
        Cursor fav_cursor = mContext.getContentResolver().query(
                uri,
                new String[]{
                        MovieFavorites.CL_MOVIE_ID,
                        MovieFavorites.CL_FAVORITE
                },
                null,
                null,
                null,
                null);

        if (fav_cursor == null || !fav_cursor.moveToFirst()) {
            return null;
        }
        Integer favorite_id;
        Integer isFavorite;
        do {
            favorite_id = fav_cursor.getInt(fav_cursor.getColumnIndex(
                    MovieFavorites.CL_MOVIE_ID));
            isFavorite = fav_cursor.getInt(fav_cursor.getColumnIndex(
                    MovieFavorites.CL_FAVORITE));
            if (isFavorite == 1) {
                mTM.addFavoriteID(favorite_id);
            } else {
                int num_deleted = mContext.getContentResolver().delete(
                        uri,
                        MovieFavorites.CL_MOVIE_ID + " = " + favorite_id,
                        null
                        );
            }
        } while (fav_cursor.moveToNext());
        return null;
    }

}
