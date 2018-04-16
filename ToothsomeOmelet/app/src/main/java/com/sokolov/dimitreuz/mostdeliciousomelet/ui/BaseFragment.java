package com.sokolov.dimitreuz.mostdeliciousomelet.ui;

import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment<VM extends Observable> extends Fragment {

    private VM mViewModel;

    public VM getViewModel() {
        return mViewModel;
    }

    public void setViewModel(VM viewModel) {
        this.mViewModel = viewModel;
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        return inflater.inflate(getLayoutResId(), container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    @LayoutRes
    protected abstract int getLayoutResId();

    protected abstract void initViews(@NonNull View view);
}
