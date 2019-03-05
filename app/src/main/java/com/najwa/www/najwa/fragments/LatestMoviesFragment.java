package com.najwa.www.najwa.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.najwa.www.najwa.R;
import com.najwa.www.najwa.adaptor.PopularMovieRecycleViewAdapter;
import com.najwa.www.najwa.models.Movie;
import com.najwa.www.najwa.viewmodel.LatestMoviesViewModel;

import java.util.List;

public class LatestMoviesFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<Movie> movieList;
    private PopularMovieRecycleViewAdapter popularMovieRecycleViewAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.latest_movies_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.popular_movies_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        ViewModelProviders.of(getActivity()).get(LatestMoviesViewModel.class).getMovies().observe(getActivity(), new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                popularMovieRecycleViewAdapter = new PopularMovieRecycleViewAdapter(movies, getActivity());
                recyclerView.setAdapter(popularMovieRecycleViewAdapter);
            }
        });
    }
}
