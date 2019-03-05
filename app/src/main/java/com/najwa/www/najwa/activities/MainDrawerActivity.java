package com.najwa.www.najwa.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.najwa.www.najwa.R;
import com.najwa.www.najwa.data.entities.MovieEN;
import com.najwa.www.najwa.fragments.FavoriteMoviesFragment;
import com.najwa.www.najwa.fragments.LatestMoviesFragment;
import com.najwa.www.najwa.fragments.SearchMoviesFragment;
import com.najwa.www.najwa.viewmodel.FavoriteMoviesViewModel;

public class MainDrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    LatestMoviesFragment latestMoviesFragment;
    FavoriteMoviesFragment favoritMoviesFragment;
    SearchMoviesFragment searchMoviesFragment;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_drawer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragmentManager = getSupportFragmentManager();
        favoritMoviesFragment = new FavoriteMoviesFragment();
        latestMoviesFragment = new LatestMoviesFragment();
        searchMoviesFragment = new SearchMoviesFragment();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            fragmentManager.beginTransaction().replace(R.id.fragment_container, latestMoviesFragment).commit();
            setTitle("Latest Movies");
        } else {
            setTitle(savedInstanceState.getString("title"));
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.nav_latest) {
            fragmentManager.beginTransaction().replace(R.id.fragment_container, latestMoviesFragment).commit();
            setTitle("Latest Movies");
        } else if (id == R.id.nav_favorite) {
            fragmentManager.beginTransaction().replace(R.id.fragment_container, favoritMoviesFragment).commit();
            setTitle("Favorite Movies");
        } else if (id == R.id.nav_search) {
            fragmentManager.beginTransaction().replace(R.id.fragment_container, searchMoviesFragment).commit();
            setTitle("Search Movies");
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void addToFavorite(MovieEN newFavMovie) {
        ViewModelProviders.of(this).get(FavoriteMoviesViewModel.class).insertMovieFavorite(newFavMovie);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("inMainVisible", false);
        outState.putString("title", getTitle().toString());
    }

    public void removeFromFavorite(MovieEN movieEN) {
        ViewModelProviders.of(this).get(FavoriteMoviesViewModel.class).removeFromFavorites(movieEN);
    }
}
