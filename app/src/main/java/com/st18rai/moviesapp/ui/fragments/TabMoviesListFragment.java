package com.st18rai.moviesapp.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.st18rai.moviesapp.R;
import com.st18rai.moviesapp.adapter.MoviesRecyclerAdapter;
import com.st18rai.moviesapp.interfaces.Constants;
import com.st18rai.moviesapp.ui.BaseFragment;
import com.st18rai.moviesapp.utils.FragmentUtil;
import com.st18rai.moviesapp.viewmodel.MoviesViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

public class TabMoviesListFragment extends BaseFragment implements MoviesRecyclerAdapter.ItemClickListener,
        SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;

    private MoviesViewModel viewModel;
    private MoviesRecyclerAdapter adapter;
    private int pageToLoad = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_movies_list, container, false);

        ButterKnife.bind(this, view);

        viewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);

        swipeRefresh.setOnRefreshListener(this);
        swipeRefresh.setRefreshing(true);

        setRecycler();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (!viewModel.getMovies(Constants.SORT_BY_POPULARITY).hasObservers()) {
            viewModel.getMovies(Constants.SORT_BY_POPULARITY).observe(this, movies ->
            {
                swipeRefresh.setRefreshing(false);
                adapter.setData(movies);
            });
        }

    }

    private void setRecycler() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new MoviesRecyclerAdapter(this);
        ScaleInAnimationAdapter moviesAnimationAdapter = new ScaleInAnimationAdapter(adapter);
        moviesAnimationAdapter.setFirstOnly(true);

        recyclerView.setAdapter(moviesAnimationAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == adapter.getData().size() - 1) {
                    //bottom of list
                    loadMoreData();
                }
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.MOVIE_ID, adapter.getData().get(position).getId());
        FragmentUtil.replaceFragment(getActivity().getSupportFragmentManager(),
                new DetailMovieFragment(), true, bundle);
    }

    @Override
    public void onRefresh() {
        viewModel.getMovies(Constants.SORT_BY_POPULARITY).observe(this, movies ->
        {
            swipeRefresh.setRefreshing(false);
            pageToLoad = 1;
            adapter.setData(movies);
        });
    }

    private void loadMoreData() {
        swipeRefresh.setRefreshing(true);

        pageToLoad++;

        viewModel.getMoreMovies(pageToLoad, Constants.SORT_BY_POPULARITY).observe(this, movieList -> {
            swipeRefresh.setRefreshing(false);
            adapter.addData(movieList);
        });
    }
}
