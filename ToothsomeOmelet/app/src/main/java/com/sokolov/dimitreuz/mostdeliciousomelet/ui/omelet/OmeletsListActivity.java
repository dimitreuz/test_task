package com.sokolov.dimitreuz.mostdeliciousomelet.ui.omelet;

import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;

import com.sokolov.dimitreuz.mostdeliciousomelet.omelet.OmeletsListViewModel;
import com.sokolov.dimitreuz.mostdeliciousomelet.ui.BaseActivity;

public class OmeletsListActivity extends BaseActivity<OmeletsListViewModel> {

    @NonNull
    private OmeletsListFragment mFragment;
    @NonNull
    private OmeletsListViewModel mModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragment = getOmeletsFragment();
        mModel = getViewModel();
        mFragment.setViewModel(mModel);
    }

    @Override
    protected Class<? extends Observable> getViewModelClass() {
        return OmeletsListViewModel.class;
    }

    @NonNull
    private OmeletsListFragment getOmeletsFragment() {
        FragmentManager manager = getSupportFragmentManager();
        OmeletsListFragment fragment = (OmeletsListFragment) manager.findFragmentById(getFragmentsViewHolderId());
        if (fragment == null) {
            fragment = OmeletsListFragment.newInstance();
            addFragment(fragment);
        }
        return fragment;
    }
}
