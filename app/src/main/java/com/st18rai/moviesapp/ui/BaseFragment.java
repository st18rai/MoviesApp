package com.st18rai.moviesapp.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.st18rai.moviesapp.R;

import java.util.Objects;

public class BaseFragment extends Fragment {
    private SwipeRefreshLayout swipeRefreshLayout;

    protected View inflateWithLoadingIndicator(int resId, ViewGroup parent) {
        swipeRefreshLayout = new SwipeRefreshLayout(getActivity());
        swipeRefreshLayout.setLayoutParams(
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        swipeRefreshLayout.setEnabled(false);
        View view = LayoutInflater.from(getActivity()).inflate(resId, parent, false);
        swipeRefreshLayout.addView(view);
        return swipeRefreshLayout;
    }

    protected boolean isLoading() {
        return swipeRefreshLayout.isRefreshing();
    }

    protected void setLoading(boolean loading) {
        swipeRefreshLayout.setRefreshing(loading);
    }

    protected void finishActivity() {
        if (getActivity() != null) {
            getActivity().finish();
        }
    }

    protected void setTitle(String title) {
        ((MainActivity) Objects.requireNonNull(getActivity())).setTitle(title);
    }

    protected void showBackButton() {
        ((MainActivity) Objects.requireNonNull(getActivity())).setBackButtonEnabled(true);
    }

    protected void hideBackButton() {
        ((MainActivity) Objects.requireNonNull(getActivity())).setBackButtonEnabled(false);
        ((MainActivity) getActivity()).setTitle(getString(R.string.app_name));
    }

}
