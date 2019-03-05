package com.najwa.www.najwa.adaptor;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.najwa.www.najwa.R;
import com.najwa.www.najwa.activities.DetailAndSimilarActivity;
import com.najwa.www.najwa.activities.MainDrawerActivity;
import com.najwa.www.najwa.api.MovieApi;
import com.najwa.www.najwa.data.entities.MovieEN;

import java.util.List;

public class FavoriteMovieRecycleViewAdapter extends RecyclerView.Adapter<FavoriteMovieRecycleViewAdapter.ViewHolder> {

    private List<MovieEN> movieList;
    private Context context;

    public FavoriteMovieRecycleViewAdapter() {
    }

    public FavoriteMovieRecycleViewAdapter(List<MovieEN> movieList, Context context) {
        this.movieList = movieList;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new FavoriteMovieRecycleViewAdapter.ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.popular_movies_recycle_view_list_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        Uri uri = Uri.parse(MovieApi.IMAGE_BASE_URL + movieList.get(viewHolder.getAdapterPosition()).getPosterPath());

        Glide.with(context)
                .load(uri)
                .into(viewHolder.poster);

        viewHolder.overview.setText(movieList.get(viewHolder.getAdapterPosition()).getOverview());
        viewHolder.releaseDate.setText(movieList.get(viewHolder.getAdapterPosition()).getReleaseDate());
        viewHolder.title.setText(movieList.get(viewHolder.getAdapterPosition()).getTitle());

        viewHolder.addToFavorite.setVisibility(View.INVISIBLE);
        viewHolder.removeFromFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (context instanceof MainDrawerActivity) {
                    ((MainDrawerActivity) context).removeFromFavorite(movieList.get(viewHolder.getAdapterPosition()));
                }
            }
        });

        viewHolder.cardViewContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailAndSimilarActivityIntent = new Intent(context.getApplicationContext(), DetailAndSimilarActivity.class);
                detailAndSimilarActivityIntent.putExtra("id", String.valueOf(movieList.get(viewHolder.getAdapterPosition()).getId_movie()));
                detailAndSimilarActivityIntent.putExtra("voteAverage", String.valueOf(movieList.get(viewHolder.getAdapterPosition()).getVoteAverage()));
                detailAndSimilarActivityIntent.putExtra("title", String.valueOf(movieList.get(viewHolder.getAdapterPosition()).getTitle()));
                detailAndSimilarActivityIntent.putExtra("posterPath", movieList.get(viewHolder.getAdapterPosition()).getPosterPath());
                detailAndSimilarActivityIntent.putExtra("overview", movieList.get(viewHolder.getAdapterPosition()).getOverview());
                detailAndSimilarActivityIntent.putExtra("releaseDate", movieList.get(viewHolder.getAdapterPosition()).getReleaseDate());
                context.startActivity(detailAndSimilarActivityIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (this.movieList == null)
            return 0;
        else
            return this.movieList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView poster;
        TextView title, releaseDate, overview;
        ImageView addToFavorite, removeFromFavorite;
        RelativeLayout cardViewContainer;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            poster = itemView.findViewById(R.id.poster_image);
            title = itemView.findViewById(R.id.title);
            releaseDate = itemView.findViewById(R.id.release_date);
            overview = itemView.findViewById(R.id.overview);
            addToFavorite = itemView.findViewById(R.id.add_to_favorite);
            removeFromFavorite = itemView.findViewById(R.id.remove_from_favorite);
            cardViewContainer = itemView.findViewById(R.id.card_view_container);
        }
    }
}
