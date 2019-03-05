package com.najwa.www.najwa.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.najwa.www.najwa.R;
import com.najwa.www.najwa.adaptor.PopularMovieRecycleViewAdapter;
import com.najwa.www.najwa.api.MovieApi;
import com.najwa.www.najwa.models.Movie;
import com.najwa.www.najwa.viewmodel.LatestMoviesViewModel;

import java.util.List;

public class DetailAndSimilarActivity extends AppCompatActivity {

    ImageView poster;
    TextView title, releaseDate, overview;

    private RecyclerView recyclerView;
    private List<Movie> movieList;
    private PopularMovieRecycleViewAdapter popularMovieRecycleViewAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_similar);
        poster = findViewById(R.id.movie_poster);
        title = findViewById(R.id.title);
        releaseDate = findViewById(R.id.release_date);
        overview = findViewById(R.id.overview);
        recyclerView = findViewById(R.id.similar_movies);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Glide.with(this)
                    .load(Uri.parse(MovieApi.IMAGE_BASE_URL + extras.getString("posterPath")))
                    .into(poster);

            title.setText(extras.getString("title"));
            releaseDate.setText(extras.getString("releaseDate"));
            overview.setText(extras.getString("overview"));
            setTitle(extras.getString("title"));
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setHasFixedSize(true);

        ViewModelProviders.of(this).get(LatestMoviesViewModel.class).getSimilarMovies(Integer.parseInt(extras.getString("id"))).observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                popularMovieRecycleViewAdapter = new PopularMovieRecycleViewAdapter(movies, getApplicationContext());
                recyclerView.setAdapter(popularMovieRecycleViewAdapter);
            }
        });
    }
}
