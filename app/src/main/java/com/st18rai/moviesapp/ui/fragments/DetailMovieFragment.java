package com.st18rai.moviesapp.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.st18rai.moviesapp.R;
import com.st18rai.moviesapp.interfaces.Constants;
import com.st18rai.moviesapp.model.MovieDetails;
import com.st18rai.moviesapp.network.ApiClient;
import com.st18rai.moviesapp.ui.BaseFragment;
import com.st18rai.moviesapp.ui.MainActivity;
import com.st18rai.moviesapp.viewmodel.MoviesViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailMovieFragment extends BaseFragment {
    @BindView(R.id.poster)
    ImageView poster;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.date)
    TextView date;

    @BindView(R.id.genre)
    TextView genre;

    @BindView(R.id.vote_count)
    TextView voteCount;

    @BindView(R.id.vote_average)
    TextView voteAverage;

    @BindView(R.id.description)
    TextView description;

    private MoviesViewModel viewModel;
    private int movieID;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        ButterKnife.bind(this, view);

        if (getArguments() != null) {
            movieID = getArguments().getInt(Constants.MOVIE_ID);
        }

        viewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        // set title and toolbar state
        ((MainActivity) getActivity()).setBackButtonEnabled(true);

    }

    @Override
    public void onStop() {
        super.onStop();
        ((MainActivity) getActivity()).setBackButtonEnabled(false);
        ((MainActivity) getActivity()).setTitle(getString(R.string.app_name));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel.getMovieDetails(movieID).observe(this, movieDetails ->
                setDataToUI(movieDetails));
    }

    private void setDataToUI(MovieDetails data) {

        ((MainActivity) getActivity()).setTitle(data.getTitle());

        description.setText(data.getOverview());

        date.setText(getString(R.string.date, data.getReleaseDate()));
        voteCount.setText(getString(R.string.vote_count, data.getVoteCount()));
        voteAverage.setText(getString(R.string.vote_average, data.getVoteAverage()));
        genre.setText(getString(R.string.genre, prepareGenres(data.getGenres())));

        Glide.with(getContext())
                .load(ApiClient.BASE_IMAGE_URL + data.getPosterPath())
                .into(poster);
    }

    private String prepareGenres(List<MovieDetails.Genre> genreList) {
        StringBuilder genres = new StringBuilder();
        for (int i = 0; i < genreList.size(); i++) {
            genres.append(",").append(genreList.get(i).getName());
        }
        genres.deleteCharAt(0);

        return genres.toString();
    }
}
