package com.example.movie_ticket_booking_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private Context context;
    private List<MovieResults.ResultsBean> items;
    private onMovieClickListener onMovieClickListener;

    public MovieAdapter(Context context, List<MovieResults.ResultsBean> items, onMovieClickListener onMovieClickListener) {
    this.context = context;
    this.items = items;
    this.onMovieClickListener = onMovieClickListener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.movie_card,parent,false);
        return new MovieViewHolder(view, onMovieClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
    MovieResults.ResultsBean item = items.get(position);
    Glide.with(context).load(item.getPoster_path()).into(holder.poster);
    holder.moviename.setText( item.getTitle());
    holder.rating.setText( String.valueOf(item.getVote_average()));
    holder.releasedate.setText(item.getRelease_date());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView poster;
        TextView moviename, rating, releasedate;
        onMovieClickListener onMovieClickListener;

        public MovieViewHolder(@NonNull final View itemView, onMovieClickListener onMovieClickListener) {
            super(itemView);
            this.onMovieClickListener = onMovieClickListener;

            poster = itemView.findViewById(R.id.poster);
            moviename = itemView.findViewById(R.id.moviename);
            rating = itemView.findViewById(R.id.rating);
            releasedate = itemView.findViewById(R.id.releasedate);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onMovieClickListener.onMovieCardClicked(getAdapterPosition());
        }
    }

    public interface onMovieClickListener
    {
        void onMovieCardClicked(int position);
    }
}
