package com.st18rai.moviesapp.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.st18rai.moviesapp.R;
import com.st18rai.moviesapp.adapter.MoviesRecyclerAdapter;
import com.st18rai.moviesapp.interfaces.Constants;
import com.st18rai.moviesapp.model.Genre;
import com.st18rai.moviesapp.ui.BaseFragment;
import com.st18rai.moviesapp.utils.FragmentUtil;
import com.st18rai.moviesapp.viewmodel.MoviesViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GenreFilterFragment extends BaseFragment implements MoviesRecyclerAdapter.ItemClickListener {

    @BindView(R.id.genres)
    TextView genresTextView;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.not_found_box)
    LinearLayout notFoundBox;

    private MoviesViewModel viewModel;
    private MoviesRecyclerAdapter adapter;
    private List<Genre> genreList;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_genre_filter, container, false);

        ButterKnife.bind(this, view);

        viewModel = ViewModelProviders.of(getActivity()).get(MoviesViewModel.class);

        setRecycler();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        showBackButton();
        setTitle(getString(R.string.select_genre));
    }

    @Override
    public void onStop() {
        super.onStop();

        hideBackButton();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel.getGenres().observe(this, genres -> {
            genreList = new ArrayList<>();
            genreList = genres;
        });

    }

    private void setRecycler() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext(),
                RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new MoviesRecyclerAdapter(this);
        recyclerView.setAdapter(adapter);
    }

    private void showChooseDialog(String[] genres) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.select_genre)
                .setMultiChoiceItems(genres, null,
                        (dialogInterface, position, isChecked) -> {

                            if (isChecked) {
                                genreList.get(position).setSelected(true);
                            } else {
                                genreList.get(position).setSelected(false);
                            }
                        })
                .setPositiveButton(R.string.ok, (dialog, id) -> {
                    // User clicked OK button
                    genresTextView.setText(viewModel.prepareSelectedGenres(genreList, 0));
                    getMoviesByGenre(viewModel.prepareSelectedGenres(genreList, 1));
                })
                .setNegativeButton(R.string.cancel, (dialog, id) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void getMoviesByGenre(String genreID) {
        viewModel.getMoviesByGenre(Constants.SORT_BY_POPULARITY, genreID).observe(this,
                movieList -> {
                    if (movieList.isEmpty()) {
                        notFoundBox.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    } else {
                        notFoundBox.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        adapter.setData(movieList);
                    }
                });
    }

    @OnClick(R.id.fab)
    public void onFabClick() {
        viewModel.getGenres();
        showChooseDialog(viewModel.prepareGenresArray(genreList));
    }

    @Override
    public void onItemClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.MOVIE_ID, adapter.getData().get(position).getId());
        FragmentUtil.replaceFragment(getFragmentManager(),
                new DetailMovieFragment(), true, bundle);
    }
}
