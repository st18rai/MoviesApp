package com.st18rai.moviesapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.st18rai.moviesapp.R;
import com.st18rai.moviesapp.model.Movie;
import com.st18rai.moviesapp.network.ApiClient;
import com.st18rai.moviesapp.utils.MovieUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesRecyclerAdapter extends RecyclerView.Adapter<MoviesRecyclerAdapter.MoviesHolder> {
    private List<Movie> data;
    private ItemClickListener itemClickListener;

    public MoviesRecyclerAdapter(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public List<Movie> getData() {
        return data;
    }

    public void setData(List<Movie> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void addData(List<Movie> data) {
        this.data.addAll(data);
        this.data = MovieUtils.removeDuplicates(new ArrayList<>(this.data));
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MoviesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie, parent, false);

        final MoviesHolder holder = new MoviesHolder(view);

        holder.layout.setOnClickListener(view1 ->
                itemClickListener.onItemClick(holder.getAdapterPosition()));

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MoviesHolder holder, int position) {

        Movie movie = data.get(position);

        holder.title.setText(movie.getTitle());
        holder.description.setText(movie.getOverview());

        Glide.with(holder.getContext()).load(ApiClient.BASE_IMAGE_URL + movie.getPosterPath()).into(holder.poster);

    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public interface ItemClickListener {
        void onItemClick(int position);
    }

    static class MoviesHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageView_poster)
        ImageView poster;

        @BindView(R.id.textView_title)
        TextView title;

        @BindView(R.id.textView_desc)
        TextView description;

        private View layout;

        MoviesHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            layout = v;
        }

        public Context getContext() {
            return layout.getContext();
        }
    }
}
