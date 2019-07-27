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

import com.st18rai.moviesapp.R;
import com.st18rai.moviesapp.adapter.MoviesRecyclerAdapter;
import com.st18rai.moviesapp.interfaces.Constants;
import com.st18rai.moviesapp.ui.BaseFragment;
import com.st18rai.moviesapp.utils.FragmentUtil;
import com.st18rai.moviesapp.viewmodel.MoviesViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

public class TabMoviesListFragment extends BaseFragment implements MoviesRecyclerAdapter.ItemClickListener {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private MoviesViewModel viewModel;
    private MoviesRecyclerAdapter adapter;

    private String sortBy = "popularity.des";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_movies_list, container, false);

        ButterKnife.bind(this, view);

        viewModel = ViewModelProviders.of(getActivity()).get(MoviesViewModel.class);

        setRecycler();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (!viewModel.getMovies(sortBy).hasObservers()) {
            viewModel.getMovies(sortBy).observe(this, movies ->
                    adapter.setData(movies));
        }

    }

    private void setRecycler() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new MoviesRecyclerAdapter(this);
        ScaleInAnimationAdapter newsAnimationAdapter = new ScaleInAnimationAdapter(adapter);
        newsAnimationAdapter.setFirstOnly(false);
        recyclerView.setAdapter(newsAnimationAdapter);
    }

    @Override
    public void onItemClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.MOVIE_ID, adapter.getData().get(position).getId());
        FragmentUtil.replaceFragment(getActivity().getSupportFragmentManager(),
                new DetailMovieFragment(), true, bundle);
    }
}
