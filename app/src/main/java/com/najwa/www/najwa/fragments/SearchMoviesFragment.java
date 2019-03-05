package com.najwa.www.najwa.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.najwa.www.najwa.R;
import com.najwa.www.najwa.adaptor.PopularMovieRecycleViewAdapter;
import com.najwa.www.najwa.models.Movie;
import com.najwa.www.najwa.viewmodel.LatestMoviesViewModel;

import java.util.List;

public class SearchMoviesFragment extends Fragment {

    private final String TAG = "SearchMoviesFragment";

    private RecyclerView recyclerView;
    private List<Movie> movieList;
    private PopularMovieRecycleViewAdapter popularMovieRecycleViewAdapter;
    private EditText name, year;
    private Button search;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.search_movie_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.searched_movies_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        name = view.findViewById(R.id.movie_name);
        year = view.findViewById(R.id.movie_year);
        search = view.findViewById(R.id.search);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(name.getText().toString()) && !TextUtils.isEmpty(year.getText().toString())) {
                    Log.d(TAG, "onClick: clicked");
                    ViewModelProviders.of(getActivity()).get(LatestMoviesViewModel.class).getSearchedMovies(name.getText().toString(), year.getText().toString()).observe(getActivity(), new Observer<List<Movie>>() {
                        @Override
                        public void onChanged(@Nullable List<Movie> movies) {
                            popularMovieRecycleViewAdapter = new PopularMovieRecycleViewAdapter(movies, getActivity());
                            recyclerView.setAdapter(popularMovieRecycleViewAdapter);
                        }
                    });
                } else {
                    Toast.makeText(getActivity(), "Please enter name and year", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
