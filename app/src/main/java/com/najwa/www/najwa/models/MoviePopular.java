package com.najwa.www.najwa.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MoviePopular {

    @SerializedName("page")
    private int page;

    @SerializedName("total_results")
    private int totalResults;

    @SerializedName("total_pages")
    private int totalPages;

    @SerializedName("results")
    private List<Movie> popularMovies;

    public MoviePopular() {
    }

    public MoviePopular(int page, int totalResults, int totalPages, List<Movie> popularMovies) {
        this.page = page;
        this.totalResults = totalResults;
        this.totalPages = totalPages;
        this.popularMovies = popularMovies;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<Movie> getPopularMovies() {
        return popularMovies;
    }

    public void setPopularMovies(List<Movie> popularMovies) {
        this.popularMovies = popularMovies;
    }
}
