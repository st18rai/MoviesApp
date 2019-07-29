package com.st18rai.moviesapp.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.st18rai.moviesapp.R;
import com.st18rai.moviesapp.adapter.MoviesRecyclerAdapter;
import com.st18rai.moviesapp.interfaces.Constants;
import com.st18rai.moviesapp.ui.BaseFragment;
import com.st18rai.moviesapp.utils.FragmentUtil;
import com.st18rai.moviesapp.viewmodel.MoviesViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

public class TabFavoritesMoviesFragment extends BaseFragment implements MoviesRecyclerAdapter.ItemClickListener {
    @BindView(R.id.recycler_favorites)
    RecyclerView recyclerFavorites;

    @BindView(R.id.empty_box)
    LinearLayout emptyBox;

    private MoviesRecyclerAdapter adapter;
    private MoviesViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_favorites_movies, container, false);

        ButterKnife.bind(this, view);

        viewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);

        setRecycler();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel.getFavoriteMovies().observe(this, movieList -> {
            if (movieList.isEmpty()) {
                emptyBox.setVisibility(View.VISIBLE);
                recyclerFavorites.setVisibility(View.GONE);
            } else {
                emptyBox.setVisibility(View.GONE);
                recyclerFavorites.setVisibility(View.VISIBLE);
                adapter.setData(movieList);
            }
        });
    }

    private void setRecycler() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL, false);
        recyclerFavorites.setLayoutManager(linearLayoutManager);

        adapter = new MoviesRecyclerAdapter(this);
        ScaleInAnimationAdapter newsAnimationAdapter = new ScaleInAnimationAdapter(adapter);
        newsAnimationAdapter.setFirstOnly(false);
        recyclerFavorites.setAdapter(newsAnimationAdapter);
    }

    @Override
    public void onItemClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.MOVIE_ID, adapter.getData().get(position).getId());
        bundle.putBoolean(Constants.FROM_FAVORITES, true);
        FragmentUtil.replaceFragment(getActivity().getSupportFragmentManager(),
                new DetailMovieFragment(), true, bundle);
    }
}
