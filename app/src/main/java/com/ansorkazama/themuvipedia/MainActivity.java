package com.ansorkazama.themuvipedia;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.ansorkazama.themuvipedia.utils.Utility;

import static com.ansorkazama.themuvipedia.TrafficManager.FAVORITES;
import static com.ansorkazama.themuvipedia.TrafficManager.POPULAR;
import static com.ansorkazama.themuvipedia.TrafficManager.TOP_RATED;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, PopularMovieFragment.Callback {

    private static final String DTFRAGMENT_TAG = "DFTAG";
    public static final String DS_MODE = "displaymode";
    private final String LOG_TAG = TrafficManager.class.getSimpleName();

    private final TrafficManager mTM = TrafficManager.getInstance(this);
    private boolean mTwoPane;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mTM.validateFavorites();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (findViewById(R.id.movie_detail_container) != null) {

            mTwoPane = true;

            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.movie_detail_container, new MovieDetailFragment(), DTFRAGMENT_TAG)
                        .commit();
            }
        } else {
            mTwoPane = false;
            //noinspection ConstantConditions
            getSupportActionBar().setElevation(0f);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            super.onBackPressed();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

         // TODO : get status of popular or top_rated...


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.popular_movies) {
            setDisplayMode(POPULAR);
            setTitle(R.string.drawer_popular_movies);
            mTM.validateFavorites();
        } else if (id == R.id.highest_rated_movies) {
            setDisplayMode(TOP_RATED);
            setTitle(R.string.drawer_top_rated);
            mTM.validateFavorites();
        } else if (id == R.id.favorite_movies) {
            if (mTM.numFavorites() > 0) {
                setDisplayMode(FAVORITES);
                setTitle(R.string.drawer_favorites);
            } else {
                showNoFavoritesSetDialog();
                return false;
            }
        }
        else if (id == R.id.nav_help) {
            Utility.viewUrl(getString(R.string.drawer_help_url), this);
        } else if (id == R.id.nav_about) {
            Utility.viewUrl(getString(R.string.drawer_info_url), this);
        } else if (id == R.id.nav_whodunnit) {
            Utility.viewUrl(getString(R.string.drawer_whodunnit_url), this);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPause() {
        super.onPause();
        @TrafficManager.DisplayMode int display_mode = getDisplayMode();
        mTM.setDisplayMode(display_mode);
    }

    @Override
    public void onResume() {
        super.onResume();
        @TrafficManager.DisplayMode int display_mode = mTM.getDisplayMode();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        switch (display_mode) {
            case POPULAR:
                setTitle(R.string.drawer_popular_movies);
                navigationView.setCheckedItem(R.id.popular_movies);
                break;
            case TOP_RATED:
                setTitle(R.string.drawer_top_rated);
                navigationView.setCheckedItem(R.id.highest_rated_movies);
                break;
            case FAVORITES:
                setTitle(R.string.drawer_favorites);
                navigationView.setCheckedItem(R.id.favorite_movies);
                break;
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        @TrafficManager.DisplayMode int display_mode = getDisplayMode();
        outState.putInt(DS_MODE, display_mode);
        super.onSaveInstanceState(outState);
    }

    public void onItemSelected(Uri contentUri) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.

            Bundle args = new Bundle();
            args.putParcelable(MovieDetailFragment.DETAIL_URI, contentUri);

            MovieDetailFragment fragment = new MovieDetailFragment();
            fragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_detail_container, fragment, DTFRAGMENT_TAG)
                    .commit();
        } else {

            Intent detailsIntent = new Intent(this, MovieDetailActivity.class)
                    .setData(contentUri);
            startActivity(detailsIntent);
        }
    }

    public void setDisplayMode(@TrafficManager.DisplayMode int mode) {
        PopularMovieFragment frag = (PopularMovieFragment)
                getSupportFragmentManager().findFragmentById(R.id.movie_fragment);
        if (frag != null) {
            frag.updateDisplayMode(mode);
        }
    }


    @TrafficManager.DisplayMode
    public int getDisplayMode() {
        PopularMovieFragment frag = (PopularMovieFragment)
                getSupportFragmentManager().findFragmentById(R.id.movie_fragment);
        if (frag != null) {
            return (frag.getDisplayMode());
        }
        return POPULAR;
    }

    /*
     * with acknowledgements to:
     * http://stackoverflow.com/a/14134437/3853712
     */
    public void showNoFavoritesSetDialog() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle("Favorites");
        builder1.setIcon(R.drawable.ic_favorite_selected);
        builder1.setMessage(R.string.dialog_no_favorites_set);
        builder1.setCancelable(true);
        builder1.setNeutralButton(R.string.dialog_no_favorites_ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert1 = builder1.create();
        alert1.show();
    }
}
