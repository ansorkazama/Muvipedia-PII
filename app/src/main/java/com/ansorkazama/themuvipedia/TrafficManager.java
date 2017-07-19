package com.ansorkazama.themuvipedia;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.IntDef;
import timber.log.Timber;

import com.ansorkazama.themuvipedia.data.MuviContract;
import com.ansorkazama.themuvipedia.data.MuviLoadDetails;
import com.ansorkazama.themuvipedia.data.MuviLoadFavorites;
import com.ansorkazama.themuvipedia.utils.Utility;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashSet;
import java.util.Iterator;


public class TrafficManager {
    private static final String LOG_TAG = TrafficManager.class.getSimpleName();
    private static TrafficManager mInstance;
    private Context mContext;
    private MuviLoadFavorites mLoadFavorites;
    private MuviLoadDetails mLoadDetails;
    private static Iterator mIterator;

    private TrafficManager(Context context) {
        mContext = context;
    }

    public static synchronized TrafficManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new TrafficManager(context);

            if (BuildConfig.DEBUG) {
                Timber.plant(new Timber.DebugTree());
            }
        }
        return mInstance;
    }


    public static final int POPULAR = 0;
    public static final int TOP_RATED = 1;
    public static final int FAVORITES = 2;
    @IntDef( {POPULAR, TOP_RATED, FAVORITES})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DisplayMode {}

    public void setDisplayMode(@DisplayMode int displayMode) {
        mTMdisplayMode = displayMode;
    }

    @TrafficManager.DisplayMode
    int mTMdisplayMode = POPULAR;

    @TrafficManager.DisplayMode
    public int getDisplayMode() {
        return mTMdisplayMode;
    }

    private String mDetailDisplayMode = null;

    public String getDetailDisplayMode() {
        return mDetailDisplayMode;
    }

    public void setDetailDisplayMode(String mDisplayMode) {
        this.mDetailDisplayMode = mDisplayMode;
    }

    private int mPopularMoviesPage = 0;
    private int mTopRatedMoviesPage = 0;

    public int getTopRatedMoviesPageNumber() {
        return mTopRatedMoviesPage;
    }

    public void setTopRatedMoviesPageNumber(int mTopRatedMoviesPage) {
        this.mTopRatedMoviesPage = mTopRatedMoviesPage;
    }

    public int getPopularMoviesPageNumber() {
        return mPopularMoviesPage;
    }

    public void setPopularMoviesPageNumber(int mPopularMoviesPage) {
        this.mPopularMoviesPage = mPopularMoviesPage;
    }

    public int calculatePopularMoviesPageNumber() {
        Uri uri = MuviContract.MoviePopular.buildPopularMoviesUri();
        Cursor cursor = mContext.getContentResolver().query(
                uri,
                new String[]{
                        MuviContract.MoviePopular.CL_MOVIE_ID,
                },
                null,
                null,
                null,
                null);


        //  TODO: assumption is that there are 20 movies per page

        if (cursor == null || !cursor.moveToFirst()) {
            return 0;
        }
        int count = cursor.getCount();
        if ((count % 20) != 0) {
            Timber.i("there are duplicate movie_ids!");
            count = count+19;
        }
        mPopularMoviesPage = count / 20;
        cursor.close();
        Timber.i("Popular page count = " + mPopularMoviesPage  + ", count is " + count);
        return (mPopularMoviesPage);
    }

    public int calculateTopRatedMoviesPageNumber() {
        Uri uri = MuviContract.MovieTopRated.buildTopRatedMoviesUri();
        Cursor cursor = mContext.getContentResolver().query(
                uri,
                new String[]{
                        MuviContract.MovieTopRated.CL_MOVIE_ID,
                },
                null,
                null,
                null,
                null);

        //  TODO: assumption is that there are 20 movies per page

        if (cursor == null || !cursor.moveToFirst()) {
            return 0;
        }
        int count = cursor.getCount();
        if ((count % 20) != 0) {
            Timber.i("there are duplicate movie_ids!!");
            count = count+19;
        }
        mTopRatedMoviesPage = count / 20;
        cursor.close();
        Timber.i("Top rated page count = " + mTopRatedMoviesPage + ", count is " + count);
        return (mTopRatedMoviesPage);
    }


    private static HashSet<Integer> mFavoritesSet = new HashSet<>();

    private static HashSet<Integer> mPopularUpdatePending = null;



    public void addFavoriteID(int movie_id) {
        mFavoritesSet.add(movie_id);
    }

    public void remFavoriteID(int movie_id) {
        mFavoritesSet.remove(movie_id);
    }

    public Boolean cheFavoriteID(int movie_id) {
        return mFavoritesSet.contains(movie_id);
    }

    public int numFavorites() {
        return mFavoritesSet.size();
    }

    public void setScrolling(Boolean mScrolling) {
        this.mScrolling = mScrolling;
    }

    private Boolean mScrolling = false;

    public void validateFavorites() {
        Iterator iterator = mFavoritesSet.iterator();
        mLoadFavorites = new MuviLoadFavorites(mContext);
        mLoadFavorites.execute("do this");

    }


    // TODO: extend detail read-ahead to Top Rated movies

    public void buildPopularMoviesListToUpdate() {
        if (!Utility.isNetworkAvailable(mContext)) {
            return;
        }

        if (Utility.isNetworkAvailable(mContext)) {
            return;
        }


        if (mPopularUpdatePending != null) {
            return;
        }
        mPopularUpdatePending = new HashSet<>();
        // mPopularUpdatePending.clear();
        mLoadDetails = new MuviLoadDetails(mContext);
        mLoadDetails.execute("do this");
    }

    public void addPopularMovieToUpdate(int movie_id) {
        mPopularUpdatePending.add(movie_id);
    }

    public void iteratePopularMoviesList() {

        mIterator = mPopularUpdatePending.iterator();
        if (!mIterator.hasNext()) {
            mIterator = null;
            return;
        }

        int movie_id = (int) mIterator.next();

        FetchMovieListTask fetchMovieListTask = new FetchMovieListTask(mContext);
        fetchMovieListTask.execute(
                fetchMovieListTask.FE_POPULAR_MOVIE_DETAILS,
                String.valueOf(movie_id));
    }

    public void nextPopularMovie() {
        if (mIterator == null) {
            return;
        }

        if (!mIterator.hasNext()) {
            mIterator = null;
            mPopularUpdatePending = null;
            return;
        }

        if (mScrolling) {
            mIterator = null;
            mPopularUpdatePending = null;
            return;
        }
        int movie_id = (int) mIterator.next();

        FetchMovieListTask fetchMovieListTask = new FetchMovieListTask(mContext);
        fetchMovieListTask.execute(
                fetchMovieListTask.FE_POPULAR_MOVIE_DETAILS,
                String.valueOf(movie_id));

    }


    private  int mPopularPosition = -1;
    private  int mTopratedPosition = -1;
    private  int mFavoritesPosition = -1;

    public  int getPopularPosition() {
        return mPopularPosition;
    }

    public  void setPopularPosition(int position) {
        mPopularPosition = position;
    }

    public  int getTopratedPosition() {
        return mTopratedPosition;
    }

    public  void setTopratedPosition(int position) {
        mTopratedPosition = position;
    }

    public  int getFavoritesPosition() {
        return mFavoritesPosition;
    }

    public  void setFavoritesPosition(int position) {
        mFavoritesPosition = position;
    }
}
