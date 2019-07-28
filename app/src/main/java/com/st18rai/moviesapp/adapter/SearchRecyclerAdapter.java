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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchRecyclerAdapter extends RecyclerView.Adapter<SearchRecyclerAdapter.SearchHolder> {
    private List<Movie> data;
    private ItemClickListener itemClickListener;

    public SearchRecyclerAdapter(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public List<Movie> getData() {
        return data;
    }

    public void setData(List<Movie> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_search, parent, false);

        final SearchHolder holder = new SearchHolder(view);

        holder.layout.setOnClickListener(view1 -> itemClickListener.onItemClick(holder.getAdapterPosition()));

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final SearchHolder holder, int position) {

        Movie movie = data.get(position);

        holder.title.setText(movie.getTitle());
        holder.date.setText(movie.getReleaseDate());

        Glide.with(holder.getContext()).load(ApiClient.BASE_IMAGE_URL + movie.getPosterPath()).into(holder.poster);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public interface ItemClickListener {
        void onItemClick(int position);
    }

    static class SearchHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageView_poster)
        ImageView poster;

        @BindView(R.id.textView_title)
        TextView title;

        @BindView(R.id.textView_date)
        TextView date;

        private View layout;

        SearchHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            layout = v;
        }

        public Context getContext() {
            return layout.getContext();
        }
    }
}
