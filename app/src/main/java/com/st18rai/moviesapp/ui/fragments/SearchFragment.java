package com.st18rai.moviesapp.ui.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.st18rai.moviesapp.R;
import com.st18rai.moviesapp.adapter.MoviesRecyclerAdapter;
import com.st18rai.moviesapp.adapter.SearchRecyclerAdapter;
import com.st18rai.moviesapp.interfaces.Constants;
import com.st18rai.moviesapp.model.Movie;
import com.st18rai.moviesapp.network.ApiClient;
import com.st18rai.moviesapp.ui.BaseFragment;
import com.st18rai.moviesapp.ui.MainActivity;
import com.st18rai.moviesapp.utils.FragmentUtil;
import com.st18rai.moviesapp.utils.KeyboardUtils;
import com.st18rai.moviesapp.viewmodel.MoviesViewModel;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SearchFragment extends BaseFragment implements SearchRecyclerAdapter.ItemClickListener {

    @BindView(R.id.editText_search)
    EditText searchField;

    @BindView(R.id.recyclerView_search)
    RecyclerView recyclerView;

    @BindView(R.id.not_found_box)
    LinearLayout notFoundBox;

    private MoviesViewModel viewModel;
    private SearchRecyclerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        ButterKnife.bind(this, view);

        viewModel = ViewModelProviders.of(getActivity()).get(MoviesViewModel.class);

        setRecycler();

        setSearchField();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        showBackButton();
        setTitle(getString(R.string.search));

    }

    @Override
    public void onStop() {
        super.onStop();

        hideBackButton();
    }

    private void setSearchField() {
        searchField.setOnEditorActionListener((v, actionId, event) -> {
            boolean handled = false;
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (!TextUtils.isEmpty(searchField.getText().toString())) {
                    search(searchField.getText().toString());
                    KeyboardUtils.hideSoftKeyboard(getActivity());
                } else {
                    Toast.makeText(getContext(), R.string.search_hint, Toast.LENGTH_SHORT).show();
                }
                handled = true;
            }
            return handled;
        });

        searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 1)
                    search(s.toString());
            }
        });
    }

    private void setRecycler() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext(),
                RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new SearchRecyclerAdapter(this);
        recyclerView.setAdapter(adapter);
    }

    @SuppressLint("CheckResult")
    private void search(String s) {
        viewModel.searchForMovie(s).observe(this, movieList -> {
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

    @OnClick(R.id.imageView_cancel)
    public void onCancelClick(View view) {
        searchField.setText("");
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.MOVIE_ID, adapter.getData().get(position).getId());
        FragmentUtil.replaceFragment(getFragmentManager(),
                new DetailMovieFragment(), true, bundle);
    }
}
