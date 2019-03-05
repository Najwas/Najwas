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
import com.najwa.www.najwa.adaptor.FavoriteMovieRecycleViewAdapter;
import com.najwa.www.najwa.data.entities.MovieEN;
import com.najwa.www.najwa.viewmodel.FavoriteMoviesViewModel;

import java.util.List;

public class FavoriteMoviesFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<MovieEN> favoriteMovies;
    private FavoriteMovieRecycleViewAdapter favoriteMovieRecycleViewAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.favorite_movies_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.favorite_movies_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        ViewModelProviders.of(getActivity()).get(FavoriteMoviesViewModel.class).getAllFavoriteMovies().observe(getActivity(), new Observer<List<MovieEN>>() {
            @Override
            public void onChanged(@Nullable List<MovieEN> movieENS) {
                favoriteMovieRecycleViewAdapter = new FavoriteMovieRecycleViewAdapter(movieENS, getActivity());
                recyclerView.setAdapter(favoriteMovieRecycleViewAdapter);
            }
        });
    }
}